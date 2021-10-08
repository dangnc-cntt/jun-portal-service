package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.User;
import com.jun.portalservice.domain.entities.types.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
  User findUserByUsername(String username);

  Page<User> findUsersByUsername(String username, Pageable pageable);

  User findUserById(Integer id);

  List<User> findUserByRole(UserRole role);

  Page<User> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
