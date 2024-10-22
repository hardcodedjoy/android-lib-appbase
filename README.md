# AppBase

<code>com.hardcodedjoy.appbase</code> <code>v2.0.8</code><br/>
minSdkVersion: <code>21</code><br/>
targetSdkVersion: <code>34</code><br/>

## Short description

Our base to use for building android apps faster and easier.


## Description

Library to be used as a starting point for developing android apps.
It is our independently-developed Single Activity Architecture, without fragments.
It also includes some additional utility classes and methods that are useful in many apps.


- ContentView = Base class for a new "screen" in the app;
- CvTMLL = ContentView with title bar, menu button and LinearLayout for additional content;
- CvTMSLL = same as CvTMLL, but the LinearLayout is inside a ScrollView;
- CvTSLL  = same as CvTMSLL, but the Menu button is invisible;
- CvAboutBase = "about" screen;
- CvSettingsBase = "settings" screen.

In ContentView, set the layout by calling inflate(int resId) in the constructor.

In CvT...:
- add content -> add it to LinearLayout (id = "appbase_ll_content");
- change title -> setTitle(...);
- change title icon -> setTitleIcon(...);

Menu button opens a drop-down menu:
- "ABOUT" screen;
- "SETTINGS" screen;
- add more options:
<code>
Vector<Option> ops = new Vector<>();
ops.add(new Option(...));
...
addMenuOptions(ops, 0);
</code>

User can add other options to the menu as well.

This repo also contains an android project that is a testbed app. See its code for more details about using the library.


## Links

developer website: [https://hardcodedjoy.com](https://hardcodedjoy.com)<br/>

