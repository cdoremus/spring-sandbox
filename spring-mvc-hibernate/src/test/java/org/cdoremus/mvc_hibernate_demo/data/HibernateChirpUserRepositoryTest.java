package org.cdoremus.mvc_hibernate_demo.data;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.cdoremus.mvc_hibernate_demo.TestConfig;
import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class HibernateChirpUserRepositoryTest {

	@Inject
	private ChirpUserRepository repository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		ChirpUser user = new ChirpUser("foobar", "foobar1", "Foo", "Bar", "foo@bar.com");
		repository.save(user);
		System.out.println("User saved: " + user);
		assertTrue(user.getId() == 1L);
	}

	@Test
	public void testFindByUsername() {
		String username = "foobar1";
		ChirpUser user = new ChirpUser(username, "foobar1", "Foo", "Bar", "foo@bar.com");
		repository.save(user);
		ChirpUser newUser = repository.findByUsername(username);
		assertEquals(user.getId(), newUser.getId());
		assertEquals(user.getFirstName(), newUser.getFirstName());
		assertEquals(user.getLastName(), newUser.getLastName());
		assertEquals(user.getEmail(), newUser.getEmail());
	}

	public ChirpUserRepository getRepository() {
		return repository;
	}

	public void setRepository(ChirpUserRepository repository) {
		this.repository = repository;
	}

}
