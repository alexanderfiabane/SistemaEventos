/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.esp.sysevent.core.service.InscricaoService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Service
public class ConfraternistaAjaxService {

    @Autowired
    private ConfraternistaService confraternistaService;    
    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private GrupoIdadeService grupoIdadeService;

    public Collection<Confraternista> findByNome(final String nome) {
        if (CharSequenceUtils.isBlank(nome)) {
            return Collections.emptyList();
        }
        return confraternistaService.findByNome(nome);
    }

    public Collection<Confraternista> findByIdDormitorio(final String idDormitorio) {
        if (CharSequenceUtils.isBlank(idDormitorio)) {
            return Collections.emptyList();
        }
        return confraternistaService.findByDormitorio(NumberUtils.parseLong(idDormitorio));
    }
    
    //Otimizar em consulta
    public Collection<Confraternista> findByIdGrupoIdade(final String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return Collections.emptyList();
        }        
        final GrupoIdade grupoIdade = grupoIdadeService.findById(NumberUtils.parseLong(idGrupoIdade));
        Collection<Confraternista> confraternistas = new HashSet<Confraternista>();
        Collection<Inscricao> inscricoes = inscricaoService.findByEdicao(grupoIdade.getEdicaoEvento().getId());
        for (Inscricao inscricao : inscricoes) {
            if(inscricao.getConfraternista().getGrupoIdade() != null && (inscricao.getConfraternista().getGrupoIdade().equals(grupoIdade))
                    && (!inscricao.getConfraternista().getTipo().equals(Confraternista.Tipo.FACILITADOR))
                    && (inscricao.getStatus().equals(Inscricao.Status.AGUARDANDO_PAGAMENTO) || inscricao.getStatus().equals(Inscricao.Status.EFETIVADA))){
                confraternistas.add(inscricao.getConfraternista());
            }
        }
        return confraternistas;
    }

    //Otimizar em consulta
    public Collection<Confraternista> findSemDormitorio(String genero, String idEdicao) {
        if (CharSequenceUtils.isBlankOrNull(genero) || CharSequenceUtils.isBlankOrNull(idEdicao)) {
            return null;
        } else {
            Collection<Inscricao> inscricoes = inscricaoService.findByEdicao(NumberUtils.parseLong(idEdicao));
            Collection<Confraternista> confraternistas = new HashSet<Confraternista>();
            for (Inscricao inscricao : inscricoes) {
                if (inscricao.getConfraternista().getDormitorio() == null
                        && (inscricao.getStatus().equals(Inscricao.Status.AGUARDANDO_PAGAMENTO) || inscricao.getStatus().equals(Inscricao.Status.EFETIVADA))) {
                    if (genero.equals(inscricao.getConfraternista().getPessoa().getSexo().getDescricao())) {
                        confraternistas.add(inscricao.getConfraternista());
                    }
                }
            }
            return confraternistas;
        }
    }

    //Otimizar em consulta
    public Collection<Confraternista> findByEdicao(final String idEdicao) {
        Collection<Inscricao> inscricoes = inscricaoService.findByEdicao(NumberUtils.parseLong(idEdicao));
        Collection<Confraternista> confraternistas = new HashSet<Confraternista>();
        for (Inscricao inscricao : inscricoes) {
            if (inscricao.getConfraternista().getGrupoIdade() != null
                    && (inscricao.getStatus().equals(Inscricao.Status.AGUARDANDO_PAGAMENTO) || inscricao.getStatus().equals(Inscricao.Status.EFETIVADA))
                    && (inscricao.getConfraternista().getTipo().equals(Confraternista.Tipo.CONFRATERNISTA) 
                    || inscricao.getConfraternista().getTipo().equals(Confraternista.Tipo.EVANGELIZADOR))) {
                confraternistas.add(inscricao.getConfraternista());
            }
        }
        return confraternistas;
    }   
}
