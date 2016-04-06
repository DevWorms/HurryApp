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
    
    var hurryPrintMethods = ConnectionHurryPrint()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        if FBSDKProfile.currentProfile() != nil {
            let imageFB = FBSDKProfilePictureView(frame: self.profileImage.frame)
            imageFB.profileID = FBSDKAccessToken.currentAccessToken().userID // "me"
            imageFB.pictureMode = FBSDKProfilePictureMode.Square
            self.view.addSubview(imageFB)
            
            self.profileName.text = FBSDKProfile.currentProfile().name
            
        } else {
            print("nil")
            
        }
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/saldo", type: "GET", headers: nil, parameters: nil, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
        })
        
    }
    
    func parseJSON(dataForJson: NSData) {
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let saldoGral = json["saldo"]!!["SaldoRegalo"] as? String {
                
                dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                    
                    self.saldo.text = saldoGral
                })
            }
        } catch {
            print("error serializing JSON: \(error)")
        }
        
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
