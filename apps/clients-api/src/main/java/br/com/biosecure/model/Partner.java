package br.com.biosecure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Partner (
    @JsonProperty("identificador_de_socio") String partnerIdentifier,
    @JsonProperty("nome_socio") String partnerName,
    @JsonProperty("cnpj_cpf_do_socio") String partnerCpfOrCnpj,
    @JsonProperty("codigo_qualificacao_socio") String partnerTypeCode,
    @JsonProperty("qualificacao_socio") String partnerType,
    @JsonProperty("data_entrada_sociedade") LocalDate entryDate,
    @JsonProperty("codigo_faixa_etaria") int ageRangeCode
) {}
