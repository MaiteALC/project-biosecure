package br.com.biosecure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistrationStatus {
    ACTIVE("ATIVA"),
    INACTIVE("INATIVA"),
    UNFIT("INAPTA"),
    SUSPENDED("SUSPENSA"),
    IRREGULAR("IRREGULAR"),
    DISSOLVED("BAIXADA"),
    ACTIVE_IRREGULAR("ATIVA NÃO REGULAR");

    private final String portugueseEquivalent;
}
