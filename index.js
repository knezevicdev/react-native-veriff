
import { NativeEventEmitter, NativeModules } from 'react-native';

class RNVeriff {
    constructor() {
        const RNVeriffCoreModule = NativeModules.RNVeriff;

        if (!RNVeriffCoreModule) {
            throw new Error("Missing veriff core module.");
        }

        this.coreModule = RNVeriffCoreModule;
        this.eventEmmiter = new NativeEventEmitter(this.coreModule);
    }

    initialize(sessionToken, sessionUrl) {
        return this.coreModule.initialize(sessionToken, sessionUrl);
    }

    startAuthentication() {
        this.coreModule.startAuthentication();
    }

    setColorSchema(colorSchema) {
        this.coreModule.setColorSchema(colorSchema);
    }

    addEventListener(event, handler) {
        return this.eventEmmiter.addListener(event, handler);
    }
}

const rnVeriff = new RNVeriff();

export default rnVeriff;
