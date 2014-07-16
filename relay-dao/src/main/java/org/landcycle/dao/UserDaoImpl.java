package org.landcycle.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.UserDao#findAll()
	 */
	@Override
	public List<UserEntity> findAll() {
		Session session = getSession(false);
		Query query = null;

		query = session.createSQLQuery("select  {u.*} from  user u")
				.addEntity("u", UserEntity.class);
		return query.list();
	}
	
	@Override
	public UserEntity findOne(String email) {
		UserEntity user = getHibernateTemplate().get(UserEntity.class, email);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.UserDao#save(org.landcycle.dao.UserEntity)
	 */
	@Override
	public UserEntity save(UserEntity user) {
		Session sess = getSession(false);
		// UserDao d =
		// getHibernateTemplate().get(UserDao.class,System.currentTimeMillis());
		getHibernateTemplate().merge(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.UserDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String entity) {

	}
}
