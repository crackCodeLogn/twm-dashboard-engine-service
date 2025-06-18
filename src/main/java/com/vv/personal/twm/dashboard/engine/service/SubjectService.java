package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.SubjectDto;
import com.vv.personal.twm.dashboard.engine.model.Subject;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface SubjectService {
  Optional<SubjectDto> getSubjectDto(int id);

  Optional<Subject> getSubject(int id);

  List<SubjectDto> getSubjectsDto();

  List<Subject> getSubjects();
}
