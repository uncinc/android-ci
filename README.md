# android-ci
 [ ![Download](https://api.bintray.com/packages/uncinc/android-ci/UncIncAndroidCIPlugin/images/download.svg) ](https://bintray.com/uncinc/android-ci/UncIncAndroidCIPlugin/_latestVersion)
 
Helper library to automatically build [~~and publish~~](https://github.com/uncinc/android-ci/issues/1) an Android app via CI.  

The current library is an internal testing alpha release, more documentation and features will be available in the future. Feature requests are welcome, please submit a Github issue.  

## Setup
1. Include the repository, to your root build.gradle add the following contents:
```gradle
buildscript {
    repositories {
        maven {
            url  "https://releases.uncinc.nl/maven"
        }
    }
    dependencies {
        classpath 'nl.uncinc.androidci:UncIncAndroidCI:0.3'
    }
}
```
2. In your app's build.gradle add the following before the `android {` block:
```
apply plugin: 'nl.uncinc.androidci'
```
3. Replace version code, version name, and the signing config for the release build in the Android config:
```
android {
    defaultConfig {
        applicationId project.ext.androidci.getApplicationId("nl.uncinc.demo")
        versionCode project.ext.androidci.getVersionCode()
        versionName project.ext.androidci.getVersionName()
    }

    buildTypes {
        release {
            signingConfig project.ext.androidci.getSigningConfig()
        }
    }
}
```
4. Create a signingProperties file in the build context (example, this as signing.properties):
```
storePassword=keystorePassword
keyPassword=keyPassword
keyAlias=key0
storeFile=/Full/Path/To/keystore.jks
```
5. For building the app use:
```
./gradlew -Pandroidci.signingProperties=/Full/Path/To/signing.properties -Pandroidci.versionCode=2
```

# Possible properties
| Property                    | Purpose                                                                            | Default Value                                                                  |
|-----------------------------|------------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| androidci.signingProperties | Full path to the file with the signing configuration. Should be available to CI.   | android-app-signing.properties (and if it does not exist. The Debug build key) |
| androidci.versionCode       | Android versionCode, use an Integer value                                          | 1                                                                              |
| androidci.versionName       | Android versionName, should be a String                                            | Git hash of the repo                                                           |
| androidci.applicationId     | Override for application identifier, for multiple app flavours (e.g. Beta release) | Parameter given in the android defaultConfig                                   |        

# License 
MIT License

Copyright (c) 2020 Unc Inc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
