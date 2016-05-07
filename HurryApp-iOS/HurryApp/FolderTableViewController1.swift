//
//  FolderTableViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 29/04/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class FolderTableViewController: UITableViewController {
    
    @IBOutlet weak var lblBeige: UILabel!
    @IBOutlet weak var lblAzul: UILabel!
    @IBOutlet weak var lblRosa: UILabel!
    @IBOutlet weak var lblVerde: UILabel!
    @IBOutlet weak var lblGuinda: UILabel!
    @IBOutlet weak var lblAzulFuerte: UILabel!
    @IBOutlet weak var lblRojo: UILabel!
    @IBOutlet weak var lblNegro: UILabel!
    @IBOutlet weak var lblMorado: UILabel!
    
    @IBOutlet weak var stepperBeige: UIStepper!
    @IBOutlet weak var stepperAzul: UIStepper!
    @IBOutlet weak var stepperRosa: UIStepper!
    @IBOutlet weak var stepperVerde: UIStepper!
    @IBOutlet weak var stepperGuinda: UIStepper!
    @IBOutlet weak var stepperAzulFuerte: UIStepper!
    @IBOutlet weak var stepperRojo: UIStepper!
    @IBOutlet weak var stepperNegro: UIStepper!
    @IBOutlet weak var stepperMorado: UIStepper!
    
    var labels: [UILabel] = []
    var steppers: [UIStepper] = []
    
    var precioFolder = 3
    var precioFolderFuerte = 5
    var labelsFolder: [UILabel] = []
    var labelsFolderFuerte: [UILabel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        labels = [lblBeige, lblAzul, lblRosa, lblVerde, lblGuinda, lblAzulFuerte, lblRojo, lblNegro, lblMorado]
        
        steppers = [stepperBeige, stepperAzul, stepperRosa, stepperVerde, stepperGuinda, stepperAzulFuerte, stepperRojo, stepperNegro, stepperMorado]
        
        labelsFolder = [lblBeige, lblAzul, lblRosa, lblVerde]
        
        labelsFolderFuerte = [lblGuinda, lblAzulFuerte, lblRojo, lblNegro, lblMorado]
        
        //http://www.apptuitions.com/programmatically-creating-uistepper-and-uisegmentedcontrol-using-swift/
        for index in 0...8 {
            steppers[index].addTarget(self, action: #selector(FolderTableViewController.stepperValueChanged(_:)), forControlEvents: .ValueChanged)
        }
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }
    
    func stepperValueChanged(sender:UIStepper!) {
        
        for item in 0...(labels.count-1) {
            if sender == steppers[item] {
                labels[item].text = Int(sender.value).description
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
        return 2
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        
        if section == 0 {
            return 4
        }
        return 5
    }
    
    /*
    // no se usa si son statics o puedes usarlos distinto
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let stringIndex = String( indexPath.row )
        
        let cell = tableView.dequeueReusableCellWithIdentifier(stringIndex, forIndexPath: indexPath)
        
        print( "jum" + stringIndex )
        
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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    override func viewDidDisappear(animated: Bool) {
        
        var noFolder = 0
        var noFolderFuerte = 0
        var noFolders = 0
        var costoTotalFolders = 0
        
        for lblFolder in labelsFolder {
            noFolder += Int( lblFolder.text! )!
        }
        
        costoTotalFolders = noFolder * precioFolder
        
        print("noFolder")
        print(noFolder)
        
        for lblFolderFuerte in labelsFolderFuerte {
            noFolderFuerte += Int( lblFolderFuerte.text! )!
        }
        
        print("noFolderFuerte")
        print(noFolderFuerte)
        
        noFolders = noFolder + noFolderFuerte
        
        print("noFolders")
        print(noFolders)
        
        costoTotalFolders = costoTotalFolders + (noFolderFuerte * precioFolderFuerte)
        
        MyFile.costoFolders = Double( costoTotalFolders )
        
        print("costoTotalFolders")
        print(costoTotalFolders)
        
    }

}
