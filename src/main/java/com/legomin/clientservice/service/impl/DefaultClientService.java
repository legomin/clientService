package com.legomin.clientservice.service.impl;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import com.legomin.clientservice.domain.Client;
import com.legomin.clientservice.domain.ClientsRepository;
import com.legomin.clientservice.service.ClientsService;
import com.legomin.clientservice.service.phoneformatter.PhoneNumberFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default client service implementation class
 * <p>
 * It handles repository & phone formatter exceptions
 * and returns to controller either success result data or error description
 */
@Service
public class DefaultClientService implements ClientsService {

  private static final Logger log = LoggerFactory.getLogger(DefaultClientService.class);

  private final ClientsRepository repository;
  private final PhoneNumberFormatter formatter;

  public DefaultClientService(ClientsRepository repository, PhoneNumberFormatter formatter) {
    this.repository = requireNonNull(repository);
    this.formatter = requireNonNull(formatter);
  }

  @Override
  public Set<String> getClients() {
    log.debug("About to get clients");
    return repository.getClientsList();
  }

  @Override
  public void insertClient(String clientName) {
    log.debug("About to add new client {}", clientName);
      repository.insertClient(clientName);
  }

  @Override
  public Client clientInfo(String clientName) {
    log.debug("About to get client {} info", clientName);
    return repository.getClientInfo(clientName);
  }

  @Override
  public void addClientNumber(String clientName, String phoneNumber) {
    log.debug("About to add new client {} number {}", clientName, phoneNumber);
      repository.addPhoneNumber(clientName, formatter.apply(phoneNumber));
  }

  @Override
  public void updateClientNumber(String clientName, String phoneNumber, int numberId) {
    log.debug("About to update client {} number {}, number id: {}", clientName, phoneNumber, numberId);
      repository.editPhoneNumber(clientName, numberId, formatter.apply(phoneNumber));
  }
}
