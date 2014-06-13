/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Inscricao;
import br.msf.commons.util.DateUtils;
import br.msf.commons.velocity.ConfigurableVelocityProcessor;
import br.msf.commons.velocity.VelocityProcessor;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Service
public class ReportServiceBean implements ReportService {

    private static final String FICHA_INSCRICAO = "br/esp/sysevent/core/report/FichaInscricaoReport.jasper";
//    private static final String FICHA_INSCRICAO = "br/esp/sysevent/core/report/FichaArteLuzReport.jasper";
    private static final String HTML = "br/esp/sysevent/core/report/FichaInscricaoReport.html";

    @Override
    public byte[] geraRelatorio(Inscricao inscricao) throws Exception {
        return geraRelatorio(Collections.singleton(inscricao), FICHA_INSCRICAO);
//        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(inscricao));
//        return JasperRunManager.runReportToPdf(getJasper(FICHA_INSCRICAO).getInputStream(), getParameters(null), dataSource);
//        final InputStream htmlModel = getClass().getClassLoader().getResourceAsStream(HTML);
//        final String htmlContent = getVelocityProcessor().process(htmlModel, Collections.singletonMap("inscricao", (Object) inscricao));
//        return parseHtmlContent(new ByteArrayInputStream(htmlContent.getBytes(Charset.forName("ISO-8859-1"))));
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

    private VelocityProcessor<Object> getVelocityProcessor() {
        final Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", "utf-8");
        velocityProperties.setProperty("output.encoding", "ISO-8859-1");
        velocityProperties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        final ConfigurableVelocityProcessor<Object> velocityProcessor = new ConfigurableVelocityProcessor<Object>(velocityProperties);
        velocityProcessor.addTool("dateUtils", DateUtils.class);
        return velocityProcessor;
    }

    private byte[] parseHtmlContent(final InputStream htmlContent) throws Exception {
        ByteArrayOutputStream baos = null;
        BufferedOutputStream buffer;
        try {
            baos = new ByteArrayOutputStream();
            buffer = new BufferedOutputStream(baos);
            Tidy tidy = new Tidy();
            tidy.setShowWarnings(false);
            Document doc = tidy.parseDOM(htmlContent, null);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
//            renderer.getFontResolver().addFont("fonts/ZHUM601N.TTF", true);
//            renderer.getFontResolver().addFont("fonts/ZHUM601B.TTF", true);
//            renderer.getFontResolver().addFont("fonts/ZHUM601I.TTF", true);
//            renderer.getFontResolver().addFont("fonts/ZHU601BI.TTF", true);
            renderer.layout();
            renderer.createPDF(buffer);
            return baos.toByteArray();
        } finally {
            if(baos != null) {
                baos.flush();
                baos.close();
            }
        }
    }
}
