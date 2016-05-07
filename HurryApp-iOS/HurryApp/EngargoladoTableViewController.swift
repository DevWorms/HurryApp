//
//  EngargoladoTableViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 06/05/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class EngargoladoTableViewController: UITableViewController {
    
    var juegosAimprimir = ""
    var juegos = 0
    var limiteJuegos = 0
    
    @IBOutlet weak var lbl1: UILabel!
    @IBOutlet weak var lbl2: UILabel!
    @IBOutlet weak var lbl3: UILabel!
    @IBOutlet weak var lbl4: UILabel!
    @IBOutlet weak var lbl5: UILabel!
    @IBOutlet weak var lbl6: UILabel!
    @IBOutlet weak var lbl7: UILabel!
    @IBOutlet weak var lbl8: UILabel!
    
    @IBOutlet weak var stp1: UIStepper!
    @IBOutlet weak var stp2: UIStepper!
    @IBOutlet weak var stp3: UIStepper!
    @IBOutlet weak var stp4: UIStepper!
    @IBOutlet weak var stp5: UIStepper!
    @IBOutlet weak var stp6: UIStepper!
    @IBOutlet weak var stp7: UIStepper!
    @IBOutlet weak var stp8: UIStepper!
    
    var labels: [UILabel] = []
    var steppers: [UIStepper] = []

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
        
        if self.juegosAimprimir == "" {
            self.limiteJuegos = 1
        } else {
            self.limiteJuegos = Int( self.juegosAimprimir )!
        }
        
        //steppers
        labels = [lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8]
        steppers = [stp1, stp2, stp3, stp4, stp5, stp6, stp7, stp8]
        
        for index in 0...7 {
            steppers[index].addTarget(self, action: #selector(EngargoladoTableViewController.stepperValueChanged(_:)), forControlEvents: .ValueChanged)
            
            steppers[index].maximumValue = Double(self.limiteJuegos)
        }
        
    }
    
    func stepperValueChanged(sender:UIStepper!) {
        
        for item in 0...(labels.count-1) {
            if sender == steppers[item] {
                
                labels[item].text = Int(sender.value).description
                
                for item in 0...(labels.count-1) {
                    self.juegos = (self.juegos + Int(labels[item].text!)!)
                }
                
                if self.juegos > self.limiteJuegos {
                    
                    let alert = UIAlertView(title: "", message: "No puedes marcar más engargolados que juegos.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    labels[item].text = Int(sender.value - 1.0).description
                    //sender tmbn tiene valor set
                    sender.value = sender.value - 1.0
                }
                
                self.juegos = 0
                
                return
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 8
    }

    /*
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier", forIndexPath: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if segue.identifier == "SegueImprimir", let destination = segue.destinationViewController as? ComprarViewController {
            
            var noEngargolados = 0
            
            for item in 0...(labels.count-1) {
                noEngargolados = (noEngargolados + Int(labels[item].text!)!)
            }
            
            destination.noEngargolado = Double(noEngargolados)
            
        }
    }

}

//https://developer.apple.com/library/ios/technotes/tn2298/_index.html#//apple_ref/doc/uid/DTS40013591-CH1-DETDEST
//https://spin.atomicobject.com/2014/10/25/ios-unwind-segues/
//http://stackoverflow.com/questions/12561735/what-are-unwind-segues-for-and-how-do-you-use-them
