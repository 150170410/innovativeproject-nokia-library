package com.nokia.library.nokiainnovativeproject.test;

import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NokiaInnovativeProjectApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void contextLoads() {

	}
}
