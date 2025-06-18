package com.vv.personal.twm.dashboard.engine.repository;

import com.vv.personal.twm.dashboard.engine.model.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {}
