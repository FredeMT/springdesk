package br.com.springdesk.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.com.springdesk.exception.NegocioExceptionHandler;
import br.com.springdesk.model.Tecnico;
import br.com.springdesk.repository.TecnicoRepository;
import br.com.springdesk.util.PasswordUtil;
import br.com.springdesk.util.UploadUtil;
import br.com.springdesk.validator.OnCreate;
import br.com.springdesk.validator.OnUpdate;


@Service
@Validated
public class TecnicoService {
	
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	PasswordUtil passwordUtil;
	
	@Autowired
	UploadUtil uploadUtil;
	
	@Validated(OnCreate.class)
	public void salvarTecnico(@Valid Tecnico tecnico, MultipartFile imagem) {

		String hashSenha = passwordUtil.encoder(tecnico.getSenha());
		tecnico.setSenha(hashSenha);

		if (uploadUtil.uploadImagem(imagem)) {
			tecnico.setImagem(imagem.getOriginalFilename());
		}
		tecnicoRepository.save(tecnico);
		System.out.println("Salvo com sucesso: " + tecnico.getNome() + " " + tecnico.getImagem());
	}
	
	@Validated(OnUpdate.class)
	public void editarTecnico(@Valid Tecnico tecnico) {
		Tecnico tecnicoSalvo = tecnicoRepository.findById(tecnico.getId())
				.orElseThrow(() -> new NegocioExceptionHandler("Pessoa inexistente."));
		BeanUtils.copyProperties(tecnico, tecnicoSalvo, "senha", "imagem");
		tecnicoRepository.save(tecnicoSalvo);
		System.out.println("Atualizado com sucesso: " + tecnicoSalvo.getNome() + " " + tecnicoSalvo.getImagem());
	}
	
	//Valida se email já existe
	public String validaEmailExistente(Tecnico tecnico) {
		if (!tecnicoRepository.findFirstByEmail(tecnico.getEmail()).isEmpty()){
			return "Já existe um email cadastrado para " + tecnico.getEmail();
		}
		return null;
	}

}
