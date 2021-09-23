package br.com.alura.carteira.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"data","quantidade","tipo"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

	private String ticker;
	private LocalDate data;
	private BigDecimal preco;
	private int quantidade;
	private TipoTransacao tipo;

}
