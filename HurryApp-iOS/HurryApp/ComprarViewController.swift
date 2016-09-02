//
//  ComprarViewController.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 23/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit

class ComprarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UIDocumentPickerDelegate, UIDocumentMenuDelegate, UIDocumentInteractionControllerDelegate, UITextFieldDelegate, UIGestureRecognizerDelegate {
    
    //variables desde otros controllers
    var noSucursal = ""
    var costoFolders = 0.0
    var noEngargolado = 0.0
    var foldersRespuesta: [String] = ["0","0","0","0","0","0","0","0","0"]
    var engargoladosRespuesta: [String] = ["0","0","0","0","0","0","0","0","0","0"]
    
    @IBOutlet weak var tableViewComprar: UITableView!
    @IBOutlet weak var nameDoc: UIButton!
    
    var hurryPrintMethods = ConnectionHurryPrint()
    
    var textFields: [UITextField] = []
    var switches: [UISwitch] = []
    var switchesRespuesta: [String] = ["1","","","1","",""]
    var deleteUrl = true
    
    var dispBlancoNegro = true
    var dispColor = true
    
    var precioImpresionBN = 0.0
    var precioImpresionColor = 0.0
    
    var costoEngargolado = 0.0
    
    var popViewController : ExportarViewController!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "fondo.png")!)
        
        let swipeDown = UISwipeGestureRecognizer(target: self, action: #selector(ComprarViewController.swipeKeyBoard(_:)))
        swipeDown.direction = UISwipeGestureRecognizerDirection.Down
        swipeDown.delegate = self
        self.tableViewComprar.addGestureRecognizer(swipeDown)
        
        let precioTiendaBN = 0.95
        let precioTiendaColor = 4.95
        let precioDevworms = 0.05
        
        self.precioImpresionBN = precioTiendaBN + precioDevworms
        self.precioImpresionColor = precioTiendaColor + precioDevworms
        
        self.nameDoc.setTitle(MyFile.Name, forState: .Normal)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func validate(value: String) -> Bool {
        // "\\d+-\\d+" para digito(mas) - digito (mas)
        // "^[1-9]\\d*"// que empiece desde el 1 al 9 y q pueda tener mas digitos o ninguno mas
        let param = "^[1-9]\\d*|\\d+-\\d+"
        let test = NSPredicate(format: "SELF MATCHES %@", param)
        let result =  test.evaluateWithObject(value)
        
        return result
    }
    
    @IBAction func mandarPHP(sender: AnyObject) {
        
        let data = NSData(contentsOfFile: MyFile.Path )
        
        if (data == nil) {
            let alert = UIAlertView(title: "Nos faltó algo", message: "Selecciona un archivo existente", delegate: nil, cancelButtonTitle: "Ok")
            alert.show()
                
            return
                
        } else if ( !validate( textFields[0].text! ) ) {
            let alert = UIAlertView(title: "Nos faltó algo", message: "¿Cuántas hojas imprimiremos?", delegate: nil, cancelButtonTitle: "Ok")
            alert.show()
            
            return
            
        } else if (textFields[1].text! != ""){
            if !validate( textFields[1].text! ) {
                let alert = UIAlertView(title: "Error en Intervalo", message: "Asegúrate de escribir correctamente #-#.", delegate: nil, cancelButtonTitle: "OK")
                alert.show()
                
                return
            }
        }
        
        if (textFields[2].text! != ""){
            if (!validate( textFields[2].text! )) {
                let alert = UIAlertView(title: "Error en Juegos", message: "Asegúrate de escribir correctamente #.", delegate: nil, cancelButtonTitle: "OK")
                alert.show()
                
                return
            }
        }
        
        print("siguió")
        
        // se ejecuta el metodo y obtiene valores para mostrar
        let precioFinalEnviar = self.calcularTotalImpresion()
        
        let message = textoConfirmación(precioFinalEnviar)
        
        let alertController = UIAlertController(title: "Resumen de compra", message: message, preferredStyle: .Alert)
        
        let okAction = UIAlertAction(title: "Confirmar", style: .Default) {
            UIAlertAction in
            
            self.mandarPostPHP(precioFinalEnviar)
        }
        let cancelAction = UIAlertAction(title: "Cancelar", style: .Cancel) {
            UIAlertAction in
            return
        }
        
        alertController.addAction(okAction)
        alertController.addAction(cancelAction)
        
        self.presentViewController(alertController, animated: true, completion: nil)
        
    }
    
    func textoConfirmación(precioFinalEnviar: String) -> String {
        let mensaje = "Tu impresión total será: $" + precioFinalEnviar + "\n" +
                        "Costo por Folders: $\(self.costoFolders) \n" +
                        "Costo por Engargolado: $\(self.costoEngargolado) \n\n" +
                        "Revisa tu compra."
        return mensaje
    }
    
    func mandarPostPHP(precioFinalEnviar: String) {
        let alert = UIAlertController(title: nil, message: "Se están enviando tus archivos...", preferredStyle: .Alert)
        alert.view.tintColor = UIColor.blackColor()
        
        let loadingIndicator: UIActivityIndicatorView = UIActivityIndicatorView(frame: CGRectMake(10, 5, 50, 50)) as UIActivityIndicatorView
        loadingIndicator.hidesWhenStopped = true
        loadingIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.Gray
        loadingIndicator.startAnimating();
        
        let callAction = UIAlertAction(title: "Cancelar", style: .Default, handler: {
            action in
            
            self.hurryPrintMethods.cancelConnection()
            return
        })
        alert.addAction(callAction)
        
        alert.view.addSubview(loadingIndicator)
        self.presentViewController(alert, animated: true, completion: nil)
        
        // ver el status de todos los switches
        for index in 0...5 {
            if switches[index].on {
                switchesRespuesta[index] = "1"
            } else {
                switchesRespuesta[index] = ""
            }
        }
        
        let parameters = [
            "sucursal" : self.noSucursal,
            "hojas" : textFields[0].text!,
            "intervalo" : textFields[1].text!,
            "totalimpresion" : precioFinalEnviar,
            "blanconegro" : switchesRespuesta[0],
            "color" : switchesRespuesta[1],
            "caratula" : switchesRespuesta[2],
            "carta" : switchesRespuesta[3],
            "oficio" : switchesRespuesta[4],
            "lados" : switchesRespuesta[5],
            "juegos" : textFields[2].text!,
            
            //Folders
            "FolderBeige" : foldersRespuesta[0],
            "FolderAzul" : foldersRespuesta[1],
            "FolderRosa" : foldersRespuesta[2],
            "FolderVerde" : foldersRespuesta[3],
            "FolderGuinda" : foldersRespuesta[4],
            "FolderAzulIntenso" : foldersRespuesta[5],
            "FolderRojo" : foldersRespuesta[6],
            "FolderNegro" : foldersRespuesta[7],
            "FolderMorado" : foldersRespuesta[8],
            
            //Engargolados
            "pGuinda" : engargoladosRespuesta[0],
            "pNegro" : engargoladosRespuesta[1],
            "pRojo" : engargoladosRespuesta[2],
            "pAzul" : engargoladosRespuesta[3],
            "pVerde" : engargoladosRespuesta[4],
            "pAmarillo" : engargoladosRespuesta[5],
            "pMorado" : engargoladosRespuesta[6],
            "pGris" : engargoladosRespuesta[7],
            "pBlanco" : engargoladosRespuesta[8],
            "pAzulFuerte" : engargoladosRespuesta[9],
            
            ]
        
        //print("json:")
        //print(parameters)
        
        //return
        
        //Completion Handler
        self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/class/SubirMovil.php", type: "POST1", headers: nil, parameters: parameters, completion: { (resultData) -> Void in
            
            self.dismissViewControllerAnimated(false, completion: nil)
            
            self.parseJSON( resultData )
            
        })
    }
    
    func parseJSON(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let estado = json["Estado"] as? Int {
                if estado == 1 {
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        let alert = UIAlertView(title: "HurryPrint!", message: "Lánzate a la sucursal por tus impresiones.", delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        self.navigationController?.popViewControllerAnimated(true)
                    })
                    
                } else {
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        let alert = UIAlertView(title: "Ocurrió algo", message: json["Descripcion"] as? String, delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
            
            dispatch_async(dispatch_get_main_queue(), {
                
                let alert = UIAlertView(title: "Ocurrió algo", message: "No pudimos enviar tu archivo, intentalo de nuevo, asegúrate de que todo este bien.", delegate: nil, cancelButtonTitle: "OK")
                alert.show()
            })
        }
        
    }
    
    func calcularTotalImpresion() -> String {

        var precioPorHojas = 0.0
        
        if self.switchesRespuesta[0] == "1" {
            precioPorHojas = Double(self.textFields[0].text!)! * self.precioImpresionBN
            
            if self.switchesRespuesta[2] == "1" { // impresiones con caratula a color
                precioPorHojas = (precioPorHojas - self.precioImpresionBN) + self.precioImpresionColor
            }
        }
        if self.switchesRespuesta[1] == "1" { // impresiones color
            precioPorHojas = Double(self.textFields[0].text!)! * self.precioImpresionColor
        }
        if textFields[2].text! != "" { // multiplicar por juegos
            precioPorHojas = precioPorHojas * Double(self.textFields[2].text!)!
        }
        
        if self.noEngargolado > 0.0 { // engargolado
            
            if ((Int(self.textFields[0].text!)) <= 40){
                self.costoEngargolado = ( 18.00 * self.noEngargolado )
            }else if ((Int(self.textFields[0].text!)) > 40 && (Int(self.textFields[0].text!)) <= 80){
                self.costoEngargolado = ( 20.00 * self.noEngargolado )
            }else if ((Int(self.textFields[0].text!)) > 80 && (Int(self.textFields[0].text!)) <= 110){
                self.costoEngargolado = ( 22.00 * self.noEngargolado )
            }else if ((Int(self.textFields[0].text!)) > 110 && (Int(self.textFields[0].text!)) <= 150){
                self.costoEngargolado = ( 24.00 * self.noEngargolado )
            }else if ((Int(self.textFields[0].text!)) > 150 && (Int(self.textFields[0].text!)) <= 200){
                self.costoEngargolado = ( 26.00 * self.noEngargolado )
            }else {
                let alert = UIAlertView(title: "Error en Engargolado", message: "No podremos engargolar.", delegate: nil, cancelButtonTitle: "OK")
                alert.show()
            }
            precioPorHojas = precioPorHojas + self.costoEngargolado
        }
        
        // Folders
        precioPorHojas = precioPorHojas + self.costoFolders
        
        return String( precioPorHojas )
    }
 
    @IBAction func deleteFile(sender: AnyObject) {
 
        self.deleteDocFile()
    }
 
    func deleteDocFile() {
        MyFile.url = NSURL(fileURLWithPath:" ")
        self.nameDoc.setTitle("", forState: .Normal)
    }
    
    @IBAction func uploadFile(sender: AnyObject) {
        
        let documentMenu = UIDocumentMenuViewController(documentTypes: ["com.adobe.pdf", "com.microsoft.word.doc", "org.openxmlformats.wordprocessingml.document"], inMode: UIDocumentPickerMode.Import)
            
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
        return 12
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        let stringIndex = String( indexPath.row )
        
        let cell = tableView.dequeueReusableCellWithIdentifier( stringIndex ) as UITableViewCell!
        
        switch (indexPath.row) {
            case 1,2,9:
                //print("This number is between 1,2,9")
                let txtF = cell.viewWithTag( indexPath.row ) as! UITextField
                textFields += [txtF]
                txtF.delegate = self
            
            case 3...8:
                //print("This number is between 3 and 8")
                let swtch = cell.viewWithTag( indexPath.row ) as! UISwitch
                switches += [swtch]
                swtch.addTarget(self, action: #selector(ComprarViewController.stateChanged(_:)), forControlEvents: UIControlEvents.ValueChanged)
            
                if indexPath.row == 3 {
                    swtch.setOn(dispBlancoNegro, animated: true)
                }
            
                if indexPath.row == 4 {
                    if dispBlancoNegro == true && dispColor == true {
                        swtch.setOn(false, animated: true)
                    } else {
                        swtch.setOn(dispColor, animated: true)
                    }
                    
                }
            
                //print("jum "+String(switches.count))
            
            default: break
        }
        
        return cell
    }
    
    func stateChanged(switchState: UISwitch) {
        
        if switchState == switches[0] { // blanco negro
            if switchState.on {
                
                if dispBlancoNegro == false {
                    switches[0].setOn(false, animated: true)
                    
                    let alert = UIAlertView(title: "Sin disponibilidad", message: "No hay impresiones a B/N en esta sucursal.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    return
                }
                
                switches[1].setOn(false, animated: true)
                switches[2].setOn(false, animated: true)
            } else {
                
                if dispColor == false {
                    switches[0].setOn(true, animated: true)
                    
                    let alert = UIAlertView(title: "Sin disponibilidad", message: "No hay impresiones a color en esta sucursal.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    return
                }
                
                switches[1].setOn(true, animated: true)
                switches[2].setOn(false, animated: true)
            }
        } else if switchState == switches[1] { // Color
            if switchState.on {
                
                if dispColor == false {
                    switches[1].setOn(false, animated: true)
                    
                    let alert = UIAlertView(title: "Sin disponibilidad", message: "No hay impresiones a color en esta sucursal.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    return
                }
                
                switches[0].setOn(false, animated: true)
                switches[2].setOn(false, animated: true)
            } else {
                
                if dispBlancoNegro == false {
                    switches[1].setOn(true, animated: true)
                    
                    let alert = UIAlertView(title: "Sin disponibilidad", message: "No hay impresiones a B/N en esta sucursal.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    return
                }

                switches[0].setOn(true, animated: true)
                switches[2].setOn(false, animated: true)
            }
        } else if switchState == switches[2] { // Cáratula color
            if switchState.on {
                
                if dispColor == false || dispBlancoNegro == false {
                    switches[2].setOn(false, animated: true)
                    
                    let alert = UIAlertView(title: "Sin disponibilidad", message: "No hay impresiones a color o b/n en esta sucursal.", delegate: nil, cancelButtonTitle: "OK")
                    alert.show()
                    
                    return
                }
                
                switches[0].setOn(true, animated: true)
                switches[1].setOn(false, animated: true)
            } else {
                switches[0].setOn(true, animated: true)
                switches[1].setOn(false, animated: true)
            }
        } else if switchState == switches[3] { // Carta
            if switchState.on {
                switches[4].setOn(false, animated: true)
            } else {
                switches[4].setOn(true, animated: true)
            }
        } else if switchState == switches[4] { // Oficio
            if switchState.on {
                switches[3].setOn(false, animated: true)
            } else {
                switches[3].setOn(true, animated: true)
            }
        }
    }
    
    // MARK: - UITextFieldDelegate
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidBeginEditing(textField: UITextField) {
        //subir el table view para que se vea el campo textfield
        if textField == self.textFields[2] {
            self.tableViewComprar.setContentOffset(CGPointMake(0, 250), animated: true)
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField == self.textFields[2] {
            self.tableViewComprar.setContentOffset(CGPointMake(0, 0), animated: true)
        }
    }
    
    // MARK: - UIGestureRecognizerDelegate
    
    func gestureRecognizer(gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWithGestureRecognizer otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        //metodo para que detecte recognizer desde table view, ya que al parecer tiene scroll y recognizer propio
        return true
    }
    
    func swipeKeyBoard(sender:AnyObject) {
        //Baja el textField
        self.view.endEditing(true)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "FolderSegue" {
            deleteUrl = false
        } else if segue.identifier == "EngargoladoSegue", let destination = segue.destinationViewController as? EngargoladoTableViewController {
            
            deleteUrl = false
            
            destination.juegosAimprimir = self.textFields[2].text!
        }
        
    }
    
    // for delete path or keep it when open another view
    override func viewDidDisappear(animated: Bool) {
        //print("ComprarViewController DidDisappear: "+animated.description)
        
        // when ComprarViewController has been eliminated from a view hierarchy
        if animated && deleteUrl {
            print("elimina url")
            self.deleteDocFile()
        }
        
        deleteUrl = true
    }
    
    @IBAction func unwindToImprimir(sender: UIStoryboardSegue)
    {
        //aqui manda el exit de otro view controller con (ctrl+obj)
        // Pull any data from the view controller which initiated the unwind segue.
    }

}
