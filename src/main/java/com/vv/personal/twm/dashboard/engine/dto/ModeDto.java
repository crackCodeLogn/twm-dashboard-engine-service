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
public class ModeDto {
  private int id;
  private String name;
  private String color;

  @Override
  public String toString() {
    return String.format("[%s, %s]", name, color);
  }
}
