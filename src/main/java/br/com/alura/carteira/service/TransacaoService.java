package br.com.alura.carteira.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.model.Transacao;
import br.com.alura.carteira.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;
	private ModelMapper modelMapper = new ModelMapper();

	public List<TransacaoDto> listar() {
		List<Transacao> transacoes = repository.findAll();
		return transacoes.stream().map(usuario -> modelMapper.map(usuario, TransacaoDto.class)).collect(Collectors.toList());
	}

	@Transactional
	public void cadastrar(@Valid TransacaoFormDto dto) {
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		// o modelmapper esta atribuindo o mesmo id para usuarioId e transacao, 
		// essa gambiarra remove o id deixando para o banco a responsabilidade;
		repository.save(transacao);
	}

}
