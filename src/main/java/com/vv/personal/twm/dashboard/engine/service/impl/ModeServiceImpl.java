package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dao.ModeDao;
import com.vv.personal.twm.dashboard.engine.dto.ModeDto;
import com.vv.personal.twm.dashboard.engine.model.Mode;
import com.vv.personal.twm.dashboard.engine.model.entity.ModeEntity;
import com.vv.personal.twm.dashboard.engine.service.ModeService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
public class ModeServiceImpl implements ModeService {

  private final ModeDao modeDao;

  @Override
  public Optional<ModeDto> getModeDto(int id) {
    return modeDao.getMode(id).map(this::generateModeDto);
  }

  @Override
  public Optional<Mode> getMode(int id) {
    return modeDao.getMode(id).map(this::generateMode);
  }

  @Override
  public List<ModeDto> getModesDto() {
    return modeDao.getModes().stream().map(this::generateModeDto).collect(Collectors.toList());
  }

  @Override
  public List<Mode> getModes() {
    return modeDao.getModes().stream().map(this::generateMode).collect(Collectors.toList());
  }

  private ModeDto generateModeDto(ModeEntity modeEntity) {
    return ModeDto.builder()
        .id(modeEntity.getId())
        .name(modeEntity.getSessionMode())
        .color(modeEntity.getColor())
        .build();
  }

  private Mode generateMode(ModeEntity modeEntity) {
    return Mode.builder()
        .sessionMode(modeEntity.getSessionMode())
        .color(modeEntity.getColor())
        .build();
  }
}
