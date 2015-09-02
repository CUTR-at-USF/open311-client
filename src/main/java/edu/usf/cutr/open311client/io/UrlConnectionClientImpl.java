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

package edu.usf.cutr.open311client.io;

import edu.usf.cutr.open311client.models.NameValuePair;
import edu.usf.cutr.open311client.utils.Open311UrlUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class UrlConnectionClientImpl implements Open311ConnectionClient {

  private final String boundary = "===" + System.currentTimeMillis() + "===";

  private static final String LINE_FEED = "\r\n";

  private HttpURLConnection httpConnection;
  private String charset = "UTF-8";
  private OutputStream outputStream;
  private PrintWriter writer;

  @Override
  public String getMethod(String url, List<NameValuePair> params)
      throws IOException {

    initGetConnection(url + Open311UrlUtil.nameValuePairsToParams(params));

    ArrayList<String> responseList = finishConnection();

    return getResponseAsString(responseList);
  }

  @Override
  public String postMethod(String url, List<NameValuePair> params)
      throws IOException {
    initPostConnection(url);

    appendFormField(params);

    appendFinishHeader();

    ArrayList<String> responseList = finishConnection();

    return getResponseAsString(responseList);
  }

  @Override
  public String postMethod(String url, List<NameValuePair> params, File file)
      throws IOException {
    initPostConnection(url);

    appendFormField(params);

    appendFilePart("media", file);

    appendFinishHeader();

    ArrayList<String> responseList = finishConnection();

    return getResponseAsString(responseList);
  }

  /**
   * Initialize a HTTP get connection from given url with parameters
   * @param requestURL
   * @throws IOException
   */
  private void initGetConnection(String requestURL) throws IOException {
    URL url = new URL(requestURL);
    httpConnection = (HttpURLConnection) url.openConnection();
    httpConnection.setRequestMethod("GET");
  }

  /**
   * Initialize a post connection with given url
   * @param requestURL
   * @throws IOException
   */
  private void initPostConnection(String requestURL) throws IOException {
    URL url = new URL(requestURL);
    httpConnection = (HttpURLConnection) url.openConnection();
    httpConnection.setUseCaches(false);
    httpConnection.setDoOutput(true);
    httpConnection.setDoInput(true);
    httpConnection.setRequestMethod("POST");
    httpConnection.setRequestProperty("Content-Type",
        "multipart/form-data; boundary=" + boundary);

    outputStream = httpConnection.getOutputStream();
    writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
        true);
  }

  /**
   * Adds a form field to the request
   *
   * @param name field name
   * @param value field value
   */
  private void appendFormField(String name, String value) {
    writer.append("--" + boundary).append(LINE_FEED);
    writer.append(
        "Content-Disposition: form-data; name=\"" + name + "\"").append(
            LINE_FEED);
    writer.append("Content-Type: text/plain; charset=" + charset).append(
        LINE_FEED);
    writer.append(LINE_FEED);
    writer.append(value).append(LINE_FEED);
    writer.flush();
  }

  private void appendFormField(List<NameValuePair> params) {
    for (NameValuePair nvp : params) {
      appendFormField(nvp.getName(), nvp.getValue());
    }
  }

  /**
   * Adds a upload file section to the request
   *
   * @param fieldName name attribute in <input type="file" name="..." />
   * @param uploadFile a File to be uploaded
   * @throws IOException
   */
  private void appendFilePart(String fieldName, File uploadFile)
      throws IOException {
    String fileName = uploadFile.getName();
    writer.append("--" + boundary).append(LINE_FEED);
    writer.append("Content-Disposition: form-data; name=\"" + fieldName
        + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
    writer.append("Content-Type: "
        + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
    writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
    writer.append(LINE_FEED);
    writer.flush();

    FileInputStream inputStream = new FileInputStream(uploadFile);
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }
    outputStream.flush();
    inputStream.close();

    writer.append(LINE_FEED);
    writer.flush();
  }

  /**
   * Adds a header field to the request.
   *
   * @param name - name of the header field
   * @param value - value of the header field
   */
  @SuppressWarnings("unused")
  private void appendHeaderField(String name, String value) {
    writer.append(name + ": " + value).append(LINE_FEED);
    writer.flush();
  }

  private void appendFinishHeader() {
    writer.append(LINE_FEED).flush();
    writer.append("--" + boundary + "--").append(LINE_FEED);
    writer.close();
  }

  /**
   * Completes the request and receives response from the server.
   *
   * @return a list of Strings as response in case the server returned status
   *         OK, otherwise an exception is thrown.
   * @throws IOException
   */
  private ArrayList<String> finishConnection() throws IOException {
    ArrayList<String> response = new ArrayList<String>();

    // checks server's status code first
    int status = httpConnection.getResponseCode();
    if (status == HttpURLConnection.HTTP_OK) {
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(httpConnection.getInputStream()));
      String line = null;
      while ((line = reader.readLine()) != null) {
        response.add(line);
      }
      reader.close();
      httpConnection.disconnect();
    } else {
      throw new IOException("Server returned non-OK status: " + status);
    }

    return response;
  }

  private String getResponseAsString(List<String> responseList) {
    StringBuilder response = new StringBuilder();
    for (String s : responseList) {
      response.append(s);
    }
    return response.toString();
  }
}
