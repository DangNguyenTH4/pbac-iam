package com.sunteco.pbaciam.model;

import java.util.List;

public interface ExpressionMatcher {
    Boolean Matches(List<String> expressions, String searchString );
}
