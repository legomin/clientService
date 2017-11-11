package com.legomin.clientservice.domain;

import java.util.List;

/**
 * class for client info data
 */
public class Client {

  private final String name;
  private final List<String> phoneNumbers;

  public Client(String name, List<String> phoneNumbers) {
    this.name = name;
    this.phoneNumbers = phoneNumbers;
  }

  public String getName() {
    return name;
  }

  public List<String> getPhoneNumbers() {
    return phoneNumbers;
  }
}
