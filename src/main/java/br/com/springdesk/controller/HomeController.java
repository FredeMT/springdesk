package br.com.springdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springdesk.repository.ChamadoRepository;

@Controller
public class HomeController {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@GetMapping()
	public ModelAndView start() {
		ModelAndView mv = new ModelAndView("home/index");
		mv.addObject("chamadosList",  chamadoRepository.findAll());
		return mv;
	}
}
