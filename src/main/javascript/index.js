const axe = require('axe-core');
const { JSDOM } = require('jsdom');

class AccessibilityLinterPlugin {
    onMessage(p, messageWriter) {
        const request = JSON.parse(p);
        const response = {request_seq: request.seq, error: 'something went wrong'}
        if (typeof request.arguments.input === 'undefined') {
            response.error = 'no input given'
            messageWriter.write(JSON.stringify(response));
            return
        }
        try {
            const dom = new JSDOM(request.arguments.input)
            axe.run(dom.window.document.documentElement, { reporter: "raw" })
                .then(results => {
                    const data = []
                    for (const rule of results) {
                        for (const violation of rule.violations) {
                            const entry = {}
                            entry.type = rule.id
                            entry.help = rule.help
                            entry.helpUrl = rule.helpUrl
                            entry.node = violation.node
                            data.push(entry)
                        }
                    }
                    messageWriter.write(JSON.stringify({request_seq: request.seq, result: data}));
                })
                .catch(e => {
                    messageWriter.write(JSON.stringify({request_seq: request.seq, error: e}));
                })
        } catch (e) {
            response.error = e
            messageWriter.write(JSON.stringify(response));
        }
    }
}

class AccessibilityLinterPluginFactory {
    create() {
        return {languagePlugin: new AccessibilityLinterPlugin()}
    }
}

module.exports = {
    factory: new AccessibilityLinterPluginFactory()
};
