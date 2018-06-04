package emx.solar.pack.domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import emx.solar.pack.utils.AppGlobalConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="edu_role", schema=AppGlobalConfig.DATABASE_SCHEMA)
@Data @NoArgsConstructor @AllArgsConstructor
public class RoleEntity implements Serializable, GrantedAuthority{

	private static final long serialVersionUID = -955789014670899775L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="role_name", nullable=false)
	private String roleName;

	@Override
	public String getAuthority() {
		return roleName;
	}
}
