/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import br.esp.sysevent.util.SimpleCipher;
import com.javaleks.commons.core.model.AbstractEntity;
import com.javaleks.commons.util.CharSequenceUtils;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Entity
@Table(name = "PAGSEGURO_CONTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_PAGSEGURO_CONTA"))
public class PagSeguroConta extends AbstractEntity{
    private static final long serialVersionUID = 1731550365218691466L;

    @Column(name = "EMAIL", nullable = true)
    private String emailPagSeguro;
    @Column(name = "TOKEN_SEGURANCA_PROD", nullable = true)
    private String tokenSegurancaProducao;
    @Column(name = "TOKEN_SEGURANCA_DEV", nullable = true)
    private String tokenSegurancaSandBox;

    public String getEmailPagSeguro() {
        return emailPagSeguro;
    }

    public void setEmailPagSeguro(String emailPagSeguro) {
        this.emailPagSeguro = emailPagSeguro;
    }

    public String getEmailPagSeguroPlain() {
        try {
            SimpleCipher crypto = new SimpleCipher();
            return CharSequenceUtils.isNotEmpty(getEmailPagSeguro())? crypto.decryptFromHexString(getEmailPagSeguro()) : "";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setEmailPagSeguroPlain(String emailPagSeguro) {
        try {
            SimpleCipher crypto = new SimpleCipher();
            setEmailPagSeguro(crypto.encryptToHexString(emailPagSeguro));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getTokenSegurancaProducao() {
        return tokenSegurancaProducao;
    }

    public void setTokenSegurancaProducao(String tokenSegurancaProducao) {
        this.tokenSegurancaProducao = tokenSegurancaProducao;
    }

    public String getTokenSegurancaSandBox() {
        return tokenSegurancaSandBox;
    }

    public void setTokenSegurancaSandBox(String tokenSegurancaSandBox) {
        this.tokenSegurancaSandBox = tokenSegurancaSandBox;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.emailPagSeguro);
        hash = 97 * hash + Objects.hashCode(this.tokenSegurancaProducao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PagSeguroConta other = (PagSeguroConta) obj;
        if (!Objects.equals(this.emailPagSeguro, other.emailPagSeguro)) {
            return false;
        }
        if (!Objects.equals(this.tokenSegurancaProducao, other.tokenSegurancaProducao)) {
            return false;
        }
        return true;
    }
}
