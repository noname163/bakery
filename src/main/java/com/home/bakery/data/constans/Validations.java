package com.home.bakery.data.constans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String PHONE_NUMBER = "^0[0-9]{9,10}$";
    public static final String ONLY_ALPHABET_REGEX = "^.*[A-Za-z].*$";
    public static final String ONLY_ALPHABET_AND_SPACE_REGEX = "^.*[A-Za-z].*[\\s]?+.*$";
    public static final String ONLY_ALPHABET_AND_NUMBER_REGEX = "^[a-zA-Z0-9]*$";
    public static final String ONLY_ALPHABET_AND_NUMBER_AND_SPACE_REGEX = "^[a-zA-Z0-9 ]*$";
}
