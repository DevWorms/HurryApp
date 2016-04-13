//
//  AppDelegate.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKCoreKit
import FBSDKLoginKit
import Darwin

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, UIAlertViewDelegate {

    var window: UIWindow?

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override point for customization after application launch.
        
        PayPalMobile.initializeWithClientIdsForEnvironments([PayPalEnvironmentProduction: "abc", PayPalEnvironmentSandbox: "xyz"])
        
        FBSDKApplicationDelegate.sharedInstance().application(application, didFinishLaunchingWithOptions: launchOptions)
        
        
        if Accesibilidad.isConnectedToNetwork() == true {
            print("Internet connection OK")
            
            //FBSDKAccessToken.currentAccessToken() == nil 
            if ( NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")! == "" ){
                print("Not logged in..")
                if FBSDKProfile.currentProfile() != nil {
                    FBSDKLoginManager().logOut()
                }
                
            }else{
                print("Logged in..")
                //print("current key: \( NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")!)")
                
                let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
                let vc = storyboard.instantiateViewControllerWithIdentifier("Principal") as! UITabBarController
                vc.selectedIndex = 0 //optional
                
                self.window?.rootViewController = vc
                
                /*
                let vc = storyboard.instantiateViewControllerWithIdentifier("someViewController") as! UIViewController
                self.presentViewController(vc, animated: true, completion: nil)
                */
            }
            
        } else {
            let alert = UIAlertView(title: "Sin conexón a internet", message: "Asegurate de estar conectado a internet.", delegate: self, cancelButtonTitle: "OK")
            alert.show()
        }
        
        return true
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
       
        if buttonIndex == 0 {
            exit(0)
        }
    }
    
    //Open app from another app
    func application(application: UIApplication, openURL url: NSURL, sourceApplication: String?, annotation: AnyObject) -> Bool {
        
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
                
                // utiliza ? por si viene en nil
                let butonNameDoc = self.window?.viewWithTag(50) as? UIButton
                
                // for fill title´s button when applicationWillEnterForeground and ComprarViewController is initialized
                if butonNameDoc != nil {
                    butonNameDoc!.setTitle(MyFile.Name , forState: .Normal)
                    print("nameDoc Foreground")
                }else {
                    print("nameDoc nil")
                }

                
            } catch {
                print("Error: \(error)")
                let alert = UIAlertView(title: "Ocurrió algo", message: "No pudimos traer tu archivo.", delegate: self, cancelButtonTitle: "OK")
                alert.show()
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

