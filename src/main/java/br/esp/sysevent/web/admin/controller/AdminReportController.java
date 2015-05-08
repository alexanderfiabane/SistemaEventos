/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.CamisetaConfraternistaDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.service.ReportService;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Alexander
 */
@Controller
public class AdminReportController {

    private static final String CONFRATERNISTAS_CIDADES_ESTADO = "br/esp/sysevent/core/report/CidadesEstadoPessoasReport.jasper";
    private static final String CONFRATERNISTAS_OFICINAS = "br/esp/sysevent/core/report/OficinasConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_OFICINAS_OFICINEIROS = "br/esp/sysevent/core/report/OficineirosOficinasConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_GRUPOS = "br/esp/sysevent/core/report/GruposConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_GRUPOS_FACILITADORES = "br/esp/sysevent/core/report/FacilitadoresGruposConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_TIPO = "br/esp/sysevent/core/report/TipoConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_SEXO = "br/esp/sysevent/core/report/SexoConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_CAMISETA = "br/esp/sysevent/core/report/CamisetasConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_DORMITORIOS = "br/esp/sysevent/core/report/DormitoriosConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_PRESENCA = "br/esp/sysevent/core/report/PresencaConfraternistasReport.jasper";
    private static final String CONFRATERNISTAS_SAUDE_ALIMENTACAO = "br/esp/sysevent/core/report/SaudeAlimentacaoConfraternistaReport.jasper";
    private static final String CAMISETA_ENCOMENDA = "br/esp/sysevent/core/report/CamisetasEncomendaReport.jasper";
    private static final String CRACHASA3 = "br/esp/sysevent/core/report/CrachasA3Report.jasper";
    private static final String CRACHASA4 = "br/esp/sysevent/core/report/CrachasA4Report.jasper";
//    private static final String FUNDO_CRACHAS = "br/esp/sysevent/core/report/fundoCracha2.jpg";
    private static final String FUNDO_CRACHAS = "br/esp/sysevent/core/report/fundoCracha3.png";
    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private ReportService reportService;
    @Autowired
    private EventoDao eventoDao;
    @Autowired
    private CamisetaConfraternistaDao camisetaConfraternistaDao;

