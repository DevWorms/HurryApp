//
//  PerfilViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 17/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class PerfilViewController: UIViewController, UIPopoverPresentationControllerDelegate {
    
    @IBOutlet weak var profileImage: UIImageView!
    @IBOutlet weak var profileName: UILabel!
    @IBOutlet weak var saldo: UILabel!
    @IBOutlet weak var saldoRegalo: UILabel!
    
    var hurryPrintMethods = ConnectionHurryPrint()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        self.navigationItem.rightBarButtonItem?.target = self
        self.navigationItem.rightBarButtonItem?.action = #selector(PerfilViewController.refresh(_:))
        
        if FBSDKProfile.currentProfile() != nil {
            let imageFB = FBSDKProfilePictureView(frame: self.profileImage.frame)
            imageFB.profileID = FBSDKAccessToken.currentAccessToken().userID // "me"
            imageFB.pictureMode = FBSDKProfilePictureMode.Square
            self.view.addSubview(imageFB)
            
            self.profileName.text = FBSDKProfile.currentProfile().name
            
        } else {
            self.profileName.text = NSUserDefaults.standardUserDefaults().stringForKey("NombreUsuario")!
            
            self.requestGraphAPIFB()
            
        }
        
        self.getSaldo()
    }
    
    func requestGraphAPIFB() {
        
        let idFb = NSUserDefaults.standardUserDefaults().stringForKey("TokenFB")!
        
        let facebookProfileUrl = NSURL(string: "https://graph.facebook.com/\(idFb)/picture?type=large")
        
        if let data = NSData(contentsOfURL: facebookProfileUrl!) {
            self.profileImage.image = UIImage(data: data)
            self.profileImage.layer.borderWidth = 1
            self.profileImage.layer.masksToBounds = false
            self.profileImage.layer.borderColor = UIColor.blackColor().CGColor
            self.profileImage.layer.cornerRadius = self.profileImage.frame.height/2
            self.profileImage.clipsToBounds = true
        } else {
            print("no se pudo cargar la imagen")
        }
    }
    
    func getSaldo() {
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/saldo", type: "GET", headers: nil, parameters: nil, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
        })
    }
    
    func parseJSON(dataForJson: NSData) {
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let saldo = json["saldo"]!!["Saldo"] as? String {
                
                dispatch_async(dispatch_get_main_queue(), {
                    
                    self.saldo.text = "$ " + saldo
                    self.saldoRegalo.text = "$ " + (json["saldo"]!!["SaldoRegalo"] as? String)!
                    self.saldoRegalo.textColor = UIColor.purpleColor()
                })
            }
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    func refresh(sender:AnyObject) {
        self.getSaldo()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func logOut(sender: AnyObject) {
        
        let defaults = NSUserDefaults.standardUserDefaults()
        defaults.setObject("", forKey: "ApiKey")
        defaults.setObject("", forKey: "NombreUsuario")
        defaults.setObject("", forKey: "TokenFB")
        
        if FBSDKProfile.currentProfile() != nil {
            FBSDKLoginManager().logOut()
            
        }
        
        let vc = storyboard!.instantiateViewControllerWithIdentifier("Inicio") 
        self.presentViewController( vc , animated: true, completion: nil)
    }

    @IBAction func showPopOver(sender: AnyObject) {
        
        self.performSegueWithIdentifier("showViewPopover", sender: nil) //showViewPopover
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "showViewPopover" {
            let vc = segue.destinationViewController as UIViewController
            
            let controller = vc.popoverPresentationController
            
            if controller != nil {
                controller?.delegate = self
            }
        }
    }
    
    func adaptivePresentationStyleForPresentationController(controller: UIPresentationController) -> UIModalPresentationStyle {
        return .None
    }
}
