package com.vv.personal.twm.dashboard.engine.remote.controller;

import com.vv.personal.twm.artifactory.generated.itemPricing.ItemPricingProto;
import com.vv.personal.twm.dashboard.engine.config.FileLocationConfig;
import com.vv.personal.twm.dashboard.engine.service.ItemPricingService;
import com.vv.personal.twm.dashboard.engine.util.TextReaderUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vivek
 * @since 7/31/25
 */
@Slf4j
@RestController("item-pricing")
@Controller
@RequestMapping("/item-pricing")
@CrossOrigin(origins = "http://localhost:5173") // Allow React app
@RequiredArgsConstructor
public class ItemPricingController {

  private final FileLocationConfig fileLocationConfig;
  private final ItemPricingService itemPricingService;

  @GetMapping("/extract")
  public ResponseEntity<ItemPricingProto.ItemPricingStats> extractPricingInfo(
      @RequestParam int startDate,
      @RequestParam(required = false, defaultValue = "20991231") int endDate) {

    try {
      List<String> csvLocation = TextReaderUtil.readTextLines(fileLocationConfig.getItemPricing());
      if (csvLocation.isEmpty()) {
        log.error("csv location is empty, cannot proceed with item pricing analysis");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      String fileLocation = itemPricingService.downloadItemPricingSheet(csvLocation.get(0));
      List<ItemPricingProto.ItemPricingRecord> itemPricingRecords =
          itemPricingService.extractRecords(fileLocation, startDate, endDate);
      ItemPricingProto.ItemPricingStats itemPricingStats =
          itemPricingService.extractStatsFromRecords(itemPricingRecords);

      System.out.println(itemPricingStats);
      log.info(
          "Responding with stats aggregating to ${} from {} to {}, with {} records",
          itemPricingStats.getTotalSpent(),
          itemPricingStats.getStartDate(),
          itemPricingStats.getEndDate(),
          itemPricingStats.getItemPricingRecordsCount());

      return new ResponseEntity<>(itemPricingStats, HttpStatus.OK);
    } catch (Exception e) {
      log.error(
          "Failed to perform extraction of item pricing records from {} to {}",
          startDate,
          endDate,
          e);
    }

    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
