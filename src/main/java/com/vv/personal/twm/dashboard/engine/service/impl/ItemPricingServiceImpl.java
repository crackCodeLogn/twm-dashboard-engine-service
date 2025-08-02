package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.artifactory.generated.itemPricing.ItemPricingProto;
import com.vv.personal.twm.dashboard.engine.service.ItemPricingService;
import com.vv.personal.twm.dashboard.engine.util.CsvDownloaderUtil;
import com.vv.personal.twm.dashboard.engine.util.TextReaderUtil;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 7/31/25
 */
@Slf4j
@Service
public class ItemPricingServiceImpl implements ItemPricingService {

  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final NumberFormat DOLLAR_NUMBER_FORMAT =
      NumberFormat.getCurrencyInstance(Locale.US);

  @Override
  public String downloadItemPricingSheet(String csvUrl) {
    return CsvDownloaderUtil.downloadCsv(csvUrl);
  }

  @Override
  public List<ItemPricingProto.ItemPricingRecord> extractRecords(
      String fileLocation, int startDate, int endDateInclusive) {
    List<ItemPricingProto.ItemPricingRecord> itemPricingRecords = new ArrayList<>();

    List<List<String>> csvLines = TextReaderUtil.readCsvLines(fileLocation);

    try {
      for (List<String> fields : csvLines.subList(1, csvLines.size())) {

        if (fields.size() < 7) {
          log.warn("Not enough columns in record: {}", fields);
          continue;
        }

        int date = getIntDate(fields.get(4));
        if (date < startDate || date > endDateInclusive) {
          log.debug("Skipping record outside of interest: {}", date);
          continue;
        }

        Double money = readMoney(fields.get(2));
        if (Double.isNaN(money)) {
          log.error("Cannot parse money in record: {}", fields);
          continue;
        }

        ItemPricingProto.ItemPricingRecord itemPricingRecord =
            ItemPricingProto.ItemPricingRecord.newBuilder()
                .setItem(fields.get(0))
                .setQuantity(fields.get(1))
                .setPrice(money)
                .setSource(fields.get(3))
                .setDate(date)
                .setLocation(fields.get(5))
                .setCategory(fields.get(6))
                //                .setNote(fields[7]) // ignoring for now
                .build();
        System.out.println(itemPricingRecord);
        itemPricingRecords.add(itemPricingRecord);
      }

    } catch (Exception e) {
      log.error("Failed to read data from {}.", fileLocation, e);
    }

    return itemPricingRecords;
  }

  private Double readMoney(String field) {
    try {
      Number number = DOLLAR_NUMBER_FORMAT.parse(field);
      return number.doubleValue();
    } catch (Exception e) {
      log.error("Failed to parse money from {}.", field, e);
    }
    return Double.NaN;
  }

  private int getIntDate(String field) {
    LocalDate localDate = LocalDate.parse(field, DTF);
    return localDate.getYear() * 10000
        + localDate.getMonthValue() * 100
        + localDate.getDayOfMonth();
  }

  @Override
  public ItemPricingProto.ItemPricingStats extractStatsFromRecords(
      List<ItemPricingProto.ItemPricingRecord> itemPricingRecords) {
    // extract category based spend info
    Map<String, Double> categoryPrices = new HashMap<>();
    Map<String, Integer> lookUpIndex = new HashMap<>();
    int index = 0;
    List<Double> counter = new ArrayList<>();
    for (ItemPricingProto.ItemPricingRecord record : itemPricingRecords) {
      if (!lookUpIndex.containsKey(record.getCategory())) {
        lookUpIndex.put(record.getCategory(), index++);
        counter.add(0.0);
      }

      counter.set(
          lookUpIndex.get(record.getCategory()),
          counter.get(lookUpIndex.get(record.getCategory())) + record.getPrice());
    }

    // extract total spent and populate category-price map
    double totalSpent = 0.0;
    for (Map.Entry<String, Integer> entry : lookUpIndex.entrySet()) {
      categoryPrices.put(entry.getKey(), counter.get(entry.getValue()));
      totalSpent += counter.get(entry.getValue());
    }

    // extract category 'extra' based spend info
    Map<String, Double> categoryExtraPrices = new HashMap<>();
    for (ItemPricingProto.ItemPricingRecord record : itemPricingRecords) {
      if ("extra".equalsIgnoreCase(record.getCategory())) {
        // lesser subset
        String key = record.getSource().toLowerCase();
        categoryExtraPrices.put(
            key, categoryExtraPrices.getOrDefault(key, 0.0) + record.getPrice());
      }
    }

    // group together transactions at a store on a day and show up as an aggr sum total for that
    // store visit on day
    List<ItemPricingProto.ItemPricingRecord.Builder> groupedRecordsBuilder = new ArrayList<>();
    Map<ItemPricingRecordKey, ItemPricingProto.ItemPricingRecord.Builder> localCache =
        new HashMap<>();
    for (ItemPricingProto.ItemPricingRecord record : itemPricingRecords) {
      ItemPricingRecordKey key =
          new ItemPricingRecordKey(
              record.getSource(), record.getDate(), record.getLocation(), record.getCategory());

      if (!localCache.containsKey(key)) {
        ItemPricingProto.ItemPricingRecord.Builder newGroupedRecordBuilder =
            ItemPricingProto.ItemPricingRecord.newBuilder().mergeFrom(record);
        localCache.put(key, newGroupedRecordBuilder);
        groupedRecordsBuilder.add(newGroupedRecordBuilder);
      } else {
        ItemPricingProto.ItemPricingRecord.Builder builder = localCache.get(key);
        builder.setPrice(builder.getPrice() + record.getPrice());
        builder.setQuantity("");
        builder.setItem("");
      }
    }

    return ItemPricingProto.ItemPricingStats.newBuilder()
        .setTotalSpent(totalSpent)
        .putAllCategoryTotalPriceMap(categoryPrices)
        .putAllCategoryExtraAggregateMap(categoryExtraPrices)
        .addAllItemPricingRecords(
            groupedRecordsBuilder.stream()
                .map(ItemPricingProto.ItemPricingRecord.Builder::build)
                .collect(Collectors.toList()))
        .build();
  }

  private record ItemPricingRecordKey(String source, int date, String location, String category) {

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ItemPricingRecordKey other)
        return source.equals(other.source)
            && date == other.date
            && location.equals(other.location)
            && category.equals(other.category);
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(source, date, location, category);
    }
  }
}
