# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

[//]: # (## [X.X.X] - YYYY-MM-DD)
[//]: # (### Added)
[//]: # (- Add new features)
[//]: # (### Changed)
[//]: # (- Add breaking changes first!)
[//]: # (- Add changes in existing functionality)
[//]: # (### Deprecated)
[//]: # (- Add soon-to-be removed features)
[//]: # (### Removed)
[//]: # (- Add now removed features)
[//]: # (### Fixed)
[//]: # (- Add any bug fixes)
[//]: # (### Security)
[//]: # (- Add vulnerabilities)

## [X.X.X] - YYYY-MM-DD
### Added
- Item details fragment was added and navigation between fragments implemented.
- Section fragment switching was added.
- Double back button tap is needed to close the app.
- App version is shown in navigation drawer menu footer.
- Navigation drawer was implemented.
- App bundle generation files were included.
- New mock server using Postman was created.

### Changed
- Data module was split into domain and data with a new shared models module.
- Android code style was updated.
- Project dependencies were updated.

### Removed
- 

### Fixed
- 

## [0.1.0] - 2018-11-28
### Added
- This change log file.
- [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin) to check for dependency updates.
- [grandcentrix GmbH Android Code Style](https://github.com/grandcentrix/AndroidCodeStyle) with minor modifications.

### Changed
- Remote Java library was converted to Android library (since shared preference may be needed to store login information).
- Git ignore file was updated with default template.
- Kotlin Gradle plugin version was updated.
- Android Gradle plugin version was updated and Android build tools version, too.
- Java compatibility upgraded to Java 1.8.
- Versions of dependencies were updated.
- Project was migrated from support library to AndroidX.
- Project tests were migrated to AndroidX.

### Removed
- Unused definitions for a presentation layer that does not exist anymore.
- Unused dependencies were cleaned.
- Unused interactor package observers, since the use cases return a reactive element and use case caller must take care of subscription handling.

### Fixed
- Android Studio and lint warnings were fixed.
- New code style was applied to sources.
- Flowable use case was exchanged for single use case, because flowables use more space since they implement backpressure.
- Last cache time was not called, so cache was always expired and never used.

## 0.0.0 - 2018-11-28
### Added
- Initial import of [Android Clean Architecture Components Boilerplate](https://github.com/bufferapp/clean-architecture-koin-boilerplate) template.