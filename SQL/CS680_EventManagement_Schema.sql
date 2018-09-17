-- CS680 - Database creation script for CS680 - Event Web Management Application

--Create the database if it does not exist
DECLARE @dbName varchar(20)
SET @dbName = 'Event_Manager'

IF NOT EXISTS(SELECT name from sys.databases where name = @dbName)
BEGIN
	CREATE DATABASE Event_Manager
	ALTER DATABASE Event_Manager SET RECOVERY SIMPLE
END

USE Event_Manager
GO

--Create a SQL DB owner if one does not exist
IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'dbEventUser')
BEGIN
	CREATE LOGIN dbEventUser WITH PASSWORD = 'dbEventUser', DEFAULT_DATABASE = Event_Manager
	CREATE USER dbEventUser for Login dbEventUser
	EXEC sp_addrolemember 'db_owner', 'dbEventUser'
END
GO

-- Drop tables
IF EXISTS ( SELECT * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'Users' and TABLE_SCHEMA = 'dbo')
	DROP TABLE [Users]
GO

IF EXISTS ( SELECT * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'Events' and TABLE_SCHEMA = 'dbo')
	DROP TABLE [Events]
GO

CREATE TABLE Users (
    user_id			varchar(20)	NOT NULL,
    first_name		varchar(20) NOT NULL,
	last_name		varchar(20) NOT NULL,
	user_type		varchar(20) NOT NULL,
	email_address	varchar(30) NOT NULL,
	password		varchar(20) NOT NULL,
	CONSTRAINT [PK_USERS] PRIMARY KEY CLUSTERED ( user_id )
	)

CREATE TABLE Events (
    eventID			int		IDENTITY(1,1)	NOT NULL,
    eventName			varchar(50)		NOT NULL,
	eventDescription	varchar(150)	NOT NULL,
	eventLocation		varchar(100)		NOT NULL,
	eventTime			smalldatetime	NOT NULL,
	ticketPrice			float			NOT NULL,
	eventCapacity		int				NOT NULL,
	availableTickets	int				NOT NULL,
	CONSTRAINT [PK_EVENTS] PRIMARY KEY CLUSTERED ( eventID )
	)

--Load test data
insert into Users (user_id, first_name, last_name, user_type, email_address, password)
	values ('TestUser1', 'fTest1', 'lTest1', 'Participant', 'mj477@drexel.edu', '@password123')
insert into Users (user_id, first_name, last_name, user_type, email_address, password)
	values ('TestUser2', 'fTest2', 'lTest2', 'Organizer', 'mj477@drexel.edu', '@password123')


insert into Events (eventName, eventDescription, eventLocation, eventTime, ticketPrice, eventCapacity, availableTickets)
	values ('TestEvent1', 'This is a test event for the Event Management web application', 'Philadelphia', '6-08-2018 19:30', 35.99, 200, 100)
insert into Events (eventName, eventDescription, eventLocation, eventTime, ticketPrice, eventCapacity, availableTickets)
	values ('Health Living 2018', 'Learn about the benefits of adapating a new whole food plant based diet', 'North Cascades National Park', '6-08-2018 18:45', 25.50, 5000, 1505)
insert into Events (eventName, eventDescription, eventLocation, eventTime, ticketPrice, eventCapacity, availableTickets)
	values ('2018 Graduation Celebration', 'Enjoy live entertainment, food, drinks, and more!!', 'University of Pittsburgh Peterson Event Center', '6-08-2018 15:30', 39.95, 3000, 3000)