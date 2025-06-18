package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.SessionDto;
import com.vv.personal.twm.dashboard.engine.model.Session;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface SessionService {

  Optional<Session> getSession(SessionDto sessionDto);
}
