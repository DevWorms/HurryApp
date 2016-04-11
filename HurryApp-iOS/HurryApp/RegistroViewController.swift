//
//  RegistroViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class RegistroViewController: UIViewController {
    
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var telefonoTxt: UITextField!
    @IBOutlet weak var contrasenaTxt: UITextField!
    @IBOutlet weak var confirmarTxt: UITextField!
    
    var hurryPrintMethods = ConnectionHurryPrint()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        self.name.text = FBSDKProfile.currentProfile().name
        
    }
    
    @IBAction func registrarseHurry(sender: AnyObject) {
        
        if !validate( self.telefonoTxt.text! ) {
            let alert = UIAlertView(title: "Error en teléfono", message: "Asegurate de escribir correctamente tu teléfono.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
            
            return
        }
        
        if self.confirmarTxt.text != self.contrasenaTxt.text ||
           self.confirmarTxt.text == "" || self.contrasenaTxt.text == "" {
            
            let alert = UIAlertView(title: "Error en campos", message: "Asegurate de escribir correctamente en los campos.", delegate: nil, cancelButtonTitle: "OK")
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
                        
                        self.performSegueWithIdentifier("PagoSegue", sender: nil)
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
