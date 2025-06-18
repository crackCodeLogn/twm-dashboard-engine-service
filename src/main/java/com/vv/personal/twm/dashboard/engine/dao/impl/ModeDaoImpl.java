package com.vv.personal.twm.dashboard.engine.dao.impl;

import com.vv.personal.twm.dashboard.engine.dao.ModeDao;
import com.vv.personal.twm.dashboard.engine.model.entity.ModeEntity;
import com.vv.personal.twm.dashboard.engine.repository.ModeRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@AllArgsConstructor
@Component
public class ModeDaoImpl implements ModeDao {

  private final ModeRepository modeRepository;

  @Override
  public Optional<ModeEntity> getMode(int id) {
    return modeRepository.findById(id);
  }

  @Override
  public List<ModeEntity> getModes() {
    return modeRepository.findAll();
  }
}
