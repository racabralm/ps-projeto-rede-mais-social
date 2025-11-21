package br.com.redemaisocial.uc002_afiliacao.controller;

import br.com.redemaisocial.uc002_afiliacao.entity.Termo;
import br.com.redemaisocial.uc002_afiliacao.service.TermoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/termos")
public class TermoController {

    private final TermoService termoService;

    public TermoController(TermoService termoService) {
        this.termoService = termoService;
    }

    @GetMapping("/atual")
    public ResponseEntity<Termo> getTermoAtual() {
        Termo termo = termoService.getTermoAtual();
        return ResponseEntity.ok(termo);
    }

    @PostMapping("/aceitar")
    public ResponseEntity<String> aceitar(
            @RequestParam Long afiliacaoId,
            @RequestParam Long termoId) {

        // CORREÇÃO: Converte Long para Integer antes de chamar o Service
        termoService.registrarAceite(afiliacaoId.intValue(), termoId.intValue());

        return ResponseEntity.ok("Aceite registrado com sucesso.");
    }
}