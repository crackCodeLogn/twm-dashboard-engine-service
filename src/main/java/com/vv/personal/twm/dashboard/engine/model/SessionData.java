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
public class SessionData {
  private int id;
  private Mode mode;
  private String student;
  private Subject subject;
  private LocalDateTime sessionDate;
  private String data;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("mode", mode)
        .append("student", student)
        .append("subject", subject)
        .append("sessionDate", sessionDate)
        .append("data", data)
        .toString();
  }
}
