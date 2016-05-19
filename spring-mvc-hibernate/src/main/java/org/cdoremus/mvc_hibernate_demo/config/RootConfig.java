package org.cdoremus.mvc_hibernate_demo.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import org.cdoremus.mvc_hibernate_demo.config.RootConfig.WebPackage;

@Configuration
@Import(JdbcDataConfig.class)
@ComponentScan(basePackages={"org.cdoremus.mvc_hibernate_demo"}, 
    excludeFilters={
        @Filter(type=FilterType.CUSTOM, value=WebPackage.class)
    })
public class RootConfig {
  public static class WebPackage extends RegexPatternTypeFilter {
    public WebPackage() {
      super(Pattern.compile("org.cdoremus.mvc_hibernate_demo\\.web"));
    }    
  }
}
