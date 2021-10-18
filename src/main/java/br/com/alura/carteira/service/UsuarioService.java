package br.com.alura.carteira.service;

import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.model.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	private ModelMapper modelMapper = new ModelMapper();

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = repository.findAll(paginacao);
		return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioDto.class));
	}

	@Transactional
	public UsuarioDto cadastrar(@Valid UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		repository.save(usuario);
		return modelMapper.map(usuario, UsuarioDto.class);
	}
}
