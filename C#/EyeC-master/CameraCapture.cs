//----------------------------------------------------------------------------
//  Copyright (C) 2004-2016 by EMGU Corporation. All rights reserved.       
//----------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Emgu.CV;
using Emgu.CV.Cuda;
using Emgu.CV.CvEnum;
using Emgu.CV.Structure;
using Emgu.CV.UI;
using Emgu.Util;
using System.Threading.Tasks;
using Imgur.API.Authentication.Impl;
using Imgur.API.Endpoints.Impl;
using System.IO;
using Imgur.API;
using System.Threading;
using Twilio;

namespace EyeC
{
    public partial class EyeC : Form
    {
        int faceCount = 0;
        const int faceFrames = 5;
        const int tooLow = 30;
        private Capture _capture = null;
        private bool blocked = false;
        public static List<string> numbers = new List<string>();

        const string phoneNumbersFileName = "PhoneNumbers.txt";
        public EyeC()
        {
            InitializeComponent();

            this.Icon = new Icon("EyeCIcon.ico");
            CvInvoke.UseOpenCL = false;
            try
            {
                _capture = new Capture();
                _capture.SetCaptureProperty(Emgu.CV.CvEnum.CapProp.FrameWidth, 1024);
                _capture.SetCaptureProperty(Emgu.CV.CvEnum.CapProp.FrameHeight, 576);
                //Emgu.CV.CvEnum.CapProp.FrameCount
                _capture.ImageGrabbed += ProcessFrame;
                _capture.Start();
                _capture.FlipHorizontal = true;
            }
            catch (NullReferenceException excpt)
            {
                MessageBox.Show(excpt.Message);
            }

            this.FormClosed += CameraCapture_FormClosed;

            loadNumbers();
        }

        void CameraCapture_FormClosed(object sender, FormClosedEventArgs e)
        {
            System.Windows.Forms.Application.Exit();
        }

        public static void loadNumbers()
        {
            StreamReader file = File.OpenText(phoneNumbersFileName);
            string s = "";
            while (!file.EndOfStream)
            {
                s = file.ReadLine();
                numbers.Add(s);
            }

            file.Close();
        }

        public static void addNumber(string number)
        {
            StreamWriter file = new StreamWriter(phoneNumbersFileName, true);

            file.WriteLine(number);
            numbers.Add(number);

            file.Close();
        }

        private void ProcessFrame(object sender, EventArgs arg)
        {
            Mat frame = new Mat();
            _capture.Retrieve(frame, 0);

            Rectangle? face = DetectFaces(frame);

            var img = frame.ToImage<Bgr, Byte>();
            captureImageBox.Image = img.Resize(this.Width, this.Height, Inter.Linear, true);

            Bgr avg;
            MCvScalar stddev;
            img.AvgSdv(out avg, out stddev);
            //Debug.WriteLine(avg.Blue + avg.Red + avg.Green);
            if (avg.Blue+avg.Red+avg.Green < tooLow)
            {
                if (!blocked)
                {
                    blocked = true;
                    sendTwilioWarning("It's very dark! Camera could be covered :(");
                }
            }
            else
            {
                blocked = false;
            }

            if (face != null)
            {
                //Emgu.CV.Image<Bgr, Byte> img = frame.ToImage<Bgr, Byte>();
                //img.ROI = (Rectangle) face;
                //var croppedFrame = img.Copy();
                faceCount++;
                if (faceCount > faceFrames)
                {
                    UploadImage(frame.ToImage<Bgr, Byte>());
                    faceCount = 0;
                    Thread.Sleep(10000);
                }
            } else
                faceCount = 0;
        }

        public static async Task UploadImage(Emgu.CV.Image<Bgr, Byte> img)
        {
            img.Save("face.jpg");
            try
            {
                var client = new ImgurClient("daa2923d8975323", "d37d874c8356e6a43921688675c2343f91e2f868");
                var endpoint = new ImageEndpoint(client);
                Imgur.API.Models.IImage image;
                using (var fs = new FileStream(@"face.jpg", FileMode.Open))
                {
                    image = await endpoint.UploadImageStreamAsync(fs);
                }
                Debug.Write("Image uploaded. Image Url: " + image.Link);
                sendTwilio(image.Link);
            }
            catch (ImgurException imgurEx)
            {
                Debug.Write("An error occurred uploading an image to Imgur.");
                Debug.Write(imgurEx.Message);
            }
        }

        public static void sendTwilio(string url) {
            string AccountSid = "ACd5b4c7fe6bd5de894d4db3a2878ffae7";
            string AuthToken = "1d9c960b686393eb1aa9ab1ce5c4d557";

            var twilio = new TwilioRestClient(AccountSid, AuthToken);
            
            foreach (string s in numbers)
            {
                var message = twilio.SendMessage("+19735676436", s, string.Format("Hey {0}, someone's at your house!", s), new string[] {url});

                if (message.RestException != null)
                {
                    var error = message.RestException.Message;
                }
            }
        }

        public static void sendTwilioWarning(string msg)
        {
            string AccountSid = "ACd5b4c7fe6bd5de894d4db3a2878ffae7";
            string AuthToken = "1d9c960b686393eb1aa9ab1ce5c4d557";

            var twilio = new TwilioRestClient(AccountSid, AuthToken);

            foreach (string s in numbers)
            {
                var message = twilio.SendMessage("+19735676436", s, msg);

                if (message.RestException != null)
                {
                    var error = message.RestException.Message;
                }
            }
        }

        private void ReleaseData()
        {
            if (_capture != null)
                _capture.Dispose();
        }

        private static Rectangle? DetectFaces(Mat image)
        {
            long detectionTime;
            List<Rectangle> faces = new List<Rectangle>();
            List<Rectangle> eyes = new List<Rectangle>();

            //The cuda cascade classifier doesn't seem to be able to load "haarcascade_frontalface_default.xml" file in this release
            //disabling CUDA module for now
            bool tryUseCuda = false;

            DetectFace.Detect(
              image, "haarcascade_frontalface_default.xml", "haarcascade_eye.xml",
              faces, eyes,
              tryUseCuda,
              out detectionTime);

            foreach (Rectangle face in faces)
            {
                CvInvoke.Rectangle(image, face, new Bgr(Color.Red).MCvScalar, 2);
                CvInvoke.PutText(image, "Face Found", face.Location, FontFace.HersheyDuplex, 1.0, new Bgr(0, 0, 255).MCvScalar);
            }

            if (faces.Count > 0)
            {
                return faces[0];
                //Cursor.Position = faces[0].Location;
            }
            return null;
        }

    }
}
