package org.landcycle.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Repository<UserEntity, String> 
{
	public List<UserEntity> findAll();
	@Query(nativeQuery = true, value = "select u.*,f.*, ( 6371 * acos( cos( radians(:latitudine) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(:longitudine) ) + sin( radians(:latitudine) ) * sin( radians( lat ) ) ) ) AS distance from User u JOIN Taggable AS f on u.mail = f.user  where f.user <> :mailvend  HAVING distance < 50 ORDER BY distance LIMIT 0 , 50")
	public Set<UserEntity> findByQuery(@Param("longitudine") Double longitudine,
			@Param("latitudine") Double latitudine,@Param("mailvend") String mailvend);
	public UserEntity save(UserEntity wsdl);
	public UserEntity findOne(String mail);
	public void delete(String entity);
	public boolean exists(String entity);
	
}
