package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.util.TestUtils.stringToJsonObject;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.util.TestUtils;
import java.text.MessageFormat;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupControllerTest {
  @Autowired private TeamController teamController;

  @Autowired private TeamRepository teamRepository;

  @Autowired private GroupController controller;

  @Autowired private ObjectMapper mapper;

  private static String TEAM_ID;

  private static final String TEAM_JSON =
      "{\"name\":\"Team Json\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":true,\"likes\":[0],\"dislikes\":[4]}]}]}";
  private static final String TEAM_JSON_WITHOUT_GROUPS =
      "{\"name\":\"Team Json without groups\",\"maxWfrlAllowed\":1}";
  private static final String GROUP_JSON =
      "{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":true,\"likes\":[0],\"dislikes\":[4]}]}";
  private static final String GROUP_JSON_WITHOUT_MEMBERS =
      "{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2}";

  @Before
  public void setup() {
    teamRepository.deleteAll();

    // TODO: Improve by avoiding setting ObjectMapper every time
    TestUtils.setObjectMapper(mapper);
  }

  @After
  public void tearDown() {
    teamRepository.deleteAll();
  }

  /** */
  @Test(expected = IllegalArgumentException.class)
  public void addNewGroup_WhenTeamIsNull_GroupJson_ResponseCode404() {
    ResponseEntity<List<Group>> response =
        controller.add(null, (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void addNewGroup_WhenTeamIsInvalid_GroupJson_ResponseCode404() {
    ResponseEntity<List<Group>> response =
        controller.add("T01", (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void addNewGroup_WhenTeamGroupsIsNull_GroupJson_ResponseCode200() {
    TEAM_ID =
        teamController
            .add((Team) stringToJsonObject(TEAM_JSON_WITHOUT_GROUPS, Team.class))
            .getBody()
            .getId();

    ResponseEntity<List<Group>> response =
        controller.add(TEAM_ID, (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /** */
  @Test
  public void addNewGroup_WhenTeamHaveGroups_GroupJson_ResponseCode200() {
    TEAM_ID =
        teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody().getId();

    ResponseEntity<List<Group>> response =
        controller.add(TEAM_ID, (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /** */
  @Test
  public void updateGroup_WhenTeamAndGroupIsNotAvailable_ResponseCode404() {
    ResponseEntity<List<Group>> response =
        controller.update("G01", (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void updateGroup_WhenTeamGroupIsNotAvailable_GroupIdIsNotAvailable_ResponseCode404() {
    teamController
        .add((Team) stringToJsonObject(TEAM_JSON_WITHOUT_GROUPS, Team.class))
        .getBody()
        .getId();

    ResponseEntity<List<Group>> response =
        controller.update("G01", (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void updateGroup_WhenGroupIsNotAvailableInTeam_ResponseCode404() {
    teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody().getId();

    ResponseEntity<List<Group>> response =
        controller.update("G01", (Group) stringToJsonObject(GROUP_JSON, Group.class));
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void updateGroup_WhenTeamGroupIsAvailable_GroupIdIsAvailable_ResponseCode200() {
    Collection collection =
        teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody();
    String groupId = collection.getGroups().get(0).getId();

    ResponseEntity<List<Group>> response =
        controller.update(groupId, (Group) stringToJsonObject(GROUP_JSON, Group.class));
    System.out.println(
        MessageFormat.format(
            "Query: id: {0} group: {1}", groupId, teamRepository.findByGroupId(groupId)));
    System.out.println(
        MessageFormat.format("Response: id: {0} group: {1}", groupId, response.getBody()));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /** */
  @Test
  public void updateGroup_WhenTeamGroupIsAvailable_GroupMemberIsNotAvailable_ResponseCode200() {
    Collection collection =
        teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody();
    String groupId = collection.getGroups().get(0).getId();

    ResponseEntity<List<Group>> response =
        controller.update(
            groupId, (Group) stringToJsonObject(GROUP_JSON_WITHOUT_MEMBERS, Group.class));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /** */
  @Test
  public void get_WhenGroupIsAvailable_GroupList() {
    Collection collection =
        teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody();
    TEAM_ID = collection.getId();

    ResponseEntity<List<Group>> response = controller.getByTeamId(TEAM_ID);

    assertEquals(1, response.getBody().size());
  }

  /** */
  @Test
  public void get_WhenGroupIsNotAvailable_ResponseCode404() {
    Collection collection =
        teamController
            .add((Team) stringToJsonObject(TEAM_JSON_WITHOUT_GROUPS, Team.class))
            .getBody();
    TEAM_ID = collection.getId();

    ResponseEntity<List<Group>> response = controller.getByTeamId(TEAM_ID);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void get_WhenTeamIsNotAvailable_ResponseCode400() {
    teamController.add((Team) stringToJsonObject(TEAM_JSON_WITHOUT_GROUPS, Team.class)).getBody();

    ResponseEntity<List<Group>> response = controller.getByTeamId("T01");

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /** */
  @Test
  public void getById_WhenGroupIsAvailable_Group() {
    Collection collection =
        teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody();
    String groupId = collection.getGroups().get(0).getId();

    ResponseEntity<Group> response = controller.getByGroupId(groupId);

    assertEquals(groupId, response.getBody().getId());
  }

  /** */
  @Test
  public void getById_WhenGroupIsNotAvailable_ResponseCode404() {
    teamController.add((Team) stringToJsonObject(TEAM_JSON, Team.class)).getBody();

    ResponseEntity<Group> response = controller.getByGroupId("G01");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** */
  @Test
  public void getById_WhenTeamGroupIsNull_ResponseCode404() {
    teamController.add((Team) stringToJsonObject(TEAM_JSON_WITHOUT_GROUPS, Team.class)).getBody();

    ResponseEntity<Group> response = controller.getByGroupId("G01");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
