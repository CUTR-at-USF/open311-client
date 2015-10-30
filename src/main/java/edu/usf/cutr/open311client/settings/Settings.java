package edu.usf.cutr.open311client.settings;

import edu.usf.cutr.open311client.settings.Logger.LogLevel;

/*
* Copyright (C) 2015 University of South Florida (sjbarbeau@gmail.com)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
