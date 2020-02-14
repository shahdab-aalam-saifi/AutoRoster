package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.util.TestUtils.stringToJsonObject;
import static org.junit.Assert.assertEquals;
import javax.validation.Valid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {
  @Autowired
  private TeamController teamController;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberController controller;

  @Autowired
  private ObjectMapper mapper;

  private static String GROUP_ID;
  private static String MEMBER_ID;

  private static final String GROUP_WITHOUT_MEMBER_JSON =
      "{\"name\":\"Team Name\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"Group Name\",\"maxWfrlAllowed\":1}]}";
  private static final String GROUP_WITH_MEMBER_JSON =
      "{\"name\":\"Team Name\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"Group Name\",\"maxWfrlAllowed\":1, \"members\": [{\"name\": \"Member Name\"}]}]}";

  @Before
  public void setup() {
    teamRepository.deleteAll();

    TestUtils.setObjectMapper(mapper);
  }

  @After
  public void tearDown() {
    teamRepository.deleteAll();
  }

  @Test
  public void addNewMember_WhenMemberIsNotExist_ResponseCode200() {
    Collection collection = teamController
        .add((@Valid Team) stringToJsonObject(GROUP_WITHOUT_MEMBER_JSON, Team.class)).getBody();
    GROUP_ID = collection.getGroups().get(0).getId();

    ResponseEntity<Collection> response =
        controller.add(GROUP_ID, Member.builder().name("Member Name").build());
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
  }

  @Test
  public void addNewMember_WhenMemberIsExist_ResponseCode200() {
    Collection collection = teamController
        .add((@Valid Team) stringToJsonObject(GROUP_WITH_MEMBER_JSON, Team.class)).getBody();
    GROUP_ID = collection.getGroups().get(0).getId();

    ResponseEntity<Collection> response =
        controller.add(GROUP_ID, Member.builder().name("Member Name").build());
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
  }

  @Test
  public void addNewMember_WhenGroupIsNotExist_ResponseCode400() {
    teamController.add((@Valid Team) stringToJsonObject(GROUP_WITHOUT_MEMBER_JSON, Team.class))
        .getBody();

    ResponseEntity<Collection> response =
        controller.add("G00000", Member.builder().name("Member Name").build());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
  }

  @Test
  public void updateMember_WhenMemberIsNotExist_ResponseCode400() {
    teamController.add((@Valid Team) stringToJsonObject(GROUP_WITHOUT_MEMBER_JSON, Team.class))
        .getBody();

    ResponseEntity<Collection> response =
        controller.update("M0000", Member.builder().name("Member Name").build());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
  }

  @Test
  public void updateMember_WhenMemberIsExist_ResponseCode200() {
    Collection collection = teamController
        .add((@Valid Team) stringToJsonObject(GROUP_WITH_MEMBER_JSON, Team.class)).getBody();

    MEMBER_ID = collection.getGroups().get(0).getMembers().get(0).getId();

    ResponseEntity<Collection> response =
        controller.update(MEMBER_ID, Member.builder().name("Member Name").build());
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
  }

  @Test
  public void getMember_WhenMemberIsExist_ResponseCode200() {
    Collection collection = teamController
        .add((@Valid Team) stringToJsonObject(GROUP_WITH_MEMBER_JSON, Team.class)).getBody();

    MEMBER_ID = collection.getGroups().get(0).getMembers().get(0).getId();

    ResponseEntity<Member> response = controller.get(MEMBER_ID);

    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
  }

  @Test
  public void getMember_WhenMemberIsNotExist_ResponseCode400() {
    Collection collection = teamController
        .add((@Valid Team) stringToJsonObject(GROUP_WITH_MEMBER_JSON, Team.class)).getBody();

    collection.getGroups().get(0).getMembers().get(0).getId();

    ResponseEntity<Member> response = controller.get("M0000");

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
  }
}