    @RequestMapping(value = "/admin/relatorio/reportConfCidadeEstado.html", method = RequestMethod.GET)
    public ModelAndView confraternistasCidadeEstadoReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoCidadeEstado(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_CIDADES_ESTADO);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasCidadesEstado.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfOficina.html", method = RequestMethod.GET)
    public ModelAndView confraternistasOficinaReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoOficina(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_OFICINAS);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasOficinas.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfOficinaOficineiro.html", method = RequestMethod.GET)
    public ModelAndView confraternistasOficinaOficineirosReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoOficina(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_OFICINAS_OFICINEIROS);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasOficinasOficineiros.pdf", "application/pdf", response);
        return null;
    }
    @RequestMapping(value = "/admin/relatorio/reportConfGrupo.html", method = RequestMethod.GET)
    public ModelAndView confraternistasGrupoReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoGrupoIdade(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_GRUPOS);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasGrupos.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfGrupoFacilitador.html", method = RequestMethod.GET)
    public ModelAndView confraternistasGrupoFacilitadoresReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoGrupoIdade(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_GRUPOS_FACILITADORES);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasGruposFacilitadores.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfDormitorio.html", method = RequestMethod.GET)
    public ModelAndView confraternistasDormitorioReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoDormitorio(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_DORMITORIOS);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasDormitorios.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfPresenca.html", method = RequestMethod.GET)
    public ModelAndView confraternistasPresencaReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoDeferidas(NumberUtils.parseLong(idEdicao));
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_PRESENCA);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasPresenca.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfSaudeAlimentacao.html", method = RequestMethod.GET)
    public ModelAndView confraternistasSaudeAlimentacaoReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoDeferidas(NumberUtils.parseLong(idEdicao));
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_SAUDE_ALIMENTACAO);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasSaudeAlimentacao.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfTipo.html", method = RequestMethod.GET)
    public ModelAndView confraternistasTipoReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoTipo(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_TIPO);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasTipo.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportCrachaA3.html", method = RequestMethod.GET)
    public ModelAndView crachaA3Report(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoTipo(edicao);
        final InputStream imagem = getClass().getClassLoader().getResourceAsStream(FUNDO_CRACHAS);
        final HashMap<String, Object> parametros = new HashMap<String, Object>(1);
        parametros.put("fundoCracha", imagem);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CRACHASA3, parametros);
        ControllerUtils.writeHttpAttached(pdf, "crachasA3.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportCrachaA4.html", method = RequestMethod.GET)
    public ModelAndView crachaA4Report(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoTipo(edicao);
        final InputStream imagem = getClass().getClassLoader().getResourceAsStream(FUNDO_CRACHAS);
        final HashMap<String, Object> parametros = new HashMap<String, Object>(1);
        parametros.put("fundoCracha", imagem);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CRACHASA4, parametros);
        ControllerUtils.writeHttpAttached(pdf, "crachasA4.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfSexo.html", method = RequestMethod.GET)
    public ModelAndView confraternistasSexoReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoSexo(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_SEXO);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasSexo.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportConfCamiseta.html", method = RequestMethod.GET)
    public ModelAndView confraternistasCamisetasReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        Collection<Inscricao> inscricoes = inscricaoDao.findByEdicaoCamiseta(edicao);
        final byte[] pdf = reportService.geraRelatorio(inscricoes, CONFRATERNISTAS_CAMISETA);
        ControllerUtils.writeHttpAttached(pdf, "confraternistasCamisetas.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/reportEncomendaCamiseta.html", method = RequestMethod.GET)
    public ModelAndView camisetasEncomendaReport(@RequestParam(value = "idEdicao", required = false) final String idEdicao,
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        final HashMap<String, Object> parametros = new HashMap<String, Object>(2);
        parametros.put("numero", edicao.getNumero());
        parametros.put("sigla", edicao.getEvento().getSigla());
        final Collection<CamisetaConfraternista> camisetas = camisetaConfraternistaDao.findByEdicao(edicao);
        final byte[] pdf = reportService.geraRelatorio(camisetas, CAMISETA_ENCOMENDA, parametros);
        ControllerUtils.writeHttpAttached(pdf, "camisetasEncomenda.pdf", "application/pdf", response);
        return null;
    }

    @RequestMapping(value = "/admin/relatorio/listEvento.html", method = RequestMethod.GET)
    public String listEvento(final ModelMap model) {

        final Collection<Evento> eventos = eventoDao.findAll();
        if (eventos == null) {
            throw new IllegalArgumentException("Eventos não encontrados.");
        }
        model.addAttribute("eventos", eventos);
        return "admin/relatorio/listEvento";
    }

    @RequestMapping(value = "/admin/relatorio/listEdicao.html", method = RequestMethod.GET)
    public String listEdicao(@RequestParam(value = "idEvento", required = false) final String idEvento, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEvento)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Evento evento = eventoDao.findById(NumberUtils.parseLong(idEvento));
        final Collection<Edicao> edicoes = edicaoDao.findByProperty("evento", evento);
        if (edicoes == null) {
            throw new IllegalArgumentException("Edições não encontradas.");
        }
        model.addAttribute("edicoes", edicoes);
        model.addAttribute("evento", evento);
        return "admin/relatorio/listEdicao";
    }

    protected Inscricao getInscricao(final String idInscricao) throws IllegalArgumentException, IllegalAccessException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao));
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if (!loggedUser.getPessoa().getId().equals(inscricao.getConfraternista().getPessoa().getId())) {
            throw new IllegalAccessException("Acesso negado a informações de outra pessoa");
        }
        return inscricao;
    }
}
