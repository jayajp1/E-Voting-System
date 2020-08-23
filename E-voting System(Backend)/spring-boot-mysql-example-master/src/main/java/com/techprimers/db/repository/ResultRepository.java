package com.techprimers.db.repository;


import com.techprimers.db.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result,Integer> {
}
