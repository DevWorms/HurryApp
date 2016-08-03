//
//  RecargaViewController.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 22/11/15.
//  Copyright © 2015 DevWorms. All rights reserved.
//

import UIKit

class RecargaViewController: UIViewController, PayPalPaymentDelegate {
    
    var hurryPrintMethods = ConnectionHurryPrint()
    var saldoArecargar = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func recagra30(sender: AnyObject) {
        
        self.payPal("Recarga $30+comisión", precio: "30.0")
        
        saldoArecargar = 30
    }
    
    @IBAction func recarga50(sender: AnyObject) {
        
        self.payPal("Recarga $50+comisión", precio: "50.0")
        
        saldoArecargar = 50
    }
    
    @IBAction func recarga100(sender: AnyObject) {
        
        self.payPal("Recarga $100+comisión ($115)", precio: "100.0")
        
        saldoArecargar = 100
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    // MARK: - Paypal
    
    override func viewWillAppear(animated: Bool) {
        
        // Start out working with the test environment! When you are ready, switch to PayPalEnvironmentProduction.
        PayPalMobile.preconnectWithEnvironment(PayPalEnvironmentNoNetwork)
    }
    
    func payPal(descripcion: String, precio: String) {
        
        let paypalConf = PayPalConfiguration() //paypalconfiguration.h
        
        //paypalConf.acceptCreditCards = true
        
        // Create a PayPalPayment
        let payment = PayPalPayment()
        
        // Amount, currency, and description
        payment.amount = NSDecimalNumber(string: precio)
        payment.currencyCode = "USD"
        payment.shortDescription = descripcion
        
        // Use the intent property to indicate that this is a "sale" payment,
        // meaning combined Authorization + Capture.
        // To perform Authorization only, and defer Capture to your server,
        // use PayPalPaymentIntentAuthorize.
        // To place an Order, and defer both Authorization and Capture to
        // your server, use PayPalPaymentIntentOrder.
        // (PayPalPaymentIntentOrder is valid only for PayPal payments, not credit card payments.)
        payment.intent = .Sale
        
        // If your app collects Shipping Address information from the customer,
        // or already stores that information on your server, you may provide it here.
        //payment.shippingAddress = address; // a previously-created PayPalShippingAddress object
        
        // Several other optional fields that you can set here are documented in PayPalPayment.h,
        // including paymentDetails, items, invoiceNumber, custom, softDescriptor, etc.
        
        // Check whether payment is processable.
        if (!payment.processable) {
            // If, for example, the amount was negative or the shortDescription was empty, then
            // this payment would not be processable. You would want to handle that here.
            print("no procedio el pago: \(payment)")
        } else {
            
            // Create a PayPalPaymentViewController.
            let paymentViewController = PayPalPaymentViewController(payment: payment , configuration: paypalConf , delegate: self)
            
            // Present the PayPalPaymentViewController.
            presentViewController(paymentViewController, animated: true, completion: nil)
        }
        
    }
    
    func payPalPaymentViewController(paymentViewController: PayPalPaymentViewController!, didCompletePayment completedPayment: PayPalPayment!) {
        
        print("PayPal Payment Success !")
        paymentViewController.dismissViewControllerAnimated(true, completion: { () -> Void in
            
            // send completed confirmaion to your server
            print("Here is your proof of payment:\n\n\(completedPayment.confirmation)\n\nSend this to your server for confirmation and fulfillment.")
            
            let parameters = [
                "Saldo": String( self.saldoArecargar ),
                "Apikey": NSUserDefaults.standardUserDefaults().stringForKey("ApiKey")!
            ]
            
            //Completion Handler
            self.hurryPrintMethods.connectionRestApi( "http://hurryprint.devworms.com/api/usuarios/recarga", type: "POST", headers: nil, parameters: parameters, completion: { (resultData) -> Void in
                
                self.parseJSON( resultData )
                
            })
            
        })
    }

    func payPalPaymentDidCancel(paymentViewController: PayPalPaymentViewController!) {
        print("PayPal Payment Cancelled")
        paymentViewController.dismissViewControllerAnimated(true, completion: nil)
    }
    
    func parseJSON(dataForJson: NSData) {
        
        do {
            let json = try NSJSONSerialization.JSONObjectWithData( dataForJson , options: .AllowFragments)
            
            if let registro = json["estado"] as? Int {
                if registro == 1 {
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        let alert = UIAlertView(title: "", message: json["mensaje"] as? String, delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                        
                        // https://www.andrewcbancroft.com/2014/10/08/fundamentals-of-nsnotificationcenter-in-swift/
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshSaldo", object: nil)
                        
                        
                    })
                    
                } else { //if registro == 8 {
                    
                    dispatch_async(dispatch_get_main_queue(), {
                        
                        let alert = UIAlertView(title: "Error en recarga", message: json["mensaje"] as? String, delegate: nil, cancelButtonTitle: "OK")
                        alert.show()
                    })
                    
                }
            }
            
        } catch {
            print("error serializing JSON: \(error)")
        }
    }
    
}
