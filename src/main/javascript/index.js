const axe = require('axe-core');
const jsdom = require('jsdom');
//const { JSDOM } = require('jsdom');

class AccessibilityLinterPlugin {
    onMessage(p, messageWriter) {
        const request = JSON.parse(p);
        const response = {request_seq: request.seq, error: 'something went wrong'}
        if (typeof request.arguments.input === 'undefined') {
            response.error = 'no input given'
            messageWriter.write(JSON.stringify(response));
            return
        }
        //messageWriter.write(JSON.stringify(response));
        try {
            const dom = new jsdom.JSDOM(request.arguments.input)
            axe.run(dom.window.document.documentElement)
                .then(results => {
                    const data = []
                    for (const violation of results.violations) {
                        for (const node of violation.nodes) {
                            const entry = {}
                            entry.type = violation.id
                            entry.help = violation.help
                            entry.html = node.html
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


        // messageWriter.write(JSON.stringify({request_seq: request.seq, result: "hello world"}));
        //or
        // const config = {maxCallTime: Infinity, concurrent: true};
        // getSize(packageInfo, config).then((pkg) => {
        //     messageWriter.write(JSON.stringify({request_seq: seq, package: pkg}))
        // }).catch((e) => {
        //     messageWriter.write(JSON.stringify({request_seq: seq, error: e}))
        // })
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
