package com.ironSchool.demo.repository;

import com.ironSchool.demo.model.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAdmin, Long> {
    UserAdmin findByEmail(String email);
}
