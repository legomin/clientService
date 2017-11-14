package com.legomin.clientservice.service;

import java.util.Set;

import com.legomin.clientservice.domain.Client;

public interface ClientsService {

  Set<String> getClients();

  void insertClient(String clientName);

  Client clientInfo(String clientName);

  void addClientNumber(String clientName, String phoneNumber);

  void updateClientNumber(String clientName, String phoneNumber, int numberId);

}
