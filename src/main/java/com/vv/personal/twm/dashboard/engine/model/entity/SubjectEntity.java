package com.vv.personal.twm.dashboard.engine.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
public class SubjectEntity {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "session_subject")
  private String sessionSubject;

  @Column(name = "mode_id")
  private int modeId;

  @Override
  public String toString() {
    return String.format("[%s, %d]", sessionSubject, modeId);
  }
}
