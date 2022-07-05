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
            axe.run(dom.window.document.documentElement)
                .then(results => {
                    const data = []
                    for (const violation of results.violations) {
                        for (const node of violation.nodes) {
                            const entry = {}
                            entry.type = violation.id
                            entry.help = violation.help
                            entry.helpUrl = violation.helpUrl
                            entry.html = node.html
                            entry.all = violation
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
