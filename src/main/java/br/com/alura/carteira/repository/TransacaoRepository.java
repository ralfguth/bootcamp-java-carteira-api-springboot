package br.com.alura.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.carteira.dto.ItemCarteiraDto;
import br.com.alura.carteira.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query("SELECT new br.com.alura.carteira.dto.ItemCarteiraDto("
			+ "t.ticker, "
			+ "SUM(t.quantidade), "
			+ "SUM(t.quantidade) * 1.0 / (SELECT SUM(t2.quantidade) FROM Transacao t2) * 1.0 )"
			+ "FROM Transacao t "
			+ "GROUP BY t.ticker")
	List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos();

}
