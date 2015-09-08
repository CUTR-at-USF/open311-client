package edu.usf.cutr.open311client.models;

public class ClientPrefs {
  
  private boolean debugMode = false;
  
  private boolean dryRun = false;
  
  public ClientPrefs() {

  }

  public boolean isDebugMode() {
    return debugMode;
  }

  public void setDebugMode(boolean debugMode) {
    this.debugMode = debugMode;
  }

  public boolean isDryRun() {
    return dryRun;
  }

  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }
}
