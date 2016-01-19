//
//  Accesibilidad.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 13/01/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import Foundation
import SystemConfiguration

class Accesibilidad {
    
    class func isConnectedToNetwork() -> Bool {
        
        /*
        var zeroAddress = sockaddr_in(sin_len: 0, sin_family: 0, sin_port: 0, sin_addr: in_addr(s_addr: 0), sin_zero: (0, 0, 0, 0, 0, 0, 0, 0))
        zeroAddress.sin_len = UInt8(sizeofValue(zeroAddress))
        zeroAddress.sin_family = sa_family_t(AF_INET)
        
        let defaultRouteReachability = withUnsafePointer(&zeroAddress) {
            SCNetworkReachabilityCreateWithAddress(kCFAllocatorDefault, UnsafePointer($0))
        }
        
        var flags: SCNetworkReachabilityFlags = SCNetworkReachabilityFlags(rawValue: 0)
        if SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) == false {
            return false
        }
        
        let isReachable = flags == .Reachable
        let needsConnection = flags == .ConnectionRequired
        
        return isReachable && !needsConnection
        */
        
        let scriptUrl = NSURL(string: "https://www.google.com.mx")
        let data = NSData(contentsOfURL: scriptUrl!)
        
        if ((data) != nil){
            return true
        } else {
            return false
        }

        /*
        var Status:Bool = false
        let url = NSURL(string: "https://www.google.com.mx")
        let request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "HEAD"
        request.cachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalAndRemoteCacheData
        //request.timeoutInterval = 1.0
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            data, response, error in
            
            print(response)
            
            if let httpResponse = response as? NSHTTPURLResponse {
                if httpResponse.statusCode == 200 {
                    Status = true
                }
            }
        }
        task.resume()        

        return Status
        */
    }
}