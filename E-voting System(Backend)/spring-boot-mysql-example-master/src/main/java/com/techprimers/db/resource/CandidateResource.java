package com.techprimers.db.resource;


import com.techprimers.db.model.Candidate;
import com.techprimers.db.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/candidate")

public class CandidateResource
{
    @Autowired
    CandidateRepository candidateRepository;

    @GetMapping(value = "/all")
    public List<Candidate> getAll(){return candidateRepository.findAll();}

    @PostMapping(value = "/load")
    public Boolean persist(@RequestBody Candidate candidate) {
        if(candidate!=null) {
            candidateRepository.save(candidate);
            return true;
        }
        else
        {
            return false;
        }
    }

    @PostMapping(value = "/delete")
    public List<Candidate> Delete(@RequestBody final Candidate candidate) {
        candidateRepository.delete(candidate.getAdhar());
        return candidateRepository.findAll();
    }

    @PostMapping(value = "/update")
    public List<Candidate> UpdateUser(@RequestBody final Candidate candidate) {
        candidateRepository.delete(candidate.getAdhar());
        candidateRepository.save(candidate);
        return candidateRepository.findAll();
    }

    @PostMapping(value="/select/{city}")
    public List<Candidate> selectbycity(@PathVariable("city") String city)
    {
        //System.out.println((candidateRepository.findByCity(city)).size());
        return candidateRepository.findByCity(city);
    }


}
