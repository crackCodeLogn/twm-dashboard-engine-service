package com.vv.personal.twm.dashboard.engine.dao.impl;

import com.vv.personal.twm.dashboard.engine.dao.SubjectDao;
import com.vv.personal.twm.dashboard.engine.model.entity.SubjectEntity;
import com.vv.personal.twm.dashboard.engine.repository.SubjectRepository;
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
public class SubjectDaoImpl implements SubjectDao {

  private final SubjectRepository subjectRepository;

  @Override
  public Optional<SubjectEntity> getSubject(int id) {
    return subjectRepository.findById(id);
  }

  @Override
  public List<SubjectEntity> getSubjects() {
    return subjectRepository.findAll();
  }
}
