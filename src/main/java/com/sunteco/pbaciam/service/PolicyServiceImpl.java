package com.sunteco.pbaciam.service;

import com.sunteco.pbaciam.model.ExpressionMatcher;
import com.sunteco.pbaciam.model.Policy;
import com.sunteco.pbaciam.model.Request;
import com.sunteco.pbaciam.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository repository;
    private final ExpressionMatcher matcher;

    @Autowired
    public PolicyServiceImpl(PolicyRepository repository, ExpressionMatcher matcher){
        this.repository = repository;
        this.matcher = matcher;
    }


    @Override
    public Policy create(Policy policy) {
        Policy createdPolicy = this.repository.save(policy);

        return createdPolicy;
    }

    @Override
    public List<Policy> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean isAllowed(Request request) {

        Boolean allowed = false;

        //TODO: filter policies on repository level
        List<Policy> policies= repository.findAll();

        // Iterate through all policies
        for (Policy policy: policies) {

            // Does the action match with one of the policy actions?
            // This is the first check because usually actions are a superset of get|update|delete|set
            // and thus match faster.
            if(!matcher.Matches(policy.getActions(), request.getAction())){
                // no, continue to next policy
                continue;
            }

            // Does the subject match with one of the policy subjects?
            // There are usually less subjects than resources which is why this is checked
            // before checking for resources.
            if(!matcher.Matches(policy.getSubjects(),request.getSubject())){
                // no, continue to next policy
                continue;
            }

            // Does the resource match with one of the policy resources?
            if(!matcher.Matches(policy.getResources(), request.getResource())){
                // no, continue to next policy
                continue;
            }

            // Are the policies conditions met?
            // This is checked first because it usually has a small complexity.
            if (!policy.passesConditions(request)){
                continue;
            }

            // Is the policies effect deny? If yes, this overrides all allow policies -> access denied.

            if(!policy.allowAccess()){
                return false;
            }

            //we found at least one policy that allows this request
            allowed = true;
        }

        return  allowed;
    }
}
