package com.legomin.clientservice.service;

public class ResultEntity {

  private final Object data;
  private final boolean error;
  private final String errorDescription;

  private ResultEntity(Object data, boolean error, String errorDescroption) {
    this.data = data;
    this.error = error;
    this.errorDescription = errorDescroption;
  }

  public static ResultEntity getSussessEntity(Object data) {
    return new ResultEntity(data, false, null);
  }

  public static ResultEntity getSussessEntity() {
    return new ResultEntity(null, false, null);
  }

  public static ResultEntity getErrorEntity(String errorDescroption) {
    return new ResultEntity(null, true, errorDescroption);
  }

  public Object getData() {
    return data;
  }

  public boolean isError() {
    return error;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ResultEntity that = (ResultEntity) o;

    if (isError() != that.isError()) {
      return false;
    }
    if (getData() != null ? !getData().equals(that.getData()) : that.getData() != null) {
      return false;
    }
    return getErrorDescription() != null ? getErrorDescription().equals(that.getErrorDescription()) :
      that.getErrorDescription() == null;
  }

}
