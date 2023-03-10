<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# accessibility-linter Changelog

## Unreleased

- ci: replace deprecated `npm install` flag

## 0.2.4 - 2023-03-10

### Changed
- update Gradle IntelliJ Plugin
- replace deprecated function `createAnnotation`
- remove `MyProjectManagerListener` due to deprecation of `projectOpened`
- ci: disable Qodana

## 0.2.3 - 2023-02-11

### Changed
- meta: IntelliJ versions (#2)
- javascript: update dependencies
- meta: Java 17

## 0.2.2 - 2022-10-15

### Added
- class to collect performance measurements
- VueAnnotator: filter out colon syntax
- more Kotlin tests

### Changed
- meta: IntelliJ versions
- JavaScript: split into more functions

### Fixed
- JavaScript: highlighting for annotations that end on a new line

## 0.2.1 - 2022-07-28

### Added
- Support for `vue` files
- tags from axe-linter.yml are now respected
- JavaScript: function tests init + communication tests improved
- Readme: instructions for config file
- Readme: development + deployment steps

### Changed
- Annotator structure (abstract class)
- ConfigAxe: properties are optional
- ConfigAxe: now ignores undefined properties

### Fixed
- Handlebars: filter out all handlebars syntax
- AnnotatorBase: potentially fix path issues for the config files for windows

## 0.2.0 - 2022-07-23

### Added
- Unit tests for JavaScript and Kotlin functions
- Support for `handlebars` files

### Changed
- Targeting the newest releases
- Cleanup readme
- HtmlAnnotator: recognise multiple occasions of the same violation snippet
- Improve locating the violations
- Adjust build script (note: before publishing run `npm install --only=production` to get rid of dev dependencies)

### Fixed
- replacing deprecated functions

## 0.1.1 - 2022-07-07

### Added
- Initial creation of a linting toolchain
- Preparation for initial release to the plugin store
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
