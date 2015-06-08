/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import com.javaleks.commons.core.dao.EntityDao;
import com.javaleks.commons.core.model.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.hibernate.criterion.Order;

/**
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <ID>
 * @param <T>
 */
public interface BaseSistemaDao<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> extends EntityDao<ID, T> {

    public Collection<T> findAll(final Order[] orders, final String... joins);
    
    public Collection<T> findAll(final Collection<Order> orders, final String... joins);
    
    public Collection<T> findByProperty(final String propertyName, final Object propertyValue, final Order... orders);

    public Collection<T> findByProperties(final Map<String, Object> properties, final Order... orders);
}
