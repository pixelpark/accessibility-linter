<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# accessibility-linter Changelog

## Unreleased

## 0.2.11 - 2024-01-22

### Changed
- update Gradle IntelliJ Plugin to 1.16.1
- update Gradle Version to 8.5

## 0.2.10 - 2023-12-11

### Changed
- meta: IntelliJ versions

## 0.2.9 - 2023-11-08

### Changed
- javascript: update dependencies
- javascript: update to axe-core 4.8.2

## 0.2.8 - 2023-11-07

### Changed
- meta: IntelliJ update for 232 versions

## 0.2.7 - 2023-07-05

### Changed
- javascript: update dependencies

## 0.2.6 - 2023-06-28

### Changed
- meta: change vendor information

## 0.2.5 - 2023-03-31

### Changed
- meta: IntelliJ versions
- ci: replace deprecated `npm install` flag
- ci: update pipelines from template repo
- ci: update `setup-node` action

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
