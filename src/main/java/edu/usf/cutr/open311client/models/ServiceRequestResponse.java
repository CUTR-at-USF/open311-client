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
package edu.usf.cutr.open311client.models;

import edu.usf.cutr.open311client.constants.Open311Constants;
import edu.usf.cutr.open311client.constants.Open311Type;
import edu.usf.cutr.open311client.utils.Open311StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model object for handling service requests from servers
 *
 * @author Cagri Cetin
 */
public class ServiceRequestResponse extends Open311BaseModel {

  private JSONObject jsonObject;
  private Open311Type open311Type;

  public ServiceRequestResponse(JSONObject jsonObject,
                                Open311Type open311Type) {
    this.jsonObject = jsonObject;
    this.open311Type = open311Type;
  }

  public ServiceRequestResponse(Open311Type open311Type) {
    this.open311Type = open311Type;
  }

  public String getServiceRequestId() {
    if (open311Type.equals(Open311Type.SEECLICKFIX)) {
      return getJsonString(Open311Constants.ID);
    } else {
      return getJsonString(Open311Constants.REQUEST_ID);
    }
  }

  public String getToken() {
    return getJsonString(Open311Constants.TOKEN);
  }

  public String getMessage() {

    if (this.isSuccess()) {
      return Open311Constants.M_REPORT_SUCCESS;
    } else {
      return this.getErrorMessage();
    }
  }

  public String getErrorMessage() {
    if (jsonObject == null) {
      return super.getResultDescription();
    } else {
      if (open311Type.equals(Open311Type.SEECLICKFIX)) {
        // Try to get from base message
        String message = getJsonStringFromArray(Open311Constants.BASE);
        if (Open311StringUtils.isEmpty(message)) {
          // Try to find duplicate message
          message = getJsonStringFromArray(Open311Constants.DUPLICATE);
        }
        if (Open311StringUtils.isEmpty(message)) {
          // One last try based on default open311 specification
          message = getJsonString(Open311Constants.DESCRIPTION);
          String fullError = getJsonString(Open311Constants.FULL_ERRORS);
          if (!Open311StringUtils.isEmpty(message) && !Open311StringUtils.isEmpty(fullError)) {
            message += " " + fullError;
          } else if (Open311StringUtils.isEmpty(message) && !Open311StringUtils.isEmpty(fullError)) {
            message = fullError;
          }
        }
        return message;
      } else {
        return getJsonString(Open311Constants.DESCRIPTION);
      }
    }
  }

  @Override
  public Boolean isSuccess() {

    if (jsonObject == null) {
      return super.isSuccess();
    } else {
      String requestId = null;
      try {
        // Try with request id
        requestId = String.valueOf(jsonObject.get(Open311Constants.REQUEST_ID));
      } catch (JSONException e) {
        try {
          requestId = jsonObject.getString(Open311Constants.ID);
        } catch (JSONException e1) {
          e1.printStackTrace();
        }
      }
      if (requestId == null) {
        return false;
      } else {
        return true;
      }
    }
  }

  private String getJsonString(String key) {
    String value = null;
    try {
      value = jsonObject.getString(key);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return value;
  }

  private String getJsonStringFromArray(String key) {
    StringBuilder sb = new StringBuilder();
    try {
      JSONArray jsonArray = jsonObject.getJSONArray(key);

      for (int i = 0; i < jsonArray.length(); i++) {
        sb.append(jsonArray.getString(i)).append(" ");
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
