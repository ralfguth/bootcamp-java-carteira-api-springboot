package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.model.Usuario;

@Service
public class UsuarioService {

	private List<Usuario> usuarios = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	public List<UsuarioDto> listar() {
		return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(@Valid UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		gerarSenha(usuario);
		usuarios.add(usuario);
	}

	private void gerarSenha(Usuario usuario) {
		String senha = Integer.toString(new Random().nextInt(99999));
		usuario.setSenha(senha);
	}
}
