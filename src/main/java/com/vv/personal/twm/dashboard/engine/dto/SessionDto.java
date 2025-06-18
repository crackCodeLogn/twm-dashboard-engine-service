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
public class SessionDto {
  private int modeId;
  private String student;
  private int subjectId;
  private LocalDateTime sessionDate;
  private int sessionStartTime;
  private int sessionLengthInMinutes;

  @Override
  public String toString() {
    return String.format(
        "[%d %s %d => %s:%d %d]",
        modeId, student, subjectId, sessionDate, sessionStartTime, sessionLengthInMinutes);
  }
}
