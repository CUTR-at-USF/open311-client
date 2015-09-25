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

package edu.usf.cutr.open311client.debug;

public class Logger {

  private String TAG = "O311C";

  private LogLevel logLevel = LogLevel.INFO;

  private boolean dryRun = false;

  public enum LogLevel {
    DEBUG("DEBUG"), INFO("INFO"), ERROR("ERROR"), FATAL("FATAL");

    private final String text;

    private LogLevel(final String text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return text;
    }
  }

  private static Logger logger;

  private Logger() {

  }

  public static synchronized Logger getLogger() {
    if (logger == null) {
      logger = new Logger();
    }
    return logger;
  }

  public void info(Object object) {
    System.out.println(getHeaderByLevel(LogLevel.INFO) + object.toString());
  }

  public void debug(Object object) {
    if (logLevel.equals(LogLevel.DEBUG)) {
      System.out.println(getHeaderByLevel(LogLevel.DEBUG) + object.toString());
    }
  }

  public void error(Object object) {
    System.err.println(getHeaderByLevel(LogLevel.ERROR) + object.toString());
    if (object instanceof Exception) {
      ((Exception) object).printStackTrace();
    }
  }

  public void fatal(Object object) {
    System.err.println(getHeaderByLevel(LogLevel.FATAL) + object.toString());
  }

  public void setLogLevel(LogLevel logLevel) {
    this.logLevel = logLevel;
  }

  public boolean isDryRun() {
    return dryRun;
  }

  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }

  private String getHeaderByLevel(LogLevel level) {
    StringBuilder sb = new StringBuilder(TAG);
    sb.append(": ").append(level.toString()).append(" ");
    return sb.toString();
  }
}