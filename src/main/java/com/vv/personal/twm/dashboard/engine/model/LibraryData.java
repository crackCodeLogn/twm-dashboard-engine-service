package com.vv.personal.twm.dashboard.engine.model;

import java.time.LocalDateTime;
import lombok.*;
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
public class LibraryData {
  @NonNull private String bookName;
  private LocalDateTime borrowDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedDate;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("BookName", bookName)
        .append("BorrowDate", borrowDate)
        .append("ReturnDate", returnDate)
        .append("ReturnedDate", returnedDate)
        .toString();
  }
}
