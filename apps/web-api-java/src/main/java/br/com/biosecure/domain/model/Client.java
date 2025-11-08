package br.com.biosecure.domain.model;

import java.util.UUID;

import br.com.biosecure.domain.exception.CnpjValidationException;
import br.com.biosecure.domain.model.Cnpj;

public class  Client {
    private String corporateName;
    private UUID id;
    private Cnpj cnpj;
    private String adress;
    private String email;
    
    public Client(String corporateName, String cnpjNum, String adress, String email) throws IllegalArgumentException{
        if (corporateName == null || corporateName.equals("")) {
            throw  new IllegalArgumentException("Please enter corporate name.");
        }
        
        try {
            this.cnpj = new Cnpj(cnpjNum);
            
        } catch (CnpjValidationException e) {
            throw new IllegalArgumentException("Please enter a valid CNPJ number");
        }
        
        if (!validateEmail(email)) {
            throw new IllegalArgumentException("Please enter a valid corporate email.");
        }
        
        this.corporateName = corporateName;
        this.id = UUID.randomUUID();
        this.adress = adress;
        this.email = email;
    } 

    private boolean validateEmail(String email) {
        if (email == null || email.equals("") || !email.contains("@")) {
            return false;
        }
        
        return true;
    }
    
    public void updateAdress(String newAdress) {
        // TODO implement the logic
        this.adress = newAdress;
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
        
        if (!id.equals(other.id) && !cnpj.equals(other.cnpj)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        String str = "Corporate Name: " + getCorporateName() + "\nCNPJ: " + getCnpj() + "\nID: " + getId() + "\nAdress: " + getAdress() + "\nEmail: " + getEmail();

        return str;
    }

    public String getCorporateName() {return corporateName;}
    public UUID getId() {return id;}
    public Cnpj getCnpj() {return cnpj;}
    public String getAdress() {return adress;}
    public String getEmail() {return email;}
    
}