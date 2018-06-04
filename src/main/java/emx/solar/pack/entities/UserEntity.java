package emx.solar.pack.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import emx.solar.pack.utils.AppGlobalConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="edu_user", schema=AppGlobalConfig.DATABASE_SCHEMA)
@Data @NoArgsConstructor @AllArgsConstructor
public class UserEntity implements Serializable{

	private static final long serialVersionUID = -2298204075884876659L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String username;
	
	private String password;
	
	private boolean enabled;
	
	@Column(name="first_name", length=30, nullable=false)
	private String firstName;
	
	@Column(name="last_name", length=30, nullable=false)
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_creation", nullable=false)
	private Date dateCreation;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_maj")
	private Date dateMaj;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<RoleEntity> roles = new ArrayList<>();

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}
}
