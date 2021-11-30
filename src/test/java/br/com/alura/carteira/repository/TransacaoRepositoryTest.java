package br.com.alura.carteira.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.carteira.dto.ItemCarteiraDto;
import br.com.alura.carteira.model.TipoTransacao;
import br.com.alura.carteira.model.Transacao;
import br.com.alura.carteira.model.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest //utiliza por padrão outro banco de dados embeded para o teste que deve ser configurado (adicionei o banco h2 no pom)
@AutoConfigureTestDatabase(replace = Replace.NONE) //com essa configuração cria um banco de dados de teste com o mesmo banco da aplicacao, neste cado o MySql
@ActiveProfiles("test") // utiliza o application-test.properties para configurar o ambiente de testes.
class TransacaoRepositoryTest {
	
	// Caso de uso do teste com Banco de Dados
	
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private TestEntityManager manager; //entity manager do spring-boot para popular o banco para o teste.
	
	@Test
	void deveriaRetornarRelatórioDeCarteiraDeInvestimentos() {
		Usuario usuario = new Usuario("Rafaela", "rafa@email.com", "123456");
		manager.persist(usuario);
		Transacao t1 = new Transacao("ITSA4", LocalDate.now(), new BigDecimal("10.00"), 50, TipoTransacao.COMPRA, usuario);
		manager.persist(t1);
		Transacao t2 = new Transacao("BBSE3", LocalDate.now(), new BigDecimal("22.80"), 80, TipoTransacao.COMPRA, usuario);
		manager.persist(t2);
		Transacao t3 = new Transacao("EGIE3", LocalDate.now(), new BigDecimal("40.00"), 25, TipoTransacao.COMPRA, usuario);
		manager.persist(t3);
		Transacao t4 = new Transacao("ITSA4", LocalDate.now(), new BigDecimal("11.20"), 40, TipoTransacao.COMPRA, usuario);
		manager.persist(t4);
		Transacao t5 = new Transacao("SAPR4", LocalDate.now(), new BigDecimal("04.02"), 120, TipoTransacao.COMPRA, usuario);
		manager.persist(t5);
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();

		assertEquals(4, relatorio.size());//asserção feita com o Junit
		
		Assertions
			.assertThat(relatorio)
			.hasSize(4)
			.extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade, ItemCarteiraDto::getPercentual)
			.contains(
					Assertions.tuple("ITSA4", 90l, new BigDecimal("28.57")),
					Assertions.tuple("BBSE3", 80l, new BigDecimal("25.40")),
					Assertions.tuple("EGIE3", 25l, new BigDecimal("7.94")),
					Assertions.tuple("SAPR4", 120l, new BigDecimal("38.10"))
					);//asserções feitas com a biblioteca AssertJ:
	}
	
	@Test
	void deveriaRetornarRelatórioDeCarteiraDeInvestimentosConsiderandoVenda() {
		Usuario usuario = new Usuario("Rafaela", "rafa@email.com", "123456");
		manager.persist(usuario);
		Transacao t1 = new Transacao("ITSA4", LocalDate.now(), new BigDecimal("10.00"), 50, TipoTransacao.COMPRA, usuario);
		manager.persist(t1);
		Transacao t2 = new Transacao("BBSE3", LocalDate.now(), new BigDecimal("22.80"), 80, TipoTransacao.COMPRA, usuario);
		manager.persist(t2);
		Transacao t3 = new Transacao("EGIE3", LocalDate.now(), new BigDecimal("40.00"), 25, TipoTransacao.COMPRA, usuario);
		manager.persist(t3);
		Transacao t4 = new Transacao("ITSA4", LocalDate.now(), new BigDecimal("11.20"), 40, TipoTransacao.VENDA, usuario);
		manager.persist(t4);
		Transacao t5 = new Transacao("SAPR4", LocalDate.now(), new BigDecimal("04.02"), 120, TipoTransacao.COMPRA, usuario);
		manager.persist(t5);
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();

		assertEquals(4, relatorio.size());
		
		Assertions
			.assertThat(relatorio)
			.hasSize(4)
			.extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade, ItemCarteiraDto::getPercentual)
			.contains(
					Assertions.tuple("ITSA4", 10l, new BigDecimal("4.26")),
					Assertions.tuple("BBSE3", 80l, new BigDecimal("34.04")),
					Assertions.tuple("EGIE3", 25l, new BigDecimal("10.64")),
					Assertions.tuple("SAPR4", 120l, new BigDecimal("51.06"))
					);
	}

}
