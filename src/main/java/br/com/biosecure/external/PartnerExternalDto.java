package br.com.biosecure.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PartnerExternalDto(

        @JsonProperty("identificador_de_socio")
        int identifierCode,

        @JsonProperty("nome_socio")
        String name,

        @JsonProperty("cnpj_cpf_do_socio")
        String cpfOrCnpj,

        @JsonProperty("codigo_qualificacao_socio")
        int typeCode,

        @JsonProperty("qualificacao_socio")
        String type,

        @JsonProperty("data_entrada_sociedade")
        LocalDate entryDate,

        @JsonProperty("codigo_faixa_etaria")
        int ageRangeCode
) {}
