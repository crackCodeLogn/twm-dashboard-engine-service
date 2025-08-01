package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.artifactory.generated.itemPricing.ItemPricingProto;
import java.util.List;

/**
 * @author Vivek
 * @since 7/31/25
 */
public interface ItemPricingService {

  String downloadItemPricingSheet(String csvUrl);

  List<ItemPricingProto.ItemPricingRecord> extractRecords(
      String fileLocation, int startDate, int endDateInclusive);

  ItemPricingProto.ItemPricingStats extractStatsFromRecords(
      List<ItemPricingProto.ItemPricingRecord> itemPricingRecords);
}
