package com.legomin.clientservice.domain.inMemoryImpl;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.legomin.clientservice.domain.Client;
import com.legomin.clientservice.domain.ClientsRepository;
import com.legomin.clientservice.domain.exception.DbException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * In memory (mock) ClientRepository implementation
 * <p>
 * data storages in HashMap<String, List<String>>, where
 * String key - client name
 * List - phone numbers list. List - for changing phone number by id support
 *
 * @Author Legomin Vitaliy
 */
@Component
public class InMemoryClientsRepository implements ClientsRepository {

  private static final Logger log = LoggerFactory.getLogger(InMemoryClientsRepository.class);
  private final Map<String, List<String>> db = newHashMap();

  @Override
  public Set<String> getClientsList() {
    return db.keySet();
  }

  @Override
  public void insertClient(String name) {
    if (db.containsKey(name)) {
      log.warn("Attempt to insert existing client {}", name);
      throw new DbException("Client " + name + " is already exists");
    }
    log.debug("Success insertion new client {}", name);
    db.put(name, newArrayList());
  }

  @Override
  public Client getClientInfo(String name) {
    if (!db.containsKey(name)) {
      log.warn("Attempt to get info non-existing client {}", name);
      throw new DbException("Client " + name + " is not found");
    }
    log.debug("Success fetching info of client {}", name);
    return new Client(name, db.get(name));
  }

  @Override
  public void addPhoneNumber(String clientName, String phoneNumber) {
    if (!db.containsKey(clientName)) {
      log.warn("Attempt to add phone number to non-existing client {}", clientName);
      throw new DbException("Client " + clientName + " is not found");
    }
    final List<String> phones = db.get(clientName);
    if (phones.contains(phoneNumber)) {
      log.warn("Attempt to add existing phone number {} to client {}", phoneNumber, clientName);
      throw new DbException("Phone number " + phoneNumber + " for client " + clientName + " already exists");
    }
    phones.add(phoneNumber);
    db.put(clientName, phones);
    log.debug("Success added new phone number {} to client {}", phoneNumber, clientName);
  }

  @Override
  public void editPhoneNumber(String clientName, int numberId, String newNumber) {
    if (!db.containsKey(clientName)) {
      log.warn("Attempt to change phone number to non-existing client {}", clientName);
      throw new DbException("Client " + clientName + " is not found");
    }
    final List<String> phones = db.get(clientName);
    if (numberId > phones.size() - 1) {
      throw new DbException("Phone number with id " + numberId + " is not exist");
    } else if (phones.contains(newNumber)) {
      throw new DbException("Phone number " + newNumber + " for client " + clientName + " already exists");
    }

    phones.set(numberId, newNumber);
    db.put(clientName, phones);
    log.debug("Success changed phone number with id: {} to {} for client {}", numberId, newNumber, clientName);
  }

}
