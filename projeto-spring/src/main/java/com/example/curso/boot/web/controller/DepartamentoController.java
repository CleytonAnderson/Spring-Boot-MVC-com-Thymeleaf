package com.example.curso.boot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.curso.boot.domain.Departamento;
import com.example.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
}
	@GetMapping("/listar") 
	public String listar(ModelMap model) {
		model.addAttribute("departamentos",service.buscarTodos()); // Busco no banco todos os departamentos
		return "/departamento/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Departamento departamento,RedirectAttributes attr) {
		service.salvar(departamento);
		attr.addFlashAttribute("success", "Departamento inserido com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}") //id que vai esta na url para saber qual departamento  quer editar.
	public String preEditar(@PathVariable("id")Long id,ModelMap model) {  // recuperar o id que enviou como path
		model.addAttribute("departamento", service.buscarPorID(id));
		return "/departamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Departamento departamento,RedirectAttributes attr) {
		service.editar(departamento);
		attr.addFlashAttribute("success","Departamento editado com sucesso");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if(service.departamentoTemCargos(id)) {
			model.addAttribute("fail", "Departamento nao removido. Possui cargos(s) vinculados(s).");
		} else {
			service.excluir(id);
			model.addAttribute("success", "Departamento excluido com sucesso.");
		}
		
		return listar(model);
	}
}