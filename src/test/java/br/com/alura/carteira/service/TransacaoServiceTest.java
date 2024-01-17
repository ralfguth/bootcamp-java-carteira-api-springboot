package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.model.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	// Exemplo de uso de mocks com o Mockito

	@Mock
	private TransacaoRepository repository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CalculadoraDeImpostoService impostoService;

	@InjectMocks
	private TransacaoService service;

	Usuario logado;

	@BeforeEach
	private void setup() {
		logado = new Usuario("Usuario do Sistema", "usuario@email.com", "123456");
		logado.setId(1l);

	}

	@Test
	void deveriaCadastrarUmaTransacao() throws Exception {
		TransacaoFormDto formDto = criarFormDto();

		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId())).thenReturn(logado);

		TransacaoDto dto = service.cadastrar(formDto, logado);

		Mockito.verify(repository).save(Mockito.any()); // Mockito verifica se o mock está sendo usado

		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
	}

	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() throws Exception {
		TransacaoFormDto formDto = criarFormDto();

		// Mockito controla o funcionamento dos mocks
		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId())).thenThrow(EntityNotFoundException.class);
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto, logado));

	}

	private TransacaoFormDto criarFormDto() {
		return new TransacaoFormDto("ITSA4", new BigDecimal("45.5"), LocalDate.now(), 25, TipoTransacao.COMPRA, 1l);
	}
}
