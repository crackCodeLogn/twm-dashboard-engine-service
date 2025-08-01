package com.vv.personal.twm.dashboard.engine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vivek
 * @since 2025-07-31
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "locations")
public class FileLocationConfig {
  private String itemPricing;
}
