package br.com.biosecure.external;

import br.com.biosecure.model.Cnpj;
import br.com.biosecure.model.InvalidCnpjException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest(MinhaReceitaClient.class)
class MinhaReceitaClientTest {

    @Autowired
    private MinhaReceitaClient client;

    @Autowired
    private MockRestServiceServer fakeServer;

    @Test
    void shouldReturnDataCorrectly_WhenRequestIsValid() {
        String responseJson = """ 
            {
                         "cnpj": "33683111000280",
                         "identificador_matriz_filial": 2,
                         "descricao_identificador_matriz_filial": "FILIAL",
                         "nome_fantasia": "REGIONAL BRASILIA-DF",
                         "situacao_cadastral": 2,
                         "descricao_situacao_cadastral": "ATIVA",
                         "data_situacao_cadastral": "2004-05-22",
                         "motivo_situacao_cadastral": 0,
                         "descricao_motivo_situacao_cadastral": "SEM MOTIVO",
                         "data_inicio_atividade": "1967-06-30",
                         "cnae_fiscal": 6204000,
                         "cnae_fiscal_descricao": "Consultoria em tecnologia da informação",
                         "descricao_tipo_de_logradouro": "AVENIDA",
                         "logradouro": "L2 SGAN",
                         "numero": "601",
                         "complemento": "MODULO G",
                         "bairro": "ASA NORTE",
                         "cep": "70836900",
                         "uf": "DF",
                         "codigo_municipio": 9701,
                         "codigo_municipio_ibge": 5300108,
                         "municipio": "BRASILIA",
                         "razao_social": "SERVICO FEDERAL DE PROCESSAMENTO DE DADOS (SERPRO)",
                         "codigo_natureza_juridica": 2011,
                         "natureza_juridica": "Empresa Pública",
                         "qualificacao_do_responsavel": 16,
                         "capital_social": 1061004800,
                         "codigo_porte": 5,
                         "porte": "DEMAIS",
                         "ente_federativo_responsavel": null,
                         "regime_tributario": null,
                         "qsa": [
                             {
                                 "identificador_de_socio": 2,
                                 "nome_socio": "ANDRE DE CESERO",
                                 "cnpj_cpf_do_socio": "***220050**",
                                 "codigo_qualificacao_socio": 10,
                                 "qualificacao_socio": "Diretor",
                                 "data_entrada_sociedade": "2016-06-16",
                                 "codigo_pais": null,
                                 "pais": null,
                                 "cpf_representante_legal": "***000000**",
                                 "nome_representante_legal": "",
                                 "codigo_qualificacao_representante_legal": 0,
                                 "qualificacao_representante_legal": null,
                                 "codigo_faixa_etaria": 6,
                                 "faixa_etaria": "Entre 51 a 60 anos"
                             },
                             {
                                 "identificador_de_socio": 2,
                                 "nome_socio": "ANTONIO DE PADUA FERREIRA PASSOS",
                                 "cnpj_cpf_do_socio": "***595901**",
                                 "codigo_qualificacao_socio": 10,
                                 "qualificacao_socio": "Diretor",
                                 "data_entrada_sociedade": "2016-12-08",
                                 "codigo_pais": null,
                                 "pais": null,
                                 "cpf_representante_legal": "***000000**",
                                 "nome_representante_legal": "",
                                 "codigo_qualificacao_representante_legal": 0,
                                 "qualificacao_representante_legal": null,
                                 "codigo_faixa_etaria": 7,
                                 "faixa_etaria": "Entre 61 a 70 anos"
                             }
                         ],
                         "cnaes_secundarios": [
                             {
                                 "codigo": 6201501,
                                 "descricao": "Desenvolvimento de programas de computador sob encomenda"
                             },
                             {
                                 "codigo": 6202300,
                                 "descricao": "Desenvolvimento e licenciamento de programas de computador customizáveis"
                             }
                         ]
                     }
        """; // This JSON string is a simplified version of the JSON found in 'docs.minhareceita.org/como-usar/'

        String cnpj = "33683111000280";

        fakeServer.expect(requestTo("https://minhareceita.org/" + cnpj))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        MinhaReceitaResponse response = client.searchCnpj(cnpj);

        assertEquals(new Cnpj("33683111000280"), response.cnpj());
        assertEquals("SERVICO FEDERAL DE PROCESSAMENTO DE DADOS (SERPRO)", response.corporateName());

        assertEquals(LocalDate.of(1967, 6, 30), response.startDateActivities());

        assertEquals(2, response.partnersList().size());

        assertEquals("ANDRE DE CESERO", response.partnersList().getFirst().name());
        assertEquals(7, response.partnersList().get(1).ageRangeCode());
        assertEquals("6204000", response.cnaeNumber());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"00000000000000", "  ", "noaneonf"})
    void shouldThrowException_WhenCnpjIsInvalid(String invalidCnpj) {
        fakeServer.expect(requestTo("https://minhareceita.org/" + invalidCnpj))
                .andRespond(withResourceNotFound());

        assertThrows(InvalidCnpjException.class, () -> client.searchCnpj(invalidCnpj));
    }

    @Test
    void shouldThrowException_WhenApiIsUnreachable() {
        fakeServer.expect(requestTo("https://minhareceita.org/12345678000100"))
                .andRespond(withServerError());

        assertThrows(ExternalApiUnreachableException.class, () -> client.searchCnpj("12345678000100"));
    }
}