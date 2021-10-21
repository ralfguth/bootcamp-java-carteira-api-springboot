package br.com.alura.carteira.service;

import static br.com.alura.carteira.model.TipoTransacao.COMPRA;
import static br.com.alura.carteira.model.TipoTransacao.VENDA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.alura.carteira.model.Transacao;
import br.com.alura.carteira.model.Usuario;

class CalculadoraDeImpostoServiceTest {

	private CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();

	private Usuario usuario = new Usuario(1l, "Rafaela", "rafa@email.com", "123456");
	private Transacao transacao;

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		transacao = new Transacao(120l, "BBSE3", LocalDate.now(), new BigDecimal("30.00"), 10, COMPRA, usuario);

		assertEquals(BigDecimal.ZERO, calculadora.calcular(transacao));
	}

	@Test
	void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto() {
		transacao = new Transacao(120l, "BBSE3", LocalDate.now(), new BigDecimal("30.00"), 10, VENDA, usuario);

		assertEquals(BigDecimal.ZERO, calculadora.calcular(transacao));
	}

	@Test
	void transacaoDoTipoVendaComValorMaiorQueVinteMilTemImpostoDeQuinzePorcento() {
		transacao = new Transacao(120l, "BBSE3", LocalDate.now(), new BigDecimal("1000.00"), 30, VENDA, usuario);

		assertEquals(new BigDecimal("4500.00"), calculadora.calcular(transacao));
	}

}
