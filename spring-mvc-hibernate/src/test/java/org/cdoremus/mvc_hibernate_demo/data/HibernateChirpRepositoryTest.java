package org.cdoremus.mvc_hibernate_demo.data;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cdoremus.mvc_hibernate_demo.TestConfig;
import org.cdoremus.mvc_hibernate_demo.domain.Chirp;
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
public class HibernateChirpRepositoryTest {

	@Inject
	private ChirpRepository repository;
	
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
		Chirp chirp = new Chirp("message 1", new Date());
		
		repository.save(chirp);
		
		System.out.println("Chirp saved: " + chirp);
		assertTrue(chirp.getId() == 1L);
	}

	@Test
	public void testFindByUsername() {
		String message = "message1";
		Chirp chirp = new Chirp(message, new Date());
		System.out.println("Date before: " + chirp.getTime());
		repository.save(chirp);

		Chirp newChirp = repository.findOne(chirp.getId());
		
		System.out.println("Date after: " + newChirp.getTime());
		assertEquals(chirp.getId(), newChirp.getId());
		assertEquals(chirp.getMessage(), newChirp.getMessage());
	}

	@Test
	public void testFindChirps() throws Exception {
		Chirp chirp1 = new Chirp("message1", new Date());
		repository.save(chirp1);
		Chirp chirp2 = new Chirp("message2", new Date());
		repository.save(chirp2);
		
		List<Chirp> chirps = repository.findChirps(10, 10);
		
		assertEquals(2, chirps.size());
		
	}
	
	
	@Test
	public void testFindRecentChirps() throws Exception {
		loadChirps(25);
		
		List<Chirp> chirps = repository.findRecentChirps();
		
		assertEquals(20, chirps.size());
		
	}
	
	private void loadChirps(int number) throws Exception {
		Chirp chirp;
		for (int i = 0; i < number; i++) {
			chirp = new Chirp("message" + i, new Date());
//			Thread.currentThread().sleep(100);
			repository.save(chirp);
		}
	}
	
	
	public ChirpRepository getRepository() {
		return repository;
	}

	public void setRepository(ChirpRepository repository) {
		this.repository = repository;
	}

}
