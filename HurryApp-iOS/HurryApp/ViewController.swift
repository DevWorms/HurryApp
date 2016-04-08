//
//  ViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class ViewController: UIViewController, FBSDKLoginButtonDelegate {
    
    @IBOutlet weak var telefonoTxt: UITextField!
    @IBOutlet weak var contrasenaTxt: UITextField!
    
    var hurryPrintMethods = ConnectionHurryPrint()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let loginButton = FBSDKLoginButton()
        loginButton.center.x = self.view.center.x
        loginButton.center.y = self.view.center.y + 20.0
        self.view.addSubview(loginButton)
        
        loginButton.delegate = self //important!
        FBSDKProfile.enableUpdatesOnAccessTokenChange(true)
        
    }

    @IBAction func loginHurry(sender: AnyObject) {
        
        if self.contrasenaTxt.text == "" || self.telefonoTxt.text == "" {
            
            let alert = UIAlertView(title: "Error en campos", message: "Asegurate de escribir correctamente en los campos.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
            
            return
        }
        
        let headers = [
            "content-type": "application/json",
        ]
        
        let parameters = [
            "contrasena": self.contrasenaTxt.text!,
            "telefono": self.telefonoTxt.text!
        ]
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/usuarios/login", type: "POST", headers: headers, parameters: parameters, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
        })
        
    }
    
    func parseJSON(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let registro = json["estado"] as? Int {
                if registro == 1 {
                    
                    let apiKey = json["usuario"]!!["APIkey"] as! String
                    
                    let defaults = NSUserDefaults.standardUserDefaults()
                    defaults.setObject(apiKey, forKey: "ApiKey")
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        self.performSegueWithIdentifier("PagoSegue", sender: nil)
                    })
                    
                } else if registro == 8 {
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        let alert = UIAlertView(title: "Error en Login", message: json["mensaje"] as? String, delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - FBSDKLoginButtonDelegate
    
    func loginButton(loginButton: FBSDKLoginButton!, didCompleteWithResult result: FBSDKLoginManagerLoginResult!, error: NSError!) {
        
        if error != nil {
            // Process error
        }
        else if result.isCancelled {
            // Handle cancellations
        }
        else {
            // Navigate to other view
            print("logIN")
            self.performSegueWithIdentifier("RegistroSegue", sender: nil)
            //self.performSegueWithIdentifier("InicioPagoSegue", sender: nil)
            
        }
    }
    
    func loginButtonDidLogOut(loginButton: FBSDKLoginButton!) {
        print("logOut")
    }

}

