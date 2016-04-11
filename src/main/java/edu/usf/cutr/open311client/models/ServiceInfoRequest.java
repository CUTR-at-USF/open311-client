package edu.usf.cutr.open311client.models;

public class ServiceInfoRequest {

  private String jurisdictionId;
  private String serviceRequestId;
  private String serviceCode;
  private Double latitude;
  private Double longitude;
  private String startDate;
  private String endDate;
  private String status;

  public ServiceInfoRequest(String jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public ServiceInfoRequest(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(String jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public String getServiceRequestId() {
    return serviceRequestId;
  }

  public void setServiceRequestId(String serviceRequestId) {
    this.serviceRequestId = serviceRequestId;
  }

  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
