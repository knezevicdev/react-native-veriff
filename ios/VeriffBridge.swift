//
//  VeriffSwiftBridge.swift
//  RNVeriff
//
//  Created by Nikola Knežević on 4/23/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import Veriff

extension RNVeriff: VeriffDelegate {
    public func onSession(result: VeriffResult, sessionToken: String) {
        self.sendEvent(withName: "onSession", body: [
            "result": result,
            "sessionToken": sessionToken
        ])
    }
    
    private func colorFromHex(hexString:String) -> UIColor {
        let hexString:NSString = hexString.trimmingCharacters(in: CharacterSet.whitespaces) as NSString
        let scanner = Scanner(string: hexString as String)
        
        if (hexString.hasPrefix("#")) {
            scanner.scanLocation = 1
        }
        
        var color:UInt32 = 0
        scanner.scanHexInt32(&color)
        
        let mask = 0x000000FF
        let r = Int(color >> 16) & mask
        let g = Int(color >> 8) & mask
        let b = Int(color) & mask
        
        let red   = CGFloat(r) / 255.0
        let green = CGFloat(g) / 255.0
        let blue  = CGFloat(b) / 255.0
        
        return UIColor(red:red, green:green, blue:blue, alpha:1)
    }
    
    @objc
    public func initVeriff(sessionToken: String, sessionUrl: String) {
        let conf = VeriffConfiguration(sessionToken: sessionToken, sessionUrl: sessionUrl)
        let veriff = Veriff.shared
        veriff.set(configuration: conf)
    }
    
    @objc
    public func startAuthentication() {
        let veriff = Veriff.shared
        veriff.startAuthentication()
    }
    
    @objc
    public func changeColorSchema(schema: NSDictionary) {
        if((schema.object(forKey: "controlsColor")) != nil) {
            let colorSchema = ColorSchema()
            colorSchema.controlsColor = colorFromHex(hexString: schema.value(forKey: "controlsColor") as! String)
            let veriff = Veriff.shared
            veriff.set(colorSchema: colorSchema)
        }

    }
}