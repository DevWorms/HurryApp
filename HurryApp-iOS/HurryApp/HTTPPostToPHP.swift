//
//  HTTPPostToPHP.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 17/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import Foundation

class HTTPPostToPHP {
    
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
    
    
}

extension NSMutableData {
    
    func appendString(string: String) {
        let data = string.dataUsingEncoding(NSUTF8StringEncoding, allowLossyConversion: true)
        appendData(data!)
    }
}
