package com.legomin.clientservice.controller;

import java.util.Set;

import com.legomin.clientservice.domain.Client;
import com.legomin.clientservice.domain.exception.DbException;
import com.legomin.clientservice.service.ClientsService;
import com.legomin.clientservice.service.phoneformatter.exception.PhoneFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * main controller class
 * <p>
 * null responses are not expected now, with current implementation,
 * but they are handled for further
 */
@RestController
public class Controller {

  private final ClientsService clientsService;

  public Controller(ClientsService clientsService) {
    this.clientsService = clientsService;
  }

  /**
   * client list GET function
   *
   * @return json clients list or internal error, if null was got from service
   */
  @RequestMapping(value = "/clients", method = RequestMethod.GET)
  public ResponseEntity<Set<String>> getClientsList() {
    final Set<String> clientsSet = clientsService.getClients();
    if (clientsSet == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(clientsSet);
  }

  /**
   * Add new client function
   *
   * @param name - new client name
   */
  @RequestMapping(value = "/clients", method = RequestMethod.POST)
  public void insertClient(@RequestParam("name") final String name) {
    clientsService.insertClient(name);
  }

  /**
   * get client info function
   *
   * @param name - client name
   * @return - client info
   */
  @RequestMapping(value = "/clients/{name}", method = RequestMethod.GET)
  public Client getClientInfo(@PathVariable final String name) {
    return clientsService.clientInfo(name);
  }

  /**
   * add or update client phone numbers function
   *
   * @param name   - client name
   * @param number - new phone number
   * @param id     - old number id or empty
   */
  @RequestMapping(value = "/clients/{name}", method = RequestMethod.POST)
  public void updatePhoneNumber(@PathVariable final String name, @RequestParam("number") final String number,
    @RequestParam("id") Integer id) {
    if (id == null) {
      clientsService.addClientNumber(name, number);
    } else {
      clientsService.updateClientNumber(name, number, id);
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleException(Exception e) {
    if (e instanceof DbException || e instanceof PhoneFormatException) {
      return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
  }

}
