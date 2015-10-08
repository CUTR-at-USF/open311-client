package edu.usf.cutr.open311client.models;

import java.util.List;

public class ServiceInfoResponse extends Open311BaseModel{

  List<ServiceInfo> serviceInfoList;

  public List<ServiceInfo> getServiceInfoList() {
    return serviceInfoList;
  }

  public void setServiceInfoList(List<ServiceInfo> serviceInfoList) {
    this.serviceInfoList = serviceInfoList;
  }
}
