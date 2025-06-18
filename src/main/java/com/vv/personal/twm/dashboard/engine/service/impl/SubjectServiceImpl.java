package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dao.SubjectDao;
import com.vv.personal.twm.dashboard.engine.dto.SubjectDto;
import com.vv.personal.twm.dashboard.engine.model.Subject;
import com.vv.personal.twm.dashboard.engine.model.entity.SubjectEntity;
import com.vv.personal.twm.dashboard.engine.service.SubjectService;
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
public class SubjectServiceImpl implements SubjectService {
  private final SubjectDao subjectDao;

  @Override
  public Optional<SubjectDto> getSubjectDto(int id) {
    return subjectDao.getSubject(id).map(this::generateSubjectDto);
  }

  @Override
  public Optional<Subject> getSubject(int id) {
    return subjectDao.getSubject(id).map(this::generateSubject);
  }

  @Override
  public List<SubjectDto> getSubjectsDto() {
    return subjectDao.getSubjects().stream()
        .map(this::generateSubjectDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<Subject> getSubjects() {
    return subjectDao.getSubjects().stream()
        .map(this::generateSubject)
        .collect(Collectors.toList());
  }

  private SubjectDto generateSubjectDto(SubjectEntity subjectEntity) {
    return SubjectDto.builder()
        .id(subjectEntity.getId())
        .name(subjectEntity.getSessionSubject())
        .modeId(subjectEntity.getModeId())
        .build();
  }

  private Subject generateSubject(SubjectEntity subjectEntity) {
    return Subject.builder()
        .sessionSubject(subjectEntity.getSessionSubject())
        .modeId(subjectEntity.getModeId())
        .build();
  }
}
