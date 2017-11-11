package com.legomin.clientservice.service;

import java.util.Set;

public interface ClientsService {

  Set<String> getClients();

  ResultEntity insertClient(String clientName);

  ResultEntity clientInfo(String clientName);

  ResultEntity addClientNumber(String clientName, String phoneNumber);

  ResultEntity updateClientNumber(String clientName, String phoneNumber, int numberId);

}
