package com.legomin.clientservice.service;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.legomin.clientservice.domain.Client;
import com.legomin.clientservice.domain.ClientsRepository;
import com.legomin.clientservice.domain.exception.DbException;
import com.legomin.clientservice.service.impl.DefaultClientService;
import com.legomin.clientservice.service.phoneformatter.PhoneNumberFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {

  @Mock
  private ClientsRepository repository;

  @Mock
  private PhoneNumberFormatter formatter;

  private ClientsService service;

  @Before
  public void setUp() throws Exception {
    service = new DefaultClientService(repository, formatter);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testGetClients() throws Exception {
    final Set<String> expected = ImmutableSet.of("test1", "test2");
    when(repository.getClientsList()).thenReturn(expected);
    assertEquals("Unexpected result set", expected, service.getClients());
    verify(repository).getClientsList();
  }

  @Test
  public void testInsertClientSussessRepoResult() throws Exception {
    assertEquals("Unexpected result", ResultEntity.getSussessEntity(), service.insertClient("test3"));
    verify(repository).insertClient("test3");
  }

  @Test
  public void testInsertClientFailRepoResult() throws Exception {
    doThrow(new DbException("WTF")).when(repository).insertClient(anyString());
    assertEquals("Unexpected result", ResultEntity.getErrorEntity("WTF"), service.insertClient("test4"));
    verify(repository).insertClient("test4");
  }

  @Test
  public void testClientInfo() throws Exception {
    final String vasya = "Vasya Pupkin";
    final Client expectedClient = new Client(vasya, newArrayList("001", "002"));
    when(repository.getClientInfo(eq(vasya))).thenReturn(expectedClient);
    assertEquals("Unexpected result", ResultEntity.getSussessEntity(expectedClient), service.clientInfo(vasya));
    verify(repository).getClientInfo(vasya);
  }

  @Test
  public void testAddClientNumber() throws Exception {

  }

  @Test
  public void testUpdateClientNumber() throws Exception {

  }

}