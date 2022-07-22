const axe = require("axe-core");
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
