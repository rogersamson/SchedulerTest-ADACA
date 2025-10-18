package com.rogersamson;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rogersamson.entity.SessionEntity;
import com.rogersamson.service.SchedulerService;

@SpringBootTest
public class SchedulerServiceTests {

	@Autowired
	private SchedulerService schedulerService;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@BeforeEach
	void populate() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 06:00:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);

		SessionEntity s2 = new SessionEntity();
		s2.setTitle("ANGULAR");
		s2.setPriority(3);
		s2.setStartTime("2025-10-16 06:31:00");
		s2.setEndTime("2025-10-16 07:00:00");
		s2.setSpeaker("ROGER");
		s2.setVip(1);

		SessionEntity s3 = new SessionEntity();
		s3.setTitle("REACT");
		s3.setPriority(1);
		s3.setStartTime("2025-10-16 07:01:00");
		s3.setEndTime("2025-10-16 07:30:00");
		s3.setSpeaker("SAMSON");
		s3.setVip(0);

		SessionEntity s4 = new SessionEntity();
		s4.setTitle("PHP");
		s4.setPriority(4);
		s4.setStartTime("2025-10-16 08:00:00");
		s4.setEndTime("2025-10-16 08:30:00");
		s4.setSpeaker("LANI");
		s4.setVip(0);

		SessionEntity s5 = new SessionEntity();
		s5.setTitle("PYTHON");
		s5.setPriority(2);
		s5.setStartTime("2025-10-16 08:31:00");
		s5.setEndTime("2025-10-16 09:00:00");
		s5.setSpeaker("ALLAN");
		s5.setVip(0);

		SessionEntity s6 = new SessionEntity();
		s6.setTitle("RUST");
		s6.setPriority(5);
		s6.setStartTime("2025-10-16 19:00:00");
		s6.setEndTime("2025-10-16 20:00:00");
		s6.setSpeaker("TUAZON");
		s6.setVip(1);

		try {
			schedulerService.createSession(s1);
			schedulerService.createSession(s2);
			schedulerService.createSession(s3);
			schedulerService.createSession(s4);
			schedulerService.createSession(s5);
			schedulerService.createSession(s6);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testSortByTVipTest() {
		List<SessionEntity> sessions = schedulerService.fetchSessionSortByVip(
				LocalDateTime.now().minusMonths(1).format(formatter).toString(), LocalDateTime.now().plusMonths(1).format(formatter).toString());
		assertTrue(sessions.get(0).getVip() == 1);
		assertTrue(sessions.get(sessions.size() - 1).getVip() == 0);
	}

	@Test
	void testCreateSessionTest() {
		List<SessionEntity> sessions = schedulerService.fetchSessionsSortByTime(
				LocalDateTime.now().minusMonths(1).format(formatter).toString(), LocalDateTime.now().plusMonths(1).format(formatter).toString());
		assertTrue(sessions.size() == 6);
	}

	@Test
	void testSortByTimeTest() {
		List<SessionEntity> sessions = schedulerService.fetchSessionsSortByTime(
				LocalDateTime.now().minusMonths(1).format(formatter).toString(), LocalDateTime.now().plusMonths(1).format(formatter).toString());
		assertTrue(sessions.get(0).getStartTime().equalsIgnoreCase("2025-10-16 06:00:00"));
		assertTrue(sessions.get(sessions.size() - 1).getStartTime().equalsIgnoreCase("2025-10-16 19:00:00"));
	}

	@Test
	void testSortByPriorityTest() {
		List<SessionEntity> sessions = schedulerService.fetchSessionsSortByPriority(
				LocalDateTime.now().minusMonths(1).format(formatter).toString(), LocalDateTime.now().plusMonths(1).format(formatter).toString());
		assertTrue(sessions.get(0).getPriority() == 1);
	}

	@Test
	void createSessionConflict() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 06:00:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);
		try {
			schedulerService.createSession(s1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(("Start and End Time Error/Conflict")));
		}
	}

	@Test
	void createSessionIsBeforeTest() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 05:59:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);
		try {
			schedulerService.createSession(s1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(("Start and End Time Error/Conflict")));
		}
	}

	@Test
	void createSessionIsEqualTest() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 05:59:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);
		try {
			schedulerService.createSession(s1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(("Start and End Time Error/Conflict")));
		}
	}

	@Test
	void createSessionIsEqualStartEndTest() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 06:30:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);
		try {
			schedulerService.createSession(s1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(("Start and End Time Error/Conflict")));
		}
	}

	@Test
	void createSessionIsAfterTest() {
		SessionEntity s1 = new SessionEntity();
		s1.setTitle("JAVA");
		s1.setPriority(5);
		s1.setStartTime("2025-10-16 06:01:00");
		s1.setEndTime("2025-10-16 06:30:00");
		s1.setSpeaker("MENDOZA");
		s1.setVip(0);
		try {
			schedulerService.createSession(s1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(("Start and End Time Error/Conflict")));
		}
	}

}
