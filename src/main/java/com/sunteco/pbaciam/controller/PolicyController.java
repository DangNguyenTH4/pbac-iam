package com.sunteco.pbaciam.controller;

import com.sunteco.pbaciam.model.Policy;
import com.sunteco.pbaciam.model.Request;
import com.sunteco.pbaciam.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Created by igor on 2017-07-22.
 */
@RestController
@RequestMapping(path="/policies")

public class PolicyController {

    private final PolicyService service;

    @Autowired
    public PolicyController(PolicyService service){
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getList(){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        List<Policy> list = service.findAll();

        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Policy item ){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (item == null)
        {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.EXPECTATION_FAILED);
        }
        final Policy savedItem = service.create(item);

        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(item.getId()).toUri());

        return new ResponseEntity<>(savedItem, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping( method = RequestMethod.POST , path = "/isAllowed")
    public ResponseEntity<?> isAllowed(@RequestBody Request item ){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (item == null)
        {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.EXPECTATION_FAILED);
        }

        Boolean isAllowed = service.isAllowed(item);

        if(!isAllowed){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
