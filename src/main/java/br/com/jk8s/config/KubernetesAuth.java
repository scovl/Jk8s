package br.com.jk8s.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.eks.AmazonEKS;
import com.amazonaws.services.eks.AmazonEKSClientBuilder;
import com.amazonaws.services.eks.model.Cluster;
import com.amazonaws.services.eks.model.DescribeClusterRequest;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.util.Config;

import java.io.ByteArrayInputStream;

public class KubernetesAuth {

    private AmazonEKS eksClient;
    private AWSSecurityTokenService stsClient;
    private V1ObjectMeta callerIdentity;

    public KubernetesAuth(String region, String clusterName, String sessionName) {
        this.eksClient = AmazonEKSClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(region)
                .build();

        Cluster cluster = getClusterInfo(clusterName);

        this.stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(region)
                .build();

        this.callerIdentity = getCallerIdentity(sessionName, cluster);
    }

    public void configureKubernetesAccess() throws Exception {
        Cluster cluster = getClusterInfo("");

        Credentials credentials = assumeRoleAndGetCredentials(cluster, this.callerIdentity.getName());

        ApiClient apiClient = Config.fromCluster();
        apiClient.setApiKeyPrefix("Bearer");
        apiClient.setAccessToken(credentials.getAccessKeyId() + ":" + credentials.getSecretAccessKey() + ":" + credentials.getSessionToken());
        apiClient.setSslCaCert(new ByteArrayInputStream(cluster.getCertificateAuthority().getData().getBytes()));
        Configuration.setDefaultApiClient(apiClient);
    }

    private Cluster getClusterInfo(String clusterName) {
        DescribeClusterRequest describeClusterRequest = new DescribeClusterRequest().withName(clusterName);
        return eksClient.describeCluster(describeClusterRequest).getCluster();
    }

    private V1ObjectMeta getCallerIdentity(String sessionName, Cluster cluster) {
        V1ObjectMeta callerIdentity = new V1ObjectMeta();
        callerIdentity.putAnnotationsItem("eks.amazonaws.com/role-arn", cluster.getArn().replace("eks", "sts").concat("/system:masters"));

        GetCallerIdentityResult callerIdentityResult = stsClient.getCallerIdentity(new GetCallerIdentityRequest());
        callerIdentity.setName(callerIdentityResult.getArn());
        callerIdentity.setUid(callerIdentityResult.getUserId());
        callerIdentity.setNamespace(callerIdentityResult.getAccount());

        return callerIdentity;
    }

    private Credentials assumeRoleAndGetCredentials(Cluster cluster, String callerIdentityName) {
        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest()
                .withRoleArn(cluster.getArn().replace("eks", "sts").concat("/system:masters"))
                .withRoleSessionName(callerIdentityName);
        com.amazonaws.services.securitytoken.model.Credentials credentials = stsClient.assumeRole(assumeRoleRequest).getCredentials();
        String accessKeyId = credentials.getAccessKeyId();
        String secretAccessKey = credentials.getSecretAccessKey();
        String sessionToken = credentials.getSessionToken();
        return new Credentials(accessKeyId, secretAccessKey, sessionToken);
    }
}
