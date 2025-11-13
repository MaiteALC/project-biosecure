package br.com.biosecure.domain.client;

import java.util.UUID;

import br.com.biosecure.domain.client.Adress;
import br.com.biosecure.domain.client.Cnpj;

public class  Client {
    private String corporateName;
    private UUID id;
    private Cnpj cnpj;
    private Adress adress;
    private String email;
    
    Client(String corporateName, UUID id, Cnpj cnpj, Adress adress, String email) {
        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.id = id;
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