package br.com.biosecure.infrastructure;

public class ExternalApiUnreachableException extends RuntimeException {
    public ExternalApiUnreachableException(String apiName) {
        super("The external API '" + apiName + "' was not reachable");
    }
}
