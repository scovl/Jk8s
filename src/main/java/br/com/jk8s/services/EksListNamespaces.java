package br.com.jk8s.services;

import br.com.jk8s.config.KubernetesAuth;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.util.Config;

public class EksListNamespaces {

    public static void main(String[] args) throws Exception {
        KubernetesAuth auth = new KubernetesAuth("", "", "");
        auth.configureKubernetesAccess();

        CoreV1Api api = new CoreV1Api(Config.defaultClient());
        try {
            V1NamespaceList list = api.listNamespace("default", true, "all", "all", "all", 10, "all", "all", 1, true);
            System.out.println("Namespaces:");
            for (int i = 0; i < list.getItems().size(); i++) {
                System.out.println(list.getItems().get(i).getMetadata().getName());
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#listNamespace");
            e.printStackTrace();
        }
    }
}
