# EventBookingSystem
	EventbookingSystem is used to book tickets to popular events. The user can register and login to search and book tickets. The administrator can add/update events.

**User Sign Up and Sign In**:

	Brief description: The user can register on the website and then sign in to purchase tickets/upload
	events.

	Actors 1. The User, who wants to purchase tickets/upload event’s from/on the website.

	Preconditions:
		1. Device capable/powerful enough to run a browser.
		2. The device should be connected to the internet.
		3. The user should know the website URL.

	Basic Flow of
	Events:

	Sign Up
		1. The user opens the chrome browser on the device.
		2. The user types the website URL in the address bar and press enter.
		3. The user clicks on the New User button on the page to register.
		4. The user is re-directed to the Sign Up page.
		5. The user is asked to enter his/her first name, last name, email id, User
		Type, password and confirm password on this page.
		6. The provided information account information is entered in the database.
		7. The user is re-directed to the Sign in page with the Sign Up successful
		message.

	Sign In
		1. The user opens the chrome browser on the device.
		2. The user types the website URL in the address bar and press enter.
		3. The user enters his/her email id and password.
		4. The user clicks on the Login button.
		5. The details entered by the user are checked in the database and if correct
		then the user is re-directed to the homepage.

	Alternative
	Flow

		1. The user enters invalid Email ID/Password
			a. The user enters Email ID/Password in their respective fields.
			b. The user clicks on the Login button.
			c. The details entered by the user are checked in the database and if
			incorrect then an error message is displayed asking the user to
			enter a valid email and password.

		2. The user tries to register with an email id which has already been
		registered
			a. The user enters Email ID/Password in their respective fields.
			b. The user clicks on the Login button.
			c. The details entered by the user are checked in the database and if
			the email id has already been registered then an error message is
			displayed informing the user that this email id has already been
			registered.

		3. The user forgets the password.
			a. The user clicks on the forget password button.
			b. The user is re-directed to a new page and is asked to enter his/her
			email id.
			c. The user receives the new password on the entered email id.
			d. The user can login in using the new password.

	Key Scenario
		 1. Error message if any required information such as email id/password have not been entered.

	Post conditions 
		1. After Sign Up the user has to login using the same email id and password.

	Special requirements
		1. The user can reset the password by clicking on the Reset Password button on the user’s homepage.


**View and Search Event(s)**:

	Brief description: Users as an Event Participant can search events in “Explore Upcoming Events” page, by typing the key words of the Event Name. And Users also can view the Upcoming Event List to explore all Events information and details.

	Actors: 1.	The users as a participant, who want to attend the event or view the event details.

	Preconditions
		User should have a device capable of running a browser.
		The device should be connected to the Internet.
		The user should know the Event Manager website URL.

	Basic Flow of Events
		The user opens a browser on the device.
		The user types the Event Manager website URL in the address bar and press enter.
		The user logs in to the Event Manager website by providing Email Address and Password or create new User account as an Event Participant.
		The user clicks “Explore Upcoming Events” and go to a new page.
		The user views all upcoming event and details below the “Upcoming Event List” .
		The user types keywords on search bar and clicks “Search Event”, the relative event shows below the Upcoming Event List.

	Alternative Flow
		1.The user enters invalid keywords
		The user clicks “Explore Upcoming Events” and type keywords on search bar.
		The keywords entered by the user are searched in the database and if there is no relative Event name then the “Upcoming Event List” would not show anything.

	Key Scenario
	Error message: there is no event name relate to the keywords entered by the user.

	Post conditions
	The user can select Number of Tickets for a event which he/she wants to participant and start the purchase event tickets process. 


