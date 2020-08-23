package com.techprimers.db.resource;



import com.techprimers.db.model.Admin;
import com.techprimers.db.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin")
public class AdminResource
{
    @Autowired
    AdminRepository adminRepository;

    @GetMapping(value = "/all")
    public List<Admin> getAll(){return adminRepository.findAll();}

    @PostMapping(value = "/load")
    public Integer persist(@RequestBody Admin admin) {


        Admin find=adminRepository.findByUserNameAndPassword(admin.getUserName(),admin.getPassword());
        if(find==null) {
            if (admin != null) {
                adminRepository.save(admin);
                return 1;
            } else {
                return 0;
            }
        }
        else
        {
            return -1;
        }
    }

    @PostMapping(value = "/delete")
    public List<Admin> Delete(@RequestBody final Admin admin) {
        adminRepository.delete(admin.getAdminId());
        return adminRepository.findAll();
    }

    @PostMapping(value = "/update")
    public List<Admin> UpdateUser(@RequestBody final Admin admin) {
        adminRepository.delete(admin.getAdminId());
        adminRepository.save(admin);
        return adminRepository.findAll();
    }

    @PostMapping(value="/auth/{uid}/{pwd}")
    public Admin AuthenticateAdmin(@PathVariable("uid") String uid, @PathVariable("pwd") String pwd)
    {
        Admin admin=adminRepository.findByUserNameAndPassword(uid,pwd);
        if(admin!=null) {
            System.out.println(admin.getUserName() + " " + admin.getPassword());
        }
        return admin;

    }

}
