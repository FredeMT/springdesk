package br.com.springdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import br.com.springdesk.util.PasswordUtil;
import br.com.springdesk.util.UploadUtil;

@Controller
@RequestMapping("tecnico")
public class TecnicoController {
	
	
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	PasswordUtil passwordUtil;
	
	@Autowired
	UploadUtil uploadUtil;
	
	@Autowired
	ChamadoRepository chamadoRepository;
	
	@GetMapping("cadastro")
	public ModelAndView cadastro(Tecnico tecnico) {
		ModelAndView mv = new ModelAndView("tecnico/cadastro");
		mv.addObject("tecnico", new Tecnico());
		mv.addObject("perfils", Perfil.values());
		return mv;
	}
	
	@PostMapping("/cadastro-tecnico")
	public ModelAndView cadastro(@ModelAttribute Tecnico tecnico, @RequestParam("file") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("tecnico/cadastro");
		
		String hashSenha = passwordUtil.encoder(tecnico.getSenha());
		tecnico.setSenha(hashSenha);
		mv.addObject("usuario", tecnico);
		
		try {
			if(uploadUtil.uploadImagem(imagem)) {
				tecnico.setImagem(imagem.getOriginalFilename());
			}
			tecnicoRepository.save(tecnico);
			System.out.println("Salvo com sucesso: " + tecnico.getNome() + " " + tecnico.getImagem());
			return home();
		} catch (Exception e) {
			mv.addObject("msgErro", e.getMessage());
			System.out.println("Erro ao salvar " + e.getMessage());
			return mv;
		}
		
	}
	
	@GetMapping("/inicio")
    public ModelAndView home(){
        ModelAndView mv =  new ModelAndView("home/index");
        mv.addObject("chamadosList",  chamadoRepository.findAll());
        return mv;
    }
	
	@GetMapping("list-tecnicos")
	public ModelAndView tecnicosList() {
		ModelAndView mv =  new ModelAndView("tecnico/tecnico-list");
		mv.addObject("tecnicos", tecnicoRepository.findAll());
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluirCliente(@PathVariable("id") Integer id) {
		tecnicoRepository.deleteById(id);
		return tecnicosList();
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editarTecnico(@PathVariable("id") Integer id) {
		ModelAndView mv =  new ModelAndView("tecnico/editar");
		mv.addObject("perfils", Perfil.values());
		mv.addObject("tecnico", tecnicoRepository.findById(id));
		return mv;
	}
	
	@PostMapping("/editar-tecnico")
	public ModelAndView editar(Tecnico tecnico) {
		tecnicoRepository.save(tecnico);
		return tecnicosList();
	}

}
