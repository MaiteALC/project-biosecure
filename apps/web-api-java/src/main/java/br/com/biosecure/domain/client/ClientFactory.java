package br.com.biosecure.domain.client;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.biosecure.domain.client.*;

public class ClientFactory {
    public Client create(String corporateName, Cnpj cnpj, Adress adress, String email) {
        if (corporateName == null || corporateName.equals("")) {
            throw  new IllegalArgumentException("Please enter corporate name.");
        }

        if (!validateEmail(email)) {
            throw new IllegalArgumentException("Please enter a valid corporate email.");
        }
        
        UUID uuid = UUID.randomUUID();
        
        Client newClient = new Client(corporateName, uuid, cnpj, adress, email);
        
        return newClient;
    }

    private boolean validateEmail(String email) {
        if (email == null || email.equals("")) {
            return false;
        }

        final String REGEX = "^[A-Za-z0-9._%+-]+@(?!(gmail\\.com|hotmail\\.com|outlook\\.com|yahoo\\.com|live\\.com)$)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
