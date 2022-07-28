const { factory } = require("../src/index");

test('getAvailableTags() returns filled array', () => {
    expect(factory.create().languagePlugin.getAvailableTags().length > 0).toBe(true);
});

test('getAvailableTags() contains "wcag2a", "wcag2aa", "wcag21a" and "wcag21aa"', () => {
    expect(factory.create().languagePlugin.getAvailableTags().includes('wcag2a')).toBe(true);
    expect(factory.create().languagePlugin.getAvailableTags().includes('wcag2aa')).toBe(true);
    expect(factory.create().languagePlugin.getAvailableTags().includes('wcag21a')).toBe(true);
    expect(factory.create().languagePlugin.getAvailableTags().includes('wcag21aa')).toBe(true);
});

test('prepareTags(tags) with undefined parameter', () => {
    expect(factory.create().languagePlugin.prepareTags()).toBeUndefined();
});

test('prepareTags(tags) with empty array parameter', () => {
    expect(factory.create().languagePlugin.prepareTags([])).toBeUndefined();
});

test('prepareTags(tags) with only invalid items in array parameter', () => {
    expect(factory.create().languagePlugin.prepareTags(['abc', 'efg'])).toBeUndefined();
});

test('prepareTags(tags) with some invalid items in array parameter', () => {
    expect(factory.create().languagePlugin.prepareTags(['abc', 'efg', 'wcag2a']).values[0]).toBe('wcag2a');
    expect(factory.create().languagePlugin.prepareTags(['abc', 'efg', 'wcag2a']).values.length).toBe(1);
});

test('prepareOptions(config) with invalid tags', () => {
    expect(factory.create().languagePlugin.prepareOptions({ tags: ['xxx'] }).runOnly).toBeUndefined();
});

test('prepareOptions(config) with valid tags (defined)', () => {
    const runOnly = factory.create().languagePlugin.prepareOptions({ tags: ['wcag2aa'] }).runOnly
    expect(runOnly).toBeDefined();
});

test('prepareOptions(config) with valid tags (values)', () => {
    const runOnly = factory.create().languagePlugin.prepareOptions({ tags: ['wcag2aa'] }).runOnly
    expect(runOnly.values).toBeDefined();
});

test('prepareOptions(config) with valid tags (values[0])', () => {
    const runOnly = factory.create().languagePlugin.prepareOptions({ tags: ['wcag2aa'] }).runOnly
    expect(runOnly.values[0]).toBe('wcag2aa');
});
