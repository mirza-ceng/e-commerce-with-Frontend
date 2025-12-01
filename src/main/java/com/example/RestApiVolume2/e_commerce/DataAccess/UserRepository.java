
package com.example.RestApiVolume2.e_commerce.DataAccess;

import com.example.RestApiVolume2.e_commerce.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author 2005m
 */
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}

