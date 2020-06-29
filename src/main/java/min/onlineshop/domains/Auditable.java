package min.onlineshop.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	
	 	@CreatedBy
	    protected U createdBy;

	    @CreatedDate
	    @Temporal(TIMESTAMP)
	    protected Date createdDate;

	    @LastModifiedBy
	    protected U updatedBy;

	    @LastModifiedDate
	    @Temporal(TIMESTAMP)
	    protected Date updatedDate;
	    
}
