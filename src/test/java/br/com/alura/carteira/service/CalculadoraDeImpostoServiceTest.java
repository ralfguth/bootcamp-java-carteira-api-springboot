package br.com.alura.carteira.service;

import static br.com.alura.carteira.model.TipoTransacao.COMPRA;
import static br.com.alura.carteira.model.TipoTransacao.VENDA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.model.Transacao;
import br.com.alura.carteira.model.Usuario;

class CalculadoraDeImpostoServiceTest {

	private CalculadoraDeImpostoService calculadora;
	private Usuario usuario;
	private Transacao transacao;

	// Exemplo de uso do Junit
	
	private Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		return new Transacao(120l, "BBSE3", LocalDate.now(), preco, quantidade, tipo, usuario);
	}
	
	@BeforeEach
	void inicializar() throws Exception {
		calculadora = new CalculadoraDeImpostoService();
		usuario = new Usuario(1l, "Rafaela", "rafa@email.com", "123456", null);
	}
	
	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		transacao = criarTransacao(new BigDecimal("30.00"), 10, COMPRA);
		assertEquals(BigDecimal.ZERO, calculadora.calcular(transacao));
	}

	@Test
	void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto() {
		transacao = criarTransacao(new BigDecimal("30.00"), 10, VENDA);
		assertEquals(BigDecimal.ZERO, calculadora.calcular(transacao));
	}

	@Test
	void transacaoDoTipoVendaComValorMaiorQueVinteMilTemImpostoDeQuinzePorcento() {
		transacao = criarTransacao(new BigDecimal("1000.00"), 30, VENDA);
		assertEquals(new BigDecimal("4500.00"), calculadora.calcular(transacao));
	}
	
	

}
