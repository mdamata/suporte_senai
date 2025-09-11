package br.com.suporte.suporte.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.suporte.suporte.model.PainelTecnico;

@Repository
public interface PainelTecnicoRepository extends CrudRepository<PainelTecnico, Long>{
	// Método para buscar por idSolicitacao
    Optional<PainelTecnico> findByIdSolicitacao(Long idSolicitacao);

}
