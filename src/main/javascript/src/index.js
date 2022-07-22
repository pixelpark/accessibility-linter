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
        if (typeof request.arguments.config.rules === 'undefined') {
            response.error = 'rules from config missing';
            messageWriter.write(JSON.stringify(response));
            return
        }
        try {
            const input = request.arguments.input;
            const dom = new JSDOM(
                input,
                { includeNodeLocations: true }
            );
            const document = dom.window.document;
            const config = request.arguments.config;
            const rules = this.prepareRules(config.rules);
            axe.run(
                dom.window.document.documentElement,
                { reporter: "raw", rules }
            )
                .then(results => {
                    const mapping = {};
                    const data = [];
                    for (const rule of results) {
                        for (const violation of rule.violations) {
                            const type = rule.id;
                            if (typeof mapping[type] === 'undefined') {
                                mapping[type] = data.length;
                                const entry = {};
                                entry.type = type;
                                entry.help = rule.help;
                                entry.helpUrl = rule.helpUrl;
                                entry.occasions = [];
                                data.push(entry);
                            }
                            for (const selector of violation.node.selector) {
                                const element = document.querySelector(selector);
                                const location = dom.nodeLocation(element);
                                if (location !== null) {
                                    const startOffset = location.startOffset;
                                    let endOffset = location.endOffset;
                                    const startTag = location.startTag;
                                    if (location.startLine !== location.endLine) {
                                        endOffset = startTag.endOffset;
                                    }
                                    if (startTag.startLine !== startTag.endLine) {
                                        endOffset = input.indexOf('\n', startOffset) - 1;
                                    }
                                    data[mapping[type]].occasions.push({
                                        startOffset,
                                        endOffset
                                    });
                                }
                            }
                        }
                    }
                    const violationsWithOccasions = data.filter(
                        violation => violation.occasions.length > 0
                    )
                    messageWriter.write(JSON.stringify({
                        request_seq: request.seq,
                        result: violationsWithOccasions
                    }));
                })
                .catch(e => {
                    messageWriter.write(JSON.stringify({
                        request_seq: request.seq,
                        error: e
                    }));
                });
        } catch (e) {
            response.error = e;
            messageWriter.write(JSON.stringify(response));
        }
    }
    prepareRules(rules) {
        const preparedRules = {}
        const ruleKeys = Object.keys(rules);
        const axeRules = axe.getRules();
        for (const key of ruleKeys) {
            const isValidRule = axeRules
                .findIndex(value => value.ruleId === key) >= 0;
            if (isValidRule) {
                const value = rules[key];
                preparedRules[key] = { enabled: value };
            }
        }
        return preparedRules;
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
