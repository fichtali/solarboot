package emx.solar.pack.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import emx.solar.pack.utils.AppGlobalConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="edu_task", schema=AppGlobalConfig.DATABASE_SCHEMA)
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="task_name")
	private String taskName;

}
