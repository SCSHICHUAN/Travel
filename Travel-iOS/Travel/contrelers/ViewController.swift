//
//  ViewController.swift
//  Travel
//
//  Created by SHICHUAN on 2019/3/22.
//  Copyright © 2019 SHICHUAN. All rights reserved.
//

import UIKit

class ViewController:UIViewController,SCHTTPDelegate,URLSessionDelegate{
    
    lazy var username:UITextField = {
        let t = UITextField(frame: CGRect(x: 10, y: 100, width: 300, height: 40))
        t.backgroundColor = .lightGray
        t.placeholder="请输入用户名"
        return t
    }()
    lazy var phone:UITextField = {
        let t = UITextField(frame: CGRect(x: 10, y: 150, width: 300, height: 40));
        t.backgroundColor = .lightGray
        t.placeholder="请输入电话"
        return t
    }()
    lazy var mail:UITextField = {
        let t = UITextField(frame: CGRect(x: 10, y: 190, width: 300, height: 40));
        t.backgroundColor = .lightGray
        t.placeholder="请输入mail"
        return t
    }()
    lazy var login:UIButton = {
        let t = UIButton(type: UIButton.ButtonType.custom)
        t.frame = CGRect(x: 10, y: 250, width: 300, height: 40)
        t.backgroundColor = UIColor.white
        t.setTitle("login", for: .normal)
        t.setTitleColor(.black, for: .normal)
        t.setTitleColor(.red, for:.highlighted)
        t.addTarget(self, action: #selector(logint), for: .touchUpInside)
        return t
    }()
    @objc func logint() ->(){
        
//        test()
        http()
        
    }
    
    func http(){
        let img1 = UIImage.init(named: "he.jpeg")?.jpegData(compressionQuality: 1)
        let img2 = UIImage.init(named: "item.png")?.jpegData(compressionQuality: 1)
        
        
        let request = SCHTTP()
        request.url = "https://stanserver.cn/http/DaoUserXML/addUser.do"
        request.parameter = [
            
            (name:"username",
             fileName:nil,
             mediaType:SCHTTP.MediaType.String,
             string:"马云",
             data:nil),
            
            (name:"password",
             fileName:nil,
             mediaType:SCHTTP.MediaType.String,
             string:"15454454545",
             data:nil),
            
            (name:"name",
             fileName:"picture1",
             mediaType:SCHTTP.MediaType.File,
             string:nil,
             data:img1),
            
            (name:"name",
             fileName:"picture2",
             mediaType:SCHTTP.MediaType.File,
             string:nil,
             data:img2)
        ]
        
        
        request.star(request:request)
        request.SCHTTPDelegate = self
    }
    
    func didSCHTTPFinished(data: Data?, response: URLResponse?, error: Error?) {
        
        if let jsonString = String(data: data!, encoding: String.Encoding.utf8){
            print(jsonString)
        }

        DispatchQueue.main.async {
            let dict = try? JSONSerialization.jsonObject(with: data!, options: .mutableContainers)
            if dict != nil {
                if #available(iOS 10.0, *) {
                    UIApplication.shared.open(URL(string: (dict as! NSDictionary)["image1"] as! String)!)
                } else {
                    // Fallback on earlier versions
                }

            }
        }
        
        
        
    }
    
    
    func test() {
        
        
        
        let config = URLSessionConfiguration.default
        let session = URLSession.init(configuration: config, delegate: self as URLSessionDelegate, delegateQueue: nil)
        let url = URL(string: "https://www.lagou.com/")
        let task =  session.dataTask(with:url!) { (data, resb, error) in
            if let jsonString = String(data: data!, encoding: String.Encoding.utf8){
                print(jsonString)
            }
        }
        
        task.resume()
        
        
        
        
        
    }
    
    
    public func urlSession(_ session: URLSession, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void)
    {
        print("urlSession")
        
        
//        let path = Bundle.main.path(forResource: "certificate", ofType: "cer");
//        let url = URL(fileURLWithPath: path!)
//        do {
//
//            let cerData = try Data(contentsOf: url)
//            let certificate = SecCertificateCreateWithData(nil,(cerData as CFData))
//
//
//            let trust = challenge.protectionSpace.serverTrust
//            var result = SecTrustResultType.init(rawValue: 0)
//
//            SecTrustSetAnchorCertificates(trust!,[certificate] as CFArray)
//            let status = SecTrustEvaluate(trust!, &result!)
//            if (status == errSecSuccess ){
//
//                let cred = URLCredential.init(trust: trust!)
//                challenge.sender?.use(cred, for: challenge)
//
//            }else{
//                challenge.sender?.cancel(challenge)
//            }
//
//        } catch _ as Error? {
//
//        }
        
        
        var disposition = URLSession.AuthChallengeDisposition.performDefaultHandling
        
        var credential:URLCredential? = nil
        /*disposition：如何处理证书
         performDefaultHandling:默认方式处理
         useCredential：使用指定的证书
         cancelAuthenticationChallenge：取消请求
         */
        
        if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust {
            credential = URLCredential(trust: challenge.protectionSpace.serverTrust!)
            
            if credential != nil {
                disposition = URLSession.AuthChallengeDisposition.useCredential
            }
        }else if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodClientCertificate {
            //单向验证，客户端不需要服务端验证，此处与默认处理一致即可
            disposition = URLSession.AuthChallengeDisposition.cancelAuthenticationChallenge
        }
        else {
            disposition = URLSession.AuthChallengeDisposition.cancelAuthenticationChallenge
        }
        
        completionHandler(disposition, credential)
        
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(login)
        view.backgroundColor = UIColor.lightGray
    }
}