**Create new event**:

	Brief description: The user as a Event Organizer can create new event and provide event details after clicking “Create New Event” on the Organizer Homepage. Event organizers can provide information include event name, description, location, date, price and time etc.

	Actors: 1.	The user as an organizer, who wants to create event on this website

	Preconditions
		User should have a device capable of running basic functionalities of a browser.
		The device should have a browser installed in it and connect to the internet.
		User has opened management system in browser

	Basic Flow of Events
		The user opens a browser on the device.
		The user types the Event Manager website URL in the address bar and press enter.
		The user logs in to the Event Manager website by providing Email Address and Password or create new User account as an Event Organizer.
		The user click “Create New Event” tab on the Organizer Homepage to enter corresponding interface.
		In the Create New Event page, the user needs to enter the information of Event Name, Event Description, Event Location, Event Data, Event Start Time and End Time, Ticket Price and Event Capacity.
		The user enters all the information about the event and clicks “Create Event” button.
		After successfully create new event, it will go back to Organizer Homepage and the user can view their event status via going to “Check Event Status”

	Alternative Flow
		1.The user did not enter all Event detailed information while creating a new event.
		User click “Create New Event” tab to enter corresponding interface
		In the Create New Event page, the user lack one or more information like Event Name or Location etc.
		After inputting information, user clicks Create Event button to create event.
		System will notify user they forget some information and will still stay in the Create New Event page.
		2. User clicks Back To Homepage while she/he creating event
		The user clicks “Create New Event” tab to enter corresponding interface
		The user clicks “Back To Homepage” button when he/she stays in the Create New Event page.
		System will return to Organizer Homepage.

	Key Scenario
		Error Message: If the user has not provided any event and click “Check Event Status”, the Event Status page would show a notification “You have not created events yet.”

	Post conditions
		1.	Successful create event operation: system save this event in the database and display it in the “Check Event Status” page.
		2.	Successful cancel operation: system will return to Organizer Homepage and the content should not change in database.


**Add, remove, and/or save events in shopping cart**:

	Brief description: Event participants can login to the event management web system and add events to the shopping cart.
	Once event tickets are in the shopping cart, the user can either remove, save, or purchase event tickets
	from the shopping cart page. Event tickets that are saved in the shopping cart will be made available in the
	shopping cart the next time the user opens up the event management system.

	Actors 
		1. User (participant) of event management system
		Preconditions 1. User should have a device capable of running basic functionalities of a browser.
		2. The device should have a browser installed on it and connected to the internet.
		3. User has opened management system in browser

	Basic Flow of
	Events

		1. User opens up the event management application home page
		2. User navigates to the selected event they wish to attend
		3. User enters the number of tickets required in a text field
		4. User clicks on the “Add” button to add the tickets to their shopping cart
		5. After clicking the “Add” button, the user then clicks on the “Shopping Cart” button to view the
		shopping cart.
		6. The shopping cart page will list all event tickets that have been added by user
		7. For each event listed in the shopping cart, the user has the option to “Delete”, “Save”, or
		“Purchase” event tickets.

	Alternative
	Flow

		1. User opens up the shopping cart without adding new event tickets to the shopping cart
		1. User opens up the event management application home page
		2. User clicks on the “Shopping Cart” button
		3. The shopping cart page will list all event tickets that were saved by the user
		4. For each event listed in the shopping cart, the user has the option to “Delete”, “Save”, or
		“Purchase” event tickets
		2. User wants to change the number of tickets in the shopping cart
		1. User opens up the event management application home page
		2. User clicks on the “Shopping Cart” button to view the shopping cart
		3. The shopping cart page will list all event tickets that were either added or saved by the user
		4. The user selects the event they wish to change the number of tickets to be purchased, and then
		clicks “Remove” to remove all tickets reserved for the event
		5. User navigates to the event system home page
		6. Steps 2 – 7 as in Basic Flow of Events

	Key Scenario 

		1. Notification message if an event ticket is added, removed, or saved in the shopping cart
		2. Update shopping cart list when user successfully added, removed, or purchased event tickets
		3. Update shopping cart list when the user successfully saved event tickets after hitting the “Save”
		button. Event tickets that are saved are annotated in the shopping cart.

	Post conditions 
		1. Event tickets successfully added to the shopping cart: Event system updates the users shopping cart with the event tickets
		2. Event tickets successfully saved or removed from the shopping cart: Event system updates the users shopping cart
		3. Event tickets are not available: Event system notifies the user if the user tries to add more event tickets than there are available


