
import { NativeEventEmitter, DeviceEventEmitter, NativeModules, Platform, Image } from 'react-native';

class RNVeriff {
    constructor() {
        const RNVeriffCoreModule = NativeModules.RNVeriff;

        if (!RNVeriffCoreModule) {
            throw new Error("Missing veriff core module.");
        }

        this.coreModule = RNVeriffCoreModule;
        this.eventEmmiter = Platform.select({
            ios: new NativeEventEmitter(this.coreModule),
            android: DeviceEventEmitter
        });
    }

    initialize(sessionToken, sessionUrl, options) {
        return this.coreModule.initialize(sessionToken, sessionUrl, {
            themeColor: options.themeColor,
            navigationBarImage: Image.resolveAssetSource(options.navigationBarImage),
        });
    }

    startAuthentication() {
        this.coreModule.startAuthentication();
    }

    addEventListener(event, handler) {
        return this.eventEmmiter.addListener(event, handler);
    }
}

const rnVeriff = new RNVeriff();

export const VeriffResultCode = Object.freeze({
    "UNABLE_TO_ACCESS_CAMERA": 0,
    "STATUS_USER_CANCELED": 1,
    "STATUS_SUBMITTED": 2,
    "STATUS_ERROR_SESSION": 3,
    "STATUS_ERROR_NETWORK": 4,
    "STATUS_ERROR_NO_IDENTIFICATION_METHODS_AVAILABLE": 5,
    "STATUS_DONE": 6,
    "STATUS_ERROR_UNKNOWN": 7,
});

export default rnVeriff;
