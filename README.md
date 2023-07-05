# accessibility-linter

![Build](https://github.com/bucherfa/accessibility-linter/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19498.svg)](https://plugins.jetbrains.com/plugin/19498-accessibility-linter/)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19498.svg)](https://plugins.jetbrains.com/plugin/19498-accessibility-linter/)

## About this plugin

<!-- Plugin description -->

Assistant for writing inclusive code. Based on [dequelabs/axe-core](https://github.com/dequelabs/axe-core).

Features:
- real-time code analysis
- annotations for improvements
- configuration file support to enable and disable rules
- currently supported file types: `html`, `htm`, `hbs`, `handlebars` and `vue`

Configuration File:

At the root directory of your project you can place a file called `axe-linter.yml`.
With the `rules` property you can enable or disable rules and with the `tags` property you can enable only rules with the specific tags. All other rules will be disabled, even when they are enabled through the `rules` property.

[List of all available rules including their tags](https://github.com/dequelabs/axe-core/blob/develop/doc/rule-descriptions.md)

Example:

```yaml
rules:
  html-has-lang: false # disables the rule
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

## Development

### Additional Steps

1. navigate to `src/main/javascript`
2. run `npm install`

To apply JavaScript changes for local debugging, run the `build > clean` gradle task. Then run the `Run Plugin` task from the run configurations like normal.

## Deployment

### Semi-Automatic

1. set the new version number in `gradle.properties`
2. update changelog
3. commit and push
4. wait for the GitHub Actions successfully finish running, if they fail: fix the errors and start over (depending on what went wrong)
5. Locate the [draft release on GitHub](https://github.com/bucherfa/accessibility-linter/releases)
6. Turn into a pre-release (This will upload the plugin to the [marketplace](https://plugins.jetbrains.com/plugin/19498-accessibility-linter) and to this pre-release; and create a [pull request](https://github.com/bucherfa/accessibility-linter/pulls) for the changelog)

once the plugin version was approved by JetBrains...

1. Merge the generated [pull request](https://github.com/bucherfa/accessibility-linter/pulls)
2. Turn the pre-release into a full release

### Manual

1. navigate to `src/main/javascript`
2. run `npm install --omit=dev`
3. set the new version number in `gradle.properties`
4. commit and push
5. run the `build > clean` gradle task
6. run the `build > build` gradle task
7. locate the plugin in `build/distributions`
8. wait for the GitHub Actions successfully finish running, if they fail: fix the errors and start over (depending on what went wrong)
9. upload the plugin to the [marketplace](https://plugins.jetbrains.com/plugin/19498-accessibility-linter) as an update with `Upload Update` (login needed)
10. upload the plugin to the generated GitHub draft from the [releases list](https://github.com/bucherfa/accessibility-linter/releases) and publish the release
11. locally run the `changelog > patchChangelog` gradle task to update the changelog, afterwards push + commit

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
