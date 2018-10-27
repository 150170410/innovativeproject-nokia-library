package com.nokia.library.nokiainnovativeproject.test;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("autotests")
public class NokiaInnovativeProjectApplicationTests {

	@Test
	public void contextLoads() {

	}
}
