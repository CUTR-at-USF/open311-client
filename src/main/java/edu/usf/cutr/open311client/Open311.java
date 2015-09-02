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

import edu.usf.cutr.open311client.io.Open311ConnectionManager;
import edu.usf.cutr.open311client.models.NameValuePair;
import edu.usf.cutr.open311client.models.Open311Option;
import edu.usf.cutr.open311client.models.ServiceDescription;
import edu.usf.cutr.open311client.models.ServiceListRequest;
import edu.usf.cutr.open311client.models.ServiceListResponse;
import edu.usf.cutr.open311client.models.ServiceRequest;
import edu.usf.cutr.open311client.models.ServiceRequestResponse;
import edu.usf.cutr.open311client.utils.Open311Parser;
import edu.usf.cutr.open311client.utils.Open311UrlUtil;

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
    public ServiceListResponse getServiceList(ServiceListRequest serviceListRequest) {
        List<NameValuePair> params = null;
        try {
            serviceListRequest.setJurisdictionId(open311Option.getJurisdiction());
            params = Open311UrlUtil.prepareNameValuePairs(serviceListRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = connectionManager.getStringResult(Open311UrlUtil.getServiceUrl(open311Option.getBaseUrl(),
                format), Open311UrlUtil.RequestMethod.GET, params);
        return Open311Parser.parseServices(result);
    }

    /**
     * @return service list response without
     * passing parameter
     * <p/>
     * May return generic service list
     */
    public ServiceListResponse getServiceList() {
        return getServiceList(null);
    }

    /**
     * method for creating service request
     *
     * @param serviceRequest takes service request object
     * @return result from open311 endpoint
     */
    public ServiceRequestResponse postServiceRequest(ServiceRequest serviceRequest) {
        List<NameValuePair> params = null;
        try {
            params = Open311UrlUtil.prepareNameValuePairs(serviceRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = connectionManager.getStringResult(Open311UrlUtil.getServiceRequestUrl(open311Option.getBaseUrl(),
                format), Open311UrlUtil.RequestMethod.POST, params, serviceRequest.getMedia());
        return Open311Parser.parseRequestResponse(result, open311Option.getOpen311Type());
    }

    /**
     * method for getting current service requests from server
     *
     * @param serviceRequest takes service request object
     * @return result from open311 endpoint
     */
    public ServiceRequestResponse getServiceRequest(ServiceRequest serviceRequest) {
        List<NameValuePair> params = null;
        try {
            params = Open311UrlUtil.prepareNameValuePairs(serviceRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = connectionManager.getStringResult(Open311UrlUtil.getServiceRequestUrl(open311Option.getBaseUrl(),
                format), Open311UrlUtil.RequestMethod.GET, params);
        return Open311Parser.parseRequestResponse(result, open311Option.getOpen311Type());
    }

    /**
     * method for getting description of the services
     *
     * @param serviceListRequest takes service request object
     * @return result from open311 endpoint
     */
    public ServiceDescription getServiceDescription(ServiceListRequest serviceListRequest) {
        List<NameValuePair> params = null;
        try {
            serviceListRequest.setJurisdictionId(open311Option.getJurisdiction());
            params = Open311UrlUtil.prepareNameValuePairs(serviceListRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = connectionManager.getStringResult(Open311UrlUtil.getServiceDescUrl(open311Option.getBaseUrl(),
                serviceListRequest.getServiceCode(), format), Open311UrlUtil.RequestMethod.GET, params);
        return Open311Parser.parseServiceDescription(result);
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
