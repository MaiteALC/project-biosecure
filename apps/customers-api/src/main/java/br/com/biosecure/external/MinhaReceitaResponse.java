package br.com.biosecure.external;

import br.com.biosecure.model.Cnpj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MinhaReceitaResponse(

        Cnpj cnpj,

        @JsonProperty("descricao_situacao_cadastral")
        String registrationStatusDescription,

        @JsonProperty("data_situacao_cadastral")
        LocalDate registrationStatusDate,

        @JsonProperty("data_inicio_atividade")
        LocalDate startDateActivities,

        @JsonProperty("cnae_fiscal")
        String cnaeNumber,

        @JsonProperty("cnae_fiscal_descricao")
        String cnaeDescription,

        String cep,

        @JsonProperty("razao_social")
        String corporateName,

        @JsonProperty("natureza_juridica")
        String legalNature,

        @JsonProperty("capital_social")
        long shareCapital,

        @JsonProperty("qsa")
        List<PartnerExternalDto> partnersList,

        @JsonProperty("cnaes_secundarios")
        List<CnaeExternalDto> secondaryCnaeNumbers
) {}
