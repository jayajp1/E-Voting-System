package com.techprimers.db.repository;

import com.techprimers.db.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter,Integer> {

    //public Voter findByElection_CardAndPassword(String uid, String pwd);

    public Voter findByUserNameAndPassword(String uid, String pwd);
    public Voter findByUserName(String uid);
    public Voter findByUserNameAndPasswordAndAdharcard(String uid,String pwd,int adr);

}
