package edu.usf.cutr.open311client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceInfo extends Open311BaseModel {

  private String description;
  private String address;

  @JsonProperty(value = "lat")
  private String latitude;
  @JsonProperty(value = "long")
  private String longitude;

  private String service_request_id;
  private String requested_datetime;
  private String updated_datetime;
  //  private String long;
  private String service_code;
  private String zipcode;
  private String status;
  private String agency_responsible;
  private String service_name;
  private String media_url;
  private String expected_datetime;
  private String address_id;
  private String service_notice;
  private String status_notes;

  public ServiceInfo() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getService_request_id() {
    return service_request_id;
  }

  public void setService_request_id(String service_request_id) {
    this.service_request_id = service_request_id;
  }

  public String getRequested_datetime() {
    return requested_datetime;
  }

  public void setRequested_datetime(String requested_datetime) {
    this.requested_datetime = requested_datetime;
  }

  public String getUpdated_datetime() {
    return updated_datetime;
  }

  public void setUpdated_datetime(String updated_datetime) {
    this.updated_datetime = updated_datetime;
  }

  public String getService_code() {
    return service_code;
  }

  public void setService_code(String service_code) {
    this.service_code = service_code;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAgency_responsible() {
    return agency_responsible;
  }

  public void setAgency_responsible(String agency_responsible) {
    this.agency_responsible = agency_responsible;
  }

  public String getService_name() {
    return service_name;
  }

  public void setService_name(String service_name) {
    this.service_name = service_name;
  }

  public String getMedia_url() {
    return media_url;
  }

  public void setMedia_url(String media_url) {
    this.media_url = media_url;
  }

  public String getExpected_datetime() {
    return expected_datetime;
  }

  public void setExpected_datetime(String expected_datetime) {
    this.expected_datetime = expected_datetime;
  }

  public String getAddress_id() {
    return address_id;
  }

  public void setAddress_id(String address_id) {
    this.address_id = address_id;
  }

  public String getService_notice() {
    return service_notice;
  }

  public void setService_notice(String service_notice) {
    this.service_notice = service_notice;
  }

  public String getStatus_notes() {
    return status_notes;
  }

  public void setStatus_notes(String status_notes) {
    this.status_notes = status_notes;
  }
}
