package br.com.suporte.suporte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormTecnicoController {

	@GetMapping(value="/formTecnico")
	public String formTecnico() {
		return "formTecnico";
	}
}
