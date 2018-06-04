package emx.solar.pack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emx.solar.pack.domains.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByUsername(String username);
	
}
