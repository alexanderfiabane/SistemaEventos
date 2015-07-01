/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Fiabane
 */

public class InscricaoAjaxService extends AbstractAjaxTable<Inscricao>{

    @Autowired
    private InscricaoDao inscricaoDao;

    @Override
    public Collection<Inscricao> search(int firstResult, int maxResults, Map<String, String> params) throws Exception {
        Long idEdicao = get(Long.class, "edicao.id", params);
        String nomePessoa = get(String.class, "inscricao.confraternista.pessoa.nome", params);
        Calendar dataSendInscricao = get(Calendar.class, "inscricao.dataInscricao", params);
        String tipoConfraternista = get(String.class, "inscricao.confraternista.tipo", params);
        String situacaoInscricao = get(String.class, "inscricao.status.value", params);
        String numeroDocPagamento = get(String.class, "inscricao.pagamento.numeroDocumento", params);
        Calendar dataPagamentoInscricao = get(Calendar.class, "inscricao.pagamento.data.time", params);
        Order order = getOrder(params);
        if (order != null) {
            return inscricaoDao.searchInscricoes(idEdicao, nomePessoa, dataSendInscricao, tipoConfraternista, situacaoInscricao, numeroDocPagamento, dataPagamentoInscricao, order.toHibernateOrder(),firstResult, maxResults);
        } else {
            return inscricaoDao.searchInscricoes(idEdicao, nomePessoa, dataSendInscricao, tipoConfraternista, situacaoInscricao, numeroDocPagamento, dataPagamentoInscricao, null,firstResult, maxResults);
        }
    }

    @Override
    public long count(Map<String, String> params) throws Exception {
        Long idEdicao = get(Long.class, "edicao.id", params);
        String nomePessoa = get(String.class, "inscricao.confraternista.pessoa.nome", params);
        Calendar dataSendInscricao = get(Calendar.class, "inscricao.dataInscricao", params);
        String tipoConfraternista = get(String.class, "inscricao.confraternista.tipo", params);
        String situacaoInscricao = get(String.class, "inscricao.status.value", params);
        String numeroDocPagamento = get(String.class, "inscricao.pagamento.numeroDocumento", params);
        Calendar dataPagamentoInscricao = get(Calendar.class, "inscricao.pagamento.data.time", params);
        Order order = getOrder(params);
        return inscricaoDao.countInscricoes(idEdicao, nomePessoa, dataSendInscricao, tipoConfraternista, situacaoInscricao, numeroDocPagamento, dataPagamentoInscricao);
    }

    public Collection<Inscricao> findByIdGrupoIdade(final String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return Collections.emptyList();
        }
        return inscricaoDao.findByIdGrupoIdade(NumberUtils.parseLong(idGrupoIdade));
    }

    public Collection<Inscricao> findSemDormitorioBySexo(String genero, String idEdicao){
        if (CharSequenceUtils.isBlankOrNull(genero) || CharSequenceUtils.isBlankOrNull(idEdicao)) {
            return null;
        }
        if (Sexo.MASCULINO.getDescricao().equals(genero)){
            return inscricaoDao.findSemDormitorioBySexo(Sexo.MASCULINO, NumberUtils.parseLong(idEdicao));
        }else{
            return inscricaoDao.findSemDormitorioBySexo(Sexo.FEMININO, NumberUtils.parseLong(idEdicao));
        }

    }

}
