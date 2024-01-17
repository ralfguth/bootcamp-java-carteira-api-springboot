package br.com.alura.carteira.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.alura.carteira.dto.TransacaoFormDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "data", "quantidade", "tipo" })
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ticker;
	private LocalDate data;
	private BigDecimal preco;
	private Integer quantidade;
	@Enumerated(EnumType.STRING)
	private TipoTransacao tipo;
	@ManyToOne
	private Usuario usuario;
	
	private BigDecimal imposto;

	public Transacao(String ticker, LocalDate data, BigDecimal preco, Integer quantidade, TipoTransacao tipo,
			Usuario usuario) {
		this.ticker = ticker;
		this.data = data;
		this.preco = preco;
		this.quantidade = quantidade;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public void atualizar(String ticker, LocalDate data, BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		this.ticker = ticker;
		this.data = data;
		this.preco = preco;
		this.quantidade = quantidade;
		this.tipo = tipo;
	}

	public boolean pertenceAoUsuario(Usuario logado) {
		return this.usuario.equals(logado);
	}

	public static Transacao from(TransacaoFormDto form, Usuario usuario) {
		return new Transacao(form.getTicker(), form.getData(), form.getPreco(), form.getQuantidade(), form.getTipo(),
				usuario);
	}

}
