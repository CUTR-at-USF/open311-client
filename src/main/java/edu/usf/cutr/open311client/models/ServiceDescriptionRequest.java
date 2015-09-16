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

/**
 * Simple model for service list requests
 * 
 * @author Cagri Cetin
 */

public class ServiceDescriptionRequest {

  private Double latitude;
  private Double longitude;
  private String address;
  private String jurisdictionId;
  private String serviceCode;

  public ServiceDescriptionRequest(Double latitude, Double longitude,
      String serviceCode) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.serviceCode = serviceCode;
  }

  public ServiceDescriptionRequest(Double latitude, Double longitude,
      String jurisdictionId, String serviceCode) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.jurisdictionId = jurisdictionId;
    this.serviceCode = serviceCode;
  }

  public ServiceDescriptionRequest() {

  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double lat) {
    this.latitude = lat;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(String jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }
}
