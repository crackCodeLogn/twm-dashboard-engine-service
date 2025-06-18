package com.vv.personal.twm.dashboard.engine.model;

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
public class Subject {
  private String sessionSubject;
  private int modeId;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("SessionSubject", sessionSubject)
        .append("ModeId", modeId)
        .toString();
  }
}
