package br.com.biosecure.domain.client;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.com.biosecure.domain.client.Adress;
import br.com.biosecure.domain.client.Cnpj;

public class  Client {
    private String corporateName;
    private UUID id;
    private Cnpj cnpj;
    private Adress adress;
    private String email;
    
    Client(String corporateName, UUID id, Cnpj cnpj, Adress adress, String email) {
        if (corporateName == null || corporateName.equals("")) {
            throw new IllegalArgumentException("Please enter corporate name");
        }
        
        if (!validateEmail(email)) {
            throw new IllegalArgumentException("Please enter a valid corporate email");
        }

        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.id = UUID.randomUUID();
        this.adress = adress;
        this.email = email;
    } 
    
    @Override
    public int hashCode() {
        final int prime = 31;

        int result = 1;

        result = prime * result + ((corporateName == null) ? 0 : corporateName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());

        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        Client other = (Client) obj;
        
        if (!id.equals(other.id) || !cnpj.equals(other.cnpj)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        String str = "Corporate Name: " + corporateName + "\nCNPJ: " + cnpj.getFormattedNumber() + "\nID: " + id + "\nAdress: " + adress + "\nEmail: " + email;

        return str;
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

    public String getCorporateName() {
        return corporateName;
    }

    public UUID getId() {
        return id;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public Adress getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    } 
}