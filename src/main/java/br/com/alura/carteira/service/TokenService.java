package br.com.alura.carteira.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.TokenDto;
import br.com.alura.carteira.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jjwt.secret}")
	private String segredo;

	public TokenDto gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		String token = Jwts.builder().setId(logado.getId().toString()).signWith(SignatureAlgorithm.HS256, segredo)
				.compact();
		return new TokenDto(token);
	}

}
