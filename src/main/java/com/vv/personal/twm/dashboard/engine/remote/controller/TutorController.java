package com.vv.personal.twm.dashboard.engine.remote.controller;

import com.vv.personal.twm.dashboard.engine.dto.*;
import com.vv.personal.twm.dashboard.engine.model.Session;
import com.vv.personal.twm.dashboard.engine.model.SessionData;
import com.vv.personal.twm.dashboard.engine.remote.feign.CalendarFeign;
import com.vv.personal.twm.dashboard.engine.service.ModeService;
import com.vv.personal.twm.dashboard.engine.service.SessionDataService;
import com.vv.personal.twm.dashboard.engine.service.SessionService;
import com.vv.personal.twm.dashboard.engine.service.SubjectService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Slf4j
@RestController("tutor")
@Controller
@RequestMapping("/tutor")
@CrossOrigin(origins = "http://localhost:5173") // Allow React app
@RequiredArgsConstructor
public class TutorController {

  private final CalendarFeign calendarFeign;
  private final ModeService modeService;
  private final SubjectService subjectService;
  private final SessionService sessionService;
  private final SessionDataService sessionDataService;

  @GetMapping("/modes")
  public ResponseEntity<List<ModeDto>> getModes() {
    log.info("Got request to get all modes");

    try {
      List<ModeDto> modesDto = modeService.getModesDto();
      log.info("Retrieved {} modes", modesDto.size());
      return ResponseEntity.ok(modesDto);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/subjects")
  public ResponseEntity<List<SubjectDto>> getSubjects() {
    log.info("Got request to get all subjects");

    try {
      List<SubjectDto> subjectDtos = subjectService.getSubjectsDto();
      log.info("Retrieved {} subjects", subjectDtos.size());
      return ResponseEntity.ok(subjectDtos);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/session")
  public ResponseEntity<String> postSession(@RequestBody SessionDto sessionDto) {
    log.info("Received request to create a new session {}", sessionDto);
    Optional<Session> session = sessionService.getSession(sessionDto);

    if (session.isPresent()) {
      String sessionJson = session.get().toString();
      try {
        ResponseEntity<String> response = calendarFeign.session(sessionJson);
        if (response.getStatusCode() == HttpStatus.OK) {
          return ResponseEntity.ok().build();
        }
      } catch (Exception e) {
        log.error("Error occurred while creating a new session {}", sessionJson, e);
      }
    } else {
      log.error("Failed to create a new session {}", sessionDto);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping("/session/data")
  public ResponseEntity<String> postSessionData(@RequestBody SessionDataDto sessionDataDto) {
    log.info("Received request to create new session data {}", sessionDataDto);
    Optional<SessionData> sessionData = sessionDataService.getSessionData(sessionDataDto);
    if (sessionData.isPresent()) {
      try {
        sessionDataService.writeDataToLocalDisk(sessionData.get());
        return ResponseEntity.ok().build();
      } catch (Exception e) {
        log.error("Error occurred while creating a new session data {}", sessionDataDto, e);
      }
    } else {
      log.error("Failed to create new session data {}", sessionDataDto);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/session/data")
  public ResponseEntity<GetSessionDto> getLatestSessionData(@RequestParam String student) {
    student = student.toLowerCase().strip();
    log.info("Received request to read latest session data of {}", student);
    Optional<GetSessionDto> getSessionDto = sessionDataService.getLatestSessionData(student);

    if (getSessionDto.isPresent()) {
      try {
        return ResponseEntity.ok(getSessionDto.get());
      } catch (Exception e) {
        log.error("Error occurred while reading latest session data pf {}", student, e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      log.warn("Did not find any session data of {}", student);
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
