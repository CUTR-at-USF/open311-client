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

import edu.usf.cutr.open311client.constants.Open311Constants;
import edu.usf.cutr.open311client.constants.Open311Type;
import edu.usf.cutr.open311client.io.Open311ConnectionManager;
import edu.usf.cutr.open311client.models.NameValuePair;
import edu.usf.cutr.open311client.models.Open311Option;
import edu.usf.cutr.open311client.models.ServiceDescription;
import edu.usf.cutr.open311client.models.ServiceDescriptionRequest;
import edu.usf.cutr.open311client.models.ServiceInfoRequest;
import edu.usf.cutr.open311client.models.ServiceInfoResponse;
import edu.usf.cutr.open311client.models.ServiceListRequest;
import edu.usf.cutr.open311client.models.ServiceListResponse;
import edu.usf.cutr.open311client.models.ServiceRequest;
import edu.usf.cutr.open311client.models.ServiceRequestResponse;
import edu.usf.cutr.open311client.settings.Logger;
import edu.usf.cutr.open311client.settings.Settings;
import edu.usf.cutr.open311client.utils.Open311Parser;
import edu.usf.cutr.open311client.utils.Open311UrlUtil;
import edu.usf.cutr.open311client.utils.Open311UrlUtil.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Open311 class for managing actions to submit request with open311
 *
 * @author Cagri Cetin
 */
public class Open311 {

  private Open311Option open311Option;
  private String format = "json";

  private Logger logger = Logger.getLogger();

  private Open311ConnectionManager connectionManager = new Open311ConnectionManager();

  /**
   * Constructor with open311 option
   */
  protected Open311(Open311Option open311Option) {
    this.open311Option = open311Option;
  }

  /**
   * Method for getting service list
   *
   * @param serviceListRequest takes request params
   * @return ServiceListResponse object
   */
  public ServiceListResponse getServiceList(
      ServiceListRequest serviceListRequest) {
    List<NameValuePair> params = null;
    try {
      params = Open311UrlUtil.prepareNameValuePairs(serviceListRequest);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    logger.debug("call getServiceList params: " + params);
    String result = connectionManager.getStringResult(
        Open311UrlUtil.getServiceUrl(open311Option.getBaseUrl(), format),
        Open311UrlUtil.RequestMethod.GET, params);
    logger.debug("call getServiceList result: " + result);

    return Open311Parser.parseServices(result);
  }

  /**
   * method for creating service request
   *
   * @param serviceRequest takes service request object
   * @return result from open311 endpoint
   */
  public ServiceRequestResponse postServiceRequest(
      ServiceRequest serviceRequest) {
    List<NameValuePair> params = null;
    try {
      if (serviceRequest.getApi_key() == null) {
        serviceRequest.setApi_key(open311Option.getApiKey());
      }
      params = Open311UrlUtil.prepareNameValuePairs(serviceRequest);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    if (!Settings.getSettings().isDryRun()) {

      logger.debug("call postServiceRequest params: " + params);
      String result = connectionManager.getStringResult(
          Open311UrlUtil.getServiceRequestUrl(open311Option.getBaseUrl(),
              format),
          Open311UrlUtil.RequestMethod.POST, params, serviceRequest.getMedia());
      logger.debug("call postServiceRequest result: " + result);
      return Open311Parser.parseRequestResponse(result,
          open311Option.getOpen311Type());
    } else {
      logger.debug("Dry call postServiceRequest params: " + params);
      ServiceRequestResponse srr = new ServiceRequestResponse(
          Open311Type.DEFAULT);
      srr.setResultCode(Open311Constants.RESULT_OK);
      return srr;
    }
  }

  /**
   * method for getting current service requests from server
   *
   * @param serviceRequest takes service request object
   * @return result from open311 endpoint
   */
  public ServiceRequestResponse getServiceRequest(
      ServiceRequest serviceRequest) {
    List<NameValuePair> params = null;
    try {
      params = Open311UrlUtil.prepareNameValuePairs(serviceRequest);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    logger.debug("call getServiceRequest params: " + params);
    String result = connectionManager.getStringResult(
        Open311UrlUtil.getServiceRequestUrl(open311Option.getBaseUrl(), format),
        Open311UrlUtil.RequestMethod.GET, params);
    logger.debug("call getServiceRequest result: " + result);
    return Open311Parser.parseRequestResponse(result,
        open311Option.getOpen311Type());
  }

  /**
   * method for getting description of the services
   *
   * @param serviceListRequest takes service request object
   * @return result from open311 endpoint
   */
  public ServiceDescription getServiceDescription(
      ServiceDescriptionRequest serviceDescriptionRequest) {
    List<NameValuePair> params = null;
    try {
      params = Open311UrlUtil.prepareNameValuePairs(serviceDescriptionRequest);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    logger.debug("call getServiceDescription params: " + params);
    String result = connectionManager.getStringResult(
        Open311UrlUtil.getServiceDescUrl(open311Option.getBaseUrl(),
            serviceDescriptionRequest.getServiceCode(), format),
        Open311UrlUtil.RequestMethod.GET, params);
    logger.debug("call getServiceDescription result: " + result);
    return Open311Parser.parseServiceDescription(result);
  }

  /**
   * Method for getting all of already submitted requests
   * @param serviceInfoRequest
   * @return ServiceInfoResponse contains list of service request informations
   */
  public ServiceInfoResponse getAllServiceRequests(ServiceInfoRequest serviceInfoRequest) {
    List<NameValuePair> params = Open311UrlUtil.prepareNameValuePairs(
        serviceInfoRequest);
    logger.debug("call getAllServiceInfo params: " + params);
    String result = connectionManager.getStringResult(
        Open311UrlUtil.getServiceRequestUrl(open311Option.getBaseUrl(), format),
        RequestMethod.GET, params);
    logger.debug("call getAllServiceInfo result: " + result);
    return Open311Parser.parseServiceInfos(result);
  }
  
  /**
   * Method for getting a service info
   * @param serviceInfoRequest
   * @return ServiceInfo
   */
  public ServiceInfoResponse getServiceRequest(ServiceInfoRequest serviceInfoRequest){
    List<NameValuePair> params = Open311UrlUtil.prepareNameValuePairs(
        serviceInfoRequest);
    logger.debug("call getAllServiceInfo params: " + params);
    String result = connectionManager.getStringResult(
        Open311UrlUtil.getServiceInfoUrl(open311Option.getBaseUrl(), format, serviceInfoRequest.getServiceRequestId()),
        RequestMethod.GET, params);
    logger.debug("call getAllServiceInfo result: " + result);
    return Open311Parser.parseServiceInfos(result);
  }
  
  /**
   * @return base url of the open311 server
   */
  public String getBaseUrl() {
    return open311Option.getBaseUrl();
  }

  public String getJurisdiction() {
    return getOpen311Option().getJurisdiction();
  }

  public String getApiKey() {
    return open311Option.getApiKey();
  }

  public String getTag() {
    return open311Option.getTag();
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Open311Option getOpen311Option() {
    return open311Option;
  }
}
