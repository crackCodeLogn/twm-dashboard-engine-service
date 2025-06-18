package com.vv.personal.twm.dashboard.engine.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDataDto {
  @NonNull private String bookName;
  private LocalDateTime borrowDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedDate;

  @Override
  public String toString() {
    return String.format("[%s:%s,%s,%s]", bookName.trim(), borrowDate, returnDate, returnedDate);
  }
}
