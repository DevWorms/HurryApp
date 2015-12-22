//
//  File.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 21/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
 
struct MyFile {
    static var url: NSURL!
    
    static var Path: String {
        get {
            return self.url.path!
        }
    }
    
    static var Name: String {
        get {
            return self.url.lastPathComponent!
        }
    }
}
