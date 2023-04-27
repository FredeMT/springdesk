package br.com.springdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.springdesk.model.Cliente;
import br.com.springdesk.model.Perfil;
import br.com.springdesk.repository.ClienteRepository;
import br.com.springdesk.util.PasswordUtil;
import br.com.springdesk.util.UploadUtil;

@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	PasswordUtil passwordUtil;
	
	@Autowired
	UploadUtil uploadUtil;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("cadastro")
	public ModelAndView cadastro(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		mv.addObject("usuario", new Cliente());
		mv.addObject("perfis", Perfil.values());
		return mv;
	}
	
	@PostMapping("/cadastro-cliente")
	public ModelAndView cadastro(@ModelAttribute Cliente cliente, @RequestParam("file") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		
		String hashSenha = passwordUtil.encoder(cliente.getSenha());
		cliente.setSenha(hashSenha);
		mv.addObject("usuario", cliente);
		
		try {
			if(uploadUtil.uploadImagem(imagem)) {
				cliente.setImagem(imagem.getOriginalFilename());
			}
			clienteRepository.save(cliente);
			System.out.println("Salvo com sucesso: " + cliente.getNome() + " " + cliente.getImagem());
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
        return mv;
    }

}
