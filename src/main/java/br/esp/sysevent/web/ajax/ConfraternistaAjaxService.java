/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.EdicaoService;
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
    private EdicaoService edicaoService;
    @Autowired
    private InscricaoService inscricaoService;

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

    public Collection<Confraternista> findSemDormitorio(String genero, String idEdicao) {
        if (CharSequenceUtils.isBlankOrNull(genero) || CharSequenceUtils.isBlankOrNull(idEdicao)) {
            return null;
        } else {
            Collection<Inscricao> inscricoes = inscricaoService.findByEdicao(NumberUtils.parseLong(idEdicao));
            Collection<Confraternista> confraternistas = new HashSet<Confraternista>();
            for (Inscricao inscricao : inscricoes) {
                if (inscricao.getConfraternista().getDormitorio() == null) {
                    if (genero.equals(Sexo.MASCULINO.getDescricao())) {
                        if (inscricao.getConfraternista().getPessoa().getSexo().equals(Sexo.MASCULINO)
                                && (inscricao.getStatus().equals(Inscricao.Status.AGUARDANDO_PAGAMENTO) || inscricao.getStatus().equals(Inscricao.Status.EFETIVADA))) {
                            confraternistas.add(inscricao.getConfraternista());
                        }
                    } else {
                        if (inscricao.getConfraternista().getPessoa().getSexo().equals(Sexo.FEMININO)
                                && (inscricao.getStatus().equals(Inscricao.Status.AGUARDANDO_PAGAMENTO) || inscricao.getStatus().equals(Inscricao.Status.EFETIVADA))) {
                            confraternistas.add(inscricao.getConfraternista());
                        }
                    }
                }
            }
            return confraternistas;
        }
    }
}
