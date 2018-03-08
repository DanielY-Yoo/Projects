# EyeC
A facial recognition program that uses a webcam to find faces within a video feed. Once a face is found, a picture will be taken and sent to the phone(s) of the owner(s) of the webcam.

- Year: 2016
- Languages: C#, HTML, JavaScript
- Created by Yeonjoon Yoo, Adeola Uthman, and Christian Cosgrove. 
- Submission for the 2016 Bergen County Academies Hackathon, hackBCA III.
- Winner for "Best Startup" by NYTechDay at hackBCA III.

[Download binaries](https://github.com/BoolClub/EyeC/blob/master/EyeCbinaries.zip?raw=true)

# Documentation
## Executable File
Ensure that your Windows machine has either an internal or external webcam. To use EyeC, open the `EyeC.exe` file. The webcam will turn on and display the surroundings. As soon as it detects a face, it will display a red square around it. If a phone number has been specified, it will snap a picture and send it to the user's phone through MMS.

## Adding phone numbers
Multiple people can recieve text alerts about the person whose face was just detected. Before receiving messages, though, you must be sure that each user's phone number is in the database. This comes in handy for an entire family, for example. Each person must first add his/her phone number by visiting the page `:8080/index.html` (which runs off of a web server on the host machine; ip address of the host machine). Once the numbers are added, the program will know to send a message to each number whenever it detects a face through the webcam.

# Applications
- One application of this program is in home security. For example, if a person walks up to a door that has a webcam connected to a computer with this program installed, the program will try to recognize that there is a person at the door and snap a clear picture of the person's face. 
- The owner(s) of the webcam will recieve a text message that contains a picture of the person at the door. That person can then act accordingly without putting his/her-self at risk. If the person at the door is a criminal, for example, you would not want to go anywhere near the door. However, from a safe distance you will recieve a text alerting you of who is there.

# Challenges
Configuring the computer vision library EmguCV was a major challenge. We needed to familiarize ourselves with the library and make use of existing examples. For instance, we combined two example projects packaged with EmguCV, `CameraCapture` (which provided realtime webcam output) and `DetectFace` (a single-frame facial recognition demo). 

As we accustomed ourselves with Twilio, we realized that in order to send images via MMS, the image must be uploaded on the Internet. We considered hosting images using AWS but were unable to access the service. Instead, we decided on the Imgur API. Our program uploads each snapshot to Imgur and feeds this URL to Twilio, which then sends the picture to each user.

We spent a large portion of our time improving the accuracy of the face detection. Occasionally it would recognize outside objects as faces, and so to combat this, we check whether the face has persisted a certain number of frames.

# Future plans

This technology is an inexpensive alternative to home security systems; many families have leftover computers and webcams that effectively make this technology free. If we continued with this project, we would create a compact PC to act as the host machine for this software.

# Technologies
In the spirit of the hack, we found ways to cement together many different technologies to make a functional product:
- [Twilio](https://www.twilio.com/)
- [Imgur](http://api.imgur.com/)
- [EmguCV](http://www.emgu.com/wiki/index.php/Main_Page)
- A basic web server, courtesy of [David's Blog](https://codehosting.net/blog/BlogEngine/post/Simple-C-Web-Server.aspx)
