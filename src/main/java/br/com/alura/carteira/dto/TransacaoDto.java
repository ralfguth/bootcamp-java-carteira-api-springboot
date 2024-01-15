package br.com.alura.carteira.dto;

import java.math.BigDecimal;

import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.model.Transacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {

	private Long id;
	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private TipoTransacao tipo;

	public static TransacaoDto from(Transacao transacao) {
		TransacaoDto dto = new TransacaoDto();
		dto.setId(transacao.getId());
		dto.setTicker(transacao.getTicker());
		dto.setPreco(transacao.getPreco());
		dto.setQuantidade(transacao.getQuantidade());
		dto.setTipo(transacao.getTipo());
		return dto;
	}

}
