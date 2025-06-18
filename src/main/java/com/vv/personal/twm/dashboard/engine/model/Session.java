package com.vv.personal.twm.dashboard.engine.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
  private int id;
  private Mode mode;
  private String student;
  private Subject subject;
  private LocalDateTime sessionDate;
  private int sessionStartTime;
  private int sessionLengthInMinutes;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("Mode", mode)
        .append("Student", student)
        .append("Subject", subject)
        .append("SessionDate", sessionDate)
        .append("SessionStartTime", sessionStartTime)
        .append("SessionLengthInMinutes", sessionLengthInMinutes)
        .toString();
  }
}
