package br.com.redemaisocial.uc002_afiliacao.controller;

import br.com.redemaisocial.uc002_afiliacao.entity.Candidato;
import br.com.redemaisocial.uc002_afiliacao.service.ControladorAfiliacaoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/afiliacao")
public class FormularioAfiliacaoController {

    private final ControladorAfiliacaoService service;

    public FormularioAfiliacaoController(ControladorAfiliacaoService service) {
        this.service = service;
    }

    // PF (Sem arquivo)
    @PostMapping("/solicitar/pf")
    public Candidato solicitarPF(@RequestParam String cpf, @RequestParam String nome,
                                 @RequestParam String dataNascimento, @RequestParam String sexo) {
        return service.solicitarAfiliacaoPF(cpf, nome, dataNascimento, sexo);
    }

    // PJ (Com arquivo)
    @PostMapping(value = "/solicitar/pj", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Candidato solicitarPJ(@RequestParam("cnpj") String cnpj,
                                 @RequestParam("razaoSocial") String razaoSocial,
                                 @RequestPart("arquivo") MultipartFile arquivo) throws IOException {
        return service.solicitarAfiliacaoPJ(cnpj, razaoSocial, arquivo);
    }


}