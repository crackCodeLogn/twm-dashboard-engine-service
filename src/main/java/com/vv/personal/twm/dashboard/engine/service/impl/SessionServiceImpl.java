package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dto.SessionDto;
import com.vv.personal.twm.dashboard.engine.model.Mode;
import com.vv.personal.twm.dashboard.engine.model.Session;
import com.vv.personal.twm.dashboard.engine.model.Subject;
import com.vv.personal.twm.dashboard.engine.service.ModeService;
import com.vv.personal.twm.dashboard.engine.service.SessionService;
import com.vv.personal.twm.dashboard.engine.service.SubjectService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Slf4j
@AllArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

  private final ModeService modeService;
  private final SubjectService subjectService;

  @Override
  public Optional<Session> getSession(SessionDto sessionDto) {
    Optional<Mode> mode = modeService.getMode(sessionDto.getModeId());
    if (mode.isEmpty()) {
      log.error("Did not find mode for {}", sessionDto.getModeId());
      return Optional.empty();
    }
    Optional<Subject> subject = subjectService.getSubject(sessionDto.getSubjectId());
    if (subject.isEmpty()) {
      log.error("Did not find subject for {}", sessionDto.getSubjectId());
      return Optional.empty();
    }

    return Optional.of(
        Session.builder()
            .mode(mode.get())
            .student(sessionDto.getStudent())
            .subject(subject.get())
            .sessionDate(sessionDto.getSessionDate())
            .sessionStartTime(sessionDto.getSessionStartTime())
            .sessionLengthInMinutes(sessionDto.getSessionLengthInMinutes())
            .build());
  }
}
