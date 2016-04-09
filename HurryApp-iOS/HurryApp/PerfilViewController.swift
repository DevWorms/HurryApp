//
//  PerfilViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 17/12/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit
import FBSDKLoginKit

class PerfilViewController: UIViewController {
    
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
            print("nil")
            
        }
        
        self.getSaldo()
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
        
        if FBSDKProfile.currentProfile() != nil {
            FBSDKLoginManager().logOut()
            
        }
        
        let vc = storyboard!.instantiateViewControllerWithIdentifier("Inicio") 
        self.presentViewController( vc , animated: true, completion: nil)
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
