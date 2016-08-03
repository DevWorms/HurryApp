//
//  File.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 21/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
 
struct MyFile {
    static var url: NSURL = NSURL(fileURLWithPath:" ")
    
    static var Path: String {
        get {
            var pathDoc = ""
            
            if self.url.path != "/ " {
                pathDoc = self.url.path!
            }
            print("Path: " + pathDoc)
            
            return pathDoc
        }
    }
    
    static var Name: String {
        get {
            var nameDoc = ""
            
            if self.url.lastPathComponent != " " {
                nameDoc = self.url.lastPathComponent!.stringByReplacingOccurrencesOfString(" ", withString: "_")
            }
            print("Name: " + nameDoc)

            return nameDoc
        }
    }
}
