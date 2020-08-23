package com.techprimers.db.resource;

import com.techprimers.db.model.Users;
import com.techprimers.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/users")
public class UsersResource {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping(value = "/all")
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @PostMapping(value = "/load")
    public List<Users> persist(@RequestBody final Users users) {
        usersRepository.save(users);
        return usersRepository.findAll();
    }

    @PostMapping(value = "/delete")
    public List<Users> Delete(@RequestBody final Users users) {
        usersRepository.delete(users.getId());
        return usersRepository.findAll();
    }

    @PostMapping(value = "/update")
    public List<Users> UpdateUser(@RequestBody final Users users) {
        //Users userfromdb = usersRepository.findOne(users.getId());
        usersRepository.delete(users.getId());

        usersRepository.save(users);


        return usersRepository.findAll();
    }


    @PostMapping(value="/auth")
    public Users authenticate() {
        Users users = usersRepository.findByNameAndAdharcard("jay","1234567890");
        if(users!=null) {
            System.out.println(users.getName() + users.getAdharcard());
        }
        return users;
    }


}
