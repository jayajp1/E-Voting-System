package com.techprimers.db.resource;

import com.techprimers.db.Blockchain.Transaction;
import com.techprimers.db.model.Candidate;
import com.techprimers.db.model.Count;
import com.techprimers.db.model.Vote;
import com.techprimers.db.model.Voter;
import com.techprimers.db.repository.CandidateRepository;
import com.techprimers.db.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/vote")

public class VoteResource {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @GetMapping(value = "/all")
    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    @PostMapping(value = "/load/{candidate}/{party}")
    public boolean persist(@PathVariable("candidate") String candidate, @PathVariable("party") String party) {
        int loopbreak = -1;
        Transaction transaction;
        String PreviousHash = "0";
        Vote vote = new Vote();
        List<Vote> list = voteRepository.findAll();
        int Size = list.size();
//get previous data
        for (int i = 0; i < list.size(); i++) {
            Vote v = list.get(i);
            if (i != 0) {
                Vote v1 = list.get(i - 1);
                PreviousHash = v1.getHash();
            }

            System.out.println(PreviousHash);
            transaction = new Transaction(v.getId(), v.getCandidate(), v.getPartyname(), PreviousHash);
            System.out.println(v.getHash() + v.getPartyname() + v.getId() + v.getCandidate());
            System.out.println(transaction.hashCode());

            String hash = "" + transaction.hashCode();
            System.out.println(v.getHash().equals(hash));
            if (!v.getHash().equals(hash)) {
                loopbreak = i;
                break;
            }

        }
//set current hash data
        System.out.println(loopbreak);
        if (loopbreak == -1) {
            vote.setCandidate(candidate);
            vote.setPartyname(party);

            if (list != null) {
                vote.setId(list.size() + 1);
            } else {
                vote.setId(1);
                PreviousHash = "0";
            }

            Transaction Settransaction = new Transaction(vote.getId(), vote.getCandidate(), vote.getPartyname(), PreviousHash);
            String hash = "" + Settransaction.hashCode();

            vote.setHash(hash);
            voteRepository.save(vote);
            System.out.println(true);
            return true;
        } else {
            System.out.println(false);
            return false;
        }
    }

    @PostMapping("/count")
    public List<Count> votecount()
    {
        int loopbreak = -1;
        Transaction transaction;
        String PreviousHash = "0";
        Vote vote = new Vote();
        List<Vote> list = voteRepository.findAll();
        int Size = list.size();

        List<Vote> voteList, voteList1;
        List<Count> countList = new ArrayList<>();
        List<Candidate> candidateList;
        String[] Party = {"BJP", "INC"};

//get previous data
        for (int i = 0; i < list.size(); i++) {
            Vote v = list.get(i);
            if (i != 0) {
                Vote v1 = list.get(i - 1);
                PreviousHash = v1.getHash();
            }

            System.out.println(PreviousHash);
            transaction = new Transaction(v.getId(), v.getCandidate(), v.getPartyname(), PreviousHash);
            System.out.println(v.getHash() + v.getPartyname() + v.getId() + v.getCandidate());
            System.out.println(transaction.hashCode());

            String hash = "" + transaction.hashCode();
            System.out.println(v.getHash().equals(hash));
            if (!v.getHash().equals(hash)) {
                loopbreak = i;
                break;
            }

        }
//set current hash data
        System.out.println(loopbreak);
        if (loopbreak == -1) {

            System.out.println("Hello");

            for (int i = 0; i < Party.length; i++) {
                voteList = voteRepository.findByPartyname(Party[i]);
                if(!voteList.isEmpty())
                {
                candidateList = candidateRepository.findBypartyname(Party[i]);
                int size = voteList.size();
                System.out.println(size + "hello");
                Count c1 = new Count();
                c1.setCandidate(voteList.get(0).getCandidate());
                c1.setPartyname(Party[i]);
                c1.setCount(size);
                c1.setParty_img(candidateList.get(0).getPartyphotopath());
                System.out.println(c1.getParty_img());
                c1.setCandidate_img(candidateList.get(0).getCandidatePhotopath());
                countList.add(c1);

                System.out.println(countList.get(0).getParty_img());
               // System.out.println(" "+countList.get(1).getParty_img());
            }}
        }
        else
        {
            return countList;
        }

        return countList;
    }


}