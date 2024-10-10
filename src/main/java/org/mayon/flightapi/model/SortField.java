package org.mayon.flightapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum SortField {
    ID("id"),
    FLIGHT("flight"),
    ORIGIN("origin"),
    DESTINATION("destination");

    private final String sortField;

    SortField(String sortField){
        this.sortField=sortField;
    }

    @JsonValue
    public String getSortField() {
        return sortField;
    }

    @JsonCreator
    public static SortField fromValue(String value) {
        for (SortField sortField : values()) {
            String currentContact = sortField.getSortField();
            if (currentContact.equals(value)) {
                return sortField;
            }
        }

        // Return a response entity with a 400 Bad Request status
        throw new IllegalArgumentException("Invalid value for Contact type Enum: " + value);
    }
}
