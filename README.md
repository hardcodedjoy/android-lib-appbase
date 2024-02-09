# AppBase

<code>com.hardcodedjoy.appbase</code> <code>v1.3.2</code><br/>
minSdkVersion: <code>21</code><br/>
targetSdkVersion: <code>33</code><br/>

## Short description

Our base to use for building android apps faster and easier.


## Description

Library to be used as a starting point for developing android apps.
It is our independently-developed Single Activity Architecture, without fragments.
It also includes some additional utility classes and methods that are useful in many apps.


- ContentView = Base class for a new "screen" in the app;
- CvTMLL = ContentView with title bar, menu button and LinearLayout for additional content;
- CvTMSLL = same as CvTMLL, but the LinearLayout is inside a ScrollView;
- CvAboutBase = "about" screen;
- CvSettingsBase = "settings" screen.

In ContentView, set the layout by calling inflate(int resId) in the constructor.

In CvTMLL and CvTMSLL, additional content to be added by the user into the LinearLayout (id = "appbase_ll_content").
Title can be changed by setting other text to the TextView (id = "appbase_tv_title").

Menu button opens a drop-down menu with "ABOUT" and "SETTINGS" options.
User can add other options to the menu as well.

This repo also contains an android project that is a testbed app. See its code for more details about using the library.


## Links

developer website: [https://hardcodedjoy.com](https://hardcodedjoy.com)<br/>

