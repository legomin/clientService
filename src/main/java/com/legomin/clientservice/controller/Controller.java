package com.legomin.clientservice.controller;

import java.util.Set;
import java.util.function.Supplier;

import com.legomin.clientservice.service.ClientsService;
import com.legomin.clientservice.service.ResultEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  private static ResponseEntity getResultResponseEntity(final ResultEntity entity, final Supplier<ResponseEntity> s) {
    if (entity == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } else if (entity.isError()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(entity.getErrorDescription());
    } else {
      return s.get();
    }
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
   * @return - internal error if null is got from service,
   * forbidden with error description if expected error is got,
   * 201 status otherwise
   */
  @RequestMapping(value = "/clients", method = RequestMethod.POST)
  public ResponseEntity insertClient(@RequestParam("name") final String name) {
    final ResultEntity result = clientsService.insertClient(name);
    return getResultResponseEntity(result, () -> ResponseEntity.status(HttpStatus.CREATED).build());
  }

  /**
   * get client info function
   *
   * @param name - client name
   * @return - internal error if null is got from service,
   * forbidden with error description if expected error is got,
   * client info otherwise
   */
  @RequestMapping(value = "/clients/{name}", method = RequestMethod.GET)
  public ResponseEntity getClientInfo(@PathVariable final String name) {
    final ResultEntity result = clientsService.clientInfo(name);
    return getResultResponseEntity(result, () -> ResponseEntity.ok().body(result.getData()));
  }

  /**
   * add or update client phone numbers function
   *
   * @param name   - client name
   * @param number - new phone number
   * @param id     - old number id or empty
   * @return - internal error if null is got from service,
   * forbidden with error description if expected error is got,
   * 201 (new number) or 202 (updated number) status otherwise
   */
  @RequestMapping(value = "/clients/{name}", method = RequestMethod.POST)
  public ResponseEntity updatePhoneNumber(@PathVariable final String name, @RequestParam("number") final String number,
    @RequestParam("id") Integer id) {

    final ResultEntity result;
    final HttpStatus responseStatus;
    if (id == null) {
      result = clientsService.addClientNumber(name, number);
      responseStatus = HttpStatus.CREATED;
    } else {
      result = clientsService.updateClientNumber(name, number, id);
      responseStatus = HttpStatus.ACCEPTED;
    }
    return getResultResponseEntity(result, () -> ResponseEntity.status(responseStatus).body(result.getData()));
  }

}
