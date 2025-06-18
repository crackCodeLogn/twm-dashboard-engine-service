package com.vv.personal.twm.dashboard.engine.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDataDto {
  private int modeId;
  private String student;
  private int subjectId;
  private LocalDateTime sessionDate;
  private String data;

  @Override
  public String toString() {
    return String.format(
        "[%d %s %d => %s:%s]", modeId, student, subjectId, sessionDate, data.trim());
  }
}
