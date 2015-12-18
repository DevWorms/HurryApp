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
