package com.eventmanager.users;

public enum UserType { 
	EVENT_ORGANIZER{
		@Override
		public String toString() {
			return "organizer";
		}
	}, 
	
	EVENT_PARTICIPANT{
		@Override
		public String toString() {
			return "participant";
		}
	}; 
}