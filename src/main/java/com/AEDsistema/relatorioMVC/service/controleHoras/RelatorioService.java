package com.AEDsistema.relatorioMVC.service.controleHoras;

import java.io.IOException;

import com.AEDsistema.relatorioMVC.error.data.DataRangeException;

public interface RelatorioService {
    public void createCSV(String data_inicial, String data_final, boolean isForAdmin, boolean isForAprovada) throws IOException, DataRangeException;
    public void createCSVByFuncionario(String data_inicial, String data_final, boolean isForAdmin) throws IOException, DataRangeException;
    public void createResumoCSV(String data_inicial, String data_final, boolean isForAdmin) throws IOException, DataRangeException;
}
