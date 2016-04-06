//
//  HTTPPostToPHP.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 17/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import Foundation

class ConnectionHurryPrint {
    
    func postToPHP(data: NSData, fileName: String, boundary: String) -> NSMutableData {
        
        // Set Content-Type in HTTP header.
        // http://www.sitepoint.com/web-foundations/mime-types-complete-list/
        let mimeType = "application/pdf"
        let fieldName = "documento" // $_FILES
        
        let requestBodyData = NSMutableData()
        
        requestBodyData.appendString("--\(boundary)\r\n")
        requestBodyData.appendString("Content-Disposition: form-data; name=\"\( "numero" )\"\r\n\r\n")
        requestBodyData.appendString("\( true )\r\n")  // numero puede ser string o integer
        
        requestBodyData.appendString("--\(boundary)\r\n")
        requestBodyData.appendString("Content-Disposition: form-data; name=\"\(fieldName)\"; filename=" + (fileName) + "\r\n")
        requestBodyData.appendString("Content-Type: \(mimeType)\r\n\r\n")
        requestBodyData.appendData( data )
        requestBodyData.appendString("\r\n")
        requestBodyData.appendString("--\(boundary)--\r\n")
        
        return requestBodyData
    }
    
    //MARK: - Completion Handler
    
    func connectionRestApi(url: String, type: String, headers: [String: String]?, parameters: [String: String]?, completion: (resultData: NSData) -> Void) {
        
        if Accesibilidad.isConnectedToNetwork() == true {
            print("Internet connection OK")
            
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = type            
            request.allHTTPHeaderFields = headers
            
            
            if type == "GET" {
                request.addValue( NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")! , forHTTPHeaderField: "Apikey")
                
            } else{ // "POST"
            
                do {
                    let postData = try NSJSONSerialization.dataWithJSONObject(parameters!, options: .PrettyPrinted)
                    
                    request.HTTPBody = postData
                    
                } catch {
                    print("error serializing JSON: \(error)")
                }
            }
            
            let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
                data, response, error in
                
                if error != nil {
                    print("error Sucursal =\(error)")
                    //return
                } else {
                    
                    let responseString = NSString(data: data!, encoding: NSUTF8StringEncoding)
                    print("responseString = \(responseString)")
                    
                    completion(resultData: data!)
                    
                }
                
            }
            task.resume()
            
        } else {
            let alert = UIAlertView(title: "Sin conexón a internet", message: "Asegurate de estar conectado a internet.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
        }
    }
    
}

extension NSMutableData {
    
    func appendString(string: String) {
        let data = string.dataUsingEncoding(NSUTF8StringEncoding, allowLossyConversion: true)
        appendData(data!)
    }
}
