README.txt

Version: 1.0
Release Date: June 21, 2013

Description:

ShopTracker is a shop inventory system and access tracker. The program keeps track
or who comes and goes from the machine shop, logging their time in, time out, machines used,
and tools checked out/returned. System Administrators can edit information in the database,
such as each user's machine certifications.

ShopTracker was developed by CSM's Summer 2013 Advanced Software Enginnering Team, CSM #2.
    Development Team:
    - Nicola Hetrick
    - Taylor Sallee
    - Shawn Toffel
    - Chaeha Park

Recommended Operating System:

The application was developed in Java, so it is fairly flexible and should be able to run on
almost any machine, as long as it has a Java Runtime Environment installed. This version of
the product was tested on Windows 7, Windows XP, and Linux. All three operating systems
worked fine, using JRE 6 and JRE 7, but it should be fine on just about any system with Java.

********************************************************************************************
********************************************************************************************

Installation Instructions:

FIRST: Make sure you have a Java Runtime Environment (JRE) installed on the target computer.
       Doing this is as simple as downloading Java and following the installation instructions.
       To download the latest version of Java, visit http://java.com/en/download/index.jsp

NEXT:

Using the provided Software CD, 
1.	Insert CD, Navigate to the CD's location in your file system.
2.	Right-click on the ShopTracker folder and select copy. Navigate to the desired location and paste.
3.	Once the ShopTracker folder is in your desired location, the application can be run by double clicking on ShopTracker.exe.
4.	To create a Desktop shortcut, right-click on ShopTracker.exe and select Send To → Desktop to create.

If the executable does not run:
•	Ensure the following items are within the ShopTracker folder:
1)	ShopTracker.exe
2)	mongodb
3)	ShopTracker.jar

If the database appears empty and no previous data is shown once ShopTracker is running:
•	Ensure the following items are within the “mongodb” folder (in ShopTracker folder):
1)	bin
2)	data (there should be a “db” folder with this location as well)
3)	log

FOR FIRST TIME SETUP (with blank database):
	Run ShopTracker.exe

	Once the "Swipe your Blastercard Screen" opens, enter "shoptracker" in the keyboard
	and press enter. "shoptracker" can be entered any time a blastercard is not present
	and a CWID needs to be entered.

	A text field should appear and say Enter CWID. Enter "33333333". 

	A system admin screen will appear. 

	Click Administrative Functions.

	Click Edit Users in the button panel on the right.

	Click Add Users from the panel that appeared.
	
	Fill in valid information for the new System Administrator, ensure CWID is valid.

	Click Save.

	Click User Privileges in the button panel on the right.

	Click Go in the panel, either one.

	Find the new user you added, click the system administrator check box connected.

	Click Save.

	Click Sign Out.

	Say Yes Twice. 

	Try to log in with the new System Administrator's Blastercard. 

	If the process works, go to remove users and remove the Default SystemAdmin.
	
	Else, redo the steps above. Delete the failed user that was created. 

********************************************************************************************
********************************************************************************************

Moving the Application to a new computer:

Copy the entire ShopTracker folder through any means to the new computer. 

Follow the same process as stated in the Production Installation Instructions except use the ShopTracker folder that was copied off the old machine.

Test the move by trying to run the ShopTracker executable.
	
If the executable does not run:
•	Ensure the following items are within the ShopTracker folder:
1)	ShopTracker.exe
2)	mongodb
3)	ShopTracker.jar

If the database appears empty and no previous data is shown once ShopTracker is running:
•	Ensure the following items are within the mongodb folder (in ShopTracker folder):
1)	bin
2)	data (there should be a “db” folder with this location aswell)
3)	log

********************************************************************************************
********************************************************************************************

Background:

The College of Engineering and Computational Sciences at The Colorado School of Mines (CSM) 
maintains a machine shop in the basement of Brown building. The machine shop
is home to many machines which require specific training to ensure safe operation.
Our software helps create a safer and more organized environment in the machine
shop by keeping track of machine safety certifications and allowing shop staff
to have fast and accurate access to certification information and the inventory database.

About the class:

Advanced Software Engineering is a required capstone course for all Computer Science
undergraduates at CSM. Students break up into small teams of two-six and complete
a project within the first six weeks of summer, usually after completing their third
academic year. Denver-area businesses and organizations often submit projects for
field session groups to complete, and each year Mines departments/faculty members
submit projects as well. This project was completed for Dr. Kevin Moore, dean of the
The College of Engineering and Computational Sciences.