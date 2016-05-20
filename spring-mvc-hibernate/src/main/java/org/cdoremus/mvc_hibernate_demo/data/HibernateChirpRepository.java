package org.cdoremus.mvc_hibernate_demo.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.cdoremus.mvc_hibernate_demo.domain.Chirp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateChirpRepository implements ChirpRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Chirp> findRecentChirps() {
//		Query query = entityManager.createNativeQuery(
//		        "select  id, message, created_at, latitude, longitude from Chirp order by created_at desc limit 20");
		Query query = entityManager.createQuery(
		        "select c from Chirp c order by c.time desc");
		query.setMaxResults(20);
		return (List<Chirp>)query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Chirp> findChirps(long max, int count) {
		Query query = entityManager.createQuery(
			"select c from Chirp c" //);
					+ " where c.id < ?1" +
			        " order by c.time desc");
		query.setParameter(1, max);
		query.setMaxResults(20);
//		query.setFirstResult(1);
		return (List<Chirp>)query.getResultList();
	}

	@Override
	public Chirp findOne(long id) {
		return entityManager.find(Chirp.class, id);
	}

	@Override
	public void save(Chirp chirp) {
		if (!entityManager.contains(chirp)) {
			entityManager.persist(chirp);
		} else {
			entityManager.merge(chirp);			
		}
	}

}
