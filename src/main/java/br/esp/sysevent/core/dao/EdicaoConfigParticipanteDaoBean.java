/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.EdicaoConfigParticipante;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Repository(value = "edicaoConfigParticipanteDao")
public class EdicaoConfigParticipanteDaoBean extends AbstractBaseSistemaDaoBean<Long, EdicaoConfigParticipante> implements EdicaoConfigParticipanteDao{

    @Autowired
    public EdicaoConfigParticipanteDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
