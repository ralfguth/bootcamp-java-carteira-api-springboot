package br.com.alura.carteira.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List<UsuarioDto> listar() {
		List<Usuario> usuarios = repository.findAll();
		return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(@Valid UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		repository.save(usuario);
	}
}
