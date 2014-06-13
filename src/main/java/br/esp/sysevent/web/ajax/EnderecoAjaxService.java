/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.service.CidadeService;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Service
public class EnderecoAjaxService {

    @Autowired
    private CidadeService cidadeService;

    public Collection<Cidade> getCidades(final Long idEstado) {
        if (idEstado == null) {
            return Collections.emptyList();
        }
        return cidadeService.findByEstado(idEstado);
    }
}
