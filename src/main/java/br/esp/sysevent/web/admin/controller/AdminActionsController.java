/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.CorCamisetaDao;
import br.esp.sysevent.core.dao.DormitorioDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.dao.NoticiaDao;
import br.esp.sysevent.core.dao.OficinaDao;
import br.esp.sysevent.core.dao.TamanhoCamisetaDao;
import br.esp.sysevent.core.dao.TipoCamisetaDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.CorCamiseta;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Noticia;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.core.model.TipoCamiseta;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
@Controller
public class AdminActionsController {

    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private CidadeDao cidadeDao;
    @Autowired
    private NoticiaDao noticiaDao;
    @Autowired
    private EventoDao eventoDao;
    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private OficinaDao oficinaDao;
    @Autowired
    private DormitorioDao dormitorioDao;
    @Autowired
    private TipoCamisetaDao tipoCamisetaDao;
    @Autowired
    private TamanhoCamisetaDao tamanhoCamisetaDao;
    @Autowired
    private CorCamisetaDao corCamisetaDao;
    @Autowired
    private GrupoIdadeDao grupoIdadeDao;

    @RequestMapping(value = "/admin/deleteEvento.html", method = RequestMethod.GET)
    public String deleteEvento(@RequestParam("idEvento") final String idEvento, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEvento)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Evento evento = eventoDao.findById(NumberUtils.parseLong(idEvento));
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado.");
        }
        eventoDao.delete(evento);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEvento.html";
    }

    @RequestMapping(value = "/admin/deleteEdicao.html", method = RequestMethod.GET)
    public String deleteEdicao(@RequestParam("idEdicao") final String idEdicao, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        final Long idEvento = edicao.getEvento().getId();
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
//        if (edicao.getTipo().equals(Edicao.Tipo.OFICINA)) {
//            Collection<Oficina> oficinas = oficinaService.findByProperty("edicaoEvento", edicao);
//            edicao.removeOficinas(oficinas);
//            oficinaService.saveOrUpdateAll(oficinas);
//        }
//        if (edicao.getTipo().equals(Edicao.Tipo.FAIXA_ETARIA)) {
//            Collection<GrupoIdade> grupos = grupoIdadeService.findByProperty("edicaoEvento", edicao);
//            edicao.removeGruposIdade(grupos);
//            grupoIdadeService.saveOrUpdateAll(grupos);
//        }
        edicaoDao.delete(edicao);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEdicao.html?idEvento=" + idEvento;
    }

    @RequestMapping(value = "/admin/deleteEstado.html", method = RequestMethod.GET)
    public String deleteEstado(@RequestParam("idEstado") final String idEstado, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEstado)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Estado estado = estadoDao.findById(NumberUtils.parseLong(idEstado));
        if (estado == null) {
            throw new IllegalArgumentException("Estado não encontrado.");
        }
        estadoDao.delete(estado);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEstado.html";
    }

    @RequestMapping(value = "/admin/deleteCidade.html", method = RequestMethod.GET)
    public String deleteCidade(@RequestParam("idCidade") final String idCidade, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idCidade)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Cidade cidade = cidadeDao.findById(NumberUtils.parseLong(idCidade));
        if (cidade == null) {
            throw new IllegalArgumentException("Cidade não encontrado.");
        }
        cidadeDao.delete(cidade);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formCidade.html";
    }

    @RequestMapping(value = "/admin/deleteNoticia.html", method = RequestMethod.GET)
    public String deleteNoticia(@RequestParam("idNoticia") final String idNoticia, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idNoticia)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Noticia noticia = noticiaDao.findById(NumberUtils.parseLong(idNoticia));
        if (noticia == null) {
            throw new IllegalArgumentException("Noticia não encontrada.");
        }
        noticiaDao.delete(noticia);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formNoticia.html";
    }

    @RequestMapping(value = "/admin/deleteTipoCamiseta.html", method = RequestMethod.GET)
    public String deleteTipoCamiseta(@RequestParam("idTipo") final String idTipoCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idTipoCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final TipoCamiseta tipoCamiseta = tipoCamisetaDao.findById(NumberUtils.parseLong(idTipoCamiseta));
        if (tipoCamiseta == null) {
            throw new IllegalArgumentException("Tipo de camiseta não encontrada.");
        }
        tipoCamisetaDao.delete(tipoCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formTipoCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteTamanhoCamiseta.html", method = RequestMethod.GET)
    public String deleteTamanhoCamiseta(@RequestParam("idTamanho") final String idTamanhoCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idTamanhoCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final TamanhoCamiseta tamanhoCamiseta = tamanhoCamisetaDao.findById(NumberUtils.parseLong(idTamanhoCamiseta));
        if (tamanhoCamiseta == null) {
            throw new IllegalArgumentException("Tamanho de camiseta não encontrado.");
        }
        tamanhoCamisetaDao.delete(tamanhoCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formTamanhoCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteCorCamiseta.html", method = RequestMethod.GET)
    public String deleteCorCamiseta(@RequestParam("idCor") final String idCorCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idCorCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final CorCamiseta corCamiseta = corCamisetaDao.findById(NumberUtils.parseLong(idCorCamiseta));
        if (corCamiseta == null) {
            throw new IllegalArgumentException("Cor de camiseta não encontrada.");
        }
        corCamisetaDao.delete(corCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formCorCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteOficina.html", method = RequestMethod.GET)
    public String deleteOficina(@RequestParam("idOficina") final String idOficina, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idOficina)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Oficina oficina = oficinaDao.findById(NumberUtils.parseLong(idOficina));
        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não encontrada.");
        }
        oficinaDao.delete(oficina);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formOficina.html?idEdicao=" + oficina.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/deleteGrupoIdade.html", method = RequestMethod.GET)
    public String deleteGrupoIdade(@RequestParam("idGrupoIdade") final String idGrupoIdade, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idGrupoIdade)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final GrupoIdade grupoIdade = grupoIdadeDao.findById(NumberUtils.parseLong(idGrupoIdade));
        if (grupoIdade == null) {
            throw new IllegalArgumentException("Grupo não encontrado.");
        }
        grupoIdadeDao.delete(grupoIdade);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formGrupoIdade.html?idEdicao=" + grupoIdade.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/deleteDormitorio.html", method = RequestMethod.GET)
    public String deleteDormitorio(@RequestParam("idDormitorio") final String idDormitorio, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idDormitorio)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Dormitorio dormitorio = dormitorioDao.findById(NumberUtils.parseLong(idDormitorio));
        if (dormitorio == null) {
            throw new IllegalArgumentException("Dormitório não encontrada.");
        }
        dormitorioDao.delete(dormitorio);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formDormitorio.html?idEdicao=" + dormitorio.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/alocaDormitorio.html", method = RequestMethod.GET)
    public String alocaConfraternistasDomitorios(@RequestParam("idEdicao") final String idEdicao, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        dormitorioDao.alocaConfraternistasAleatoriamente(edicao);
        attributes.addFlashAttribute("message", "Confraternistas alocados com sucesso.");
        return "redirect:/admin/formDormitorio.html?idEdicao=" + idEdicao;

    }
}
