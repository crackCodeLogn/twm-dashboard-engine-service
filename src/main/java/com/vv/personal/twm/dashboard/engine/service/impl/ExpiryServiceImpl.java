package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dto.ExpiryDataDto;
import com.vv.personal.twm.dashboard.engine.model.ExpiryData;
import com.vv.personal.twm.dashboard.engine.service.ExpiryService;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Service
public class ExpiryServiceImpl implements ExpiryService {

  @Override
  public ExpiryData getExpiryData(ExpiryDataDto expiryDataDto) {
    return ExpiryData.builder().date(expiryDataDto.getDate()).data(expiryDataDto.getData()).build();
  }
}
