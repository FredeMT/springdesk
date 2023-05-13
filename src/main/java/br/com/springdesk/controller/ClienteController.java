package br.com.springdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.springdesk.model.Cliente;
import br.com.springdesk.model.Perfil;
import br.com.springdesk.repository.ChamadoRepository;
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
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@GetMapping("cadastro")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView cadastro(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		mv.addObject("usuario", new Cliente());
		mv.addObject("perfils", Perfil.values());
		return mv;
	}
	
	@PostMapping("/cadastro-cliente")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
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
        mv.addObject("chamadosList",  chamadoRepository.findAll());
        return mv;
    }
	
	@GetMapping("/editar-perfil")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView editarPerfil(@RequestParam("id") Integer id) {
		ModelAndView mv = new ModelAndView("cliente/editProfile");
		mv.addObject("usuario", clienteRepository.findById(id));
		return mv;
	}
	
	@PostMapping("/editar-perfil")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView editarPerfil(@ModelAttribute Cliente cliente, @RequestParam("file") MultipartFile imagem ) {
		ModelAndView mv = new ModelAndView("cliente/editProfile");
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
	
	@GetMapping("list-clientes")
	public ModelAndView clientesList() {
		ModelAndView mv =  new ModelAndView("cliente/list-cliente");
		mv.addObject("clientes", clienteRepository.findAll());
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView excluirCliente(@PathVariable("id") Integer id) {
		clienteRepository.deleteById(id);
		return clientesList();
	}
	
	@GetMapping("/editar/{id}")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView editarCliente(@PathVariable("id") Integer id) {
		ModelAndView mv =  new ModelAndView("cliente/editar");
		mv.addObject("perfils", Perfil.values());
		mv.addObject("usuario", clienteRepository.findById(id));
		return mv;
	}
	
	@PostMapping("/editar-cliente")
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	public ModelAndView editar(Cliente cliente) {
		clienteRepository.save(cliente);
		return clientesList();
	}
	
	

}
