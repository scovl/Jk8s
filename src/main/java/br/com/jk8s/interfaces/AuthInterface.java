package br.com.jk8s.interfaces;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;

public interface AuthInterface {
    ApiClient authenticate() throws ApiException;
}
