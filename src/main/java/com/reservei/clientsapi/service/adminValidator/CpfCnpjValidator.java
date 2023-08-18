package com.reservei.clientsapi.service.adminValidator;
import com.reservei.clientsapi.domain.model.Admin;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.CpfRegisteredException;
import com.reservei.clientsapi.exception.EmailRegisteredException;
import com.reservei.clientsapi.repository.AdminRepository;
import com.reservei.clientsapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class CpfCnpjValidator {
    private static final String CPF_CNPJ_REGEX = "^(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})$|^(\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2})$";
    private static final Pattern pattern = Pattern.compile(CPF_CNPJ_REGEX);
    private static final int[] firstBaseCnpjNumbersMultipliers = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] secondBaseCnpjNumbersMultipliers = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    @Autowired
    private AdminRepository adminRepository;

    public static boolean isValidCpfCnpj(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return false;
        }

        String digits = input.replaceAll("\\D", ""); // Remove non-digit characters
        if (digits.length() == 11) {
            return isValidCpf(digits);
        } else if (digits.length() == 14) {
            return isValidCnpj(digits);
        }
        return false; // Invalid length
    }

    private static boolean isValidCpf(String cpf) {
        int firstActualVerifier = Character.getNumericValue(cpf.charAt(9));
        int secondActualVerifier = Character.getNumericValue(cpf.charAt(10));

        // first verification number calculate
        String firstNineNumbersOfCpf = cpf.substring(0, 9);
        int firstCalculation = getCpfNumbersCalculation(8, firstNineNumbersOfCpf);
        int realFirstVerifier = getRealVerifierNumber(firstCalculation);

        if (realFirstVerifier != firstActualVerifier) {
            return false;
        }

        // second verification number calculate
        String firstTenNumbersOfCpf = cpf.substring(0, 10);
        int secondCalculation = getCpfNumbersCalculation(9, firstTenNumbersOfCpf);
        int realSecondVerifier = getRealVerifierNumber(secondCalculation);

        return (realSecondVerifier == secondActualVerifier);
    }

    private static boolean isValidCnpj(String cnpj) {
        int firstActualVerifier = Character.getNumericValue(cnpj.charAt(12));
        int secondActualVerifier = Character.getNumericValue(cnpj.charAt(13));

        // first verification number calculate
        String firstTwelveNumbersOfCnpj = cnpj.substring(0, 12);
        int firstCalculation = calculateCnpjBaseNumbers(firstBaseCnpjNumbersMultipliers, firstTwelveNumbersOfCnpj);
        int realFirstVerifier = getRealVerifierNumber(firstCalculation);

        if (realFirstVerifier != firstActualVerifier) {
            return false;
        }

        // second verification number calculate
        String firstThirteenNumbersOfCnpj = cnpj.substring(0, 13);
        int secondCalculation = calculateCnpjBaseNumbers(secondBaseCnpjNumbersMultipliers, firstThirteenNumbersOfCnpj);
        int realSecondVerifier = getRealVerifierNumber(secondCalculation);

        return (realSecondVerifier == secondActualVerifier);
    }

    private static int getRealVerifierNumber(int calculationTotal) {
        // Calculate the remainder of the division by 11
        int modOfFirstDivision = calculationTotal % 11;
        // Calculate the real verifier number based on the remainder
        return modOfFirstDivision < 2 ? 0 : 11 - modOfFirstDivision;
    }

    private static int getCpfNumbersCalculation(int rangeEnd, String numbers) {
        int calculationTotal = 0;
        int multiplier = rangeEnd + 2;
        // Iterate through the numbers in reverse order
        for (int position = 0; position <= rangeEnd; position++) {
            int value = Character.getNumericValue(numbers.charAt(position));
            // Calculate the contribution of the current digit to the calculation
            calculationTotal += value * multiplier;
            multiplier--;
        }
        return calculationTotal;
    }

    private static int calculateCnpjBaseNumbers(int[] baseMultipliers, String numbers) {
        int calculationTotal = 0;
        // Iterate through the numbers and their corresponding multipliers
        for (int position = 0; position < baseMultipliers.length; position++) {
            int value = Character.getNumericValue(numbers.charAt(position));
            // Calculate the contribution of the current digit to the calculation
            calculationTotal += value * baseMultipliers[position];
        }
        return calculationTotal;
    }

    public void validateCreate(Admin admin) throws CpfRegisteredException {
        if ((adminRepository.findByCpfCnpj(admin.getCpfCnpj()) != null)) {
            throw new CpfRegisteredException("CPF/CNPJ já cadastrado");
        }
    }


    public void validateUpdate(Admin admin, Admin updatedAdmin) throws Exception {
        if (!(admin.getCpfCnpj().equals(updatedAdmin.getCpfCnpj())) &&
                (adminRepository.findByCpfCnpj(admin.getCpfCnpj()) != null)) {
            throw new EmailRegisteredException("CPF/CNPJ já cadastrado");
        }
    }

}
