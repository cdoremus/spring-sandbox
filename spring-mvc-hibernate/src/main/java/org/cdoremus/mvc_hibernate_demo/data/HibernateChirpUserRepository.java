package org.cdoremus.mvc_hibernate_demo.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.cdoremus.mvc_hibernate_demo.domain.ChirpUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateChirpUserRepository implements ChirpUserRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public ChirpUser save(ChirpUser user) {
		if (!entityManager.contains(user)) {
			entityManager.persist(user);
		} else {
			entityManager.merge(user);			
		}
		return user;
	}

	@Override
	public ChirpUser findByUsername(String username) {
		Query query = entityManager.createQuery("select u from ChirpUser u where u.username= ?1", ChirpUser.class);
		query.setParameter(1, username);
		return (ChirpUser)query.getSingleResult();
	}

}
