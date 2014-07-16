package org.landcycle.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public class ForSaleDaoImpl extends HibernateDaoSupport implements ForSaleDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.ForSaleDao#findAll()
	 */
	@Override
	public List<ForSaleEntity> findAll() {
		ForSaleEntity d = getHibernateTemplate().get(ForSaleEntity.class,
				System.currentTimeMillis());
		return null;
	}

	
	@Override
	public List<ForSaleEntity> findByQuery(ForSaleEntity user) {
		Session session = getSession(false);
		Query query = null;
		if (user.getLat() != null && user.getLng() != null) {
			//cerca nel raggio di 50 km
			query = session.createSQLQuery("select  {f.*}, ( 6371 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(:longitudine) ) + sin( radians(:latitudine) ) * sin( radians( lat ) ) ) ) AS distance from user u, forsale f HAVING distance < 50 ORDER BY distance LIMIT 0 , 50")  
					   .addEntity("f", ForSaleEntity.class);
			query.setParameter("longitudine", user.getLat());
			query.setParameter("latitudine", user.getLng());
		}else{
			query = session.createSQLQuery("select  {f.*} from  forsale f")  
			   .addEntity("f", ForSaleEntity.class);
			   
//			   .list();
//			hql += " WHERE ent.mailvend = :id_user and dom.id = :id_domain";
//			query = session
//					.createSQLQuery("select f.id, f.name,f.img,f.description,f.tags,f.mailvend,f.mailacq,f.optional,f.citta, f.lat, f.lng, u.mail,u.nome,u.avatar  FROM forsale f , user u where u.mail = f.mailvend ");
			
		}
		List<ForSaleEntity> ret = query.list();
		
		return ret;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.ForSaleDao#save(org.landcycle.dao.ForSaleEntity)
	 */
	@Override
	public ForSaleEntity save(ForSaleEntity user) {
		Session sess = getSession(false);
		// UserDao d =
		// getHibernateTemplate().get(UserDao.class,System.currentTimeMillis());
		getHibernateTemplate().merge(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.landcycle.dao.ForSaleDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String entity) {

	}

}
