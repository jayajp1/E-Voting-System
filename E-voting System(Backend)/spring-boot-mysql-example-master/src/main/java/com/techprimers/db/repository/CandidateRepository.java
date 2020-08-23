package com.techprimers.db.repository;

import com.techprimers.db.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate,Integer>
{
    //public Candidate GetCandidate();
    public List<Candidate> findByCity(String city);

    public List<Candidate> findBypartyname(String partyname);
}
