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
    
    var hurryPrintMethods = ConnectionHurryPrint()
    
    var textFields: [UITextField] = []
    var switches: [UISwitch] = []
    var deleteUrl = true
    
    var popViewController : ExportarViewController!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.nameDoc.setTitle(MyFile.Name, forState: .Normal)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func mandarPHP(sender: AnyObject) {
        
        let data = NSData(contentsOfFile: MyFile.Path )
        
        if (data == nil) {
            print("ño")
                
            let alert = UIAlertView(title: "Nos faltó algo", message: "Selecciona un archivo existente", delegate: nil, cancelButtonTitle: "Ok")
            alert.show()
                
            return
                
        } else if (textFields[0].text == "" || textFields[0].text == " ") {
            print("ño1")
            
            let alert = UIAlertView(title: "Nos faltó algo", message: "¿Cuantas hojas imprimiremos?", delegate: nil, cancelButtonTitle: "Ok")
            alert.show()
            
            return
            
            /*
            let alert = UIAlertController(title: "Nos faltó algo", message: "¿Cuantas hojas imprimiremos?", preferredStyle: UIAlertControllerStyle.Alert)
            alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler: nil))
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
            self.presentViewController(alert, animated: true, completion: nil)
            */
            
        }
        
        print("siguió")
        
            
                    let parameters = [
                        "usuario" : "@usuario",
                        "folio" : "@folio",
                        "nombre" : "@nombre",
                        "url" : "@url",
                        "ver" : "@ver",
                        "sucursal" : "17",
                        "hojas" : "2",
                        "intervalo" : "",
                        "blanconegro" : "1",
                        "color" : "",
                        "caratula" : "",
                        "lados" : "",
                        "carta" : "1",
                        "oficio" : "",
                        "total" : "2.2"
                    ]
                    
                    //Completion Handler
                    self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/class/SubirMovil.php", type: "POST1", headers: nil, parameters: parameters, completion: { (resultData) -> Void in
                    
                    //self.parseJSON( resultData )
                    
                    })
    }
    
    
    
    func parseJSON(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let registro = json["estado"] as? Int {
                if registro == 1 {
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        let alert = UIAlertView(title: "HurryApp!", message: "Lánzate a la sucursal por tus impresiones.", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                    })
                    
                } else if registro == 8 {
                    
                    dispatch_async(dispatch_get_main_queue(), { // swift 3, This application is modifying the autolayout engine from a background thread, which can lead to engine corruption and weird crashes.  This will cause an exception in a future release.
                        
                        let alert = UIAlertView(title: "Ocurrió algo", message: "No pudimos enviar tu archivo, intentalo de nuevo, asegúrate de que todo este bien.", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
        
    }
    
    @IBAction func deleteFile(sender: AnyObject) {
        
        MyFile.url = NSURL(fileURLWithPath:" ")
        self.nameDoc.setTitle("", forState: .Normal)
    }
    
    @IBAction func uploadFile(sender: AnyObject) {
        
        if #available(iOS 8.0, *) {
            let documentMenu = UIDocumentMenuViewController(documentTypes: ["public.image","public.data"], inMode: UIDocumentPickerMode.Import)
            
            documentMenu.delegate = self
            
            //documentMenu.modalPresentationStyle = UIModalPresentationStyle.FullScreen
            
            documentMenu.addOptionWithTitle("Desde otra aplicación", image: nil, order: .First, handler: { () -> Void in
                self.openPopUpTutorial()
            })
            
            /*
            documentMenu.addOptionWithTitle("iPhone", image: nil, order: .First,
                    handler: {
                        
                        let fm = NSFileManager.defaultManager()
                        let url = NSBundle.mainBundle().resourcePath
                        
                        //
                        let dirPaths = NSSearchPathForDirectoriesInDomains(.DocumentDirectory,
                            .UserDomainMask, true)
                        let docsDir = dirPaths[0] as String
                        print(docsDir)
                        //
                        
                        if(url != nil ){
                            do {
                                let items = try fm.contentsOfDirectoryAtPath(url!)
                                
                                let pick = UIDocumentPickerViewController(URL: NSURL(fileURLWithPath: url!), inMode: UIDocumentPickerMode.ExportToService)
                                
                                pick.delegate = self
                                self.presentViewController(pick, animated: true, completion: nil)
                                
                                
                                for item in items {
                                    print("Found \(item)")
                                }
                                
                            } catch {
                                // failed to read directory – bad permissions, perhaps?
                                print("catch")
                            }
                        
                        }else { print("ni se pudo leer la ruta") }
                        
                        print("New Doc Requested") })
            */
            
            //ipad
            documentMenu.popoverPresentationController?.sourceView = self.view
            
            self.presentViewController(documentMenu, animated: true, completion: nil)
            
        } else {
            // Fallback on earlier versions            
            
            self.openPopUpTutorial()
            
        }
        
    }
    
    func openPopUpTutorial() {
        
        self.popViewController = storyboard!.instantiateViewControllerWithIdentifier("ExportarViewController") as! ExportarViewController
        self.popViewController.showInView( self.view , animated: true, scaleX: 0.72, scaleY: 0.72)
    }
    
    @IBAction func openFile(sender: AnyObject) {
        
        if MyFile.Path != "" {
            let documentInteraction =  UIDocumentInteractionController(URL: NSURL(fileURLWithPath: MyFile.Path) )
            documentInteraction.delegate = self
            
            deleteUrl = false
            
            // Preview PDF
            documentInteraction.presentPreviewAnimated(true)
            // menu abajo para abrir algun archivo
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
            
            MyFile.url = url
            
            var fileSize : UInt64 = 0
            
            do {
                let attr : NSDictionary? = try NSFileManager.defaultManager().attributesOfItemAtPath( MyFile.Path )
                
                if let _attr = attr {
                    fileSize = _attr.fileSize();
                    
                    print("fileSize: \(fileSize)")
                }
                
                self.nameDoc.setTitle(MyFile.Name, forState: .Normal)
                
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
            case 1,2:
                //print("This number is between 1,2,8")
                let txtF = cell.viewWithTag( indexPath.row ) as! UITextField
                textFields += [txtF]
                txtF.delegate = self
                //print(textFields.count)
            
            case 3...8:
                //print("This number is between 3 and 7")
                let swtch = cell.viewWithTag( indexPath.row ) as! UISwitch
                switches += [swtch]
                swtch.addTarget(self, action: #selector(ComprarViewController.stateChanged(_:)), forControlEvents: UIControlEvents.ValueChanged)
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
                switches[2].setOn(false, animated: true)
            } else {
                switches[1].setOn(true, animated: true)
                switches[2].setOn(false, animated: true)
            }
        } else if switchState == switches[1] {
            if switchState.on {
                switches[0].setOn(false, animated: true)
                switches[2].setOn(false, animated: true)
            } else {
                switches[0].setOn(true, animated: true)
                switches[2].setOn(false, animated: true)
            }
        } else if switchState == switches[2] {
            if switchState.on {
                switches[0].setOn(true, animated: true)
                switches[1].setOn(false, animated: true)
            } else {
                switches[0].setOn(true, animated: true)
                switches[1].setOn(false, animated: true)
            }
        } else if switchState == switches[3] {
            if switchState.on {
                switches[4].setOn(false, animated: true)
            } else {
                switches[4].setOn(true, animated: true)
            }
        } else if switchState == switches[4] {
            if switchState.on {
                switches[3].setOn(false, animated: true)
            } else {
                switches[3].setOn(true, animated: true)
            }
        }
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        print("textFieldShouldReturn")
        textField.resignFirstResponder()
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
    
    // for delete path or keep it when open another view
    override func viewDidDisappear(animated: Bool) {
        print("ComprarViewController DidDisappear: "+animated.description)
        
        // when ComprarViewController has been eliminated from a view hierarchy
        if animated && deleteUrl {
            print("elimina url")
            MyFile.url = NSURL(fileURLWithPath:" ")
        }
        
        deleteUrl = true
    }    

}
