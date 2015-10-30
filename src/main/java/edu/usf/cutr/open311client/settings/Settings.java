package edu.usf.cutr.open311client.settings;

import edu.usf.cutr.open311client.settings.Logger.LogLevel;

public class Settings {

  private boolean dryRun = false;

  // Default connection time is 15 sec
  private int connectionTimeout = 1000*15;
  
  private static Settings settings;
  
  private Logger logger = Logger.getLogger();
  
  
  public static synchronized Settings getSettings() {
    if (settings == null) {
      settings = new Settings();
    }
    return settings;
  }

  public boolean isDryRun() {
    return dryRun;
  }

  public void setDryRun(boolean dryRun) {
    logger.info("Dry run mode changed: " + dryRun);
    this.dryRun = dryRun;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    logger.info("Set connection timeout: " + connectionTimeout);
    this.connectionTimeout = connectionTimeout;
  }

  public void setLogLevel(LogLevel logLevel) {
    logger.setLogLevel(logLevel);
  }
  
  public void setDebugMode(boolean mode) {
    if (mode) {
      logger.setLogLevel(LogLevel.DEBUG);
      logger.info("Log level changed: " + LogLevel.DEBUG);
    } else {
      logger.setLogLevel(LogLevel.INFO);
      logger.info("Log level changed: " + LogLevel.INFO);
    }
  }
}
