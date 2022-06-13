package com.AEDsistema.relatorioMVC.service.controleHoras;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.AEDsistema.relatorioMVC.dto.send.RotasExtrasSendDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasSendDTO;
import com.AEDsistema.relatorioMVC.dto.send.ServicosExtrasSendDTO;
import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.service.Login.UsuarioServices;
import com.AEDsistema.relatorioMVC.service.Rotas.RotasExtrasServices;
import com.AEDsistema.relatorioMVC.service.Rotas.RotasServices;
import com.AEDsistema.relatorioMVC.service.Rotas.ServicosExtrasServices;

import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    private final RotasServices rotasServices;
    private final RotasExtrasServices rotasExtrasServices;
    private final ServicosExtrasServices ServicosExtrasServices;
    private final UsuarioServices usuarioServices;

    private LocalDate dataAtual = LocalDate.now();
    private boolean isAdmin = false;

    public RelatorioServiceImpl(RotasServices rotasServices, RotasExtrasServices rotasExtrasServices,
            ServicosExtrasServices ServicosExtrasServices, UsuarioServices usuarioServices) {
        this.rotasServices = rotasServices;
        this.rotasExtrasServices = rotasExtrasServices;
        this.ServicosExtrasServices = ServicosExtrasServices;
        this.usuarioServices = usuarioServices;
    }

    @Override
    public void createCSV(String data_inicial, String data_final, boolean isForAdmin, boolean isForAprovada)
            throws IOException, DataRangeException {
        isAdmin = isForAdmin;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rotas");
        header(workbook, sheet, "PE EXPRESS - RELATÓRIO DE ROTAS", isAdmin ? 15 : 13);

        int position = 2;
        int positionSoma = 2;
        int colunaSoma = 18;
        double[] valores = new double[] { 0, 0, 0 };
        double[] depesas = new double[] { 0, 0, 0 };

        if (!isAdmin) {
            List<RotasSendDTO> rotas = isForAprovada
                    ? rotasServices.pickAllRotasAprovadasByData(data_inicial, data_final)
                    : rotasServices.pickAllRotasByData(data_inicial, data_final);
            if (rotas.size() == 0)
                return;

            if (rotas != null) {
                rotasDisplay(workbook, sheet, position, rotas, valores, depesas,
                        new String[] { "Data de emissão", "Dia", "N° do MDF-e", "numero de romaneio",
                                "Placa do caminhão", "Motorista",
                                "Contato motorista", "Gerente externo", "Contato gerente externo",
                                "Responsável interno",
                                "Contato responsável interno", "Pix responsavel interno", "Rota", "Destino" },
                        false, false);
            }
        } else if (isAdmin) {
            List<RotasSendDTO> rotas = isForAprovada
                    ? rotasServices.pickAllRotasAprovadasByData(data_inicial, data_final)
                    : rotasServices.pickAllRotasByData(data_inicial, data_final);
            List<RotasExtrasSendDTO> rotasExtras = rotasExtrasServices.pickAllRotasByDataRange(data_inicial,
                    data_final);
            List<ServicosExtrasSendDTO> servicosExtras = ServicosExtrasServices
                    .pickAllServicosExtrasByRangeData(data_inicial, data_final);
            positionSoma = headerValores(workbook, sheet, colunaSoma, positionSoma);

            if (rotas.size() == 0 && rotasExtras.size() == 0 && servicosExtras.size() == 0)
                return;

            for (LocalDate data : getRange(data_inicial, data_final)) {
                List<RotasSendDTO> rotasTemp = new ArrayList<>();
                List<RotasExtrasSendDTO> rotasExtrasTemp = new ArrayList<>();
                List<ServicosExtrasSendDTO> servicosTemp = new ArrayList<>();

                double[] rotaValores = new double[] { 0, 0 };
                double[] rotaExtraValores = new double[] { 0, 0 };
                double[] servicoExtraValores = new double[] { 0, 0 };

                for (RotasSendDTO valor : rotas) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        rotasTemp.add(valor);
                        rotaValores[0] += valor.getDespesasRota();
                        rotaValores[1] += valor.getValorRota();
                    }
                }

                for (RotasExtrasSendDTO valor : rotasExtras) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        rotasExtrasTemp.add(valor);
                        rotaExtraValores[0] += valor.getDespesasRota();
                        rotaExtraValores[1] += valor.getValorRota();
                    }
                }

                for (ServicosExtrasSendDTO valor : servicosExtras) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        servicosTemp.add(valor);
                        servicoExtraValores[0] += valor.getDespesasRota();
                        servicoExtraValores[1] += valor.getValorRota();
                    }
                }

                if (rotasTemp.size() > 0 || rotasExtrasTemp.size() > 0 || servicosTemp.size() > 0) {
                    position = rotasDisplay(workbook, sheet, position, rotasTemp, valores, depesas,
                            new String[] { "Data de emissão", "Dia", "N° do MDF-e", "numero de romaneio",
                                    "Placa do caminhão", "Motorista", "Contato motorista", "Gerente externo",
                                    "Contato gerente externo", "Responsável interno", "Contato responsável interno",
                                    "Pix responsavel interno", "Rota", "Destino", "Despesas", "Valores" },
                            false, false);
                    position = rotasExtrasDisplay(workbook, sheet, position, rotasExtrasTemp, valores, depesas,
                            new String[] { "Data de emissão", "Dia", "N° do MDF-e", "numero de romaneio",
                                    "Placa do caminhão", "Motorista", "Contato motorista", "nome administrador",
                                    "Contato administrador", "Rota", "Destino", "Despesas", "Valores" },
                            false);
                    position = servicosExtrasDisplay(workbook, sheet, position, servicosTemp, valores, depesas,
                            new String[] { "Data de emissão", "Dia", "Serviço", "Destino", "Despesas", "Valores" },
                            false);
                    position++;
                    positionSoma = valoresDoDia(workbook, sheet, rotaValores, rotaExtraValores, servicoExtraValores,
                            positionSoma, colunaSoma - 1, data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
            despesasTotais(workbook, sheet, positionSoma, colunaSoma, valores, depesas);
        }

        for (int index = 0; index < 23; index++) {
            sheet.autoSizeColumn(index);
        }

        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    @Override
    public void createResumoCSV(String data_inicial, String data_final, boolean isForAdmin)
            throws IOException, DataRangeException {
        isAdmin = isForAdmin;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rotas");
        header(workbook, sheet, "PE EXPRESS - RELATÓRIO DE ROTAS", 8);

        int position = 2;
        double[] valores = new double[] { 0, 0, 0 };

        if (!isAdmin) {
            List<RotasSendDTO> rotas = rotasServices.pickAllRotasByData(data_inicial, data_final);
            if (rotas.size() == 0)
                return;

            for (RotasSendDTO rotasSendDTO : rotas) {
                valores[0] += rotasSendDTO.getValorRota();
            }

            if (rotas != null) {
                rotasDisplay(workbook, sheet, position, rotas, valores, null,
                        new String[] { "N° do MDF-e", "Data de emissão", "Dia", "N° do romaneio",
                                "Veículo", "Motorista", "Rota", "Destino", "Valores" },
                        true, false);
            }
        } else if (isAdmin) {
            List<RotasSendDTO> rotas = rotasServices.pickAllRotasByData(data_inicial, data_final);
            List<RotasExtrasSendDTO> rotasExtras = rotasExtrasServices.pickAllRotasByDataRange(data_inicial,
                    data_final);
            List<ServicosExtrasSendDTO> servicosExtras = ServicosExtrasServices
                    .pickAllServicosExtrasByRangeData(data_inicial, data_final);

            if (rotas.size() == 0 && rotasExtras.size() == 0 && servicosExtras.size() == 0)
                return;

            for (LocalDate data : getRange(data_inicial, data_final)) {
                List<RotasSendDTO> rotasTemp = new ArrayList<>();
                List<RotasExtrasSendDTO> rotasExtrasTemp = new ArrayList<>();
                List<ServicosExtrasSendDTO> servicosTemp = new ArrayList<>();

                for (RotasSendDTO valor : rotas) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        rotasTemp.add(valor);
                    }
                }

                for (RotasExtrasSendDTO valor : rotasExtras) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        rotasExtrasTemp.add(valor);
                    }
                }

                for (ServicosExtrasSendDTO valor : servicosExtras) {
                    if (valor.getDataEmissao().isEqual(data)) {
                        servicosTemp.add(valor);
                    }
                }

                if (rotasTemp.size() > 0 || rotasExtrasTemp.size() > 0 || servicosTemp.size() > 0) {
                    position = rotasDisplay(workbook, sheet, position, rotasTemp, valores, null,
                            new String[] { "N° do MDF-e", "Data de emissão", "Dia", "N° do romaneio",
                                    "Veículo", "Motorista", "Rota", "Destino", "Valores" },
                            true, false);
                    position = rotasExtrasDisplay(workbook, sheet, position, rotasExtrasTemp, valores, null,
                            new String[] { "N° do MDF-e", "Data de emissão", "Dia", "N° do romaneio",
                                    "Veículo", "Motorista", "Rota", "Destino", "Valores" },
                            true);
                    position = servicosExtrasDisplay(workbook, sheet, position, servicosTemp, valores, null,
                            new String[] { "Data de emissão", "Dia", "Serviço", "Destino", "Valores" }, true);
                    position++;
                }
            }
        }

        if (valores[0] != 0 || valores[1] != 0 || valores[2] != 0) {
            valorTotal(workbook, sheet, 1, 9, valores);
        }

        for (int index = 0; index < 10; index++) {
            sheet.autoSizeColumn(index);
        }

        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    @Override
    public void createCSVByFuncionario(String data_inicial, String data_final, boolean isForAdmin)
            throws IOException, DataRangeException {
        isAdmin = isForAdmin;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rotas");
        header(workbook, sheet, "PE EXPRESS - RELATÓRIO DE ROTAS", isAdmin ? 15 : 13);

        int position = 2, positionSoma = 2;
        double valoresTotais = 0, despesasTotais = 0;

        List<RotasSendDTO> rotas = rotasServices
                .pickAllRotasByData(data_inicial, data_final);

        List<UsuarioSendDTO> usuarios = usuarioServices.pickAllResponsaveisInterno();

        if (rotas.size() == 0)
            return;

        positionSoma = headerValoresFuncionarios(workbook, sheet, isAdmin ? 18 : 16, positionSoma);

        for (UsuarioSendDTO user : usuarios) {
            double valores = 0, despesas = 0;
            List<RotasSendDTO> rotasTemp = new ArrayList<>();

            for (RotasSendDTO valor : rotas) {
                if (valor.getIdResponsavel() == user.getId()) {
                    rotasTemp.add(valor);
                    valores += valor.getValorRota();
                    despesas += valor.getDespesasRota();
                }
            }

            if (rotasTemp.size() > 0) {
                rotasTemp = sortRotas(rotasTemp);

                positionSoma = valoresDoDiaFUncionarios(workbook, sheet, valores, despesas, positionSoma,
                        isAdmin ? 17 : 15,
                        user.getNome());
                position = rotasDisplay(workbook, sheet, position, rotasTemp, null, null,
                        new String[] { "Data de emissão", "Dia", "N° do MDF-e", "numero de romaneio",
                                "Placa do caminhão", "Motorista", "Contato motorista", "Gerente externo",
                                "Contato gerente externo", "Responsável interno", "Contato responsável interno",
                                "Pix responsavel interno", "Rota", "Destino", "Despesas", "Valores" },
                        false, true);

                valoresTotais += valores;
                despesasTotais += despesas;
                position++;
            }
        }

        despesasTotaisFuncionarios(workbook, sheet, positionSoma, isAdmin ? 18 : 16, valoresTotais, despesasTotais);

        for (int index = 0; index < 23; index++) {
            sheet.autoSizeColumn(index);
        }

        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    private void despesasTotais(Workbook workbook, Sheet sheet, int fileiraPos, int position, double[] valores,
            double[] depesas) {
        double totalDepesas = 0, totalValores = 0, somaTotal = 0;
        int positionColuna = position;
        PropertyTemplate pt = new PropertyTemplate();

        for (int index = 0; index < 3; index++) {
            totalDepesas += depesas[index];
            totalValores += valores[index];
        }

        somaTotal = totalValores - totalDepesas;

        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        Cell cell = fileira.createCell(positionColuna);
        cell.setCellValue("Total de despesas");
        positionColuna += 3;
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Total de valores");
        positionColuna += 3;
        autoMerge(sheet, fileiraPos, fileiraPos, position + 3, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        positionColuna = position;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue(totalDepesas);
        positionColuna += 3;
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue(totalValores);
        positionColuna += 3;
        autoMerge(sheet, fileiraPos, fileiraPos, position + 3, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(position);
        cell.setCellValue("Soma dos valores");
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(position);
        cell.setCellValue(somaTotal);
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);
    }

    private void valorTotal(Workbook workbook, Sheet sheet, int fileiraPos, int position, double[] rotas) {
        PropertyTemplate pt = new PropertyTemplate();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);

        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);
        Cell cell = fileira.createCell(position);
        cell.setCellValue("MENSAL");
        cell.setCellStyle(cellStyle);

        fileira = sheet.getRow(fileiraPos + 1);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos + 1);

        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell = fileira.createCell(position);
        cell.setCellValue(String.format("%.2f", rotas[0] + rotas[1] + rotas[2]).replace(".", ","));
        cell.setCellStyle(cellStyle);

        pt.drawBorders(new CellRangeAddress(fileiraPos + 1, fileiraPos + 1, position, position),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);
    }

    private int valoresDoDia(Workbook workbook, Sheet sheet, double[] rotas,
            double[] rotasExtras, double[] servicos, int fileiraPos, int position, String data) {
        PropertyTemplate pt = new PropertyTemplate();
        int initialPos = position;

        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);
        Cell cell = fileira.createCell(position);
        cell.setCellValue(data);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(rotas[0]);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(rotas[1]);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(rotasExtras[0]);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(rotasExtras[1]);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(servicos[0]);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(servicos[1]);
        position++;

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, initialPos, position - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        return fileiraPos + 1;
    }

    private int valoresDoDiaFUncionarios(Workbook workbook, Sheet sheet, double valor, double despesas, int fileiraPos,
            int position,
            String nome) {
        PropertyTemplate pt = new PropertyTemplate();
        int initialPos = position;

        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);
        Cell cell = fileira.createCell(position);
        cell.setCellValue(nome);
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(String.format("%.2f", despesas).replace(".", ","));
        position++;

        cell = fileira.createCell(position);
        cell.setCellValue(String.format("%.2f", valor).replace(".", ","));
        position++;

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, initialPos, position - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        return fileiraPos + 1;
    }

    private void despesasTotaisFuncionarios(Workbook workbook, Sheet sheet, int fileiraPos, int position,
            double valores,
            double despesas) {
        double somaTotal = valores - despesas;
        int positionColuna = position;
        PropertyTemplate pt = new PropertyTemplate();

        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        Cell cell = fileira.createCell(positionColuna);
        cell.setCellValue("Total de despesas");
        positionColuna++;

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Total de valores");
        positionColuna++;

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        positionColuna = position;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue(String.format("%.2f", despesas).replace(".", ","));
        positionColuna++;

        cell = fileira.createCell(positionColuna);
        cell.setCellValue(String.format("%.2f", valores).replace(".", ","));
        positionColuna++;

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(position);
        cell.setCellValue("Soma dos valores");
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(position);
        cell.setCellValue(String.format("%.2f", somaTotal).replace(".", ","));
        autoMerge(sheet, fileiraPos, fileiraPos, position, positionColuna - 1);

        pt.drawBorders(new CellRangeAddress(fileiraPos, fileiraPos, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);
    }

    private int headerValoresFuncionarios(Workbook workbook, Sheet sheet, int position, int fileiraPos) {
        PropertyTemplate pt = new PropertyTemplate();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int positionColuna = position;

        // parte de cima
        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        Cell cell = fileira.createCell(positionColuna);
        cell.setCellValue("Rotas");
        positionColuna += 2;
        autoMerge(sheet, fileiraPos, fileiraPos, positionColuna - 2, positionColuna - 1);
        cell.setCellStyle(cellStyle);

        // parte de baixo
        positionColuna = position;
        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Despesas");
        positionColuna++;
        cell.setCellStyle(cellStyle);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Valores");
        positionColuna++;
        cell.setCellStyle(cellStyle);
        fileiraPos++;

        pt.drawBorders(new CellRangeAddress(fileiraPos - 2, fileiraPos - 1, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        return fileiraPos;
    }

    private int headerValores(Workbook workbook, Sheet sheet, int position, int fileiraPos) {
        PropertyTemplate pt = new PropertyTemplate();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int positionColuna = position;

        // parte de cima
        Row fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        Cell cell = fileira.createCell(positionColuna);
        cell.setCellValue("Rotas");
        positionColuna += 2;
        autoMerge(sheet, fileiraPos, fileiraPos, positionColuna - 2, positionColuna - 1);
        cell.setCellStyle(cellStyle);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Rotas Extras");
        positionColuna += 2;
        autoMerge(sheet, fileiraPos, fileiraPos, positionColuna - 2, positionColuna - 1);
        cell.setCellStyle(cellStyle);

        cell = fileira.createCell(positionColuna);
        cell.setCellValue("Servicos Extras");
        positionColuna += 2;
        autoMerge(sheet, fileiraPos, fileiraPos, positionColuna - 2, positionColuna - 1);
        cell.setCellStyle(cellStyle);

        // parte de baixo
        positionColuna = position;
        fileiraPos++;
        fileira = sheet.getRow(fileiraPos);
        if (fileira == null)
            fileira = sheet.createRow(fileiraPos);

        for (int index = 0; index < 3; index++) {
            cell = fileira.createCell(positionColuna);
            cell.setCellValue("Despesas");
            positionColuna++;
            cell.setCellStyle(cellStyle);

            cell = fileira.createCell(positionColuna);
            cell.setCellValue("Valores");
            positionColuna++;
            cell.setCellStyle(cellStyle);
        }
        fileiraPos++;

        pt.drawBorders(new CellRangeAddress(fileiraPos - 2, fileiraPos - 1, position, positionColuna - 1),
                BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);

        return fileiraPos;
    }

    private void header(Workbook workbook, Sheet sheet, String title, int until) { // TITULO DA PÁGINA
        Row fileira = sheet.createRow(0);
        Cell cell = fileira.createCell(0);
        cell.setCellValue(title);

        fileira.setHeight((short) 500);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.WHITE.getIndex());

        cellStyle.setFont(font);

        autoMerge(sheet, 0, 0, 0, until);
        cell.setCellStyle(cellStyle);

        sheet.autoSizeColumn(0);

    }

    private int headerTabelas(Workbook workbook, Sheet sheet, int position, String title) {
        PropertyTemplate pt = new PropertyTemplate();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row fileira = sheet.getRow(position);
        if (fileira == null) {
            fileira = sheet.createRow(position);
        }
        Cell cell = fileira.createCell(0);

        cell.setCellValue(title);

        pt.drawBorders(new CellRangeAddress(position, position, 0, 0), BorderStyle.THIN,
                BorderExtent.ALL);
        pt.applyBorders(sheet);
        position++;
        return position;
    }

    private int headerRotas(Workbook workbook, Sheet sheet, Row fileira, CellStyle cellStyle, String[] headersName) {

        Cell cell = null;

        for (int index = 0; index < headersName.length; index++) {
            cell = fileira.createCell(index);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headersName[index]);
            sheet.autoSizeColumn(index);
        }

        return headersName.length;
    }

    private int headerRotasExtras(Workbook workbook, Sheet sheet, Row fileira, CellStyle cellStyle,
            String[] headersName) {
        Cell cell = null;

        for (int index = 0; index < headersName.length; index++) {
            cell = fileira.createCell(index);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headersName[index]);
            sheet.autoSizeColumn(index);
        }

        return headersName.length;
    }

    private int rotasDisplay(Workbook workbook, Sheet sheet, int position,
            List<RotasSendDTO> rotas, double[] valores, double[] depesas, String[] header,
            boolean isResumo, boolean isForResponsavel) {
        if (rotas.size() > 0) {
            PropertyTemplate pt = new PropertyTemplate();
            int initialPosition = isForResponsavel ? position : position + 1;

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Calibri");
            font.setBold(true);

            cellStyle.setFont(font);

            if (!isForResponsavel)
                position = headerTabelas(workbook, sheet, position, "Rotas");

            Row fileira = sheet.getRow(position);
            if (fileira == null) {
                fileira = sheet.createRow(position);
            }
            int size = headerRotas(workbook, sheet, fileira, cellStyle, header);
            dataAtual = rotas.get(0).getDataEmissao();

            for (RotasSendDTO rotasSendDTO : rotas) {
                if (dataAtual.equals(rotasSendDTO.getDataEmissao()) || isForResponsavel)
                    position++;
                else {
                    pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, size - 1), BorderStyle.THIN,
                            BorderExtent.ALL);
                    pt.applyBorders(sheet);
                    position += 2;

                    initialPosition = position;
                }
                fileira = sheet.getRow(position);
                if (fileira == null) {
                    fileira = sheet.createRow(position);
                }
                if (isResumo) {
                    adicionarRotaResumo(workbook, sheet, fileira, cellStyle, rotasSendDTO);
                } else {
                    adicionarRota(workbook, sheet, fileira, cellStyle, rotasSendDTO);
                }
                if (depesas != null) {
                    depesas[0] += rotasSendDTO.getDespesasRota();
                }
                if (valores != null) {
                    valores[0] += rotasSendDTO.getValorRota();
                }
                sheet.autoSizeColumn(position);
            }

            pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, size - 1), BorderStyle.THIN,
                    BorderExtent.ALL);
            pt.applyBorders(sheet);

            dataAtual = LocalDate.now();
            position++;
        }
        return position;
    }

    private void adicionarRota(Workbook workbook, Sheet sheet, Row fileira, CellStyle style,
            RotasSendDTO rotasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(rotasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(1);
        cell.setCellValue(diasDaSemana[rotasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(2);
        cell.setCellValue(rotasSendDTO.getNumeroManifesto());
        cell = fileira.createCell(3);
        cell.setCellValue(rotasSendDTO.getNumeroRomaneio());
        cell = fileira.createCell(4);
        cell.setCellValue(rotasSendDTO.getPlacaCaminhao());
        cell = fileira.createCell(5);
        cell.setCellValue(rotasSendDTO.getNomeMotorista());
        cell = fileira.createCell(6);
        cell.setCellValue(rotasSendDTO.getNumeroCelularMotorista());
        cell = fileira.createCell(7);
        cell.setCellValue(rotasSendDTO.getNomeGerente());
        cell = fileira.createCell(8);
        cell.setCellValue(rotasSendDTO.getContatoGerente());
        cell = fileira.createCell(9);
        cell.setCellValue(rotasSendDTO.getNomeResponsavel());
        cell = fileira.createCell(10);
        cell.setCellValue(rotasSendDTO.getContatoResponsavel());
        cell = fileira.createCell(11);
        cell.setCellValue(rotasSendDTO.getPixResponsavel());
        cell = fileira.createCell(12);
        cell.setCellValue(rotasSendDTO.getCodigoRota());
        cell = fileira.createCell(13);
        cell.setCellValue(rotasSendDTO.getDestinoRota());
        if (isAdmin) {
            cell = fileira.createCell(14);
            cell.setCellValue(String.format("%.2f", rotasSendDTO.getDespesasRota()).replace(".", ","));
            cell = fileira.createCell(15);
            cell.setCellValue(String.format("%.2f", rotasSendDTO.getValorRota()).replace(".", ","));
        }
    }

    private void adicionarRotaResumo(Workbook workbook, Sheet sheet, Row fileira, CellStyle style,
            RotasSendDTO rotasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(rotasSendDTO.getNumeroManifesto());
        cell = fileira.createCell(1);
        cell.setCellValue(rotasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(2);
        cell.setCellValue(diasDaSemana[rotasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(3);
        cell.setCellValue(rotasSendDTO.getNumeroRomaneio());
        cell = fileira.createCell(4);
        cell.setCellValue(rotasSendDTO.getPlacaCaminhao());
        cell = fileira.createCell(5);
        cell.setCellValue(rotasSendDTO.getNomeMotorista());
        cell = fileira.createCell(6);
        cell.setCellValue(rotasSendDTO.getCodigoRota());
        cell = fileira.createCell(7);
        cell.setCellValue(rotasSendDTO.getDestinoRota());
        cell = fileira.createCell(8);
        cell.setCellValue(String.format("%.2f", rotasSendDTO.getValorRota()).replace(".", ","));
    }

    private int rotasExtrasDisplay(Workbook workbook, Sheet sheet, int position,
            List<RotasExtrasSendDTO> rotasExtras, double[] valores, double[] despesas,
            String[] header, boolean isResumo) {
        if (rotasExtras.size() > 0) {
            PropertyTemplate pt = new PropertyTemplate();
            int initialPosition = position + 1;

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Calibri");
            font.setBold(true);

            cellStyle.setFont(font);

            position = headerTabelas(workbook, sheet, position, "Rotas extras");
            Row fileira = sheet.getRow(position);
            if (fileira == null) {
                fileira = sheet.createRow(position);
            }
            int size = headerRotasExtras(workbook, sheet, fileira, cellStyle, header);
            dataAtual = rotasExtras.get(0).getDataEmissao();

            for (RotasExtrasSendDTO rotas : rotasExtras) {
                if (dataAtual.equals(rotas.getDataEmissao()))
                    position++;
                else {
                    dataAtual = rotas.getDataEmissao();
                    pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, 12), BorderStyle.THIN,
                            BorderExtent.ALL);
                    pt.applyBorders(sheet);
                    position += 2;
                    initialPosition = position;
                }
                fileira = sheet.getRow(position);
                if (fileira == null) {
                    fileira = sheet.createRow(position);
                }
                if (isResumo)
                    adicionarRotaExtraResumo(workbook, sheet, fileira, rotas);
                else
                    adicionarRotaExtra(workbook, sheet, fileira, rotas);
                if (despesas != null) {
                    despesas[1] += rotas.getDespesasRota();

                }
                if (valores != null) {
                    valores[1] += rotas.getValorRota();

                }
                sheet.autoSizeColumn(position);
            }

            pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, size - 1), BorderStyle.THIN,
                    BorderExtent.ALL);
            pt.applyBorders(sheet);

            dataAtual = LocalDate.now();
            position++;

        }
        return position;
    }

    private void adicionarRotaExtra(Workbook workbook, Sheet sheet, Row fileira,
            RotasExtrasSendDTO rotasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(rotasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(1);
        cell.setCellValue(diasDaSemana[rotasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(2);
        cell.setCellValue(rotasSendDTO.getNumeroManifesto());
        cell = fileira.createCell(3);
        cell.setCellValue(rotasSendDTO.getNumeroRomaneio());
        cell = fileira.createCell(4);
        cell.setCellValue(rotasSendDTO.getPlacaCaminhao());
        cell = fileira.createCell(5);
        cell.setCellValue(rotasSendDTO.getNomeMotorista());
        cell = fileira.createCell(6);
        cell.setCellValue(rotasSendDTO.getNumeroCelularMotorista());
        cell = fileira.createCell(7);
        cell.setCellValue(rotasSendDTO.getNomeAdministrador());
        cell = fileira.createCell(8);
        cell.setCellValue(rotasSendDTO.getContatoAdministrador());
        cell = fileira.createCell(9);
        cell.setCellValue(rotasSendDTO.getCodigoRota());
        cell = fileira.createCell(10);
        cell.setCellValue(rotasSendDTO.getDestinoRota());
        cell = fileira.createCell(11);
        cell.setCellValue(String.format("%.2f", rotasSendDTO.getDespesasRota()).replace(".", ","));
        cell = fileira.createCell(12);
        cell.setCellValue(String.format("%.2f", rotasSendDTO.getValorRota()).replace(".", ","));
    }

    private void adicionarRotaExtraResumo(Workbook workbook, Sheet sheet, Row fileira,
            RotasExtrasSendDTO rotasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(rotasSendDTO.getNumeroManifesto());
        cell = fileira.createCell(1);
        cell.setCellValue(rotasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(2);
        cell.setCellValue(diasDaSemana[rotasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(3);
        cell.setCellValue(rotasSendDTO.getNumeroRomaneio());
        cell = fileira.createCell(4);
        cell.setCellValue(rotasSendDTO.getPlacaCaminhao());
        cell = fileira.createCell(5);
        cell.setCellValue(rotasSendDTO.getNomeMotorista());
        cell = fileira.createCell(6);
        cell.setCellValue(rotasSendDTO.getCodigoRota());
        cell = fileira.createCell(7);
        cell.setCellValue(rotasSendDTO.getDestinoRota());
        cell = fileira.createCell(8);
        cell.setCellValue(String.format("%.2f", rotasSendDTO.getValorRota()).replace(".", ","));
    }

    private int headerServicosExtras(Workbook workbook, Sheet sheet, Row fileira, CellStyle cellStyle,
            String[] headersName) {
        Cell cell = null;

        for (int index = 0; index < headersName.length; index++) {
            cell = fileira.createCell(index);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headersName[index]);
            sheet.autoSizeColumn(index);
        }

        return headersName.length;
    }

    private int servicosExtrasDisplay(Workbook workbook, Sheet sheet, int position,
            List<ServicosExtrasSendDTO> servicos, double[] valores, double[] depesas,
            String[] header, boolean isResumo) {
        if (servicos.size() > 0) {
            PropertyTemplate pt = new PropertyTemplate();
            int initialPosition = position + 1;

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Calibri");
            font.setBold(true);

            cellStyle.setFont(font);

            position = headerTabelas(workbook, sheet, position, "Serviços extras");
            Row fileira = sheet.getRow(position);
            if (fileira == null) {
                fileira = sheet.createRow(position);
            }
            int size = headerServicosExtras(workbook, sheet, fileira, cellStyle, header);
            dataAtual = servicos.get(0).getDataEmissao();

            for (ServicosExtrasSendDTO servicosExtrasSendDTO : servicos) {
                if (dataAtual.equals(servicosExtrasSendDTO.getDataEmissao()))
                    position++;
                else {
                    pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, 5), BorderStyle.THIN,
                            BorderExtent.ALL);
                    pt.applyBorders(sheet);

                    position += 2;

                    initialPosition = position;
                }
                fileira = sheet.getRow(position);
                if (fileira == null) {
                    fileira = sheet.createRow(position);
                }
                if (isResumo) {
                    adicionarServicoExtraResumo(workbook, sheet, fileira, servicosExtrasSendDTO);
                } else {
                    adicionarServicoExtra(workbook, sheet, fileira, servicosExtrasSendDTO);
                }
                if (depesas != null) {
                    depesas[2] += servicosExtrasSendDTO.getDespesasRota();
                }
                if (valores != null) {
                    valores[2] += servicosExtrasSendDTO.getValorRota();
                }
            }

            pt.drawBorders(new CellRangeAddress(initialPosition, position, 0, size - 1), BorderStyle.THIN,
                    BorderExtent.ALL);
            pt.applyBorders(sheet);

            dataAtual = LocalDate.now();
            position++;

        }
        return position;
    }

    private void adicionarServicoExtra(Workbook workbook, Sheet sheet, Row fileira,
            ServicosExtrasSendDTO servicosExtrasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(servicosExtrasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(1);
        cell.setCellValue(diasDaSemana[servicosExtrasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(2);
        cell.setCellValue(servicosExtrasSendDTO.getDescricaoServico());
        cell = fileira.createCell(3);
        cell.setCellValue(servicosExtrasSendDTO.getDestinoRota());
        cell = fileira.createCell(4);
        cell.setCellValue(String.format("%.2f", servicosExtrasSendDTO.getDespesasRota()).replace(".", ","));
        cell = fileira.createCell(5);
        cell.setCellValue(String.format("%.2f", servicosExtrasSendDTO.getValorRota()).replace(".", ","));
    }

    private void adicionarServicoExtraResumo(Workbook workbook, Sheet sheet, Row fileira,
            ServicosExtrasSendDTO servicosExtrasSendDTO) {
        String[] diasDaSemana = new String[] { "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
                "Sexta-feira", "Sábado", "Domingo" };
        Cell cell = fileira.createCell(0);
        cell.setCellValue(servicosExtrasSendDTO.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cell = fileira.createCell(1);
        cell.setCellValue(diasDaSemana[servicosExtrasSendDTO.getDataEmissao().getDayOfWeek().getValue() - 1]);
        cell = fileira.createCell(2);
        cell.setCellValue(servicosExtrasSendDTO.getDescricaoServico());
        cell = fileira.createCell(3);
        cell.setCellValue(servicosExtrasSendDTO.getDestinoRota());
        cell = fileira.createCell(4);
        cell.setCellValue(String.format("%.2f", servicosExtrasSendDTO.getValorRota()).replace(".", ","));
    }

    private void autoMerge(Sheet sheet, int primeraFileira, int ultimaFileira, int primeiraColuna, int ultimaColuna) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(primeraFileira, ultimaFileira, primeiraColuna,
                ultimaColuna);
        if (!sheet.getMergedRegions().contains(cellRangeAddress))
            sheet.addMergedRegion(cellRangeAddress);
    }

    private List<LocalDate> getRange(String minimun, String max) throws DataRangeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate minimunDate = LocalDate.parse(minimun, formatter);
        LocalDate maxDate = LocalDate.parse(max, formatter);

        List<LocalDate> rangeList = new ArrayList<>();

        if (minimunDate.isAfter(maxDate))
            throw new DataRangeException();

        for (LocalDate date = minimunDate; maxDate.isAfter(date); date = date.plusDays(1L)) {
            rangeList.add(date);
        }
        rangeList.add(maxDate);

        return rangeList;
    }

    public List<RotasSendDTO> sortRotas(List<RotasSendDTO> rotas) {
        List<RotasSendDTO> returnList = new ArrayList<>();
        Comparator<RotasSendDTO> compareValues = (rota1, rota2) -> {
            return rota1.getDataEmissao().compareTo(rota2.getDataEmissao());
        };
        rotas.stream().sorted(compareValues).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
