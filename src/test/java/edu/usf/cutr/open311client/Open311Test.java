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
package edu.usf.cutr.open311client;

import static org.junit.Assert.assertEquals;

import edu.usf.cutr.open311client.models.Open311Option;
import edu.usf.cutr.open311client.models.Service;
import edu.usf.cutr.open311client.models.ServiceDescription;
import edu.usf.cutr.open311client.models.ServiceDescriptionRequest;
import edu.usf.cutr.open311client.models.ServiceInfoRequest;
import edu.usf.cutr.open311client.models.ServiceInfoResponse;
import edu.usf.cutr.open311client.models.ServiceListRequest;
import edu.usf.cutr.open311client.models.ServiceListResponse;
import edu.usf.cutr.open311client.models.ServiceRequest;
import edu.usf.cutr.open311client.models.ServiceRequestResponse;

import org.junit.Before;
import org.junit.Test;

public class Open311Test {

  private Open311 open311;

  @Before
  public void setup() {
    Open311Option option = new Open311Option(TestConstants.ENDPOINT,
        TestConstants.API_KEY);
    Open311Manager.initOpen311WithOption(option);
    Open311Manager.setDebugMode(true);
    Open311Manager.setDryRun(true);
    open311 = Open311Manager.getDefaultOpen311();
  }

  /**
   * Test the GetServiceList method
   */
  @Test
  public void testOpen311GetServiceList() {
    LatLong latLong = TestUtils.getRandomLocation();
    // Create ServiceListRequest with Lat and Lon
    // Alternatively jurisdictionId also could be used
    ServiceListRequest slr = new ServiceListRequest(latLong.getLat(),
        latLong.getLon());
    // Call service list with ServiceListRequest
    ServiceListResponse serviceListResponse = open311.getServiceList(slr);

    assertEquals(serviceListResponse.isSuccess(), true);
  }

  /**
   * Test the GetServiceDescription method
   */
  @Test
  public void testOpen311GetServiceDescription() {
    LatLong latLong = TestUtils.getRandomLocation();

    ServiceListRequest slr = new ServiceListRequest(latLong.getLat(),
        latLong.getLon());
    ServiceListResponse serviceListResponse = open311.getServiceList(slr);

    assertEquals(serviceListResponse.isSuccess(), true);

    String serviceCode = serviceListResponse.getServiceList().get(
        0).getService_code();

    // Create ServiceDescriptionRequest from the service code
    ServiceDescriptionRequest sdr = new ServiceDescriptionRequest(
        latLong.getLat(), latLong.getLon(), serviceCode);

    // Call getServiceDescription with ServiceDescriptionRequest
    ServiceDescription serviceDescription = open311.getServiceDescription(sdr);

    assertEquals(serviceDescription.isSuccess(), true);
  }

  /**
   * Test the PostServiceRequest method
   */
  @Test
  public void testPostServiceRequest() {
    LatLong latLong = TestUtils.getRandomLocation();

    ServiceListRequest slr = new ServiceListRequest(latLong.getLat(),
        latLong.getLon());
    ServiceListResponse serviceListResponse = open311.getServiceList(slr);
    Service service = serviceListResponse.getServiceList().get(0);

    assertEquals(serviceListResponse.isSuccess(), true);

    // Create a ServiceRequest with required parameters
    ServiceRequest.Builder builder = new ServiceRequest.Builder();
    ServiceRequest serviceRequest = builder.setDescription(
        "Test Service Request").setService_code(
            service.getService_code()).setLatitude(
                latLong.getLat()).setLongitude(latLong.getLon()).setFirst_name(
                    "Test").setLast_name("tester").setEmail(
                        "cutr@usf.edu").setAddress_string(
                            "9389 Silverthorn Rd, Seminole, FL 33777").setService_name(
                                service.getService_name()).setDevice_id(
                                    "asd213123").createServiceRequest();

    // Call postServiceRequest with ServiceRequest
    ServiceRequestResponse requestResponse = open311.postServiceRequest(
        serviceRequest);
    assertEquals(requestResponse.isSuccess(), true);
  }
  
  /**
   * Test the GetServiceInfo method
   */
  @Test
  public void testOpen311GetServiceInfo() {
    LatLong latLong = TestUtils.getRandomLocation();
    // Create ServiceInfoRequest with Lat and Lon
    // Alternatively jurisdictionId also could be used
    ServiceInfoRequest sir = new ServiceInfoRequest(latLong.getLat(),
        latLong.getLon());
    sir.setServiceRequestId("231155");
    // Call service info with ServiceInfoRequest
    ServiceInfoResponse serviceInfoResponse = open311.getServiceRequest(sir);
    assertEquals(serviceInfoResponse.isSuccess(), true);
  }
}
