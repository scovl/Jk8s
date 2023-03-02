package br.com.jk8s.config;

public class Credentials {

    private final String accessKeyId;
    private final String secretAccessKey;
    private final String sessionToken;

    public Credentials(String accessKeyId, String secretAccessKey, String sessionToken) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.sessionToken = sessionToken;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
