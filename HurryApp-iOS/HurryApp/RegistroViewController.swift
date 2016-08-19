//
//  RegistroViewController.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class RegistroViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var telefonoTxt: UITextField!
    @IBOutlet weak var contrasenaTxt: UITextField!
    @IBOutlet weak var confirmarTxt: UITextField!
    
    var hurryPrintMethods = ConnectionHurryPrint()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "fondo.png")!)
        
        self.telefonoTxt.delegate = self
        self.contrasenaTxt.delegate = self
        self.confirmarTxt.delegate = self
        
        let swipeDown = UISwipeGestureRecognizer(target: self, action: #selector(ViewController.swipeKeyBoard(_:)))
        swipeDown.direction = UISwipeGestureRecognizerDirection.Down
        self.view.addGestureRecognizer(swipeDown)
        
        self.name.text = FBSDKProfile.currentProfile().name
        
    }
    
    @IBAction func registrarseHurry(sender: AnyObject) {
        
        if !validate( self.telefonoTxt.text! ) {
            let alert = UIAlertView(title: "Error en teléfono", message: "Asegúrate de escribir correctamente tu teléfono.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
            
            return
        }
        
        if self.confirmarTxt.text != self.contrasenaTxt.text ||
           self.confirmarTxt.text == "" || self.contrasenaTxt.text == "" ||
            !validatePassword( self.contrasenaTxt.text! ) {
            
            let alert = UIAlertView(title: "Error en campos", message: "Asegúrate de escribir correctamente la contraseña, se requiere al menos 1 letra ó 1 número, no se aceptan carácteres especiales |!()-,*/?.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
            
            return
        }
        
        let headers = [
            "content-type": "application/json",
        ]
        
        let parameters = [
            "nombre": FBSDKProfile.currentProfile().name!,
            "contrasena": self.contrasenaTxt.text!,
            "telefono": self.telefonoTxt.text!,
            "token": FBSDKAccessToken.currentAccessToken().userID!
        ]
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/usuarios/registro", type: "POST", headers: headers, parameters: parameters, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
        })
    }
    
    func parseJSON(dataForJson: NSData) {
        
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
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    func validate(value: String) -> Bool {
        let PHONE_REGEX = "^55\\d{8}"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
        let result =  phoneTest.evaluateWithObject(value)
        
        return result
    }
    
    func validatePassword(value: String) -> Bool {
        let PHONE_REGEX = "[a-zA-Z0-9]+"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
        let result =  phoneTest.evaluateWithObject(value)
        
        return result
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func willMoveToParentViewController(parent: UIViewController?) {
        if parent == nil {
            
            //Apretó btn back
            if FBSDKProfile.currentProfile() != nil {
                FBSDKLoginManager().logOut()
            }
        }
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

}
