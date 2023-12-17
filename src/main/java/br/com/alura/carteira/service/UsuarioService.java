package br.com.alura.carteira.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.model.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = repository.findAll(paginacao);
		return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioDto.class));
	}

	@Transactional
	public UsuarioDto cadastrar(@Valid UsuarioFormDto form) {
		Usuario usuario = modelMapper.map(form, Usuario.class);
		usuario.setSenha(passwordEncoder.encode(form.getSenha()));
		repository.save(usuario);
		return modelMapper.map(usuario, UsuarioDto.class);
	}
}
