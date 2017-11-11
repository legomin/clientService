package com.legomin.clientservice.domain;

import java.util.Set;

/**
 * Main data access interface
 */
public interface ClientsRepository {

  Set<String> getClientsList();

  void insertClient(String name);

  Client getClientInfo(String name);

  void addPhoneNumber(String clientName, String phoneNumber);

  void editPhoneNumber(String clientName, int numberId, String newNumber);

}
