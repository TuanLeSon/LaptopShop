package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

//crud: create, read, update, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    // return only one user
    User findByEmail(String email);

    User findById(long id);

    void deleteById(long id);

    boolean existsByEmail(String email);
}
