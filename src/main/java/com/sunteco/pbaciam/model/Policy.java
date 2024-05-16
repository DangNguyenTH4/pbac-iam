package com.sunteco.pbaciam.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Policy implements Serializable {

    static public Policy Create(
            String id,
            String description,
            List<String> subjects,
            List<String> resources,
            List<String> actions,
            String effect){

        Policy policy = new Policy();

        policy.id = id;
        policy.description = description;
        policy.subjects = subjects;
        policy.effect = effect;
        policy.resources = resources;
        policy.actions = actions;

        return policy;
    }

    @Id
    protected String id;

    protected String description;

    @ElementCollection
    protected List<String> subjects = new ArrayList<>();

    protected String effect;

    @ElementCollection
    protected List<String> resources = new ArrayList<>();

    @ElementCollection
    protected List<String> actions = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    protected Map<String,Condition> conditions = new HashMap<>();

    // GetID returns the policies id.
    public String getId() {
        return this.id;
    }

    // GetDescription returns the policies description.
    public String getDescription() {
        return this.description;
    }

    // GetSubjects returns the policies subjects.
    public List<String> getSubjects(){
        return this.subjects;
    }

    // AllowAccess returns true if the policy effect is allow, otherwise false.
    public Boolean allowAccess(){
        return effect.equals("allow");
    }

    // GetEffect returns the policies effect which might be 'allow' or 'deny'.
    public String getEffect() {
        return this.effect;
    }

    // GetResources returns the policies resources.
    public List<String> getResources() {
        return this.resources;
    }

    // GetActions returns the policies actions.
    public List<String> getActions() {
        return this.actions;
    }

    // GetConditions returns the policies conditions.
    public Map<String,Condition> getConditions() {
        return this.conditions;
    }


    public void addCondition(String key, Condition condition) {
        conditions.put(key, condition);
    }


    public Boolean passesConditions(Request request){
        for(Map.Entry<String, Condition> entry : conditions.entrySet()) {
            String key = entry.getKey();
            Condition condition = entry.getValue();

            if(!condition.fullfills(key,request)){
                return  false;
            }
        }

        return true;
    }
}
