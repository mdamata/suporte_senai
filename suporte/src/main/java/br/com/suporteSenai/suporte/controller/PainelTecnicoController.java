package br.com.suporteSenai.suporte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PainelTecnicoController {
	
	@GetMapping ("/painelTecnico")
	public String painelTecnico ( ) {
	return "painelTecnico";
}
}
