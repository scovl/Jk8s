package br.com.jk8s.config;

import br.com.jk8s.dto.AuthDTO;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AuthenticationV1Api;
import io.kubernetes.client.openapi.models.V1TokenReview;
import io.kubernetes.client.openapi.models.V1TokenReviewSpec;

public class KubernetesAuth {

    private final AuthDTO authDTO;

    public KubernetesAuth(AuthDTO authDTO) {
        this.authDTO = authDTO;
    }

    public ApiClient authenticate() throws ApiException {
        // Obtém uma instância do cliente API
        ApiClient client = Configuration.getDefaultApiClient();

        // Configuração do token de autenticação
        V1TokenReview tokenReview = new V1TokenReview();
        V1TokenReviewSpec spec = new V1TokenReviewSpec();

        // Define o token de autenticação a ser utilizado
        spec.token(authDTO.getToken());
        tokenReview.spec(spec);

        // Cria uma instância da API de autenticação
        AuthenticationV1Api api = new AuthenticationV1Api(client);

        // Realiza a revisão do token
        V1TokenReview result = api.createTokenReview(tokenReview, null, null, null, null);

        // Aqui você pode tratar o resultado do token review, como acessar o token retornado pelo Kubernetes

        // Retorna o cliente API autenticado
        return client;
    }
}