**Purchasing event ticket(s)**:

	Brief description: The user can log into the website and purchase tickets, to have them delivered them to user’s email account.

	Actors 
		1. The User, who wants to purchase an event’s ticket from the website.
		Preconditions 1. Device capable/powerful enough to run a browser.
		2. The device should be connected to the internet.
		3. The user should know the website URL.

	Basic Flow of
	Events

	1. The user opens the chrome browser on the device.
	2. The user types the website URL in the address bar and press enter.
	3. The user selects the desired event listed on the website.
	4. The user is redirected to the Event information page displaying details
	name of the event, pictures, reviews, name of cast & crew, location etc.
	5. The user clicks on the Buy Tickets page on this page.
	6. The user is re-directed to the Checkout page.
	7. The user enters the number of tickets he/she wants to enter.
	8. The server calculates the tax for the ticket(s) and displays the total cost on
	the page.
	9. The user clicks the Continue to Seat Selection button.
	10. The user is re-directed to the seat selection page.
	11. The user selects the desired seats.
	12. The user clicks the Continue button.
	13. The user is redirected to the Login page.
	14. The types the username and password in the respective field.
	15. The user clicks the Submit button.
	16. The user’s credentials are verified by the server.
	17. The user gets logged into his/her account.
	18. The user is re-directed to the Checkout page displaying the user’s name,
	email id, event information, total cost with payment section.
	19. The user types the credit card details in the respective fields.
	20. The user clicks on the Complete my Purchase button.
	21. The credit card information is sent to Credit payment service for
	verification.
	22. After payment verification, the ticket(s) are sent to the user’s email id.
	23. The user is re-directed to the website homepage.


	Alternative Flow 

		1. The user does not have an account in the website 
			Steps 1-13 same as in Basic Flow of Events.
			14. The user clicks on the Create an Account button.
			15. The page asks user to enter his/her name, email id, phone
			number, password.
			16. The user clicks the Submit button.
			17. The account information is entered in the database and the user
			is logged into the new account.
			Steps 18-23 same as in Basic Flow of Events.

		2. The user’s credit card information is saved in the account
			Steps 1-18 same as in Basic Flow of Events.
			19. The saved credit card is auto-selected for payment.
			Steps 20-23 same as in Basic Flow of Events.

		3. If the user session is active but the website tab has been closed
			Steps 1-13 same as in Basic Flow of Events.
			Steps 18-23 same as in Basic Flow of Events.

		4. Session Timeout
			Steps 1-18 same as in Basic Flow of Events.
			19. Due to no activity the user session expires.
			Steps 13-23 same as in Basic Flow of Events.

		5. Checkout as Guest
			Steps 1-13 same as in Basic Flow of Events.
			14. Enter name and email in the respective textfields.
			15. Enter the credit/debit card information.
			16. Click Complete my Purchase button.
			17. The credit card information is sent to Credit payment service for
			verification.
			18. After payment verification, the ticket(s) are sent to the user’s email
			id.
			19. The user is re-directed to the website homepage.

	Key Scenario 
		1. Error message if any required information has not been entered like credit/debit card information, number of seats etc.
		2. Error message if wrong login credentials are entered.
		3. Error message if wrong credit/debit card information is entered.

	Post conditions 
		1. Receipt of the purchase: ​The receipt of the purchase along with the tickets is sent to the user’s registered email account.
		2. Record of the purchase: ​The website logs the record of the purchase with the user’s website account.

	Special requirements

		1. The user is presented with a review page displaying the purchased ticket
		information and the card details to confirm the payment.
		2. If the user credit/debit card does not have enough funds, then an email is
		sent to the user regarding it.
		3. In case of cancelling the ticket(s), the user is sent an email regarding the
		refund.# EventBookingSystem
