package it.dcm.storage.exception;

import it.dcm.rest.exception.ExceptionType;
import lombok.Getter;

@Getter
public enum ExceptionEnum implements ExceptionType {
    GENERIC_ERROR("GENERIC ERROR", "Control logs"),
    AUTH_FIREBASE_ERROR("AUTH_FIREBASE_ERROR", "Only code from exception"),
    AUTH_USER_ALREADY_EXIST("AUTH_USER_ALREADY_EXIST", "The user already exist with this email"),
    AUTH_PASSW_LENGTH("AUTH_PASSW_LENGTH", "The password must be at least 6 character long"),
    AUTH_PASSW_NULL("AUTH_PASSW_NULL", "The password must not be null"),
    AUTH_VALIDATION_TOKEN_NULL("AUTH_VALIDATION_TOKEN_NULL", "The token is invalid or expired"),
    ;


    private final String value, message;

    ExceptionEnum(String value, String message){
        this.value = value;
        this.message = message;
    }

    @Override
    public String getError() {
        return this.value;
    }
}
