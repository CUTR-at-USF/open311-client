# open311-client [![Build Status](https://travis-ci.org/CUTR-at-USF/open311-client.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/open311-client) [ ![Download](https://api.bintray.com/packages/cutr-at-usf/cutr-mvn-repo/open311client/images/download.svg) ](https://bintray.com/cutr-at-usf/cutr-mvn-repo/open311client/_latestVersion)

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
<?xml version="1.0" encoding="UTF-8" ?>
<settings
	xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
	xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>

	<profiles>
		<profile>
			<repositories>
				<repository>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
					<id>central</id>
					<name>bintray</name>
					<url>http://jcenter.bintray.com</url>
				</repository>
			</repositories>
			<pluginRepositories>
				<pluginRepository>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
					<id>central</id>
					<name>bintray-plugins</name>
					<url>http://jcenter.bintray.com</url>
				</pluginRepository>
			</pluginRepositories>
			<id>bintray</id>
		</profile>
	</profiles>
	<activeProfiles>
		<activeProfile>bintray</activeProfile>
	</activeProfiles>
</settings>
```

###### Gradle
```
repositories {
    jcenter()
}

dependencies {
	compile 'edu.usf.cutr:open311client:1.0.0'
}
```

### Publishing

This is the steps for publishing the `open311-client` on `jcenter` repository.

###### 1 - Create an account on [Bintray](https://bintray.com/).
###### 2 - Setup your pom.xml
We need to specify the URL from which to distribute your project. 
```
<distributionManagement>
  <repository>
      <id>maven-example-id</id>
      <url>https://api.bintray.com/maven/testaccount/maven-repo/maven-example/;publish=1</url>
  </repository>
</distributionManagement>
```

###### 3 - Setup your setting.xml
We need to provide Bintray username and API Key to the Maven `settings.xml` file.

```
<server>
  <id>maven-example-id</id>
  <username>testaccount</username>
  <password>***testaccount-secret-api-key***</password>
</server>
```

###### 4 - Run maven deploy

Finally, we can run ```mvn deploy``` to complete publishing.

### Usage

###### 1 - Create an open311 endpoint in your project

```
// Create an open311 option with api url and api key
Open311Option option = new Open311Option(API_URL, API_KEY, JURISDICTION_ID)
Open311Manager.initOpen311WithOption(option);
```

###### 2 - Change open311 library settings (optional)

```
// Enable debug logging 
Open311Manager.getSettings().setDebugMode(true);

// Don't call post methods 
Open311Manager.getSettings().setDryRun(true);

// Set connection timeout to 30 seconds
Open311Manager.getSettings().setConnectionTimeout(30*1000);
```


###### 3 - Do an API call

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
