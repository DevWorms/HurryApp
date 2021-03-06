//
//  ExportarViewController.swift
//  HurryPrint
//
//  Created by Emmanuel Valentín Granados López on 18/01/16.
//  Copyright © 2016 DevWorms. All rights reserved.
//

import UIKit

class ExportarViewController: UIViewController, UIPageViewControllerDataSource {
    
    var popTransparentView: UIView!
    
    var pageViewController: UIPageViewController!
    var pageTitles: NSArray!
    var pageImages: NSArray!
    let myStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let pageController = UIPageControl.appearance()
        pageController.pageIndicatorTintColor = UIColor.lightGrayColor()
        pageController.currentPageIndicatorTintColor = UIColor.blackColor()
        pageController.backgroundColor = UIColor.whiteColor()
        
        self.pageTitles = NSArray(objects: "Abrir otra aplicación", "Abrir el archivo", "Abrir opciones de compartir archivo", "Abrir en...", "Seleccionar HurryPrint", "Selecciona sucursal", "Archivo cargado" )
        self.pageImages = NSArray(objects: "i1","i2","i3","i4","i5","i6","i7")
        self.pageViewController = myStoryboard.instantiateViewControllerWithIdentifier("PageViewController") as! UIPageViewController
        self.pageViewController.dataSource = self
        
        //posicionar en la primera
        let startVC = self.viewControllerAtIndex(0) as ContentPageViewController
        let viewControllers = NSArray(object: startVC)
        
        self.pageViewController.setViewControllers(viewControllers as? [UIViewController] , direction: .Forward , animated: true, completion: nil)
        //
        
        self.pageViewController.view.frame = CGRect(x: 0, y: 5, width: self.view.frame.width, height: self.view.frame.height - 60)
        
        self.addChildViewController(self.pageViewController)
        
        self.view.addSubview(self.pageViewController.view)
        
        self.pageViewController.didMoveToParentViewController(self)
        
        //PopUp
        self.view.layer.cornerRadius = 5
        self.view.layer.shadowOpacity = 0.8
        self.view.layer.shadowOffset = CGSizeMake(0.0, 0.0)
        self.view.layer.masksToBounds = true
        self.view.layer.zPosition = 1;
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - PopUp

    @IBAction func closePopUp(sender: AnyObject) {
        
        self.removeAnimate()
    }
    
    func showInView(aView: UIView!, animated: Bool, scaleX: CGFloat, scaleY: CGFloat) {
        
        self.popTransparentView = UIView.init(frame:  aView.frame)
        self.popTransparentView.backgroundColor = UIColor(white: 0.5, alpha: 0.6)
        
        aView.addSubview( self.popTransparentView )
        aView.addSubview(self.view)
        
        if animated {
            
            self.showAnimate(scaleX, sY: scaleY)
        }
    }
    
    func showAnimate(sX: CGFloat, sY: CGFloat) {
        self.view.transform = CGAffineTransformMakeScale(1.3, 1.3)
        self.view.alpha = 0.0;
        UIView.animateWithDuration(0.25, animations: {
            self.view.alpha = 1.0
            self.view.transform = CGAffineTransformMakeScale(sX, sY)
        });
    }
    
    func removeAnimate() {
        UIView.animateWithDuration(0.25, animations: {
            self.view.transform = CGAffineTransformMakeScale(1.3, 1.3)
            self.view.alpha = 0.0;
            }, completion:{(finished : Bool)  in
                if (finished) {
                    
                    self.view.removeFromSuperview()
                    self.popTransparentView.removeFromSuperview()
                }
        });
    }
    
    func viewControllerAtIndex(index: Int) ->  ContentPageViewController {
        
        if( (self.pageTitles.count == 0) || (index >= self.pageTitles.count) ){
            return ContentPageViewController()
        }
        
        let vc: ContentPageViewController = myStoryboard.instantiateViewControllerWithIdentifier("ContentPageViewController") as! ContentPageViewController
        
        vc.imageFile = self.pageImages[index] as! String
        vc.titleText = self.pageTitles[index] as! String
        vc.pageIndex = index
        
        return vc
        
    }

    // MARK: - UIPageViewControllerDataSource

    func pageViewController(pageViewController: UIPageViewController, viewControllerAfterViewController viewController: UIViewController) -> UIViewController? {
        
        let vc = viewController as! ContentPageViewController
        var index = vc.pageIndex as Int
        
        if (index == NSNotFound){
            return nil
        }
        
        index += 1
        
        if (index == self.pageTitles.count){
            return nil
        }
        
        return self.viewControllerAtIndex(index)
    }
    
    func pageViewController(pageViewController: UIPageViewController, viewControllerBeforeViewController viewController: UIViewController) -> UIViewController? {
        
        let vc = viewController as! ContentPageViewController
        var index = vc.pageIndex as Int
        
        if (index == 0 || index == NSNotFound){
            return nil
        }
        
        index -= 1
        
        return self.viewControllerAtIndex(index)
    }
    
    func presentationCountForPageViewController(pageViewController: UIPageViewController) -> Int {
        return self.pageTitles.count
    }
    
    func presentationIndexForPageViewController(pageViewController: UIPageViewController) -> Int {
        return 0
    }

}
