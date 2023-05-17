package br.com.springdesk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.springdesk.model.Perfil;
import br.com.springdesk.model.Tecnico;
import br.com.springdesk.repository.ChamadoRepository;
import br.com.springdesk.repository.TecnicoRepository;
import br.com.springdesk.service.TecnicoService;
import br.com.springdesk.util.PasswordUtil;
import br.com.springdesk.util.UploadUtil;

@Controller
@RequestMapping("tecnico")
public class TecnicoController {
	
	
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	TecnicoService tecnicoService;
	
	@Autowired
	PasswordUtil passwordUtil;
	
	@Autowired
	UploadUtil uploadUtil;
	
	@Autowired
	ChamadoRepository chamadoRepository;
	
	@GetMapping("cadastro")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView cadastro(Tecnico tecnico) {
		ModelAndView mv = new ModelAndView("tecnico/cadastro");
		mv.addObject("tecnico", new Tecnico());
		mv.addObject("perfils", Perfil.values());
		return mv;
	}
	
	@PostMapping("/cadastro-tecnico")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView cadastro(@Valid @ModelAttribute Tecnico tecnico, BindingResult result, @RequestParam("file") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("tecnico/cadastro");
		mv.addObject("tecnico", tecnico);
		String erro = tecnicoService.validaEmailExistente(tecnico);
		if( erro != null && !erro.isEmpty()) {
			ObjectError error = new ObjectError("globalError", erro);
			result.addError(error);
		}
		
		if(result.hasErrors()) {
			System.out.println("Erro ao salvar " + result.getAllErrors());
			mv.setViewName("tecnico/cadastro");
			mv.addObject("tecnico", tecnico);
			mv.addObject("perfils", Perfil.values());
			return mv;
		} else {
			try {
				tecnicoService.salvarTecnico(tecnico, imagem);
			} catch (Exception e) {
				erro = e.getLocalizedMessage();
				ObjectError error = new ObjectError("globalError", erro);
				result.addError(error);
				System.out.println("Erro ao salvar " + e.getMessage());
				mv.setViewName("tecnico/cadastro");
				mv.addObject("tecnico", tecnico);
				mv.addObject("perfils", Perfil.values());
				return mv;
			}
			
		}
		return home();
	}
	
	@GetMapping("/inicio")
    public ModelAndView home(){
        ModelAndView mv =  new ModelAndView("home/index");
        mv.addObject("chamadosList",  chamadoRepository.findAll());
        return mv;
    }
	
	@GetMapping("list-tecnicos")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView tecnicosList() {
		ModelAndView mv =  new ModelAndView("tecnico/tecnico-list");
		mv.addObject("tecnicos", tecnicoRepository.findAll());
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView excluirTecnico(@PathVariable("id") Integer id) {
		
		try {
			tecnicoRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			ModelAndView mv =  new ModelAndView("tecnico/tecnico-list");
			mv.addObject("tecnicos", tecnicoRepository.findAll());
			String erro = "Operação não permitida - registro vinculado";
			mv.addObject("msgErro", erro);
			return mv;
		}		
		return tecnicosList();
	}
	
	@GetMapping("/editar/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView editarTecnico(@PathVariable("id") Integer id) {
		ModelAndView mv =  new ModelAndView("tecnico/editar");
		mv.addObject("perfils", Perfil.values());
		mv.addObject("tecnico", tecnicoRepository.findById(id));
		return mv;
	}
	
	@PostMapping("/editar-tecnico")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView editar(Tecnico tecnico) {
		tecnicoService.editarTecnico(tecnico);
		return tecnicosList();
	}

}
