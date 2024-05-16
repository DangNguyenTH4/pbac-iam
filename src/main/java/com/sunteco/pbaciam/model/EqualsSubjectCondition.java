package com.sunteco.pbaciam.model;

import jakarta.persistence.*;

/**
 * Created by igor on 2017-07-22.
 */
@Entity(name = "EqualsSubjectCondition")
@DiscriminatorValue("EqualsSubjectCondition")
public class EqualsSubjectCondition extends Condition {

    @Override
    public Boolean fullfills(String conditionKey,Request request) {

        if (!request.context.containsKey(conditionKey))
            return false;

        String value = request.context.getProperty(conditionKey);

        return request.subject.equals(value);
    }
}
