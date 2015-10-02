# open311-client 

[![Build Status](https://travis-ci.org/CUTR-at-USF/open311-client.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/open311-client)

This is a java client to submit issues to Open311 GeoReport v2 compliant systems.

### Features:

* REST API design
* Image submission
* Compatible platforms:
 * Java applications
 * Android 

### Add the library

##### Add library as dependency

###### Maven
```
TODO: maven repo
```

###### Gradle
```
TODO: gradle repo
```

### Usage

###### 1 - Create an open311 endpoint in your project

```
// Create an open311 option with api url and api key
Open311Option option = new Open311Option(API_URL, API_KEY, JURISDICTION_ID)
Open311Manager.initOpen311WithOption(option);
```

###### 2 - Do an API call

```
Open311 open311 = Open311Manager.getDefaultOpen311();

// Returns service list
ServiceListResponce slr = open311.getServiceList();

// Get service description
ServiceDescription sd = open311.getServiceDescription(serviceListRequest);

// Post a service request
ServiceRequestResponse srr = open311.postServiceRequest(serviceRequest);

```

### License 

```
/*
* Copyright (C) 2014-2015 University of South Florida (sjbarbeau@gmail.com, cagricetin@mail.usf.edu)
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

```
