/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

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
import br.esp.sysevent.core.service.CidadeService;
import br.esp.sysevent.core.service.CorCamisetaService;
import br.esp.sysevent.core.service.DormitorioService;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.EstadoService;
import br.esp.sysevent.core.service.EventoService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.esp.sysevent.core.service.InscricaoService;
import br.esp.sysevent.core.service.NoticiaService;
import br.esp.sysevent.core.service.OficinaService;
import br.esp.sysevent.core.service.TamanhoCamisetaService;
import br.esp.sysevent.core.service.TipoCamisetaService;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.NumberUtils;
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
    private EstadoService estadoService;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private OficinaService oficinaService;
    @Autowired
    private DormitorioService dormitorioService;
    @Autowired
    private TipoCamisetaService tipoCamisetaService;
    @Autowired
    private TamanhoCamisetaService tamanhoCamisetaService;
    @Autowired
    private CorCamisetaService corCamisetaService;
    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private GrupoIdadeService grupoIdadeService;

    @RequestMapping(value = "/admin/deleteEvento.html", method = RequestMethod.GET)
    public String deleteEvento(@RequestParam("idEvento") final String idEvento, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEvento)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Evento evento = eventoService.findById(NumberUtils.parseLong(idEvento));
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado.");
        }
        eventoService.delete(evento);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEvento.html";
    }

    @RequestMapping(value = "/admin/deleteEdicao.html", method = RequestMethod.GET)
    public String deleteEdicao(@RequestParam("idEdicao") final String idEdicao, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        edicaoService.delete(edicao);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEdicao.html";
    }

    @RequestMapping(value = "/admin/deleteEstado.html", method = RequestMethod.GET)
    public String deleteEstado(@RequestParam("idEstado") final String idEstado, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idEstado)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Estado estado = estadoService.findById(NumberUtils.parseLong(idEstado));
        if (estado == null) {
            throw new IllegalArgumentException("Estado não encontrado.");
        }
        estadoService.delete(estado);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formEstado.html";
    }

    @RequestMapping(value = "/admin/deleteCidade.html", method = RequestMethod.GET)
    public String deleteCidade(@RequestParam("idCidade") final String idCidade, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idCidade)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Cidade cidade = cidadeService.findById(NumberUtils.parseLong(idCidade));
        if (cidade == null) {
            throw new IllegalArgumentException("Cidade não encontrado.");
        }
        cidadeService.delete(cidade);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formCidade.html";
    }

    @RequestMapping(value = "/admin/deleteNoticia.html", method = RequestMethod.GET)
    public String deleteNoticia(@RequestParam("idNoticia") final String idNoticia, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idNoticia)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Noticia noticia = noticiaService.findById(NumberUtils.parseLong(idNoticia));
        if (noticia == null) {
            throw new IllegalArgumentException("Noticia não encontrada.");
        }
        noticiaService.delete(noticia);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formNoticia.html";
    }

    @RequestMapping(value = "/admin/deleteTipoCamiseta.html", method = RequestMethod.GET)
    public String deleteTipoCamiseta(@RequestParam("idTipo") final String idTipoCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idTipoCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final TipoCamiseta tipoCamiseta = tipoCamisetaService.findById(NumberUtils.parseLong(idTipoCamiseta));
        if (tipoCamiseta == null) {
            throw new IllegalArgumentException("Tipo de camiseta não encontrada.");
        }
        tipoCamisetaService.delete(tipoCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formTipoCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteTamanhoCamiseta.html", method = RequestMethod.GET)
    public String deleteTamanhoCamiseta(@RequestParam("idTamanho") final String idTamanhoCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idTamanhoCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final TamanhoCamiseta tamanhoCamiseta = tamanhoCamisetaService.findById(NumberUtils.parseLong(idTamanhoCamiseta));
        if (tamanhoCamiseta == null) {
            throw new IllegalArgumentException("Tamanho de camiseta não encontrado.");
        }
        tamanhoCamisetaService.delete(tamanhoCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formTamanhoCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteCorCamiseta.html", method = RequestMethod.GET)
    public String deleteCorCamiseta(@RequestParam("idCor") final String idCorCamiseta, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idCorCamiseta)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final CorCamiseta corCamiseta = corCamisetaService.findById(NumberUtils.parseLong(idCorCamiseta));
        if (corCamiseta == null) {
            throw new IllegalArgumentException("Cor de camiseta não encontrada.");
        }
        corCamisetaService.delete(corCamiseta);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formCorCamiseta.html";
    }

    @RequestMapping(value = "/admin/deleteOficina.html", method = RequestMethod.GET)
    public String deleteOficina(@RequestParam("idOficina") final String idOficina, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idOficina)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Oficina oficina = oficinaService.findById(NumberUtils.parseLong(idOficina));
        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não encontrada.");
        }
        oficinaService.delete(oficina);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formOficina.html?idEdicao=" + oficina.getEdicaoEvento().getId();
    }
    
    @RequestMapping(value = "/admin/deleteGrupoIdade.html", method = RequestMethod.GET)
    public String deleteGrupoIdade(@RequestParam("idGrupoIdade") final String idGrupoIdade, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idGrupoIdade)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final GrupoIdade grupoIdade = grupoIdadeService.findById(NumberUtils.parseLong(idGrupoIdade));
        if (grupoIdade == null) {
            throw new IllegalArgumentException("Grupo não encontrado.");
        }
        grupoIdadeService.delete(grupoIdade);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formGrupoIdade.html?idEdicao=" + grupoIdade.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/deleteDormitorio.html", method = RequestMethod.GET)
    public String deleteDormitorio(@RequestParam("idDormitorio") final String idDormitorio, final RedirectAttributes attributes) {
        if (!CharSequenceUtils.isNumber(idDormitorio)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Dormitorio dormitorio = dormitorioService.findById(NumberUtils.parseLong(idDormitorio));
        if (dormitorio == null) {
            throw new IllegalArgumentException("Dormitório não encontrada.");
        }
        dormitorioService.delete(dormitorio);
        attributes.addFlashAttribute("message", "Deletado com sucesso.");
        return "redirect:/admin/formDormitorio.html?idEdicao=" + dormitorio.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/alocaDormitorio.html", method = RequestMethod.GET)
    public String alocaConfraternistasDomitorios(@RequestParam("idEdicao") final String idEdicao, final RedirectAttributes attributes) {

        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));        
        dormitorioService.alocaConfraternistasAleatoriamente(edicao);
        attributes.addFlashAttribute("message", "Confraternistas alocados com sucesso.");
        return "redirect:/admin/formDormitorio.html?idEdicao=" + idEdicao;

    }
}
