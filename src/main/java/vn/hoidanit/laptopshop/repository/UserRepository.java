package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;

//crud: create, read, update, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);

    // insert into user value()
    List<User> findByEmail(String email);

    List<User> findAll();

    User findById(long id);

    void deleteById(long id);

    boolean existsByEmail(String email);
}
