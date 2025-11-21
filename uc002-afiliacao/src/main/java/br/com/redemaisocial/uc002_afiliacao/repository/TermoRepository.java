package br.com.redemaisocial.uc002_afiliacao.repository;

import br.com.redemaisocial.uc002_afiliacao.entity.Termo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// CORREÇÃO: Usando Long para a chave primária
public interface TermoRepository extends JpaRepository<Termo, Long> {

    // BUSCA O TERMO MAIS RECENTE
    Optional<Termo> findTopByOrderByIdDesc();
}