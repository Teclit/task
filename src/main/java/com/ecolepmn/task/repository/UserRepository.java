package com.ecolepmn.task.repository;

import com.ecolepmn.task.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);

}
