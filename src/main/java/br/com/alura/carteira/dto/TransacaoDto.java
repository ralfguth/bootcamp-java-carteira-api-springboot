package br.com.alura.carteira.dto;

import java.math.BigDecimal;

import br.com.alura.carteira.model.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {

	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private TipoTransacao tipo;
	private UsuarioDto usuario;

}
