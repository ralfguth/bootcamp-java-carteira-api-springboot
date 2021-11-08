package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	//Exemplo de uso de mocks com o Mockito

	@Mock
	private TransacaoRepository repository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private TransacaoService service;

	@Test
	void deveriaCadastrarUmaTransacao() throws Exception {
		TransacaoFormDto formDto = new TransacaoFormDto("ITSA4", new BigDecimal("45.5"), LocalDate.now(), 25, TipoTransacao.COMPRA, 1l);
		TransacaoDto dto = service.cadastrar(formDto);

		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
		assertEquals(formDto.getUsuarioId(), dto.getUsuario().getId());
	}
	
	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioNaoExistente() throws Exception {
		TransacaoFormDto formDto = new TransacaoFormDto("ITSA4", new BigDecimal("45.5"), LocalDate.now(), 25, TipoTransacao.COMPRA, 1l);
		
		//Mockito controla o funcionamento dos mocks
		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId())).thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));

	}
}
