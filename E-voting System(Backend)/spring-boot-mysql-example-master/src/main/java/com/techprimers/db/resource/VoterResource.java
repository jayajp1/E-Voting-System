package com.techprimers.db.resource;



import com.techprimers.db.model.AES;
import com.techprimers.db.model.Users;
import com.techprimers.db.model.Voter;
import com.techprimers.db.repository.UsersRepository;
import com.techprimers.db.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/voter")

public class VoterResource
{
    @Autowired
    VoterRepository voterRepository;

    @Autowired
    UsersRepository usersRepository;



    @GetMapping(value="/all")
    public List<Voter> getAll(){ return voterRepository.findAll(); }

    @PostMapping(value="/load")
    public Integer persist(@RequestBody Voter voter)
    {
        String name=voter.getFirstName().trim();
        String Adhar=""+voter.getAdharcard();
        System.out.println(name+Adhar+voter.getUserName());
        Voter find=voterRepository.findByUserName(voter.getUserName());
        System.out.println((find==null));
        if(find==null)
        {
            //System.out.println(find.getUserName() + find.getAdharcard());
            Users users = usersRepository.findByNameAndAdharcard(name, Adhar);
            if (users != null)
            {
                System.out.println(users.getName() + users.getAdharcard());
                System.out.println("true");
                voter.setPassword("" + voter.getPassword().hashCode());
                if (voter != null)
                {
                    voterRepository.save(voter);
                    String r=new String("true");
                    return 1;
                }
                else
                {
                    String r=new String("false");
                    return 0;
                }
            }
            else
            {
                String r=new String("false");
                return 0;
            }
        }
System.out.println("-1");
return -1;
    }



    @PostMapping(value = "/delete")
    public List<Voter> Delete(@RequestBody final Voter voter) {
        voterRepository.delete(voter.getAdharcard());
        return voterRepository.findAll();
    }

    @PostMapping(value = "/update")
    public List<Voter> UpdateUser(@RequestBody final Voter voter) {
        voterRepository.delete(voter.getAdharcard());
        voterRepository.save(voter);
        return voterRepository.findAll();
    }

    @PostMapping(value="/auth/{uid}/{pwd}")
    public Voter AuthenticateVoter(@PathVariable("uid") String uid, @PathVariable("pwd") String pwd)
    {
        //pwd=""+pwd.hashCode();
        System.out.println(pwd+uid);
        Voter voter=voterRepository.findByUserNameAndPassword(uid,""+pwd.hashCode());
        if(voter!=null) {
            System.out.println(voter.getUserName() + " " + voter.getPassword()+voter.getVdone());
        }
        return voter;

    }

    @PostMapping(value="/auth/{uid}/{pwd}/{adr}")
    public Voter AuthenticateVoterQR(@PathVariable("uid") String uid, @PathVariable("pwd") String pwd,@PathVariable("adr") int adr)
    {
        //pwd=""+pwd.hashCode();
        System.out.println(pwd+uid+adr);
        Voter voter=voterRepository.findByUserNameAndPasswordAndAdharcard(uid,""+pwd.hashCode(),adr);
        if(voter!=null) {
            System.out.println(voter.getUserName() + " " + voter.getPassword()+voter.getVdone());
        }
        return voter;

    }

    @PostMapping(value="/update/{uid}/{pwd}/{mob}")
    public Boolean updatevoter(@PathVariable("uid") String uid , @PathVariable("pwd") String pwd,@PathVariable("mob") String mob)
    {
        Voter voter=voterRepository.findByUserName(uid);

        System.out.println(voter.getMobileNo()+"  \t\t"+mob+"\t\t"+voter.getMobileNo().equals(mob));
        if(voter!=null && voter.getMobileNo().equals(mob)) {
            voterRepository.delete(voter.getAdharcard());
            voter.setPassword(""+pwd.hashCode());
            voterRepository.save(voter);
            System.out.println("true");
            return true;
        }
        else
        {
            System.out.println("false");
            return false;
        }

    }

    @PostMapping(value="/updatevt/{uid}/{vdone}")
    public Boolean updatevdone(@PathVariable("uid") String uid, @PathVariable("vdone") String vdone)
    {
        Voter voter=voterRepository.findByUserName(uid);
        if(voter!=null) {
            voterRepository.delete(voter.getAdharcard());
            voter.setVdone(vdone);
            voterRepository.save(voter);
            System.out.println("true"+uid+vdone);
            return true;
        }
        else
        {
            System.out.println("false");
            return false;
        }
    }

}

