package br.com.suporte.suporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class SolicitacaoController {
	
	@Autowired
	private SolicitacaoRepository sr;
	
	
	@GetMapping(value="/solicitacao")
	public String solicitacao() {
		return "solicitacao";
	}
	
	@PostMapping(value="/solicitacao")
	public String solicitacao(Solicitacao solicitacao) {
		sr.save(solicitacao);
		return "redirect:/solicitacao";
	}
	
	@RequestMapping("/solicitacao")
	public ModelAndView listasolicitacao() {
		ModelAndView mv = new ModelAndView("/painelTecnico");
		Iterable<Solicitacao> solicitacao = sr.findAll();
		mv.addObject("solicitacao",solicitacao);
		return mv;
	}

}
