package br.com.redemaisocial.uc002_afiliacao.service;

import br.com.redemaisocial.uc002_afiliacao.entity.AceiteTermo;
import br.com.redemaisocial.uc002_afiliacao.entity.Afiliacao;
import br.com.redemaisocial.uc002_afiliacao.entity.Termo;
import br.com.redemaisocial.uc002_afiliacao.repository.AceiteTermoRepository;
import br.com.redemaisocial.uc002_afiliacao.repository.AfiliacaoRepository;
import br.com.redemaisocial.uc002_afiliacao.repository.TermoRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class TermoService {

    private final TermoRepository termoRepository;
    private final AfiliacaoRepository afiliacaoRepository;
    private final AceiteTermoRepository aceiteTermoRepository;

    public TermoService(
            TermoRepository termoRepository,
            AfiliacaoRepository afiliacaoRepository,
            AceiteTermoRepository aceiteTermoRepository) {
        this.termoRepository = termoRepository;
        this.afiliacaoRepository = afiliacaoRepository;
        this.aceiteTermoRepository = aceiteTermoRepository;
    }

    // BUSCA O TERMO MAIS RECENTE
    public Termo getTermoAtual() {
        return termoRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("Nenhum termo encontrado"));
    }

    // REGISTRA ACEITE
    public void registrarAceite(Integer afiliacaoId, Integer termoId) {

        // CORREÇÃO: Conversão de Integer para Long para buscar no repositório (findById espera Long)
        Afiliacao afiliacao = afiliacaoRepository.findById(afiliacaoId.longValue())
                .orElseThrow(() -> new RuntimeException("Afiliação não encontrada"));

        // CORREÇÃO: Conversão de Integer para Long para buscar no repositório
        Termo termo = termoRepository.findById(termoId.longValue())
                .orElseThrow(() -> new RuntimeException("Termo não encontrado"));

        AceiteTermo aceite = new AceiteTermo();
        aceite.setAfiliacao(afiliacao);    // nome EXATO do seu campo
        aceite.setTermo(termo);
        aceite.setStatus("ACEITO");
        aceite.setData(Date.valueOf(LocalDate.now()));

        aceiteTermoRepository.save(aceite);
    }
}