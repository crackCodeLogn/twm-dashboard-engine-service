package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.LibraryDataDto;
import com.vv.personal.twm.dashboard.engine.model.LibraryData;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface LibraryService {

  LibraryData getLibraryData(LibraryDataDto libraryDataDto);
}
