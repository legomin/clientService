package com.legomin.clientservice.domain;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import com.legomin.clientservice.domain.exception.DbException;
import com.legomin.clientservice.domain.inMemoryImpl.InMemoryClientsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientsRepositoryTest {

  private ClientsRepository repository;

  @Before
  public void setup() {
    repository = new InMemoryClientsRepository();
    repository.insertClient("Zombie Ivanovich");
    repository.insertClient("Mike Tyson");
    repository.addPhoneNumber("Mike Tyson", "002");
  }

  @After
  public void clean() {
    repository = null;
  }

  @Test
  public void testGetClientList() {
    final Set<String> expectedSet = new HashSet<>();
    expectedSet.add("Zombie Ivanovich");
    expectedSet.add("Mike Tyson");

    final Set<String> result = repository.getClientsList();
    assertEquals("Unexpected result size", 2, result.size());
    assertEquals("Unexpected result", expectedSet, result);
  }

  @Test
  public void testInsertClentSussess() {
    repository.insertClient("Frodo Baggins");

    final Set<String> actual = repository.getClientsList();
    assertEquals("Unexpected result size", 3, actual.size());
    assertTrue("Unexpected result", actual.contains("Frodo Baggins"));
  }

  @Test(expected = DbException.class)
  public void testInsertClentFail() {
    repository.insertClient("Mike Tyson");
  }

  @Test
  public void testGetClientInfoSuccess() {
    final Client client = repository.getClientInfo("Mike Tyson");
    assertNotNull("Expected not null client", client);
    assertEquals("Unexpected client", "Mike Tyson", client.getName());
  }

  @Test(expected = DbException.class)
  public void testClentInfoFail() {
    final Client client = repository.getClientInfo("Frodo Baggins");
  }

  @Test
  public void testAddPhoneNumberSuccess() {
    repository.addPhoneNumber("Mike Tyson", "555");

    final Client client = repository.getClientInfo("Mike Tyson");
    assertEquals("Unexpected client phone numbers size", 2, client.getPhoneNumbers().size());
    assertEquals("Unexpected phone number", "555", client.getPhoneNumbers().get(1));
  }

  @Test(expected = DbException.class)
  public void testAddPhoneNumberInvalidClientFail() {
    repository.addPhoneNumber("Frodo Baggins", "002");
  }

  @Test(expected = DbException.class)
  public void testAddPhoneNumberNumberExistsFail() {
    repository.addPhoneNumber("Mike Tyson", "002");
  }

  @Test
  public void testEditPhoneNumberSuccess() {
    repository.editPhoneNumber("Mike Tyson", 0, "555");

    final Client client = repository.getClientInfo("Mike Tyson");
    assertEquals("Unexpected client phone numbers size", 1, client.getPhoneNumbers().size());
    assertEquals("Unexpected phone number", "555", client.getPhoneNumbers().get(0));
  }

  @Test(expected = DbException.class)
  public void testEditPhoneNumberFail() {
    repository.editPhoneNumber("Frodo Baggins", 0, "002");
  }

}
