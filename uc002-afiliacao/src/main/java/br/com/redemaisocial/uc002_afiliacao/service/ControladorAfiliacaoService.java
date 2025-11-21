package br.com.redemaisocial.uc002_afiliacao.service;

import br.com.redemaisocial.uc002_afiliacao.entity.*;
import br.com.redemaisocial.uc002_afiliacao.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class ControladorAfiliacaoService {

    private final PessoaFisicaRepository pfRepo;
    private final PessoaJuridicaRepository pjRepo;
    private final CandidatoRepository candRepo;
    private final AfiliacaoRepository afiRepo;
    private final PerfilRepository perfilRepo;
    private final CertidaoRepository certidaoRepo;
    private final TermoRepository termoRepo;
    private final AceiteTermoRepository aceiteRepo;

    public ControladorAfiliacaoService(PessoaFisicaRepository pfRepo, PessoaJuridicaRepository pjRepo,
                                       CandidatoRepository candRepo, AfiliacaoRepository afiRepo,
                                       PerfilRepository perfilRepo, CertidaoRepository certidaoRepo,
                                       TermoRepository termoRepo, AceiteTermoRepository aceiteRepo) {
        this.pfRepo = pfRepo;
        this.pjRepo = pjRepo;
        this.candRepo = candRepo;
        this.afiRepo = afiRepo;
        this.perfilRepo = perfilRepo;
        this.certidaoRepo = certidaoRepo;
        this.termoRepo = termoRepo;
        this.aceiteRepo = aceiteRepo;
    }

    // MÉTODO PESSOA FÍSICA
    @Transactional
    public Candidato solicitarAfiliacaoPF(String cpf, String nome, String dataNasc, String sexo) {

        // 1. TRATAMENTO DE CPF VAZIO/NULO (NOVA CORREÇÃO)
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new RuntimeException("O campo CPF é obrigatório.");
        }

        // 2. TRATAMENTO DE CPF EXISTENTE
        if (pfRepo.findAll().stream().anyMatch(p -> p.getCpf().equals(cpf))) {
            throw new RuntimeException("CPF já existe.");
        }

        PessoaFisica pf = new PessoaFisica();
        pf.setCpf(cpf);
        pf.setNome(nome);

        // 3. TRATAMENTO DE DATA INVÁLIDA
        try {
            if (dataNasc == null || dataNasc.trim().isEmpty()) {
                throw new RuntimeException("O campo Data de Nascimento é obrigatório.");
            }
            pf.setData_nascimento(Date.valueOf(dataNasc));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Formato da Data de Nascimento inválido. Utilize o formato YYYY-MM-DD.");
        }

        pf.setSexo(sexo);
        PessoaFisica salva = pfRepo.save(pf);

        return processarCandidato(salva, null);
    }

    // --- METODO PESSOA JURÍDICA (Com Upload) ---
    @Transactional
    public Candidato solicitarAfiliacaoPJ(String cnpj, String razaoSocial, MultipartFile arquivo) throws IOException {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new RuntimeException("O campo CNPJ é obrigatório.");
        }

        if (pjRepo.existsByCnpj(cnpj)) {
            throw new RuntimeException("CNPJ já existe.");
        }

        if (arquivo == null || arquivo.isEmpty()) {
            throw new RuntimeException("O campo Certidão é obrigatório.");
        }

        // 1. Salvar PJ
        PessoaJuridica pj = new PessoaJuridica();
        pj.setCnpj(cnpj);
        pj.setRazao_social(razaoSocial);
        PessoaJuridica salvaPj = pjRepo.save(pj);

        // 2. Salvar Arquivo (Certidão)
        Certidao cert = new Certidao();
        cert.setTipo(arquivo.getContentType());
        cert.setArquivo(arquivo.getBytes());
        cert.setPessoaJuridica(salvaPj); // Usa o setter que adicionamos no Passo 1
        certidaoRepo.save(cert);

        return processarCandidato(null, salvaPj);
    }

    // --- LÓGICA COMUM (Candidato, Afiliação, Termo, Email) ---
    private Candidato processarCandidato(PessoaFisica pf, PessoaJuridica pj) {
        // Criar Candidato
        Candidato c = new Candidato();
        c.setStatus("Aguardando Validação");
        if (pf != null) c.setPessoaFisica(pf);
        if (pj != null) c.setPessoaJuridica(pj);
        Candidato salvo = candRepo.save(c);

        // Criar Afiliação
        Afiliacao afi = new Afiliacao();
        afi.setStatus("Aguardando Aprovação");
        afi.setCandidato(salvo);
        Afiliacao afiSalva = afiRepo.save(afi);

        // Registrar Aceite do Termo (Corrigido para usar findTopByOrderByIdDesc().orElse(null))
        Termo termo = termoRepo.findTopByOrderByIdDesc()
                .orElse(null);

        if (termo != null) {
            AceiteTermo aceite = new AceiteTermo();
            aceite.setAfiliacao(afiSalva);
            aceite.setTermo(termo);
            aceite.setData(Date.valueOf(LocalDate.now()));
            aceite.setStatus("ACEITO");
            aceiteRepo.save(aceite);
        }

        // Criar Perfil
        Perfil p = new Perfil();
        p.setCandidato(salvo);
        p.setDescricao("Perfil Inicial");
        perfilRepo.save(p);

        System.out.println(">> EMAIL ENVIADO PARA CANDIDATO ID: " + salvo.getId());
        return salvo;
    }
}