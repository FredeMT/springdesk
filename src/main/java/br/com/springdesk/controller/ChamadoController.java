package br.com.springdesk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.springdesk.model.Chamado;
import br.com.springdesk.model.Prioridade;
import br.com.springdesk.model.StatusTicket;
import br.com.springdesk.repository.ChamadoRepository;
import br.com.springdesk.repository.TecnicoRepository;

@Controller
@RequestMapping("/ticket")
public class ChamadoController {
	
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	ChamadoRepository chamadoRepository;

	
	@GetMapping("/criar")
	@PreAuthorize("hasAnyAuthority('CLIENTE','ADMIN')")
	public ModelAndView ticket(Chamado chamado) {
		ModelAndView mv = new ModelAndView("chamados/ticket");
		mv.addObject("ticket", chamado);
		mv.addObject("statusChamados", StatusTicket.values());
		mv.addObject("Prioridade", Prioridade.values());
		mv.addObject("tecnicos", tecnicoRepository.findAll());
		return mv;
	}
	
	@PostMapping("/new-ticket")
	@PreAuthorize("hasAnyAuthority('CLIENTE','ADMIN')")
	public ModelAndView newTicket(@Valid @ModelAttribute("ticket") Chamado chamado, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()) {
			//mv.setViewName("pagesTema/erros/error-404");
			mv.setViewName("chamados/ticket");
			mv.addObject("ticket", chamado);
			mv.addObject("statusChamados", StatusTicket.values());
			mv.addObject("Prioridade", Prioridade.values());
			mv.addObject("tecnicos", tecnicoRepository.findAll());
			return mv;
		}
		chamadoRepository.save(chamado);
		return home();
	}
	
	@GetMapping("/inicio")
	@PreAuthorize("hasAnyAuthority('CLIENTE','ADMIN')")
    public ModelAndView home(){
        ModelAndView mv =  new ModelAndView("home/index");
        mv.addObject("chamadosList",  chamadoRepository.findAll());
        return mv;
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/excluir/{id}")
	public ModelAndView excluirChamado(@PathVariable("id") Integer id) {
		chamadoRepository.deleteById(id);
		return home();
	}
	
	@GetMapping("/editar/{id}")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView editarTicket(@PathVariable("id") Integer id) {
		ModelAndView mv =  new ModelAndView("chamados/editar-ticket");
		mv.addObject("ticket", chamadoRepository.findById(id));
		mv.addObject("statusChamados", StatusTicket.values());
		mv.addObject("Prioridade", Prioridade.values());
		mv.addObject("tecnicos", tecnicoRepository.findAll());
		return mv;
	}
	
	@PostMapping("/editar-ticket")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView editar(Chamado chamado) {
		chamadoRepository.save(chamado);
		return home();
	}
	
	
}
