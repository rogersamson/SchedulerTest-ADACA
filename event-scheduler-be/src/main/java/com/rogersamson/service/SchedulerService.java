package com.rogersamson.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rogersamson.entity.SessionEntity;
import com.rogersamson.utility.SchedulerUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

	private Set<SessionEntity> sessions = new HashSet<SessionEntity>();

	@Value("${thread-pool-number}") // Get Thread Pool Number form Property File
	private int threadNumber;

	@Autowired
	private SchedulerUtility schedulerUtility;

	public Object createSession(SessionEntity session) throws Exception {
		boolean conflict = checkSchedule(session, sessions);
		if (conflict) {
			log.info("CONFLICT is {}", conflict);
			throw new BadRequestException("Start and End Time Error/Conflict");
		} else {
			sessions.add(session);
			log.info("SESSIONS : {}", sessions);
		}
		return sessions;
	}

	// Thread Pool To Check the Conflict
	public Boolean checkSchedule(SessionEntity session, Set<SessionEntity> sessions) throws Exception {

		boolean conflict = false;
		try {
			log.info("NUMBER OF THREAD : {}", threadNumber);
			ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
			Future<Boolean> future = executorService.submit(() -> schedulerUtility.checkConflict(session, sessions));
			conflict = future.get();
		} catch (InterruptedException e) {
			throw new Exception();
		} catch (ExecutionException e) {
			throw new Exception();
		}
		return conflict;
	}

	public List<SessionEntity> fetchSessionSortByVip(String startDateTime, String endDateTime) {
		Set<SessionEntity> filteredSessions = schedulerUtility.filterByStartDateTime(sessions, startDateTime, endDateTime);
		 return schedulerUtility.sortByVip(filteredSessions );
	}

	public List<SessionEntity> fetchSessionsSortByPriority(String startDateTime, String endDateTime) {
		Set<SessionEntity> filteredSessions = schedulerUtility.filterByStartDateTime(sessions, startDateTime, endDateTime);
		return schedulerUtility.sortByPriority(filteredSessions);
	}

	public List<SessionEntity> fetchSessionsSortByTime(String startDateTime, String endDateTime) {
		Set<SessionEntity> filteredSessions = schedulerUtility.filterByStartDateTime(sessions, startDateTime, endDateTime);
		return schedulerUtility.sortByStartTime(filteredSessions);
	}

}
