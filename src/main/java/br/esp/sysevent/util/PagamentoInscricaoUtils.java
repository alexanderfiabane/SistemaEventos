/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.util;

import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.PaymentMethodType;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Inscricao;
import com.javaleks.commons.text.EnhancedStringBuilder;
import java.math.BigDecimal;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
public abstract class PagamentoInscricaoUtils {

    public static String montaDescricaoPagamento(Transaction transaction, boolean qtip){
        EnhancedStringBuilder descricaoPagamento = new EnhancedStringBuilder();
        descricaoPagamento.setLineBreakMode(EnhancedStringBuilder.LineBreakMode.HTML);
        if (qtip){
            descricaoPagamento.append("<div class='mini-font-size'>");
            descricaoPagamento.append("<strong>Situação do pagmento</strong>: ")
                    .appendln(transaction.getStatus().getDescription());
            descricaoPagamento.append("<strong>Meio de pagmento</strong>: ")
                    .append(transaction.getPaymentMethod().getType().getType())
                    .append("( ").append(transaction.getPaymentMethod().getCode().getDescription());
            if (transaction.getPaymentMethod().getType().equals(PaymentMethodType.CREDIT_CARD)){
                descricaoPagamento.append(" ").append(Integer.toString(transaction.getInstallmentCount())).appendln("x )");
            }else{
                descricaoPagamento.appendln(" )");
            }
            descricaoPagamento.append("<strong>Valor pago em taxas</strong>: ")
                    .append(" R$ ").appendln(transaction.getFeeAmount().toString());
            descricaoPagamento.append("<strong>Valor pago</strong>: ")
                    .append(" R$ ").append(transaction.getGrossAmount().toString());
            descricaoPagamento.append("</div");
        }else{
            descricaoPagamento.append("<label class='label'>Situação do pagmento</label>: ")
                    .appendln(transaction.getStatus().getDescription());
            descricaoPagamento.append("<label class='label'>Meio de pagmento</label>: ")
                    .append(transaction.getPaymentMethod().getType().getType())
                    .append("( ").append(transaction.getPaymentMethod().getCode().getDescription());
            if (transaction.getPaymentMethod().getType().equals(PaymentMethodType.CREDIT_CARD)){
                descricaoPagamento.append(" ").append(Integer.toString(transaction.getInstallmentCount())).appendln("x )");
            }else{
                descricaoPagamento.appendln(" )");
            }
            descricaoPagamento.append("<label class='label'>Valor pago</label>: ")
                    .append(" R$ ").append(transaction.getGrossAmount().toString());
        }
        return descricaoPagamento.toString();
    }

    public static boolean transacaoStatusPaga(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatus.PAID);
    }

    public static Item montaInscricaoItemPagSeguro(Inscricao inscricao) {
        Item item = new Item(
                Long.toString(inscricao.getId()),
                "Inscrição" + " - " + inscricao.getEdicaoEvento().getEvento().getNome(),
                1,
                inscricao.getEdicaoEvento().getValorInscricao(),
                new Long(1),
                new BigDecimal("0.00"));
        return item;
    }

    public static Item montaCamisetaItemPagSeguro(CamisetaConfraternista camiseta, BigDecimal valorCamiseta) {
        Item item = new Item(
                Long.toString(camiseta.getId()),
                camiseta.getTipoCamiseta().getDescricao() + " - " + camiseta.getCorCamiseta().getDescricao() + " - " + camiseta.getTamanhoCamiseta().getDescricao(),
                camiseta.getQuantidadeCamiseta(),
                valorCamiseta,
                new Long(150),
                new BigDecimal("0.00")
        );
        return item;
    }

    public static Address montaAddressPagSeguro(Inscricao inscricao) {
        Endereco endereco = inscricao.getConfraternista().getPessoa().getEndereco();
        Cidade cidade = endereco.getCidade();
        Address address = new Address("BRA", // País
                cidade.getEstado().getSigla(), // UF
                cidade.getNome(), // Cidade
                endereco.getBairro(), // Bairro
                endereco.getCep(), // CEP
                endereco.getLogradouro(), // Logradouro
                endereco.getNumero(), // Número
                endereco.getComplemento() // Complemento
        );
        return address;
    }

}
