package br.com.consultdg.boletos_api.domain.to;

public record LoginResponse(String token, Long expiresIn, String userId, String nome, String sobrenome) {
}
