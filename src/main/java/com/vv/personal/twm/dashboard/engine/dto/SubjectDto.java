package com.vv.personal.twm.dashboard.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
  private int id;
  private String name;
  private int modeId;

  @Override
  public String toString() {
    return String.format("[%s, %d]", name, modeId);
  }
}
