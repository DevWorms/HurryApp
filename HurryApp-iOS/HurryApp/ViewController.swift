//
//  ViewController.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class ViewController: UIViewController, UITextFieldDelegate, FBSDKLoginButtonDelegate {
    
    var hurryPrintMethods = ConnectionHurryPrint()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let swipeDown = UISwipeGestureRecognizer(target: self, action: #selector(ViewController.swipeKeyBoard(_:)))
        swipeDown.direction = UISwipeGestureRecognizerDirection.Down
        self.view.addGestureRecognizer(swipeDown)
        
        let loginButton = FBSDKLoginButton()
        loginButton.center.x = self.view.center.x
        loginButton.center.y = self.view.center.y - 20.0
        loginButton.setAttributedTitle( NSAttributedString(string: "Iniciar Sesión") , forState: .Normal)
        loginButton.delegate = self //important!
        self.view.addSubview(loginButton)
        
        let registrarButton = FBSDKLoginButton()
        registrarButton.center.x = self.view.center.x
        registrarButton.center.y = self.view.center.y + 20.0
        registrarButton.setAttributedTitle( NSAttributedString(string: "Registrarte") , forState: .Normal)
        registrarButton.delegate = self //important!
        self.view.addSubview(registrarButton)
        
        FBSDKProfile.enableUpdatesOnAccessTokenChange(true)
        
    }

    func loginHurry() {
        
        let headers = [
            "content-type": "application/json",
        ]
        
        let parameters = [
            "token": FBSDKAccessToken.currentAccessToken().userID!
        ]
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/usuarios/login", type: "POST", headers: headers, parameters: parameters, completion: { (resultData) -> Void in
            
            self.parseJSONlogin( resultData )
            
        })
    }
    
    func parseJSONlogin(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let registro = json["estado"] as? Int {
                if registro == 1 {
                    
                    let apiKey = json["usuario"]!!["APIkey"] as! String
                    let nombreUsuario = json["usuario"]!!["Nombre"] as! String
                    let tokenFBid = json["usuario"]!!["Token"] as! String
                    
                    let defaults = NSUserDefaults.standardUserDefaults()
                    defaults.setObject(apiKey, forKey: "ApiKey")
                    defaults.setObject(nombreUsuario, forKey: "NombreUsuario")
                    defaults.setObject(tokenFBid, forKey: "TokenFB")
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        self.performSegueWithIdentifier("PrincipalSegue", sender: nil)
                    })
                    
                } else {
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        let alert = UIAlertView(title: "Error en Login", message: json["mensaje"] as? String, delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        if FBSDKProfile.currentProfile() != nil {
                            FBSDKLoginManager().logOut()
                        }
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    func alertRegistro(email: String) {
        
        let mesage = "Confirma tu celular:" + "\n" +
                    "Recuerda que con este número podrás hacer recargas y acceder a beneficios HurryPrint"
        
        let alertController = UIAlertController(title: FBSDKProfile.currentProfile().name, message: mesage, preferredStyle: .Alert)
        
        let confirmAction = UIAlertAction(title: "Confirmar", style: .Default) { (_) in
            if let field = alertController.textFields![0] as? UITextField {
                
                if !self.validate( field.text ) {
                    let alert = UIAlertView(title: "Error en teléfono", message: "Asegúrate de escribir correctamente tu teléfono.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    if FBSDKProfile.currentProfile() != nil {
                        FBSDKLoginManager().logOut()
                    }
                    
                    return
                }
                
                self.registrarseHurry(email, telefono: field.text!)
            
            } else {
                // user did not fill field
            }
        }
        
        let cancelAction = UIAlertAction(title: "Cancelar", style: .Cancel) {
            UIAlertAction in
            
            if FBSDKProfile.currentProfile() != nil {
                FBSDKLoginManager().logOut()
            }
        }
        
        alertController.addTextFieldWithConfigurationHandler { (textField) in
            textField.placeholder = "5510203040"
            textField.keyboardType = .NumberPad
            textField.textAlignment = .Center
        }
        
        alertController.addAction(confirmAction)
        alertController.addAction(cancelAction)
        
        self.presentViewController(alertController, animated: true, completion: nil)
    }
    
    func registrarseHurry(email: String, telefono: String) {
        
        
        
        let headers = [
            "content-type": "application/json",
            ]
        
        let parameters = [
            "nombre": FBSDKProfile.currentProfile().name!,
            "mail": email,
            "telefono": telefono,
            "token": FBSDKAccessToken.currentAccessToken().userID!
            ]
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/usuarios/registro", type: "POST", headers: headers, parameters: parameters, completion: { (resultData) -> Void in
            
            self.parseJSONregistro( resultData )
            
        })
    }
    
    func parseJSONregistro(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let registro = json["estado"] as? Int {
                if registro == 1 {
                    
                    let apiKey = json["APIkey"] as! String
                    //saber si es la primera vez o no
                    let defaults = NSUserDefaults.standardUserDefaults()
                    defaults.setObject(apiKey, forKey: "ApiKey")
                    defaults.setObject(FBSDKProfile.currentProfile().name!, forKey: "NombreUsuario")
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        let alert = UIAlertView(title: "", message: "Te regalamos SALDO para tus primeras impresiones, después podrás recargar en las sucursales o por PayPal*.", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        self.performSegueWithIdentifier("PrincipalSegue", sender: nil)
                    })
                    
                } else if registro == 3 {
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        let alert = UIAlertView(title: "Usuario ya registrado", message: "Ya te registraste anteriormente.", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        if FBSDKProfile.currentProfile() != nil {
                            FBSDKLoginManager().logOut()
                        }
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    func validate(value: String?) -> Bool {
        //si es nil doThis si no doThat
        //value == nil ? doThis(): doThat()
                
        let PHONE_REGEX = "^55\\d{8}"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
        let result =  phoneTest.evaluateWithObject(value)
        
        return result
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    func swipeKeyBoard(sender:AnyObject) {
        //Baja el textField
        self.view.endEditing(true)
    }
    
    
    // MARK: - FBSDKLoginButtonDelegate
    
    func loginButton(loginButton: FBSDKLoginButton!, didCompleteWithResult result: FBSDKLoginManagerLoginResult!, error: NSError!) {
        
        if error != nil {
            // Process error
            if FBSDKProfile.currentProfile() != nil {
                FBSDKLoginManager().logOut()
            }
        }
        else if result.isCancelled {
            // Handle cancellations
        }
        else {
            // Navigate to other view
            
            if loginButton.titleLabel?.text == "Registrarte" {
                print("Register")
                
                let graphRequest : FBSDKGraphRequest = FBSDKGraphRequest(graphPath: "me", parameters: ["fields":"name, email"])
                graphRequest.startWithCompletionHandler({ (connection, result, error) -> Void in
                    
                    if ((error) != nil)
                    {
                        // Process error
                        print("Error: \(error)")
                    }
                    else
                    {
                        print("fetched user: \(result)")
                        //let userName : NSString = result.valueForKey("name") as! NSString
                        let userEmail = result.valueForKey("email") as! String
                        
                        self.alertRegistro(userEmail)
                    }
                })
                
                //self.performSegueWithIdentifier("RegistroSegue", sender: nil)
                
            } else if loginButton.titleLabel?.text == "Iniciar Sesión" {
                print("logIN")
                self.loginHurry()
            }
        }
        
    }
    
    func loginButtonDidLogOut(loginButton: FBSDKLoginButton!) {
        print("logOut")
    }

}

