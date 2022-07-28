const { factory } = require("../src/index");

const input = `
<!--<html></html>-->
<html>
    <something></something>
    <head></head>
    <body><img
    src=""><img
    src="">
    <blink></blink>
    <a href="#yo">asd</a>
        <a href="#yo">asd</a>
        <a href="#yo" class="123">asd</a>
        <blink></blink>
    </body>
</html>
`;

const config = {
    rules: {
        "color-contrast": true,
        "valid-lang": false,
        "region": false,
        "not-a-rule": false
    },
    tags: []
};

factory.create().languagePlugin.onMessage(
    JSON.stringify({ seq: 1, arguments: { input, config } }),
    new class { write(msg) { console.log(JSON.stringify(JSON.parse(msg), null, 2)) }}
);

console.log(factory.create().languagePlugin.getAvailableTags().sort())
