package com.saalamsaifi.auto.roster.controller;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.saalamsaifi.auto.roster.data.repository.TeamRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TeamController.class)
public class TeamControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeamRepository teamRepository;

  @Test
  public void addNewTeam() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put("/team/add/")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(
            "{\n" + "  \"groups\": [\n" + "    {\n" + "      \"id\": \"string\",\n"
                + "      \"maxWfrlAllowed\": 0,\n" + "      \"members\": [\n" + "        {\n"
                + "          \"dislikes\": [\n" + "            \"string\"\n" + "          ],\n"
                + "          \"id\": \"string\",\n" + "          \"interested\": true,\n"
                + "          \"likes\": [\n" + "            \"string\"\n" + "          ],\n"
                + "          \"name\": \"string\"\n" + "        }\n" + "      ],\n"
                + "      \"name\": \"string\",\n" + "      \"teamId\": \"string\"\n" + "    }\n"
                + "  ],\n" + "  \"id\": \"string\",\n" + "  \"maxWfrlAllowed\": 0,\n"
                + "  \"name\": \"string\"\n" + "}");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    System.out
        .println(
            MessageFormat
                .format("{0} vs {1}", HttpStatus.OK.value(), result.getResponse().getStatus()));
    // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K
    // Students","steps":["Learn Maven","Import Project","First Example","Second
    // Example"]}

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
  }
}
