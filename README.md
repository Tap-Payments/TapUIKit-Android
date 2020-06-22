# TapUIKit-Android
Kit for all tap UI atoms - molecule - organisms and templates

TapUIKit-Android is based on Atomic design . It is a methodology composed of five distinct stages working together to create interface design systems in a more deliberate and hierarchical manner. The five stages of atomic design are:
Atoms
Molecules
Organisms
Templates
Pages

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/Tap-Payments/TapQRCode-Android.git)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-21-blue.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-29-informational.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)

## Requirements

To use the SDK the following requirements must be met:

1. **Android Studio 3.6** or newer
2. **Android SDK Tools 29.0.0 ** or newer
3. **Android Platform Version: API 29: Android 10.0 (Q)
4. **Android targetSdkVersion: 29

# Installation
---
<a name="include_library_to_code_locally"></a>
### Include TapUIKit-Android library as a dependency module in your project
---
1. Clone TapUIKit-Android library from Tap repository
   ```
       git@github.com:Tap-Payments/TapUIKit-Android.git
    ```
2. Add TapUIKit-Android library to your project settings.gradle file as following
    ```java
        include ':tapuilibrary', ':YourAppName'
    ```
3. Setup your project to include TapUIKit-Android as a dependency Module.
   1. File -> Project Structure -> Modules -> << your project name >>
   2. Dependencies -> click on **+** icon in the screen bottom -> add Module Dependency
   3. select qrcode library

<a name="installation_with_jitpack"></a>
### Installation with JitPack
---
[JitPack](https://jitpack.io/) is a novel package repository for JVM and Android projects. It builds Git projects on demand and provides you with ready-to-use artifacts (jar, aar).

To integrate TapUIKit-Android into your project add it in your **root** `build.gradle` at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```java
	dependencies {
      implementation 'com.github.Tap-Payments:TapUIKit-Android:0.0.6.8'
	}
```
## Features

------

`TapUIKit-Android` provides extensive ways for using the Tap UI views as atoms , molecules etc and are easy to
integrate with.

### Atoms
If atoms are the basic building blocks of matter, then the atoms of our interfaces serve as the foundational building blocks that comprise all our user interfaces. These atoms include basic elements like textviews, inputlayouts, buttons, and others that can’t be broken down any further without ceasing to be functional.

 Atom Name    |                  Usage                       | view|
------------- | -------------------------------------------- | ----|
TapTextView   |  <company.tap.tapuilibrary.atoms.TapTextView |     |
TapSwitch     | <company.tap.tapuilibrary.atoms.TapSwitch    |     |
TapImageView   | <company.tap.tapuilibrary.atoms.TapImageView |     |
TapButton     | <company.tap.tapuilibrary.atoms.TapButton |     |
TapEditText   | <company.tap.tapuilibrary.atoms.TapEditText |     |
TapSeparatorView   | <company.tap.tapuilibrary.atoms.TapSeparatorView |     |
TapDragIndicator   | <company.tap.tapuilibrary.atoms.TapDragIndicator |     |

### Molecules
Molecules are relatively simple groups of UI elements functioning together as a unit. For example, a form label, search  input, and button can join together to create a search form molecule.

Molecule Name    |                  Usage                       | view|
------------- | -------------------------------------------- | ----|
TapHeader   |  <company.tap.tapuilibrary.views.TapHeader |     |
TapItemsView     | <company.tap.tapuilibrary.views.TapItemsView    |     |
TapAmountSectionView |  <company.tap.tapuilibrary.views.TapAmountSectionView |     |
TapSelectionTabLayout| <company.tap.tapuilibrary.views.TapSelectionTabLayout|      |