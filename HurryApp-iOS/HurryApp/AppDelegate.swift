//
//  AppDelegate.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKCoreKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override point for customization after application launch.
        
        FBSDKApplicationDelegate.sharedInstance().application(application, didFinishLaunchingWithOptions: launchOptions)
        
        if ((FBSDKAccessToken.currentAccessToken()) == nil){
            print("Not logged in..")
            
        }else{
            print("Logged in..")
            print("current: \(FBSDKAccessToken.currentAccessToken().userID)")
            
            let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard.instantiateViewControllerWithIdentifier("Principal") as! UITabBarController
            vc.selectedIndex = 0 //optional
            
            // vc = storyboard.instantiateViewControllerWithIdentifier("Registro") as! UIViewController
            
            self.window?.rootViewController = vc
            
            /*
            let storyboard = UIStoryboard(name: "MyStoryboardName", bundle: nil)
            let vc = storyboard.instantiateViewControllerWithIdentifier("someViewController") as! UIViewController
            self.presentViewController(vc, animated: true, completion: nil)
            */
            
        }
        
        return true
    }
    
    //Facebook
    func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject) -> Bool {
        
        print("openURL: \(url)" )
        print("openPath: " + url.path!)
        print("nameFile: " + url.lastPathComponent!)
        print("sourceApplication: " + sourceApplication!)
        
        var boolean: Bool = true
        
        if sourceApplication == "com.facebook.Facebook" ||      // entra a la app por FB
            (url.path == "/" &&  url.lastPathComponent == "/") {
            boolean = FBSDKApplicationDelegate.sharedInstance().application(application, openURL: url, sourceApplication: sourceApplication, annotation: annotation)
        } else {    //entra a la app abriendo otro archivo externo (ej. un .doc de mail)
        
            var fileSize : UInt64 = 0
            
            do {
                let attr : NSDictionary? = try NSFileManager.defaultManager().attributesOfItemAtPath( url.path! )
                
                if let _attr = attr {
                    fileSize = _attr.fileSize();
                    
                    print("fileSize: \(fileSize)")
                    
                }
                
                // fill myFile
                MyFile.url = url
                print( "url: \(MyFile.url)" )
                
            } catch {
                print("Error: \(error)")
            }

        }
        
        return boolean
        
    }

    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
        FBSDKAppEvents.activateApp()
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }

}

