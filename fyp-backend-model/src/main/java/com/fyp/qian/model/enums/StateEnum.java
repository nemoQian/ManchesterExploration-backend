package com.fyp.qian.model.enums;

public enum StateEnum {
    SUCCESS(20000, "Success"),
    UPDATE_SUCCESS(21000, "Update Success"),

    REQUEST_ERROR(40000, "Request error"),
    PARAMS_EMPTY_ERROR(40001, "Input params is empty"),
    USER_AUTH_ERROR(40002, ""),

    NO_RESULTS_ERROR(40100, "Username or Password Wrong"),
    NO_USER_ERROR(40101, "Cannot find user"),

    USERNAME_LENGTH_ERROR(40200, "Username length need over 4"),
    USERNAME_INVALID_ERROR(40201, "Username has special character"),
    PASSWORD_LENGTH_ERROR(40202, "Password length need over 8"),
    PASSWORD_INVALID_ERROR(40203, "Password needs at least two types of numeric, letter and special character"),
    PASSWORD_COMPARED_ERROR(40204, "Checked password is not equal to the original one"),
    EMAIL_INVALID_ERROR(40205, "Email address is invalid"),
    PHONE_INVALID_ERROR(40206, "Phone number is invalid"),
    GENDER_INVALID_ERROR(40207, "Gender is invalid"),
    UPDATE_FAILED(40208, "Update failed"),
    AVATAR_UPDATE_FAILED(40209, "Avatar update failed"),

    DUPLICATE_USER_ERROR(40300, "User info duplicated"),
    DUPLICATE_NICKNAME_ERROR(40301, "Nickname duplicated"),
    DUPLICATE_TAG_ERROR(40302, "Tag name duplicated"),

    NO_PLACE_FOUND_ERROR(40400, "Cannot find any place"),

    SYSTEM_ERROR(50000, "Something wrong in the system"),
    ;

    private final int code;
    private final String message;

    StateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static StateEnum codeOf(int index){
        for(StateEnum error : values()){
            if (error.getCode() == index){
                return error;
            }
        }
        return null;
    }
}
