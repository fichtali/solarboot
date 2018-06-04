package emx.solar.pack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emx.solar.pack.entities.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
	
	public RoleEntity findByRoleName(String roleName);
}
