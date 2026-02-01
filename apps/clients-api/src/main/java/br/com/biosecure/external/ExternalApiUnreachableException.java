package br.com.biosecure.external;

public class ExternalApiUnreachableException extends RuntimeException {
    public ExternalApiUnreachableException(String apiName) {
        super("The external API '" + apiName + "' was not reachable");
    }
}
