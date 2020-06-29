package min.onlineshop.repository;

import min.onlineshop.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);
    List<Cart> findAllByUserIdAndProductId(Long userId, Long productId);
    void deleteAllByUserIdAndProductId(Long userId, Long productId);
}
