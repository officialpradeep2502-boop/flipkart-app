package com.flipkartclone.backend.repository;

import com.flipkartclone.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String Email);
  //  Optional<User> findByEmail(String email);

//              findAll() → saare users nikal lo.
//            	findById(Long id) → ek user id se.
//            	save(User user) → ek user save karo.
//            	deleteById(Long id) → user delete karo
}
