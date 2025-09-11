package br.com.suporte.suporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.suporte.suporte.model.Solicitacao;

@Repository
public interface SolicitacaoRepository extends CrudRepository<Solicitacao, Long> {

}
