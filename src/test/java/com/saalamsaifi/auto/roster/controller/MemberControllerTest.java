package com.saalamsaifi.auto.roster.controller;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;

@IfProfileValue(name = "spring.profiles.active", values = { "test" })
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberControllerTest {
	@Autowired
	private TeamController teamController;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private GroupController controller;

	@Autowired
	private ObjectMapper mapper;

	private static String TEAM_ID;

	private static final String TEAM_JSON = "{\"name\":\"Operation Intelligence\",\"maxWfrlAllowed\":1,\"groups\":[{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":true,\"likes\":[0],\"dislikes\":[4]}]}]}";
	private static final String TEAM_JSON_WITHOUT_GROUPS = "{\"name\":\"Operation Intelligence\",\"maxWfrlAllowed\":1}";
	private static final String GROUP_JSON = "{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":true,\"likes\":[0],\"dislikes\":[4]}]}";
	private static final String GROUP_JSON_WITHOUT_MEMBERS = "{\"name\":\"SMAPI\",\"maxWfrlAllowed\":2}";

	private Object stringToObject(String json, Class<? extends Object> _class) {
		try {
			return mapper.readValue(json, _class);
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Before
	public void setup() {
		teamRepository.deleteAll();
	}

	@After
	public void tearDown() {
		teamRepository.deleteAll();
	}

	@Test
	public void test() {

	}
}
