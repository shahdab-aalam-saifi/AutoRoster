package com.saalamsaifi.auto.roster.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

@IfProfileValue(name = "spring.profiles.active", values = { "test" })
@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTest {
	@Autowired
	private TeamController controller;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ObjectMapper mapper;

	private static final String TEAM_JSON = "{\"name\":\"Operation Intelligence\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":true,\"likes\":[0],\"dislikes\":[4]}]}]}";
	private static final String TEAM_JSON_WITHOUT_MEMBERS = "{\"name\":\"Operation Intelligence\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2}]}";
	private static final String TEAM_JSON_WITHOUT_GROUPS = "{\"name\":\"Operation Intelligence\",\"maxWfrlAllowed\":1}";

	private Object stringToObject(String json, Class<? extends Object> _class) {
		try {
			return mapper.readValue(json, _class);
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Before
	public void setUp() {
		teamRepository.deleteAll();
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void addNewTeam_WhenTeamIsNull_ThrowException() {
		controller.add(null);
	}

	/**
	 * 
	 */
	@Test
	public void addNewTeam_WhenTeamIsValidJson_ResponseCode200() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject("{}", Team.class));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void addNewTeam_WhenTeamJsonWithoutGroups_ResponseCode200() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON_WITHOUT_GROUPS,
				Team.class));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void addNewTeam_WhenTeamJsonWithoutMembers_ResponseCode200() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON_WITHOUT_MEMBERS,
				Team.class));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void addNewTeam_WhenTeamJson_ResponseCode200() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON, Team.class));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void updateTeam_WhenTeamIsAvailable_ResponseCode200() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON_WITHOUT_GROUPS,
				Team.class));
		Collection collection = response.getBody();

		response = controller.update(collection.getId(), (Team) stringToObject(TEAM_JSON, Team.class));
		collection = response.getBody();

		assertEquals(true, !collection.getGroups().isEmpty());
	}

	/**
	 * 
	 */
	@Test
	public void updateTeam_WhenTeamIsNotAvailable_ResponseCode404() {
		ResponseEntity<Collection> response = controller.update("T0001", (Team) stringToObject(TEAM_JSON, Team.class));
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void updateTeam_WhenTeamIsAvailable_ThenSettingEmptyJson_GroupIsNull() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON_WITHOUT_GROUPS,
				Team.class));
		Collection collection = response.getBody();

		response = controller.update(collection.getId(), (Team) stringToObject("{}", Team.class));
		collection = response.getBody();

		assertEquals(null, collection.getGroups());
	}

	/**
	 * 
	 */
	@Test
	public void getTeam_WhenTeamIsAvailable_TeamArray() {
		controller.add((Team) stringToObject(TEAM_JSON_WITHOUT_GROUPS, Team.class));

		ResponseEntity<List<Collection>> response = controller.get();

		assertEquals(true, !response.getBody().isEmpty());
	}

	/**
	 * 
	 */
	@Test
	public void getTeam_WhenTeamIdIsNull_ResponseCode400() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON, Team.class));

		response = controller.get(null);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void getTeam_WhenTeamIdIsEmpty_ResponseCode400() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON, Team.class));

		response = controller.get("");

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void getTeam_WhenTeamIdIsInvalid_ResponseCode404() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON, Team.class));

		response = controller.get("T-0");

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}

	/**
	 * 
	 */
	@Test
	public void getTeam_WhenTeamIdIsGiven_Team() {
		ResponseEntity<Collection> response = controller.add((Team) stringToObject(TEAM_JSON, Team.class));
		Collection collection = response.getBody();

		response = controller.get(collection.getId());

		assertEquals(collection, response.getBody());
	}

}
