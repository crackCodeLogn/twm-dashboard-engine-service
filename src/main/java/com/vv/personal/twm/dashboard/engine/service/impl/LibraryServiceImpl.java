package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dto.LibraryDataDto;
import com.vv.personal.twm.dashboard.engine.model.LibraryData;
import com.vv.personal.twm.dashboard.engine.service.LibraryService;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Service
public class LibraryServiceImpl implements LibraryService {

  @Override
  public LibraryData getLibraryData(LibraryDataDto libraryDataDto) {
    return LibraryData.builder()
        .bookName(libraryDataDto.getBookName())
        .borrowDate(libraryDataDto.getBorrowDate())
        .returnedDate(libraryDataDto.getReturnedDate())
        .returnDate(libraryDataDto.getReturnDate())
        .build();
  }
}
