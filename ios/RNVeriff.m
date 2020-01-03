
#import "RNVeriff.h"
#import "RNVeriff-Swift.h"

@implementation RNVeriff

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"onSession"];
}

RCT_REMAP_METHOD(initialize, sessionToken:(NSString *)sessionToken sessionUrl:(NSString *) sessionUrl options:(NSDictionary *)options initializeWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    @try {
        NSDictionary *rawImage = [options valueForKey:@"navigationBarImage"];
        UIImage *navigationBarImage = [RCTConvert UIImage: rawImage];
        NSString *themeColor = [options valueForKey:@"themeColor"];
        
        [self initVeriffWithSessionToken:sessionToken sessionUrl:sessionUrl themeColor:themeColor navigationbarImage:navigationBarImage];
        return resolve(@"Successfully initialized.");
    } @catch (NSException *exception) {
        NSMutableDictionary * info = [NSMutableDictionary dictionary];
        [info setValue:exception.name forKey:@"ExceptionName"];
        [info setValue:exception.reason forKey:@"ExceptionReason"];
        [info setValue:exception.callStackReturnAddresses forKey:@"ExceptionCallStackReturnAddresses"];
        [info setValue:exception.callStackSymbols forKey:@"ExceptionCallStackSymbols"];
        [info setValue:exception.userInfo forKey:@"ExceptionUserInfo"];
        
        NSError *error = [[NSError alloc] initWithDomain:@"veriff" code:3 userInfo:info];
        return reject(@"failed_init", exception.reason, error);
    }
}

RCT_EXPORT_METHOD(startAuthentication)
{
    [self startAuthentication];
}

@end
