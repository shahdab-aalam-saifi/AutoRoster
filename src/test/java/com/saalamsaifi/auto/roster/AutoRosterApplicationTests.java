package com.saalamsaifi.auto.roster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

@IfProfileValue(name = "spring.profiles.active", values = { "test" })
@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoRosterApplicationTests {

	@Test
	public void contextLoads() {
	}

}
