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
public class ExpiryDataDto {
  private LocalDateTime date;
  private String data;

  @Override
  public String toString() {
    return String.format("[%s:%s]", date, data.trim());
  }
}
