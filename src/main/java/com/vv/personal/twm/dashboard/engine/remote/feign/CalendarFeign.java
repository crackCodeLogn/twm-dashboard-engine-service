package com.vv.personal.twm.dashboard.engine.remote.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Vivek
 * @since 2024-08-11
 */
@FeignClient(name = "flask-calendar", url = "http://localhost:8089/cal")
public interface CalendarFeign {

  @PostMapping(value = "/expiry", produces = "application/json", consumes = "application/json")
  ResponseEntity<String> expire(@RequestBody String expiryContent);

  @PostMapping(value = "/lib", produces = "application/json", consumes = "application/json")
  ResponseEntity<String> libraryData(@RequestBody String libraryContent);

  @PostMapping(value = "/session", produces = "application/json", consumes = "application/json")
  ResponseEntity<String> session(@RequestBody String sessionContent);
}
