package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;


@RestController
@RequestMapping("/meetings")
//localhost:8080
//POST localhost:8080/participants
//localhost:8080/meetings
public class MeetingRestController {

	
	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	// GET localhost:8080/meetings
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	
//GET  localhost:8080/participants/3

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	// POST localhost:8080/participants
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
		if (meetingService.findById(meeting.getId())!=null) {
			return new ResponseEntity("Unable to create. A meeting with login " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
		
	}

	// DELETE localhost:8080/participants/user2
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
	if (meeting == null) { 
	return new ResponseEntity(HttpStatus.NOT_FOUND);
	} 
	meetingService.delete(meeting);
	return new ResponseEntity<Meeting>(HttpStatus.OK);
	}
	
	// Put localhost:8080/participants/user2
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting meeting) {
	    Meeting foundMeeting = meetingService.findById(id);
	if (foundMeeting == null) { 
	return new ResponseEntity(HttpStatus.NOT_FOUND);
	} 
	foundMeeting.setTitle(meeting.getTitle());
	foundMeeting.setDescription(meeting.getDescription());
	foundMeeting.setDate(meeting.getDate());
	meetingService.update(foundMeeting);
	
	
	
	return new ResponseEntity<Meeting>(HttpStatus.OK);
	}
	
}


