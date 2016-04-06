//
//  SucursalesViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 15/01/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class SucursalesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tableViewSucursales: UITableView!
    
    var names = [String]()
    var blanco_negro = [String]()
    var color = [String]()
    var sucursalesAbiertas = [String]()
    var numberRows = 0
    var hurryPrintMethods = ConnectionHurryPrint()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/sucursales", type: "GET", headers: nil, parameters: nil, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
            dispatch_async(dispatch_get_main_queue(), {
                
                self.tableViewSucursales.reloadData()
            })
        })
        
    }
    
    func parseJSON(dataForJson: NSData) {
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let sucursales = json["sucursal"] as? [[String: AnyObject]] {
                for sucursal in sucursales {
                    if let name = sucursal["nombre_tienda"] as? String {
                        self.names.append(name)
                    }
                    if let clr = sucursal["color"] as? String {
                        self.color.append(clr)
                    }
                    if let bn = sucursal["blanco_negro"] as? String {
                        self.blanco_negro.append(bn)
                    }
                    if let sucAbierta = sucursal["abierto"] as? String {
                        self.sucursalesAbiertas.append(sucAbierta)
                    }
                }
                
                if sucursales.count > 0 {
                    self.numberRows = sucursales.count
                    
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
    
    // MARK: - UITableViewDelegate, UITableViewDataSource
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.numberRows
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("CellSucursal", forIndexPath: indexPath) as UITableViewCell
        
        let nameSucursal = cell.viewWithTag(1) as! UILabel
        nameSucursal.text = self.names[ indexPath.row ]
        
        let disponibilidad_sucAbierta = cell.viewWithTag(2)
        
        if self.sucursalesAbiertas[indexPath.row] == "1" {
            disponibilidad_sucAbierta?.backgroundColor = UIColor.greenColor()
        }else {
            disponibilidad_sucAbierta?.backgroundColor = UIColor.grayColor()
        }
        
        let disponibilidad_b_n = cell.viewWithTag(3)
        
        if self.blanco_negro[indexPath.row] == "1" {
            disponibilidad_b_n?.backgroundColor = UIColor.greenColor()
        }else {
            disponibilidad_b_n?.backgroundColor = UIColor.grayColor()
        }
        
        let disponibilidad_color = cell.viewWithTag(4)
        
        if self.color[indexPath.row] == "1" {
            disponibilidad_color?.backgroundColor = UIColor.greenColor()
        }else {
            disponibilidad_color?.backgroundColor = UIColor.grayColor()
        }
        
        return cell
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

//http://stackoverflow.com/questions/12301256/is-it-possible-to-set-uiview-border-properties-from-interface-builder
