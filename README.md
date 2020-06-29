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

# Table of Contents
---

1. [Requirements](#requirements)
2. [Installation](#installation)
   1. [Include TapUIKit-Android library as a dependency module in your project](#include_library_to_code_locally)
   2. [Installation with jitpack](#installation_with_jitpack)

<a name="requirements"></a>
## Requirements

To use the SDK the following requirements must be met:

1. **Android Studio 3.6** or newer
2. **Android SDK Tools 29.0.0 ** or newer
3. **Android Platform Version: API 29: Android 10.0 (Q)
4. **Android targetSdkVersion: 29

<a name="installation"></a>
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
   3. select tapuilibrary library

<a name="installation_with_jitpack"></a>
### Installation with JitPack
---
[JitPack](https://jitpack.io/) is a novel package repository for JVM and Android projects. It builds Git projects on demand and provides you with ready-to-use artifacts (jar, aar).

To integrate TapUIKit-Android into your project add it in your **root** `build.gradle` at the end of repositories:
```groovy
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


 ![Atoms](https://github.com/Tap-Payments/TapUIKit-Android/blob/master/images/atom.png)

 Atom Name    |                  Usage                       | configuration|
------------- | -------------------------------------------- | ----|
TapTextView   |  <company.tap.tapuilibrary.atoms.TapTextView/> |     |
TapSwitch     | <company.tap.tapuilibrary.atoms.TapSwitch />   |     |
TapImageView   | <company.tap.tapuilibrary.atoms.TapImageView />|     |
TapButton     | <company.tap.tapuilibrary.atoms.TapButton/> |     |
TapEditText   | <company.tap.tapuilibrary.atoms.TapEditText />|     |
TapSeparatorView   | <company.tap.tapuilibrary.atoms.TapSeparatorView />|     |
TapDragIndicator   | <company.tap.tapuilibrary.atoms.TapDragIndicator/> |     |
TapChip            |<company.tap.tapuilibrary.atoms.TapChip/>|              |
TapChipGroup|<company.tap.tapuilibrary.atoms.TapChipGroup />|                |

### Molecules
Molecules are relatively simple groups of UI elements functioning together as a unit. For example, a form label, search  input, and button can join together to create a search form molecule.


 ![Molecules](https://github.com/Tap-Payments/TapUIKit-Android/blob/master/images/molecules.png)
Molecule Name    |                  Usage                       | configuration|
------------- | -------------------------------------------- | ----|
TapHeader   |  <company.tap.tapuilibrary.views.TapHeader |     |
TapItemsView     | <company.tap.tapuilibrary.views.TapItemsView    |     |
TapAmountSectionView |  <company.tap.tapuilibrary.views.TapAmountSectionView |     |
TapSelectionTabLayout| <company.tap.tapuilibrary.views.TapSelectionTabLayout|      |
TapBottomSheetDialog| <company.tap.tapuilibrary.views.TapBottomSheetDialog|      |

## Usage

1. TapTextView

 1.a. Enable any View extending TextView in code:
 ```
 AnyTextView : TapTextView()
 ```
 2.b. Enable any View extending TextView in XML:
 ```xml
  <company.tap.tapuilibrary.atoms.TapTextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Welcome to Tap UI Kit"
        android:gravity="center"
        android:textSize="30sp"/>
```
2. TapSwitch
 Enable any View extending TapSwitch in XML:
 ```xml
  <company.tap.tapuilibrary.atoms.TapSwitch
        android:id="@+id/switch_discount"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:text="Apply Discount"
        android:layout_marginEnd="7dp"
        android:layout_marginStart="7dp"/>
```
3. TapImageView

Enable any View extending TapImageView in XML:
```xml
<company.tap.tapuilibrary.atoms.TapImageView
        android:id="@+id/imageView_master"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/mastercard"
        android:scaleType="centerCrop"
        android:visibility="gone"
        android:layout_gravity="center"
        android:layout_marginStart="@dimen/margin_basic_8dp"
        android:layout_marginEnd="@dimen/margin_basic_8dp"/>
```
4.TapButton

4.a. Enable any View extending TapButton in code:
```
AnyButton:TapButton()
```
 4.b.Enable any View extending TapButton in XML:
 ```xml
 <company.tap.tapuilibrary.atoms.TapButton
        android:id="@+id/textView_itemcount"
        android:layout_width="70dp"
        android:layout_height="@dimen/margin_basic_20dp"
        android:fontFamily="sans-serif-light"
        android:gravity="center"
        android:textColor="#4b4847"
        android:textSize="9sp"
        android:textStyle="normal"
        tools:text="10 ITEMS"
        android:background="@drawable/rounded_rectangle"
        android:layout_marginTop="@dimen/margin_basic_19dp"
        android:layout_marginBottom="@dimen/margin_basic_19dp"
        android:layout_marginEnd="21dp"
        android:layout_marginStart="283dp"
        android:elevation="@dimen/margin_basic_0dp"
       />
 ```
 5. TapSeparatorView

Enable any View extending TapSeparatorView in XML:
```xml
<company.tap.tapuilibrary.atoms.TapSeparatorView
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_below="@+id/textView_itemcount"
        android:layout_marginTop="@dimen/margin_basic_9dp"
        android:background="#dfdfdf"
        />
```
6. TapDragIndicator

Enable any View extending TapDragIndicator in XML:
```
 <company.tap.tapuilibrary.atoms.TapDragIndicator
        android:layout_width="75dp"
        android:layout_height="4dp"
        android:layout_marginEnd="150dp"
        android:layout_marginStart="150dp"
        app:cardCornerRadius="@dimen/margin_basic_8dp"
        android:layout_marginTop="@dimen/margin_basic_10dp"
        app:cardElevation="@dimen/margin_basic_0dp"
        />
```
7. TapChip

Enable any View extending TapChip in XML:
```xml
 <company.tap.tapuilibrary.atoms.TapChip
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:layout_marginStart="@dimen/margin_basic_16dp"
            android:layout_marginBottom="@dimen/margin_basic_13dp"
            app:cardCornerRadius="8dp"
            app:cardElevation="@dimen/margin_basic_0dp"
          />
 ```
 8.TapChipGroup

 Enable any View extending TapChipGroup in XML:
 ```xml
 <company.tap.tapuilibrary.atoms.TapChipGroup
        android:id="@+id/currencyLayout1"
        android:layout_width="match_parent"
        android:layout_height="75dp"
        android:background="@color/chip_background"
        android:orientation="horizontal">

    </company.tap.tapuilibrary.atoms.TapChipGroup>
```
9. TapHeader Molecule

 Enable any View extending TapHeader in XML:
 ```xml
   <company.tap.tapuilibrary.views.TapHeader
        android:layout_marginTop="@dimen/margin_basic_9dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```
10. TapItemsView Molecule

 Enable any View extending TapItemsView in XML:
 ```xml
 <company.tap.tapuilibrary.views.TapItemsView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
11. TapAmountSectionView Molecule

Enable any View extending TapAmountSectionView in XML:
```
  <company.tap.tapuilibrary.views.TapAmountSectionView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
12. TapSelectionTabLayout Molecule

Enable any View extending TapSelectionTabLayout in XML:
```xml
  <company.tap.tapuilibrary.views.TapSelectionTabLayout
        android:id="@+id/sections_tablayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
13.TapBottomSheetDialog Molecule

 13.a. Enable any View extending TapBottomSheetDialog in code:
 ```
open class BottomSheetDialog : TapBottomSheetDialog()
 ```
 13.b. Enable any View extending TextView in XML:
 ```xml
 <company.tap.tapuilibrary.views.TapBottomSheetDialog
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
       />
```