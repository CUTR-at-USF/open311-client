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

package edu.usf.cutr.open311client.io;

import edu.usf.cutr.open311client.models.NameValuePair;
import edu.usf.cutr.open311client.utils.Open311UrlUtil;

import java.io.File;
import java.util.List;

/**
 * Manager that manages all http connections while communicating any open311
 * server.
 *
 * @author Cagri Cetin
 */
public class Open311ConnectionManager {

  private Open311ConnectionClient connectionClient;

  public Open311ConnectionManager() {
    connectionClient = new UrlConnectionClientImpl();
  }

  /**
   * Makes connection to any server.
   *
   * @param url Destination url
   * @param requestMethod request method Post or Get
   * @return Returns the result of the request as string
   */
  public String getStringResult(String url,
      Open311UrlUtil.RequestMethod requestMethod, List<NameValuePair> params,
      File file) {

    String result = null;
    try {

      if (requestMethod == Open311UrlUtil.RequestMethod.POST) {
        if (file == null) {
          result = connectionClient.postMethod(url, params);
        } else {
          result = connectionClient.postMethod(url, params, file);
        }
      } else {
        result = connectionClient.getMethod(url, params);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public String getStringResult(String url,
      Open311UrlUtil.RequestMethod requestMethod, List<NameValuePair> params) {
    return getStringResult(url, requestMethod, params, null);
  }

  /**
   * Creates requests without any parameters.
   *
   * @param url
   * @param requestMethod
   * @return
   */
  public String getStringResult(String url,
      Open311UrlUtil.RequestMethod requestMethod) {
    return getStringResult(url, requestMethod, null);
  }
}
