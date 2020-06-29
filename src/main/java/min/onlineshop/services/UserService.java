package min.onlineshop.services;

import min.onlineshop.domains.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByEmail(String email);
}
