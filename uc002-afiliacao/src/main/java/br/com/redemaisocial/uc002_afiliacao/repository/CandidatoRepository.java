package br.com.redemaisocial.uc002_afiliacao.repository;

import br.com.redemaisocial.uc002_afiliacao.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {}
