package br.com.jk8s.controller;

import br.com.jk8s.dto.AuthDTO;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/auth")
    public String auth(@RequestBody AuthDTO authDTO) {
        AWSCredentialsProvider credentialsProvider = new AuthAws(authDTO);
        // Faça o que for necessário com o credentialsProvider
        return "Autenticação realizada com sucesso!";
    }

}
