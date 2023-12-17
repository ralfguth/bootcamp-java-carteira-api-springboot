package br.com.alura.carteira.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.TokenDto;
import br.com.alura.carteira.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jjwt.secret}")
	private String segredo;

	public TokenDto gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		String token = Jwts.builder().setSubject(logado.getId().toString()).signWith(SignatureAlgorithm.HS256, segredo)
				.compact();
		return new TokenDto(token);
	}

	public boolean isValido(String token) {
		try {
			decodificarToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long extrairIdUsuario(String token) {
		Claims claims = decodificarToken(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	private Jws<Claims> decodificarToken(String token) {
		return Jwts.parser().setSigningKey(segredo).parseClaimsJws(token);
	}

}
