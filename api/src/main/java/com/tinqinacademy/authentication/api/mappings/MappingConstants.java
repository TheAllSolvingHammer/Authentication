package com.tinqinacademy.authentication.api.mappings;

import java.util.List;

public class MappingConstants {
    private static final String auth="/auth/";
    public static final String login=auth+"login";
    public static final String register=auth+"register";
    public static final String recover=auth+"recover-password";
    public static final String confirm=auth+"confirm-registration";
    public static final String change=auth+"change-password";
    public static final String recoverChange=auth+"recover-password-mail";
    public static final String promote=auth+"promote";
    public static final String demote=auth+"demote";
    public static final String logout=auth+"logout";
    public static final String validate=auth+"validate";
    public static final List<String> adminPaths=List.of(promote,demote);
    public static final List<String> requiredLogPaths=List.of(change,logout);

}
