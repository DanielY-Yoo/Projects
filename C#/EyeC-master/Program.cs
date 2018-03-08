//----------------------------------------------------------------------------
//  Copyright (C) 2004-2016 by EMGU Corporation. All rights reserved.       
//----------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.Threading;
using Emgu.CV;
using Emgu.CV.CvEnum;
using Emgu.CV.Structure;
using Emgu.CV.UI;
using Emgu.CV.Cuda;
using System.Net;
using System.IO;

namespace EyeC
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Thread t = new Thread(handleWebRequests);
            t.Start();
            System.Windows.Forms.Application.EnableVisualStyles();
            System.Windows.Forms.Application.SetCompatibleTextRenderingDefault(false);
            System.Windows.Forms.Application.Run(new EyeC());
            t.Abort();
        }

        static void handleWebRequests()
        {
            WebServer ws = new WebServer(SendResponse, "http://localhost:8080/index.html/");
            WebServer ws2 = new WebServer(SendResponse2, "http://localhost:8080/addnumber.html/");
            ws.Run();
            ws2.Run();
            while (true)
            {
                
            }
            ws.Stop();
            ws2.Stop();
        }

        static string SendResponse(HttpListenerRequest request)
        {
            return File.ReadAllText(@"index.html");
        }
        static string SendResponse2(HttpListenerRequest request)
        {
            string phoneNumber = request.QueryString["phone"];
            EyeC.addNumber(phoneNumber);
            return string.Format("<HTML><head><meta http-equiv=\"refresh\" content=\"3;url=index.html\" /></head><BODY>Successfully Added! {0} <br></BODY></HTML>", phoneNumber);
        }

    }
}