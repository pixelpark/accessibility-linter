const { factory } = require('../src/index');

const languagePlugin = factory.create().languagePlugin;

class AnswerHandler {
    constructor(observer = ()=>{}) {
        this.observer = observer
    }
    write(msg) {
        this.message = JSON.parse(msg);
        this.observer()
    }
    toString() {
        JSON.stringify(JSON.parse(this.message), null, 2)
    }
}

test('Missing Input', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.error).toBe('no input given');
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: {} }), ah);
});

test('Missing Config', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.error).toBe('no config given');
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '' } }), ah);
});

test('Missing rules from Config', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.error).toBeUndefined();
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '<blink></blink>', config: {} } }), ah);
});

test('Should have no error', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.error).toBeUndefined();
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '<blink></blink>', config: { rules: {} } } }), ah);
});

test('Should have one violation', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.result.length).toBe(1);
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '<blink></blink>', config: { rules: {} } } }), ah);
});

test('Should have zero violations (rule)', done => {
    const ah = new AnswerHandler(() => {
        expect(ah.message.result.length).toBe(0);
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '<blink></blink>', config: { rules: { blink: false } } } }), ah);
});

test('Should have zero violations (tag)', done => {
    const ah = new AnswerHandler(() => {
        console.log(ah.message)
        expect(ah.message.result.length).toBe(0);
        done();
    });
    languagePlugin.onMessage(JSON.stringify({ seq: 1, arguments: { input: '<blink></blink>', config: { tags: ['wcag2aa'] } } }), ah);
});
