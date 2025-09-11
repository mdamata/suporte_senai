package br.com.suporte.suporte.controller;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.suporte.suporte.model.PainelTecnico;
import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.PainelTecnicoRepository;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class PainelTecnicoController {
    
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;
    
    @Autowired
    private PainelTecnicoRepository painelTecnicoRepository;
    
    /**
     * Exibe o painel técnico com todas as solicitações
     */
    @GetMapping("/painelTecnico")
    public String mostrarPainelTecnico(Model model) {
        Iterable<Solicitacao> solicitacoes = solicitacaoRepository.findAll();
        model.addAttribute("solicitacoes", solicitacoes);
        return "painelTecnico";
    }
    
    /**
     * Método para assumir uma solicitação - redireciona para formulário do técnico
     */
    @GetMapping("/assumirSolicitacao/{id}")
    public String assumirSolicitacao(@PathVariable("id") long id, Model model) {
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
        if (solicitacao.isPresent()) {
            model.addAttribute("solicitacao", solicitacao.get());
            return "formTecnico";
        }
        return "redirect:/painelTecnico";
    }
    
    /**
     * Método para processar o formulário do técnico (assumir a solicitação)
     */
    @PostMapping("/assumirSolicitacao")
    public String processarAssumirSolicitacao(
            @RequestParam Long idSolicitacao,
            @RequestParam String tecnicoResponsavel,
            @RequestParam String observacoes) {
        
        // Cria ou atualiza registro no PainelTecnico
        PainelTecnico painel = new PainelTecnico();
        painel.setIdSolicitacao(idSolicitacao);
        painel.setTecnicoResponsavel(tecnicoResponsavel);
        painel.setObservacoes(observacoes);
        painel.setStatus("Em Andamento");
        painel.setAcao("Assumida");
        
        painelTecnicoRepository.save(painel);
        
        // Atualiza status da solicitação
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(idSolicitacao);
        if (solicitacao.isPresent()) {
            Solicitacao sol = solicitacao.get();
            sol.setStatus("Em Andamento");
            solicitacaoRepository.save(sol);
        }
        
        return "redirect:/painelTecnico";
    }
    
    /**
     * Método para concluir uma solicitação
     */
    @GetMapping("/concluirSolicitacao/{id}")
    public String concluirSolicitacao(@PathVariable("id") long id) {
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
        if (solicitacao.isPresent()) {
            Solicitacao sol = solicitacao.get();
            sol.setStatus("Concluído");
            solicitacaoRepository.save(sol);
            
            // Atualiza também no PainelTecnico se existir
            Optional<PainelTecnico> painelOpt = painelTecnicoRepository.findByIdSolicitacao(id);
            if (painelOpt.isPresent()) {
                PainelTecnico painel = painelOpt.get();
                painel.setStatus("Concluído");
                painel.setAcao("Concluída");
                painelTecnicoRepository.save(painel);
            }
        }
        return "redirect:/painelTecnico";
    }
    @PostMapping("/assumirSolicitacao/{id}")
    public String assumirSolicitacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id).orElse(null);
        if (solicitacao != null && "Pendente".equals(solicitacao.getStatus())) {
            solicitacao.setStatus("Em Andamento");
            solicitacaoRepository.save(solicitacao);
            redirectAttributes.addFlashAttribute("mensagem", "Solicitação assumida com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Solicitação não encontrada ou já assumida.");
        }
        return "redirect:/painelTecnico";
    }

    // ✅ Método para concluir solicitação
    @PostMapping("/concluirSolicitacao/{id}")
    public String concluirSolicitacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id).orElse(null);
        if (solicitacao != null && "Em Andamento".equals(solicitacao.getStatus())) {
            solicitacao.setStatus("Concluído");
            solicitacaoRepository.save(solicitacao);
            redirectAttributes.addFlashAttribute("mensagem", "Solicitação concluída com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Solicitação não encontrada ou não pode ser concluída.");
        }
        return "redirect:/painelTecnico";
    }
}