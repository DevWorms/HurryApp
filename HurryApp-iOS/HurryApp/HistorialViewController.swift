//
//  HistorialViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 13/01/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class HistorialViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    var refreshControl = UIRefreshControl()
    var dateFormatter = NSDateFormatter()
    
    var numberRows = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        if Accesibilidad.isConnectedToNetwork() == true {
            print("Internet connection OK")
            
        } else {
            let alert = UIAlertView(title: "Sin conexón a internet", message: "Asegurate de estar conectado a internet.", delegate: nil, cancelButtonTitle: "OK")
            alert.show()
        }
        
        // set up the refresh control
        //https://grokswift.com/pull-to-refresh-swift-table-view/
        self.dateFormatter.dateStyle = NSDateFormatterStyle.ShortStyle
        self.dateFormatter.timeStyle = NSDateFormatterStyle.LongStyle
        
        self.refreshControl.attributedTitle = NSAttributedString(string: "Actualizando...")
        self.refreshControl.addTarget(self, action: "refresh:", forControlEvents: UIControlEvents.ValueChanged)
        self.tableView.addSubview( self.refreshControl )
        
    }
    
    func refresh(sender:AnyObject) {
        self.loadStockQuoteItems()
    }
    
    func loadStockQuoteItems() {
        
        // update "last updated" title for refresh control
        let now = NSDate()
        let updateString = "Última actualización " + self.dateFormatter.stringFromDate(now)
        self.refreshControl.attributedTitle = NSAttributedString(string: updateString)
        
        // tell refresh control it can stop showing up now
        if self.refreshControl.refreshing {
            self.refreshControl.endRefreshing()
        }
        
        self.numberRows = 1
        
        self.tableView?.reloadData()

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - UITableViewDelegate
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if numberRows == 0 {
            
            let noDataLabel = UILabel.init(frame: CGRect(x: 0, y: 0, width: tableView.bounds.width, height: tableView.bounds.height))
            noDataLabel.numberOfLines = 2
            noDataLabel.font = UIFont.boldSystemFontOfSize(16)
            noDataLabel.textColor = UIColor.blackColor()
            noDataLabel.textAlignment = NSTextAlignment.Center
            noDataLabel.backgroundColor = UIColor.lightGrayColor()
            noDataLabel.text = "No tienes productos por recoger"
            
            tableView.backgroundView = noDataLabel
            tableView.separatorStyle = UITableViewCellSeparatorStyle.None
                        
        } else {
        
            tableView.backgroundView = nil
            tableView.separatorStyle = UITableViewCellSeparatorStyle.SingleLine
        }
        
        return self.numberRows
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCellWithIdentifier("CellHistorial", forIndexPath: indexPath) as UITableViewCell
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
