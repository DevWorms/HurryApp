//
//  InfoHistorialViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 19/01/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class InfoHistorialViewController: UIViewController {
    
    @IBOutlet weak var estatusLabel: UILabel!
    @IBOutlet weak var folioLabel: UILabel!
    @IBOutlet weak var docLabel: UILabel!
    @IBOutlet weak var sucuLabel: UILabel!
    @IBOutlet weak var precioLabel: UILabel!
    @IBOutlet weak var horaLabel: UILabel!
    @IBOutlet weak var fechaLabel: UILabel!
    
    var hurryPrintMethods = ConnectionHurryPrint()
    
    var folioSearch = ""

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let headers = [
           "folio": self.folioSearch
        ]
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/folio", type: "GET", headers: headers, parameters: nil, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
        })
    }
    
    func parseJSON(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let folio = json["descripcion"]!!["Folio"] as? String {
                
                let fullDate = json["descripcion"]!!["Fecha"] as? String
                let fullDateArr = fullDate!.componentsSeparatedByString(" ")
                
                dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                    self.folioLabel.text = folio
                    self.estatusLabel.text = json["descripcion"]!!["Status"] as? String
                    
                    if self.estatusLabel.text == "esperando" {
                        self.estatusLabel.textColor = UIColor.orangeColor()
                    } else {
                        self.estatusLabel.textColor = UIColor.greenColor()
                    }                    
                    
                    self.docLabel.text = json["descripcion"]!!["Nombre"] as? String
                    self.sucuLabel.text = json["descripcion"]!!["Sucursal"] as? String
                    self.precioLabel.text = "$ " + (json["descripcion"]!!["Costo"] as? String)!
                    self.fechaLabel.text = fullDateArr[0]
                    self.horaLabel.text = fullDateArr[1]
                    
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

//http://stackoverflow.com/questions/22759167/how-to-make-a-push-segue-when-a-uitableviewcell-is-selected
//https://developer.apple.com/library/ios/documentation/UserExperience/Conceptual/TableView_iPhone/ManageReorderRow/ManageReorderRow.html
