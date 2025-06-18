package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.GetSessionDto;
import com.vv.personal.twm.dashboard.engine.dto.SessionDataDto;
import com.vv.personal.twm.dashboard.engine.model.SessionData;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface SessionDataService {

  Optional<SessionData> getSessionData(SessionDataDto sessionDataDto);

  void writeDataToLocalDisk(SessionData sessionData);

  Optional<GetSessionDto> getLatestSessionData(String student);
}
