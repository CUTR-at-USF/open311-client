/*
* Copyright (C) 2014 University of South Florida (sjbarbeau@gmail.com)
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

package edu.usf.cutr.open311client;

import edu.usf.cutr.open311client.models.Open311Option;
import edu.usf.cutr.open311client.models.Service;
import edu.usf.cutr.open311client.settings.Logger;
import edu.usf.cutr.open311client.settings.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Open311 manager design to manage multiple endpoints in one application
 * 
 * @author Cagri Cetin
 */
public class Open311Manager {

  private static List<Open311> open311List = new ArrayList<Open311>();

  private static Logger logger = Logger.getLogger();
  
  private static Settings settings = Settings.getSettings();

  public static Open311 getDefaultOpen311() {
    if (open311List.size() == 0) {
      return null;
    } else {
      return open311List.get(0);
    }
  }

  public static Open311 getOpen311ByJurisdictionId(String jurisdictionId) {
    for (Open311 open311 : open311List) {
      if (jurisdictionId.equals(open311.getJurisdiction())) {
        return open311;
      }
    }
    return null;
  }

  public static Open311 getOpen311ByTag(String tag) {
    for (Open311 open311 : open311List) {
      if (tag.equals(open311.getTag())) {
        return open311;
      }
    }
    return null;
  }

  public static List<Open311> getAllOpen311() {
    return open311List;
  }

  public static void initOpen311WithOption(Open311Option option) {
    Open311Factory open311Factory = new Open311Factory();
    Open311 open311 = open311Factory.getOpen311(option);
    open311List.add(open311);

    logger.info("Open311 initilized with " + open311.getBaseUrl());
  }

  public static void initOpen311WithOptions(List<Open311Option> options) {
    for (Open311Option option : options) {
      initOpen311WithOption(option);
    }
  }
  
  public static void clearOpen311(){
    open311List.clear();
    logger.info("clearOpen311");
  }

  public static boolean isOpen311Exist() {
    return open311List.size() != 0;
  }

  public static boolean isAreaManagedByOpen311(List<Service> serviceList) {
    boolean result = false;
    if (serviceList != null && serviceList.size() > 1) {
      result = true;
    } else if (serviceList != null && serviceList.size() == 1) {
      Service service = serviceList.get(0);
      result = Boolean.getBoolean(service.getMetadata());
    }
    logger.debug("call isAreaManagedByOpen311: " + result);
    return result;
  }

  public static Settings getSettings() {
    return settings;
  }
}
