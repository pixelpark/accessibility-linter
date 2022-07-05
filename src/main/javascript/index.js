const axe = require('axe-core');
const { JSDOM } = require('jsdom');

class AccessibilityLinterPlugin {
    onMessage(p, messageWriter) {
        const request = JSON.parse(p);
        const response = { request_seq: request.seq, error: 'something went wrong' };
        if (typeof request.arguments.input === 'undefined') {
            response.error = 'no input given';
            messageWriter.write(JSON.stringify(response));
            return
        }
        if (typeof request.arguments.config === 'undefined') {
            response.error = 'no config given';
            messageWriter.write(JSON.stringify(response));
            return
        }
        try {
            const dom = new JSDOM(request.arguments.input);
            const config = request.arguments.config;
            const rules = config.rules;
            const ruleKeys = Object.keys(rules);
            const axeRules = axe.getRules();
            for (const key of ruleKeys) {
                if (axeRules.findIndex(value => value.ruleId === key) < 0) {
                    delete (rules[key]);
                } else {
                    const value = rules[key];
                    rules[key] = { enabled: value };
                }
            }
            // TODO maybe filter out the tags after receiving the result
            // TODO maybe same for rule id's?
            // TODO if both are decided on afterwards one could think about moving the logic back into Kotlin...
            axe.run(dom.window.document.documentElement, { reporter: "raw", rules })
                .then(results => {
                    const data = [];
                    for (const rule of results) {
                        for (const violation of rule.violations) {
                            const entry = {};
                            entry.type = rule.id;
                            entry.help = rule.help;
                            entry.helpUrl = rule.helpUrl;
                            entry.node = violation.node;
                            data.push(entry);
                        }
                    }
                    messageWriter.write(JSON.stringify({ request_seq: request.seq, result: data }));
                })
                .catch(e => {
                    messageWriter.write(JSON.stringify({ request_seq: request.seq, error: e }));
                });
        } catch (e) {
            response.error = e;
            messageWriter.write(JSON.stringify(response));
        }
    }
}

class AccessibilityLinterPluginFactory {
    create() {
        return { languagePlugin: new AccessibilityLinterPlugin() };
    }
}

module.exports = {
    factory: new AccessibilityLinterPluginFactory()
};
