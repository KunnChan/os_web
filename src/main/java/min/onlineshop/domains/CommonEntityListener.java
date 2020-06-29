package min.onlineshop.domains;

import min.onlineshop.services.impl.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class CommonEntityListener {

	 	@PrePersist
	    public void prePersist(Object target) {
	        perform(target, Action.INSERTED);
	    }

	    @PreUpdate
	    public void preUpdate(Object target) {
	        perform(target, Action.UPDATED);
	    }

	    @PreRemove
	    public void preRemove(Object target) {
	        perform(target, Action.DELETED);
	    }

	    @Transactional(MANDATORY)
	    private void perform(Object target, Action action) {
	        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
	        entityManager.persist(new History(target, action));
	    }
}
