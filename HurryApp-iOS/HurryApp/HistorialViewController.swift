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
    
    var hurryPrintMethods = ConnectionHurryPrint()
    var numberRows = 0
    var names = [String]()
    var stats = [String]()
    var noFolios = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // set up the refresh control
        //https://grokswift.com/pull-to-refresh-swift-table-view/
        self.dateFormatter.dateStyle = NSDateFormatterStyle.ShortStyle
        self.dateFormatter.timeStyle = NSDateFormatterStyle.LongStyle
        
        self.refreshControl.attributedTitle = NSAttributedString(string: "Actualizando...")
        self.refreshControl.addTarget(self, action: #selector(HistorialViewController.refresh(_:)), forControlEvents: UIControlEvents.ValueChanged)
        self.tableView.addSubview( self.refreshControl )
        
        self.loadStockQuoteItems()
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
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/folios", type: "GET", headers: nil, parameters: nil, completion: { (resultData) -> Void in
            
            self.parseJSON( resultData )
            
            dispatch_async(dispatch_get_main_queue(), {
                
                self.tableView.reloadData()
            })
            
        })
        
    }
    
    func parseJSON(dataForJson: NSData) {
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let folios = json["folios"] as? [[String: AnyObject]] {
                for folio in folios {
                    
                    if let noFolio = folio["folio_documento"] as? String {
                        self.noFolios.append(noFolio)
                    }
                    if let name = folio["nombre_documento"] as? String {
                        self.names.append(name)
                    }
                    if let estatus = folio["descripcion"] as? String {
                        self.stats.append(estatus)
                    }
                }
                
                if folios.count > 0 {
                    self.numberRows = folios.count
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
    
    // MARK: - UITableViewDelegate
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if numberRows == 0 {
            
            let noDataLabel = UILabel.init(frame: CGRect(x: 0, y: 0, width: tableView.bounds.width, height: tableView.bounds.height))
            noDataLabel.numberOfLines = 2
            noDataLabel.font = UIFont.boldSystemFontOfSize(16)
            noDataLabel.textColor = UIColor.blackColor()
            noDataLabel.textAlignment = NSTextAlignment.Center
            noDataLabel.backgroundColor = UIColor.clearColor()//UIColor.lightGrayColor()
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
        
        let nameDocumento = cell.viewWithTag(1) as! UILabel
        nameDocumento.text = self.names[ indexPath.row ]
        
        let folio = cell.viewWithTag(2) as! UILabel
        folio.text = self.noFolios[ indexPath.row ]
        
        let estatus = cell.viewWithTag(3) as! UILabel
        estatus.text = self.stats[ indexPath.row ]
        
        if self.self.stats[indexPath.row] == "esperando" {
            estatus.textColor = UIColor.redColor()
        } else {
            estatus.textColor = UIColor.greenColor()
        }
        
        return cell
        
    }
    
    
    // MARK: - Navigation
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if segue.identifier == "FolioSegue", let destination = segue.destinationViewController as? InfoHistorialViewController {
            if let cell = sender as? UITableViewCell, let indexPath = self.tableView.indexPathForCell(cell) {
                
                destination.folioSearch = self.noFolios[ indexPath.row ]
            }
        }
    }

}
