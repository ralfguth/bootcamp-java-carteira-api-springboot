package br.com.alura.carteira.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.model.Transacao;

public class CalculadoraDeImpostoService {

	// 15% de imposto para transações do tipo venda com valor superior a R$20.000,00
	public BigDecimal calcular(Transacao transacao) {
		BigDecimal valorTransacao = transacao.getPreco().multiply(new BigDecimal(transacao.getQuantidade()));
		Boolean isCompra = TipoTransacao.COMPRA.equals(transacao.getTipo());
		Boolean valorMenorQue20Mil = valorTransacao.compareTo(new BigDecimal("20000")) < 0;
		
		return isCompra || valorMenorQue20Mil ? BigDecimal.ZERO : valorTransacao.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.HALF_UP);
	}

}
