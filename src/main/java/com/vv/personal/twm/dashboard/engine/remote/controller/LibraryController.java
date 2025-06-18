package com.vv.personal.twm.dashboard.engine.remote.controller;

import com.vv.personal.twm.dashboard.engine.dto.LibraryDataDto;
import com.vv.personal.twm.dashboard.engine.model.LibraryData;
import com.vv.personal.twm.dashboard.engine.remote.feign.CalendarFeign;
import com.vv.personal.twm.dashboard.engine.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Slf4j
@RestController("library")
@Controller
@RequestMapping("/library")
@CrossOrigin(origins = "http://localhost:5173") // Allow React app
@RequiredArgsConstructor
public class LibraryController {

  private final CalendarFeign calendarFeign;
  private final LibraryService libraryService;

  @PostMapping("/")
  public ResponseEntity<String> postLibraryData(@RequestBody LibraryDataDto libraryDataDto) {
    log.info("Received request to set library data for {}", libraryDataDto);
    LibraryData libraryData = libraryService.getLibraryData(libraryDataDto);
    String libraryDataJson = libraryData.toString();

    try {
      ResponseEntity<String> expire = calendarFeign.libraryData(libraryDataJson);
      log.info("{}, {}", expire.getStatusCode(), expire.getBody());
      if (expire.getStatusCode() == HttpStatus.OK) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.badRequest().body(expire.getBody());
      }
    } catch (Exception e) {
      log.error("Error occurred while setting expiry for {}", libraryDataJson, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
