package br.com.redemaisocial.uc002_afiliacao.repository;
import br.com.redemaisocial.uc002_afiliacao.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    boolean existsByCnpj(String cnpj);
}