package br.com.alura.carteira.infra.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.carteira.model.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;
import br.com.alura.carteira.service.TokenService;


public class VerificacaoTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (Objects.isNull(token) || token.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}
		token = token.replace("Bearer ", "");
		if(tokenService.isValido(token)) {
			Long idUsuario = tokenService.extrairIdUsuario(token);
			Usuario usuario = usuarioRepository.getById(idUsuario);
			Authentication autentication = new UsernamePasswordAuthenticationToken(usuario, null,null);
			SecurityContextHolder.getContext().setAuthentication(autentication);
		}
		filterChain.doFilter(request, response);
	}

}
