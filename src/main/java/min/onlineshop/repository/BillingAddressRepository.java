package min.onlineshop.repository;

import min.onlineshop.domains.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
    List<BillingAddress> findByActive(Boolean active);
}
