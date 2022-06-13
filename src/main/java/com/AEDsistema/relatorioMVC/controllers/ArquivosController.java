package com.AEDsistema.relatorioMVC.controllers;

import java.io.File;
import java.io.IOException;

import com.AEDsistema.relatorioMVC.dto.ArquivoDTO;
import com.AEDsistema.relatorioMVC.service.ArquivoService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/arquivos")

public class ArquivosController {
    private final ArquivoService fileService;

    @Autowired
    public ArquivosController(ArquivoService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/{rotaId}")
    public void upload(@PathVariable String rotaId, @RequestBody ArquivoDTO file) throws IOException {
        //logger.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        File outputFile = new File(file.getNomeArquivo());
        byte[] decodedBytes = Base64.decodeBase64(file.getFile());
        FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
        fileService.upload(outputFile, rotaId);
    }
}
