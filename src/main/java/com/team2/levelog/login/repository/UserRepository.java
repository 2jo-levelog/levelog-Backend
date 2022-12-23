package com.team2.levelog.login.repository;

import com.team2.levelog.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
