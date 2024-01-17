package br.com.alura.carteira.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal imposto;

	public static TransacaoDto from(Transacao transacao) {
		TransacaoDto dto = new TransacaoDto();
		dto.setId(transacao.getId());
		dto.setTicker(transacao.getTicker());
		dto.setPreco(transacao.getPreco());
		dto.setQuantidade(transacao.getQuantidade());
		dto.setTipo(transacao.getTipo());
		dto.setImposto(transacao.getImposto());
		return dto;
	}
	
}
