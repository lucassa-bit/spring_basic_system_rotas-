package com.AEDsistema.relatorioMVC.service;

import java.io.File;
import java.io.IOException;

public interface ArquivoService {
    public Object download(String rotaId) throws IOException;
    public Object upload(File file, String rotaId);
}
