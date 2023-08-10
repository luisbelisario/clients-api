package com.reservei.clientsapi.util;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CpfValidator {
    private static final String CPF_CNPJ_REGEX = "^(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})$|^(\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2})$";
    private static final Pattern pattern = Pattern.compile(CPF_CNPJ_REGEX);

    public static boolean isValidCpfCnpj(String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
