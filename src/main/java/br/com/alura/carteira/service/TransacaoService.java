package br.com.alura.carteira.service;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.AtualizacaoTransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.model.Transacao;
import br.com.alura.carteira.model.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CalculadoraDeImpostoService impostoService;

	public Page<TransacaoDto> listar(Pageable paginacao, Usuario usuario) {
		Page<Transacao> transacoes = repository.findAllByUsuario(paginacao, usuario);
		return transacoes.map(transacao -> TransacaoDto.from(transacao));
	}

	@Transactional
	public TransacaoDto cadastrar(@Valid TransacaoFormDto dto, Usuario logado) {
		try {
			Usuario usuario = usuarioRepository.getById(dto.getUsuarioId());
			validarUsuario(usuario, logado);
			Transacao transacao = Transacao.from(dto, logado);
			BigDecimal imposto = impostoService.calcular(transacao);
			transacao.setImposto(imposto);
			repository.save(transacao);
			return TransacaoDto.from(transacao);
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("usuario inexistente");
		}
	}

	@Transactional
	public TransacaoDto atualizar(AtualizacaoTransacaoFormDto dto, Usuario logado) {
		Transacao transacao = repository.getById(dto.getId());
		validarUsuario(transacao, logado);
		transacao.atualizar(dto.getTicker(), dto.getData(), dto.getPreco(), dto.getQuantidade(), dto.getTipo());
		// O hibernate percebe que foi carregado uma entidade do banco e após
		// sobrescrever os atributos ele faz o update no banco
		return modelMapper.map(transacao, TransacaoDto.class);
	}

	@Transactional
	public void remover(Long id, Usuario logado) {
		Transacao transacao = repository.getById(id);
		validarUsuario(transacao, logado);
		repository.deleteById(id);
	}

	public TransacaoDetalhadaDto detalhar(Long id, Usuario logado) {
		Transacao transacao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		validarUsuario(transacao, logado);
		return modelMapper.map(transacao, TransacaoDetalhadaDto.class);
	}

	private void validarUsuario(Transacao transacao, Usuario logado) {
		if (!transacao.pertenceAoUsuario(logado)) {
			throw new AccessDeniedException("Acesso Negado");
		}
	}
	
	private void validarUsuario(Usuario usuarioForm, Usuario logado) {
		if (!usuarioForm.equals(logado)) {
			throw new AccessDeniedException("Acesso Negado");
		}
	}

}
