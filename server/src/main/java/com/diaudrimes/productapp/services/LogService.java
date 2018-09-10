package com.diaudrimes.productapp.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;

/**
 * Provides logging services to be used by all code.
 * 
 * @author diego
 *
 */
@Service
public class LogService {
  static LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
  static ConsoleAppender<ILoggingEvent> consoleAppender;
  
  static {
    // Configure pattern that will be used everywhere, 
    // instead of every application needing to have one in the application.yaml
    PatternLayoutEncoder patternEncoder = new PatternLayoutEncoder();
    
    Map<String, String> ruleRegistry = (Map)context.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
    if (ruleRegistry == null) {
      ruleRegistry = new HashMap<String, String>();
    }
    context.putObject(CoreConstants.PATTERN_RULE_REGISTRY, ruleRegistry);
    ruleRegistry.put("exec_env", "com.ibm.lighthouse.common.logging.ExecutionEnvironmentConverter");
  
    // The time will be in UTC, not in the timezone of the current machine.
    patternEncoder.setPattern(
        "%clr(%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX, UTC}){faint} %clr(%5p) %exec_env %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx");
    patternEncoder.setContext(context);
    patternEncoder.start();
    
    consoleAppender = new ConsoleAppender<>();
    consoleAppender.setEncoder(patternEncoder);
    consoleAppender.setContext(context);
    consoleAppender.start();
    
    Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    // Remove the default console appender and add the custom  one.
    Appender<ILoggingEvent> originalConsoleAppender = root.getAppender("CONSOLE");
    root.detachAppender(originalConsoleAppender);
    root.addAppender(consoleAppender);
  }
  
  /**
   * Log a message at the DEBUG level.
   *
   * @param sourceClass The class which generated the debug message.
   * @param message The message string to be logged
   */
  public void debug(Class sourceClass, String message) {
    Logger logger = this.getLogger(sourceClass);
    logger.debug(this.enrichMessage(message));
  }
  
  /**
   * Log an exception (throwable) at the ERROR level with an accompanying message.
   * 
   * @param sourceClass The class which generated the error.
   * @param message The message accompanying the exception
   */
  public void error(Class sourceClass, String message) {
     Logger logger = this.getLogger(sourceClass);
     logger.error(this.enrichMessage(message));
  }
  
  /**
   * Log an exception (throwable) at the ERROR level with an accompanying message.
   * 
   * @param sourceClass The class which generated the error.
   * @param message The message accompanying the exception
   * @param ex  The exception (throwable) to log
   */
  public void error(Class sourceClass, String message, Throwable ex) {
     Logger logger = this.getLogger(sourceClass);
     logger.error(this.enrichMessage(message), ex);
  }
  
  /**
   * Log a message at the INFO level.
   *
   * @param sourceClass The class which generated the informational message.
   * @param message the message string to be logged
   */
  public void info(Class sourceClass, String message) {
    Logger logger = this.getLogger(sourceClass);
    logger.info(this.enrichMessage(message));
  }
  
  /**
   * Log a message at the WARN level.
   *
   * @param sourceClass The class which generated the warning message.
   * @param message the message string to be logged
   */
  public void warn(Class sourceClass, String message) {
    Logger logger = this.getLogger(sourceClass);
    logger.warn(this.enrichMessage(message));
  }
  
  /**
   * Log a message at the WARN level.
   *
   * @param sourceClass The class which generated the warning message.
   * @param message the message string to be logged
   * @param throwable 
   */
  public void warn(Class sourceClass, String message, Throwable throwable) {
    Logger logger = this.getLogger(sourceClass);
    logger.warn(this.enrichMessage(message), throwable);
  }
  
  /**
   * Enrich the message with additional details, so it is easier to debug it.
   * @param message
   * @return
   */
  private String enrichMessage(String message) {
    return String.format("--- %s", message);
  }
  
  
  private Logger getLogger(Class sourceClass) {
    Logger result = (Logger) LoggerFactory.getLogger(sourceClass);
    result.addAppender(consoleAppender);
    result.setAdditive(false);
    
    return result;
  }
}