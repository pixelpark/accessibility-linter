const { factory } = require("../src/index");

test('AccessibilityLinterPluginFactory has create() function', () => {
    expect(factory.create).toBeDefined();
});

test('AccessibilityLinterPluginFactory only has one function (next to constructor)', () => {
    expect(Object.getOwnPropertyNames(Object.getPrototypeOf(factory)).length).toBe(2);
});

test('AccessibilityLinterPluginFactory.create() returns Object containing "languagePlugin" property', () => {
    expect(typeof factory.create()).toBe('object');
    expect(factory.create().languagePlugin).toBeDefined();
});

test('AccessibilityLinterPlugin implements onMessage function', () => {
    expect(factory.create().languagePlugin.onMessage).toBeDefined();
});
