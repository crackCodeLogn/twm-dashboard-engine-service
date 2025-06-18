package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.ModeDto;
import com.vv.personal.twm.dashboard.engine.model.Mode;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface ModeService {
  Optional<ModeDto> getModeDto(int id);

  Optional<Mode> getMode(int id);

  List<ModeDto> getModesDto();

  List<Mode> getModes();
}
