/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Inscricao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Service
public class ReportServiceBean implements ReportService {

    private static final String FICHA_INSCRICAO = "br/esp/sysevent/core/report/FichaInscricaoReport.jasper";    

    @Override
    public byte[] geraRelatorio(Inscricao inscricao) throws Exception {        
        final HashMap<String, Object> parametros = new HashMap<>(1);        
        parametros.put("menorIdade", isMenorIdade(inscricao.getEdicaoEvento().getPeriodoEdicao().getStart(), inscricao.getConfraternista().getPessoa().getDataNascimento()));
        return geraRelatorio(Collections.singleton(inscricao), FICHA_INSCRICAO, parametros);
    }

    @Override
    public byte[] geraRelatorio(Collection<?> colecao, String relatorio) throws Exception {
        return geraRelatorio(colecao, relatorio, null);
    }

    @Override
    public byte[] geraRelatorio(Collection<?> colecao, String relatorio, Map<String, Object> parametros) throws Exception {
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(colecao);
        return JasperRunManager.runReportToPdf(getJasper(relatorio).getInputStream(), getParameters(parametros), dataSource);
    }

    private Map<String, Object> getParameters(Map<String, Object> parametros) {
        final HashMap<String, Object> params = new HashMap<String, Object>(parametros != null ? parametros.size() + 1 : 1);
        params.put("SUBREPORT_DIR", "br/esp/sysevent/core/report/");
        if(parametros != null) {
            params.putAll(parametros);
        }
        return params;
    }

    private Resource getJasper(String jasper) {
        return new ClassPathResource(jasper);
    }

    private boolean isMenorIdade(Calendar dataEvento, Calendar dataNascimento){
        int idade = getIdade(dataEvento, dataNascimento);
        return (idade < 18);
    }

    private int getIdade(Calendar dataMaior, Calendar dataMenor) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        int year1 = dataMenor.get(Calendar.YEAR);
        int year2 = dataMaior.get(Calendar.YEAR);
        int month1 = dataMenor.get(Calendar.MONTH);
        int month2 = dataMaior.get(Calendar.MONTH);
        int day1 = dataMenor.get(Calendar.DAY_OF_MONTH);
        int day2 = dataMaior.get(Calendar.DAY_OF_MONTH);
        int idade = year2 - year1;
        if ((month2 < month1)
                || ((month2 == month1) && (day2 < day1))) {
            idade -= 1;
        }
        return idade;
    }
}
