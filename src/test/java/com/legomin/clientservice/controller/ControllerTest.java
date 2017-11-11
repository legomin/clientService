package com.legomin.clientservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testController() throws Exception {
    this.mockMvc.perform(post("/clients?name=Vasya")).andExpect(status().isCreated());
    this.mockMvc.perform(post("/clients?name=Vasya")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Client Vasya is already exists")));
    this.mockMvc.perform(post("/clients?name=Petya")).andExpect(status().isCreated());
    this.mockMvc.perform(MockMvcRequestBuilders.get("/clients")).andExpect(status().isOk())
      .andExpect(content().json("[Vasya,Petya]"));

    this.mockMvc.perform(post("/clients/Vasya?number=+71231231212&id=")).andExpect(status().isCreated());
    this.mockMvc.perform(post("/clients/Vova?number=+71231231212&id=")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Client Vova is not found")));
    this.mockMvc.perform(post("/clients/Vasya?number=+71231231212&id=")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Phone number +71231231212 for client Vasya already exists")));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/Vasya")).andExpect(status().isOk())
      .andExpect(content().json("{name:Vasya,phoneNumbers:[\"+71231231212\"]}"));

    this.mockMvc.perform(post("/clients/Vova?number=+71231231212&id=1")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Client Vova is not found")));
    this.mockMvc.perform(post("/clients/Vasya?number=+71231231212&id=1")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Phone number with id 1 is not exist")));
    this.mockMvc.perform(post("/clients/Vasya?number=+71231231212&id=0")).andExpect(status().isForbidden())
      .andExpect(content().string(containsString("Phone number +71231231212 for client Vasya already exists")));
    this.mockMvc.perform(post("/clients/Vasya?number=+75551231212&id=0")).andExpect(status().isAccepted());

    this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/Vasya")).andExpect(status().isOk())
      .andExpect(content().json("{name:Vasya,phoneNumbers:[\"+75551231212\"]}"));
  }

}