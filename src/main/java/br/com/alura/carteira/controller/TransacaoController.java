package br.com.alura.carteira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.model.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController  {

	private List<Transacao> transacoes = new ArrayList<>();
	
	@GetMapping
	public List<TransacaoDto> listar() {
		return transacoes.stream().map(TransacaoDto::new).collect(Collectors.toList());
	}
	
	@PostMapping
	public void cadastrar(@RequestBody TransacaoFormDto dto) {
		Transacao transacao = new Transacao();
		transacao.setTicker(dto.getTicker());
		transacao.setData(dto.getData());
		transacao.setPreco(dto.getPreco());
		transacao.setQuantidade(dto.getQuantidade());
		transacao.setTipo(dto.getTipo());
		transacoes.add(transacao);
	}
	
	
}
