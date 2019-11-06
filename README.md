# react-native-veriff

## Getting started

`$ npm install react-native-veriff --save`

### Mostly automatic installation

`$ react-native link react-native-veriff`

### Manual installation


#### iOS
1. Add veriff as pod dependency:
    ```
        pod 'VeriffSDK', '~> 2.0.0', :modular_headers => true
    ```
2. Create empty swift file in order to make library work with React Native    
3. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
4. Go to `node_modules` ➜ `react-native-veriff` and add `RNVeriff.xcodeproj`
5. In XCode, in the project navigator, select your project. Add `libRNVeriff.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
6. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import co.decem.RNVeriffPackage;` to the imports at the top of the file
  - Add `new RNVeriffPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-veriff'
  	project(':react-native-veriff').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-veriff/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-veriff')
  	```
4. Add two new maven (probity, veriff) repository destination under the root build.gradle repositories tag in allprojects bracket.
   It should contain the following two maven repositories:
    ```
    allprojects {
        repositories {
            mavenLocal()
            google()
            jcenter()
            maven { url "https://cdn.veriff.me/android/" } //veriff
            maven {
                // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
                url "$rootDir/../node_modules/react-native/android"
            }
        }
    }
    ```
5. Add two dependency imports in the application build.gradle dependency list:
    ```
        implementation('com.veriff:veriff-library:2.4.8'){
            exclude group: 'com.veriff', module: 'libwebrtc'
        }
        implementation 'io.probity.sdk:collector:1.0.0'
    ```
6. Update the Manifest.xml with the new BroadcastReceiver and add a separate permission for it:
    ```
        <uses-permission android:name="${applicationId}.VERIFF_STATUS_BROADCAST_PERMISSION" />
        ...
        <application>
            ...
                <receiver
                    android:name="co.decem.VeriffStatusReceiver"
                    android:permission="${applicationId}.VERIFF_STATUS_BROADCAST_PERMISSION">
                        <intent-filter>
                            <category android:name="${applicationId}" />
                            <action android:name="veriff.info.status" />
                        </intent-filter>
                </receiver>
            ...
        </application>
    ```

## Usage

1. Import library and result constants:
    ```
        import RNVeriff, {VeriffResultCode} from 'react-native-veriff';
    ```
2. Initialize library:

    ```
        componentDidMount() {
            ...
            try {
                RNVeriff.initialize(SESSION_TOKEN, SESSION_URL);
            } catch (error) {
                Alert.alert("Error while initializing Veriff.");
            }
            ...
        }
    ```
- Setting event listener:
    ```
        this.onSubmitListener = RNVeriff.addEventListener("onSession", (result) => {
            switch(result.code) {
            case VeriffResultCode.STATUS_USER_CANCELED: 
                console.log("User canceled");
            break;
            }
        }); 
    ```
    * Don't forget to remove listener in componentWillUnmount
    ```
        componentWillUnmount() {
            ... 
            this.onSubmitListener.remove();
            ...
        }
    ```
- Authenticate:
    ```
        RNVeriff.startAuthentication();
    ```