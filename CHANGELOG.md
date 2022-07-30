<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# accessibility-linter Changelog

## [Unreleased]
### Added
- class to collect performance measurements

### Changed
- JavaScript: split into more functions

### Fixed
- JavaScript: highlighting for annotations that end on a new line

## [0.2.1]
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

## [0.2.0]
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

## [0.1.1]
### Added
- Initial creation of a linting toolchain
- Preparation for initial release to the plugin store
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)