package com.vv.personal.twm.dashboard.engine.remote.controller;

import com.vv.personal.twm.dashboard.engine.dto.ExpiryDataDto;
import com.vv.personal.twm.dashboard.engine.model.ExpiryData;
import com.vv.personal.twm.dashboard.engine.remote.feign.CalendarFeign;
import com.vv.personal.twm.dashboard.engine.service.ExpiryService;
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
@RestController("expiry")
@Controller
@RequestMapping("/expiry")
@CrossOrigin(origins = "http://localhost:5173") // Allow React app
@RequiredArgsConstructor
public class ExpiryController {

  private final CalendarFeign calendarFeign;
  private final ExpiryService expiryService;

  @PostMapping("/")
  public ResponseEntity<String> expire(@RequestBody ExpiryDataDto expiryDataDto) {
    log.info("Received request to set expiry for {}", expiryDataDto);
    ExpiryData expiryData = expiryService.getExpiryData(expiryDataDto);
    String expiryDataJson = expiryData.toString();

    try {
      ResponseEntity<String> expire = calendarFeign.expire(expiryDataJson);
      log.info("{}, {}", expire.getStatusCode(), expire.getBody());
      if (expire.getStatusCode() == HttpStatus.OK) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.badRequest().body(expire.getBody());
      }
    } catch (Exception e) {
      log.error("Error occurred while setting expiry for {}", expiryDataJson, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
