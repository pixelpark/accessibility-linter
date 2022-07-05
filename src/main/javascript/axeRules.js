const axe = require("axe-core");
// TODO figure out if each rule has at least one of these tags and what do with that information, regarding the axe-linter.yml
const axeRules = axe.getRules();
for (const rule of axeRules) {
    let hasRelevantTag = false;
    for (const tag of ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa', 'best-practice']) {
        hasRelevantTag = rule.tags.includes(tag) || hasRelevantTag;
    }
    if (!hasRelevantTag) {
        console.log(rule);
    }
}
