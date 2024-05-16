package com.sunteco.pbaciam.model;

import jakarta.persistence.*;

/**
 * Created by igor on 2017-07-22.
 */
@Entity(name = "StringEqualCondition")
@DiscriminatorValue("StringEqualCondition")
public class StringEqualCondition extends Condition {

    public  StringEqualCondition(String value){
        this.options.setProperty("equals", value);
    }

    public  StringEqualCondition(){

    }

    @Override
    public Boolean fullfills(String conditionKey,Request request) {

        if (!request.context.containsKey(conditionKey))
                return false;

        String value = request.context.getProperty(conditionKey);
        String conditionValue = this.options.getProperty("equals");
        return conditionValue.equals(value);

    }
}
