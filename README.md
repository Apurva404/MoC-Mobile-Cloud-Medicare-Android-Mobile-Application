# MoC-Mobile-Cloud-Medicare-Android-Mobile-Application
An Android based health monitoring and fall detection mobile app, complemented by a J2EE based cloud backend on AWS, for the elderly. 

Core Features: 
 
 -Health data tracking via FitBit integration
 
 -Patient-doctor engagement via appointment scheduling and health data exchange
 
 -Automatic fall detection. In case of a detected fall,
 	-Auto alert emergency contact,
	-Auto share geo-location data,
	-One touch 911 dialing
	
Tools & Technologies: Android Studio 2.3 Java 1.8, Jersey , AWS EC2, AWS RDS, MySQL

README:
1. Download/Transfer the .apk file of the project to your device
2. Install the application on the device.
3. Navigate to the icon within your mobile applications, to access MoC Medicare.
4.Register as a Patient or a Doctor providing the required information.
5. Once registered, login to your account. 

LOGIN AS A PATIENT:
1. Add an emergency contact who would respond promptly in case of emergencies. 
2. To add Emergency Contact:	Navigate to the sidebar and select Emergency Contact. 
					> It would display the emergency contact if there is any. 
					> Else, add an emergency contact.
					> Once added, the contact would be listed in the emergency contact tab.
					> The contact can be changed at any time needed. 
3. To add/associate Doctors:	Navigate to the sidebar and select Doctors.
					> It would display the list of associated doctors, if there are any.
					> You can click on the doctor name and the details of the doctor would be listed.
					> If no associated doctors, add the required doctor based on the speciality and the list of available doctors. 
					> Once added, the doctor would be displayed in the list of associated doctors.							
4. To add Appointments:		Navigate to the sidebar and select Appointments.
					> It would display the appointments, if there are any.
					> Else, add/get an appointment with one of your associated doctors. 
					> Once added, the appointment would be displayed in the list of appointments. 
5. To add Reminders:		Navigate to the sidebar and select Reminders.
					> It would display any to-do-tasks or list of reminders based on 
						-Pending tasks
						-Completed tasks
						-List of all tasks
					> Add a task and set alarms if needed. 
					> Once tasks are completed, they can be checked, which would transfer to the task to "done" tab.
							Tasks can be edited or deleted when needed. 
6. To add Fitbit Health Data:	Navigate to the sidebar and select Sources.
					> Click on fitbit.
					> Login to your fitbit account by providing the credentials. 
					> Once logged in, the health data displays on the patient dashboard. 
					> It can be sent to the doctors associated when needed.

LOGIN AS A DOCTOR:
1. To manage Appointments:	Navigate to the sidebar and select Appointments.
					> It would list the appointments requested. 
					> Accept or decline the request based on your appointment schedules.
2. To access Patient data: 	Navigate to the sidebar and select Patients.
					> It would list the current patients associated to you. 
					> You can access the details of the patient by clicking on the name. 
					> You can request the health data from the patient by clicking "Request health Data". 
 
