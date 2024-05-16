package com.sunteco.pbaciam.service;

import com.sunteco.pbaciam.model.Policy;
import com.sunteco.pbaciam.model.Request;

import java.util.List;


public interface PolicyService {
    Policy create(Policy policy);
    List<Policy> findAll();
    Boolean isAllowed(Request request);
}
