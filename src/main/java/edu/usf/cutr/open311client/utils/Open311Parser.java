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
package edu.usf.cutr.open311client.utils;

import edu.usf.cutr.open311client.constants.Open311Constants;
import edu.usf.cutr.open311client.constants.Open311Type;
import edu.usf.cutr.open311client.models.Service;
import edu.usf.cutr.open311client.models.ServiceDescription;
import edu.usf.cutr.open311client.models.ServiceListResponse;
import edu.usf.cutr.open311client.models.ServiceRequestResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Helper class for parsing responses
 *
 * @author Cagri Cetin
 */
public class Open311Parser {

  /**
   * Parses json string result for service list
   *
   * @param json string result
   * @return ServiceListResponse object
   */
  public static ServiceListResponse parseServices(String json) {

    if (json == null) {
      return new ServiceListResponse();
    }

    ObjectMapper om = new ObjectMapper();
    ArrayList<Service> services;
    ServiceListResponse slr = new ServiceListResponse();
    try {
      services = om.readValue(json, new TypeReference<ArrayList<Service>>() {
      });
      slr.setServiceList(services);
      slr.setResultCode(Open311Constants.RESULT_OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return slr;
  }

  /**
   * Parses json string result for service description
   *
   * @param json string result
   * @return ServiceDescription object
   */
  public static ServiceDescription parseServiceDescription(String json) {

    if (json == null) {
      return new ServiceDescription();
    }

    ObjectMapper om = new ObjectMapper();
    ServiceDescription serviceDescription = new ServiceDescription();
    try {
      serviceDescription = om.readValue(json, ServiceDescription.class);
      serviceDescription.setResultCode(Open311Constants.RESULT_OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return serviceDescription;
  }

  /**
   * Parses json string result for service request responses
   *
   * @param json
   * @param open311Type
   * @return
   */
  public static ServiceRequestResponse parseRequestResponse(String json,
      Open311Type open311Type) {
    ServiceRequestResponse response = null;

    if (json == null) {
      return new ServiceRequestResponse(open311Type);
    }

    try {
      JSONArray jsonArray = new JSONArray(json);

      if (jsonArray.getJSONObject(0) != null) {
        response = new ServiceRequestResponse(jsonArray.getJSONObject(0),
            open311Type);
      } else {
        response = new ServiceRequestResponse(open311Type);
        response.setResultCode(Open311Constants.RESULT_FAIL);
      }
    } catch (JSONException e) {
      try {
        JSONObject jsonObject = new JSONObject(json);
        response = new ServiceRequestResponse(jsonObject, open311Type);

      } catch (JSONException e1) {
        e1.printStackTrace();
        response = new ServiceRequestResponse(open311Type);
        response.setResultCode(Open311Constants.RESULT_FAIL);
        response.setResultDescription(e.getMessage());
      }
      e.printStackTrace();
    }
    return response;
  }
}
