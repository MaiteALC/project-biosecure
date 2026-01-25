package br.com.biosecure.infrastructure;

import br.com.biosecure.model.InvalidCnpjException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MinhaReceitaClient {
    private final RestClient restClient;

    public MinhaReceitaClient(RestClient.Builder builder) {
        this.restClient = builder
            .baseUrl("https://minhareceita.org")
            .build();
    }

    public MinhaReceitaResponse searchCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new InvalidCnpjException("The CNPJ is null or blank.");
        }

        return restClient.get()
                .uri("/{cnpj}", cnpj)
                .retrieve()
                .onStatus(statusCode -> statusCode.value() == 404,
                    (request, response) -> {
                    throw new InvalidCnpjException("The CNPJ '" + cnpj + "' was not found");
                })
                .onStatus(statusCode -> statusCode.value() == 400,
                    (request, response) -> {
                    throw new InvalidCnpjException("The CNPJ '" + cnpj + "' is invalid");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ExternalApiUnreachableException("MinhaReceita");
                })
                .body(MinhaReceitaResponse.class);
    }
}
