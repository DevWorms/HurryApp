//
//  ComprarViewController.swift
//  HurryApp
//
//  Created by Emmanuel Valentín Granados López on 23/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit

class ComprarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UIDocumentPickerDelegate, UIDocumentMenuDelegate, UIDocumentInteractionControllerDelegate, UITextFieldDelegate {
    
    @IBOutlet weak var nameDoc: UIButton!
    
    var filePath: String! = ""
    var textFields: [UITextField] = []
    var switches: [UISwitch] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func mandarPHP(sender: AnyObject) {
        
        
        for i in switches {
            print(i.on)
        }
        for j in textFields {
            print(j.text!)
        }
        
        let data = NSData(contentsOfFile: self.filePath )
        
        if #available(iOS 8.0, *) {
            
            if (data == nil) {
                print("ño")
                
                let alert = UIAlertController(title: "Nos faltó algo", message: "Selecciona un archivo existente", preferredStyle: UIAlertControllerStyle.Alert)
                alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler: nil))
                self.presentViewController(alert, animated: true, completion: nil)
                
                return
                
            } else if (textFields[0].text == "" || textFields[0].text == " ") {
                print("ño1")
                
                let alert = UIAlertController(title: "Nos faltó algo", message: "¿Cuantas hojas imprimiremos?", preferredStyle: UIAlertControllerStyle.Alert)
                alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler: nil))
                self.presentViewController(alert, animated: true, completion: nil)
                
                return
            }
            
        } else {
            // Fallback on earlier versions
        }
        
        print("siguió")
        /*
        alert.addAction(UIAlertAction(title: "Ok", style: .Default, handler: { action in
        switch action.style{
        case .Default:
        print("default")
        
        case .Cancel:
        print("cancel")
        
        case .Destructive:
        print("destructive")
        }
        }))
        */
        
        let boundary = generateBoundaryString()
        
        // Set Content-Type in HTTP header.
        let contentType = "multipart/form-data; boundary=" + boundary
        
        let request = NSMutableURLRequest(URL: NSURL(string: "http://hurryapp.devworms.com/subir.php")!)
        request.HTTPMethod = "POST"
        
        // Set the HTTPBody we'd like to submit
        request.HTTPBody = HTTPPostToPHP().postToPHP(data!, fileName: (self.nameDoc.titleLabel?.text)!, boundary: boundary)
        
        request.setValue(contentType, forHTTPHeaderField: "Content-Type")
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            data, response, error in
            
            if error != nil {
                print("error=\(error)")
                return
            }
            
            print("response = \(response)")
            
            let responseString = NSString(data: data!, encoding: NSUTF8StringEncoding)
            print("responseString = \(responseString)")
        }
        task.resume()
        

    }
    
    func generateBoundaryString() -> String {
        return "Boundary-\(NSUUID().UUIDString)"
    }
    
    @IBAction func uploadFile(sender: AnyObject) {
        
        if #available(iOS 8.0, *) {
            let documentMenu = UIDocumentMenuViewController(documentTypes: ["public.image","public.data"], inMode: UIDocumentPickerMode.Import)
            
            documentMenu.delegate = self
            
            //documentMenu.modalPresentationStyle = UIModalPresentationStyle.FullScreen
            
            self.presentViewController(documentMenu, animated: true, completion: nil)
            
        } else {
            // Fallback on earlier versions
        }
        
    }
    
    @IBAction func openFile(sender: AnyObject) {
        
        if self.filePath != "" {
            let documentInteraction =  UIDocumentInteractionController(URL: NSURL(fileURLWithPath: self.filePath) )
            
            documentInteraction.delegate = self
            
            // Preview PDF
            documentInteraction.presentPreviewAnimated(true)
            //documentInteraction.presentOpenInMenuFromRect(sender.frame, inView: self.view, animated: true)
        }
        
    }
    
    // MARK: - UIDocumentInteractionControllerDelegate
    
    func documentInteractionControllerViewControllerForPreview(controller: UIDocumentInteractionController) -> UIViewController {
        return self
    }
    
    // MARK: - UIDocumentPickerDelegate
    
    @available(iOS 8.0, *)
    func documentPicker(controller: UIDocumentPickerViewController, didPickDocumentAtURL url: NSURL) {
        if controller.documentPickerMode == UIDocumentPickerMode.Import {
            
            self.filePath = url.path!
            
            var fileSize : UInt64 = 0
            
            do {
                let attr : NSDictionary? = try NSFileManager.defaultManager().attributesOfItemAtPath( self.filePath)
                
                if let _attr = attr {
                    fileSize = _attr.fileSize();
                    
                    print("fileSize: \(fileSize)")
                    
                }
                
                //print ("hola: \(attr)")
                
                //self.ima.image = UIImage(contentsOfFile: self.filePath )
                //self.ima.image = UIImage(data: NSData(contentsOfFile: self.filePath )! )
                self.nameDoc.titleLabel?.text = url.lastPathComponent!
                
                
            } catch {
                print("Error: \(error)")
            }
            
        }
    }
    
    // MARK: - UIDocumentMenuDelegate
    
    @available(iOS 8.0, *)
    func documentMenu(documentMenu: UIDocumentMenuViewController, didPickDocumentPicker documentPicker: UIDocumentPickerViewController) {
        
        documentPicker.delegate = self
        presentViewController(documentPicker, animated: true, completion: nil)
    }
    
    // MARK: - UITableViewDelegate, UITableViewDataSource
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 9
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        let stringIndex = String( indexPath.row )
        
        //print(stringIndex)
        
        let cell = tableView.dequeueReusableCellWithIdentifier( stringIndex ) as UITableViewCell!
        
        switch (indexPath.row) {
            case 1,2,8:
                //print("This number is between 1,2,8")
                let txtF = cell.viewWithTag( indexPath.row ) as! UITextField
                textFields += [txtF]
                txtF.delegate = self
                //print(textFields.count)
            
            case 3...7:
                //print("This number is between 3 and 7")
                let swtch = cell.viewWithTag( indexPath.row ) as! UISwitch
                switches += [swtch]
                swtch.addTarget(self, action: Selector("stateChanged:"), forControlEvents: UIControlEvents.ValueChanged)
                //print(switches.count)
            
            default:
                print("This number is not between 0 and 8")
        }
        
        return cell

    }
    
    func stateChanged(switchState: UISwitch) {
        
        if switchState == switches[0] {
            if switchState.on {
                switches[1].setOn(false, animated: true)
            } else {
                switches[1].setOn(true, animated: true)
            }
        } else if switchState == switches[1] {
            if switchState.on {
                switches[0].setOn(false, animated: true)
            } else {
                switches[0].setOn(true, animated: true)
            }
        } else if switchState == switches[2] {
            if switchState.on {
                switches[3].setOn(false, animated: true)
            } else {
                switches[3].setOn(true, animated: true)
            }
        } else if switchState == switches[3] {
            if switchState.on {
                switches[2].setOn(false, animated: true)
            } else {
                switches[2].setOn(true, animated: true)
            }
        }
        
        
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        print("textFieldShouldReturn")
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        print("textFieldDidEndEditing")
    }
    
    func textField(textField: UITextField, shouldChangeCharactersInRange range: NSRange, replacementString string: String) -> Bool {
        print("shouldChangeCharactersInRange")
        return true
    }
    
    func textFieldDidBeginEditing(textField: UITextField) {
        print("textFieldDidBeginEditing")
    }
    
    func textFieldShouldBeginEditing(textField: UITextField) -> Bool {
        print("textFieldShouldBeginEditing")
        return true
    }
    
    func textFieldShouldClear(textField: UITextField) -> Bool {
        print("textFieldShouldClear")
        return true
    }
    
    func textFieldShouldEndEditing(textField: UITextField) -> Bool {
        print("textFieldShouldEndEditing")
        return true
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
