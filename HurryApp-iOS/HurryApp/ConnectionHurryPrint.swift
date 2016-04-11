//
//  HTTPPostToPHP.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 17/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import Foundation

class ConnectionHurryPrint {
    
    func prepareBodyDataToPHP(parameters: [String: String], boundary: String) -> NSMutableData {
        
        // Set Content-Type in HTTP header.
        // http://www.sitepoint.com/web-foundations/mime-types-complete-list/
        let mimeType = "application/pdf"
        let fieldName = "documento" // $_FILES
        
        let requestBodyData = NSMutableData()
        
        for (keys, values) in parameters {
            
            requestBodyData.appendString("--\(boundary)\r\n")
            requestBodyData.appendString("Content-Disposition: form-data; name=" + keys + "\r\n\r\n")
            requestBodyData.appendString("\( values )\r\n")  // numero puede ser string o integer
            print("\(keys): \(values)")
        }
        
        requestBodyData.appendString("--\(boundary)\r\n")
        requestBodyData.appendString("Content-Disposition: form-data; name=\"\( "llave" )\"\r\n\r\n")
        requestBodyData.appendString("\( NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")! )\r\n")  // numero puede ser string o integer
        
        requestBodyData.appendString("--\(boundary)\r\n")
        requestBodyData.appendString("Content-Disposition: form-data; name=\"\(fieldName)\"; filename=" + ( MyFile.Name ) + "\r\n")
        requestBodyData.appendString("Content-Type: \(mimeType)\r\n\r\n")
        requestBodyData.appendData( NSData(contentsOfFile: MyFile.Path )! )
        requestBodyData.appendString("\r\n")
        requestBodyData.appendString("--\(boundary)--\r\n")
        
        return requestBodyData
    }
    
    func generateBoundaryString() -> String {
        return "Boundary-\(NSUUID().UUIDString)"
    }
    
    //MARK: - Completion Handler
    
    func connectionRestApi(url: String, type: String, headers: [String: String]?, parameters: [String: String]?, completion: (resultData: NSData) -> Void) {
        
        if Accesibilidad.isConnectedToNetwork() == true {
            print("Internet connection OK")
            
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.allHTTPHeaderFields = headers
            
            
            if type == "GET" {
                request.HTTPMethod = type
                
                request.addValue( NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")! , forHTTPHeaderField: "Apikey")
                
            } else if type == "POST" { // "POST1"
            
                do {
                    let postData = try NSJSONSerialization.dataWithJSONObject(parameters!, options: .PrettyPrinted)
                    
                    request.HTTPMethod = type
                    
                    request.HTTPBody = postData
                    
                } catch {
                    print("error serializing JSON: \(error)")
                }
            } else {
                
                print("entra")
                
                let boundary = generateBoundaryString()
                
                // Set Content-Type in HTTP header.
                let contentType = "multipart/form-data; boundary=" + boundary
                
                request.HTTPMethod = "POST"
                
                // Set the HTTPBody we'd like to submit
                request.HTTPBody = self.prepareBodyDataToPHP(parameters!, boundary: boundary)
                
                request.setValue(contentType, forHTTPHeaderField: "Content-Type")
                
                
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
