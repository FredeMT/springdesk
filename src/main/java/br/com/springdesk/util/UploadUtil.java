package br.com.springdesk.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadUtil {
	
	public boolean uploadImagem(MultipartFile imagem) {
		boolean sucessoUpload = false;
		
		if(!imagem.isEmpty()) {
			String nomeArquivo = imagem.getOriginalFilename();
			try {
				//criando diretorio para armazenar o arquivo
				String pastaUploadImagem = this.getClass().getResource("/static/images/img-user-profiles").toURI().getPath();
				File dir = new File(pastaUploadImagem);
				if(!dir.exists()) {
					dir.mkdir();
				}
				//criando o arquivo no diretorio
				File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(imagem.getBytes());
				stream.close();
				
				System.out.println("Armazenado em: " + serverFile.getAbsolutePath());
                System.out.println("Você fez o upload do arquivo: " + nomeArquivo + " com sucesso!");
                
                sucessoUpload = true;
			} catch (Exception e) {
				System.out.println("Você falhou em carregar o arquivo : " + nomeArquivo + " =>" + e.getMessage());
			}
		} else {
			System.out.println(" Você falhou em carregr o arquivo por que ele está vazio!");
		}
		
		return sucessoUpload;
	}

}
