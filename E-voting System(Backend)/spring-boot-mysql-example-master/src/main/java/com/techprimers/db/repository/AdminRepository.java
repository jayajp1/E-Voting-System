package com.techprimers.db.repository;

import com.techprimers.db.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    public Admin findByUserNameAndPassword(String uid, String pwd);
}
