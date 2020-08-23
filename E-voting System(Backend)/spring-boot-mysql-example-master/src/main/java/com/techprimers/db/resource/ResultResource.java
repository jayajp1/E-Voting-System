package com.techprimers.db.resource;


import com.techprimers.db.model.Result;
import com.techprimers.db.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/result")
public class ResultResource
{
    @Autowired
    ResultRepository resultRepository;

    @GetMapping(value = "/all")
    public List<Result> getAll(){
        System.out.println(resultRepository.findAll().get(0).getChoice());
        return resultRepository.findAll();}

    @PostMapping(value = "/load")
    public List<Result> persist(@RequestBody  Result result)
    {
        resultRepository.save(result);
        return resultRepository.findAll();
    }
    @PostMapping(value = "/delete")
    public List<Result> Delete(@RequestBody Result result) {
        resultRepository.delete(result.getId());
        return resultRepository.findAll();
    }

    @PostMapping(value = "/update")
    public Boolean Updateresult(@RequestBody  Result result) {
        if(resultRepository.findAll().size()==0) {
            result.setId(1);
            resultRepository.save(result);
            return true;
        }
        else
        {
            resultRepository.deleteAll();
            resultRepository.save(result);
            return true;
        }

    }
}
