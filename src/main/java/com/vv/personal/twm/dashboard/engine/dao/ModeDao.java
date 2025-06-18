package com.vv.personal.twm.dashboard.engine.dao;

import com.vv.personal.twm.dashboard.engine.model.entity.ModeEntity;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface ModeDao {
  Optional<ModeEntity> getMode(int id);

  List<ModeEntity> getModes();
}
