# accessibility-linter

![Build](https://github.com/bucherfa/accessibility-linter/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19498.svg)](https://plugins.jetbrains.com/plugin/19498-accessibility-linter/)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19498.svg)](https://plugins.jetbrains.com/plugin/19498-accessibility-linter/)

<!-- Plugin description -->

Assistant for writing inclusive code. Based on [dequelabs/axe-core](https://github.com/dequelabs/axe-core).

Features:
- real-time code analysis
- annotations for improvements
- configuration file support to enable and disable rules
- currently supported file types: `html`, `htm`, `hbs`, `handlebars` and `vue`

Configuration File:

At the root directory of your project you can place a file called `axe-linter.yml`.
With the `rules` property you can enable or disable rules and with the `tags` property you can enable only rules with the specific tags. All other rules will be disabled, even they are enabled through the `rules` property.

[List of all available Rules including their tags](https://github.com/dequelabs/axe-core/blob/develop/doc/rule-descriptions.md)

Example:

```yaml
rules:
  html-has-lang: false # explicitly enables the rule
tags: # excludes all rules that don't have one of these tags
  - wcag2a
  - wcag21a
```

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "accessibility-linter"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/bucherfa/accessibility-linter/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
