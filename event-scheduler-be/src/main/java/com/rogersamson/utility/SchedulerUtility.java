package com.rogersamson.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rogersamson.entity.SessionEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerUtility {
	
	private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// Checker Function for Schedule Conflict
	public synchronized boolean checkConflict(SessionEntity sessionNew, Set<SessionEntity> sessions)  {
		
		boolean conflict = false;
		LocalDateTime startDateTimeNew = LocalDateTime.parse(sessionNew.getStartTime(), formatter);
		LocalDateTime endDateTimeNew = LocalDateTime.parse(sessionNew.getEndTime(), formatter);
		
		if(startDateTimeNew.isEqual(endDateTimeNew) || endDateTimeNew.isBefore(startDateTimeNew)) {
			conflict = true;
//			throw new BadRequestException("Start and End Time Error");
			return conflict;
		}
		
		log.info("START : {}. END : {})", startDateTimeNew, endDateTimeNew);
		List<SessionEntity> sortedList = sortByStartTime(sessions);
		for (SessionEntity session : sortedList) {

			if (startDateTimeNew.isBefore(LocalDateTime.parse(session.getStartTime(), formatter))) {
				log.info("IS BEFORE");
				if (endDateTimeNew.isAfter(LocalDateTime.parse(session.getStartTime(), formatter))) {
					conflict = true;
					return conflict;
				}
			}

			if (startDateTimeNew.isAfter(LocalDateTime.parse(session.getStartTime(), formatter))) {
				log.info("IS AFTER");
				if (startDateTimeNew.isBefore(LocalDateTime.parse(session.getEndTime(), formatter))
						|| startDateTimeNew.isEqual(LocalDateTime.parse(session.getEndTime(), formatter))) {
					conflict = true;
					return conflict;
				}

			}

			if (startDateTimeNew.isEqual(LocalDateTime.parse(session.getStartTime(), formatter))) {
				log.info("IS EQUAL");
				conflict = true;
				return conflict;
			}
			log.info("SESSION : {} ",session);
		}
		return conflict;
	}

	public List<SessionEntity>  sortByVip(Set<SessionEntity> sessions) {
		// Convert Set to List
		List<SessionEntity> sortedList = new ArrayList<>(sessions);
		// Sort the List
		Collections.sort(sortedList);

		for (SessionEntity session : sortedList) {
			System.out.println(session);
		}
		return sortedList;
	}

	public List<SessionEntity>  sortByPriority(Set<SessionEntity> sessions) {
		List<SessionEntity> priorityList = sessions.stream().sorted(Comparator.comparingInt(SessionEntity::getPriority))
				.collect(Collectors.toList());
		for (SessionEntity session : priorityList) {
			log.info("SESSION : {} ",session);
		}
		return priorityList;
	}

	public List<SessionEntity>  sortByStartTime(Set<SessionEntity> sessions) {
		List<SessionEntity> startTimeList = sessions.stream().sorted(Comparator.comparing(SessionEntity::getStartTime))
				.collect(Collectors.toList());
		for (SessionEntity session : startTimeList) {
			log.info("SESSION : {} ",session);
		}
		return startTimeList;
	}

	public Set<SessionEntity> filterByStartDateTime(Set<SessionEntity> sessions, String startDateTime, String endDateTime) {
		LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
		LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);

		Set<SessionEntity> filteredSessions = sessions.stream()
				.filter(session -> !(LocalDateTime.parse(session.getStartTime(), formatter).isBefore(start))
						&& !(LocalDateTime.parse(session.getStartTime(), formatter).isAfter(end)))
				.collect(Collectors.toSet());
		return filteredSessions;
		
	}

}
