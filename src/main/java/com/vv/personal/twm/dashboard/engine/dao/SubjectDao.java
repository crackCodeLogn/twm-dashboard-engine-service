package com.vv.personal.twm.dashboard.engine.dao;

import com.vv.personal.twm.dashboard.engine.model.entity.SubjectEntity;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface SubjectDao {

  Optional<SubjectEntity> getSubject(int id);

  List<SubjectEntity> getSubjects();
}
