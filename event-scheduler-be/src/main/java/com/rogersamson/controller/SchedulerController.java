package com.rogersamson.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rogersamson.entity.SessionEntity;
import com.rogersamson.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SchedulerController {
	
	@Autowired
	private SchedulerService schedulerService;
	
	@PostMapping("/create-session")
	public Object createSession(@RequestBody SessionEntity session) throws Exception{
		log.info("REQUEST : {}", session);
		Set<SessionEntity> sessions = (Set<SessionEntity>) schedulerService.createSession(session);
		return sessions;
	}

	@GetMapping("/fetch-sessions/sort-by-vip")
	public Object fetchSessionsSortByVip(@RequestParam String startDateTime, @RequestParam  String endDateTime) {
		log.info("PARAM : START ({}) to END({})",   startDateTime, endDateTime);
		return schedulerService.fetchSessionSortByVip(startDateTime, endDateTime);
	}
	
	@GetMapping("/fetch-sessions/sort-by-priority")
	public Object fetchSessionsSortByPriority(@RequestParam String startDateTime, @RequestParam  String endDateTime) {
		log.info("PARAM : START ({}) to END({})",   startDateTime, endDateTime);
		return schedulerService.fetchSessionsSortByPriority(startDateTime, endDateTime);
	}
	
	@GetMapping("/fetch-sessions/sort-by-time")
	public Object fetchSessionsSortByTime(@RequestParam String startDateTime, @RequestParam  String endDateTime) {
		log.info("PARAM : START ({}) to END({})",   startDateTime, endDateTime);
		return schedulerService.fetchSessionsSortByTime(startDateTime, endDateTime);
	}

}
