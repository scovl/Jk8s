package br.com.jk8s.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    private String clusterUrl;
    private String username;
    private String password;

    public String getToken() {
        return null;
    }
}