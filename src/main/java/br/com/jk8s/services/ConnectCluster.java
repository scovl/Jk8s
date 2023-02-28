package br.com.jk8s.services;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.util.Config;

import java.io.IOException;
import java.util.List;

import br.com.jk8s.dto.AuthDTO;
import br.com.jk8s.config.KubernetesAuth;

public class ConnectCluster {

    private final ApiClient client;
    private final CoreV1Api coreApi;

    public ConnectCluster(AuthDTO authDTO) throws IOException, ApiException {
        KubernetesAuth kubernetesAuth = new KubernetesAuth(authDTO);
        this.client = kubernetesAuth.authenticate();
        Configuration.setDefaultApiClient(this.client);
        this.coreApi = new CoreV1Api();
    }

    public List<V1Namespace> listNamespaces() throws ApiException {
        V1NamespaceList namespaceList = this.coreApi.listNamespace("foo", true, "bar", "baz", "qux", 15, "quux", "corge", 50, true);
        return namespaceList.getItems();
    }
}
