package min.onlineshop.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String content;

	@CreatedBy
	private String modifiedBy;

	@CreatedDate
	@Temporal(TIMESTAMP)
	private Date modifiedDate;

	@Enumerated(STRING)
	private Action action;

	public History() {
	}

	public History(Object file, Action action) {
		this.content = file.toString();
		this.action = action;
	}

}
