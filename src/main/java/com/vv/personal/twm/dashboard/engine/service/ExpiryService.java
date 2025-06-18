package com.vv.personal.twm.dashboard.engine.service;

import com.vv.personal.twm.dashboard.engine.dto.ExpiryDataDto;
import com.vv.personal.twm.dashboard.engine.model.ExpiryData;

/**
 * @author Vivek
 * @since 2025-06-17
 */
public interface ExpiryService {

  ExpiryData getExpiryData(ExpiryDataDto expiryDataDto);
}
