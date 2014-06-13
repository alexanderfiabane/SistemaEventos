/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.guest.validation;

import br.esp.sysevent.core.model.CasaEspirita;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.service.EnderecoService;
import br.esp.sysevent.core.service.InscricaoService;
import br.msf.commons.persistence.springframework.validation.AbstractValidator;
import br.msf.commons.util.PeriodUtils;
import br.msf.commons.util.CharSequenceUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Valida uma Inscricao.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Component
public class InscricaoValidator extends AbstractValidator<Inscricao> {

    protected final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");
    protected final Pattern NUMERO_PATTERN = Pattern.compile("[0-9]+");
    protected final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");
    protected final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Autowired
    protected InscricaoService inscricaoService;
    @Autowired
    protected EnderecoService enderecoService;

    /**
     * Valida o command inteiro.
     */
    @Override
    public void validateCommand(final Inscricao inscricao, final Errors errors) {
        validateEdicao(inscricao, errors);
        validateConfraternista(inscricao, errors);
        validateInscricao(inscricao, errors);
    }

    protected void validateEdicao(Inscricao inscricao, Errors errors) {
        final Edicao edicaoEvento = inscricao.getEdicaoEvento();
        if (edicaoEvento == null) {
            errors.rejectValue("edicaoEvento", "errors.required");
        } else {
            if (!PeriodUtils.isCurrent(edicaoEvento.getPeriodoInscricao(), true)) {
                errors.rejectValue("edicaoEvento", "errors.subscriptionPeriod.invalid");
            } else if (inscricao.getId() == null && !edicaoEvento.temVaga()) {
                errors.rejectValue("edicaoEvento", "errors.subscription.full");
            }
        }
    }

    protected void validateConfraternista(Inscricao inscricao, Errors errors) {
        final Confraternista confraternista = inscricao.getConfraternista();
        if (confraternista.getTipo() == null) {
            errors.rejectValue("confraternista.tipo", "errors.required");
        }
        if (CharSequenceUtils.isBlank(confraternista.getAtividadeCasaEspirita())) {
            errors.rejectValue("confraternista.atividadeCasaEspirita", "errors.required");
        }
        if (CharSequenceUtils.isBlank(confraternista.getNomeCracha())) {
            errors.rejectValue("confraternista.nomeCracha", "errors.required");
        }
        validatePessoa(confraternista.getPessoa(), errors);
        validateOficina(inscricao, errors);
        validateCasaEspirita(confraternista.getCasaEspirita(), errors);
    }

    protected void validatePessoa(Pessoa pessoa, Errors errors) {
        if (CharSequenceUtils.isBlank(pessoa.getNome())) {
            errors.rejectValue("confraternista.pessoa.nome", "errors.required");
        } else if (!NOME_PATTERN.matcher(pessoa.getNome()).matches()) {
            errors.rejectValue("confraternista.pessoa.nome", "errors.invalid");
        }
        if (pessoa.getSexo() == null) {
            errors.rejectValue("confraternista.pessoa.sexo", "errors.required");
        }
        if (pessoa.getDataNascimento() == null) {
            errors.rejectValue("confraternista.pessoa.dataNascimento", "errors.required");
        } 
//        if (pessoa.getDataNascimento() == null) {
//            errors.rejectValue("confraternista.pessoa.dataNascimento", "errors.required");
//        } else if (validaIdade(pessoa.getDataNascimento()) < 16) {
//            errors.rejectValue("confraternista.pessoa.dataNascimento", "errors.data.restriction");
//        }
        validateDocumentos(pessoa.getDocumentos(), errors);
        validateEndereco(pessoa.getEndereco(), errors, "confraternista.pessoa.endereco", true);
    }

    protected void validateOficina(Inscricao inscricao, Errors errors) {
        boolean temOficina = !inscricao.getEdicaoEvento().getOficinas().isEmpty();
        if(!temOficina) {
            return;
        }
        final Oficina oficina = inscricao.getConfraternista().getOficina();
        if (oficina == null) {
            errors.rejectValue("confraternista.oficina", "errors.required");
            return;
        }

        if(inscricao.getId() == null) { //nova inscricao
            if (!oficina.temVaga()) {
                errors.rejectValue("confraternista.oficina", "errors.workshop.full");
            }
        } else {
            final Inscricao inscricaoAtual = inscricaoService.findById(inscricao.getId());
            final Oficina oficinaAtual = inscricaoAtual.getConfraternista().getOficina();
            if (!oficina.getId().equals(oficinaAtual.getId())) {
                //trocou de oficina
                if (!oficina.temVaga()) {
                    errors.rejectValue("confraternista.oficina", "errors.workshop.full");
                }
            }
        }
    }

    protected void validateCasaEspirita(CasaEspirita casaEspirita, Errors errors) {
        if (CharSequenceUtils.isBlank(casaEspirita.getNome())) {
            errors.rejectValue("confraternista.casaEspirita.nome", "errors.required");
        } 
        validateEndereco(casaEspirita.getEndereco(), errors, "confraternista.casaEspirita.endereco", false);
    }

    protected void validateDocumentos(Documento documentos, Errors errors) {
        if (CharSequenceUtils.isBlank(documentos.getCpf()) && CharSequenceUtils.isBlank(documentos.getRg())) {
            errors.rejectValue("confraternista.pessoa.documentos.cpf", "errors.required.doc");
            errors.rejectValue("confraternista.pessoa.documentos.rg", "errors.required.doc");
        } else if (!CharSequenceUtils.isBlank(documentos.getCpf())) {
            if (!isValidCPF(documentos.getCpf())) {
                errors.rejectValue("confraternista.pessoa.documentos.cpf", "errors.invalid");
            }
        }
        if (!CharSequenceUtils.isBlank(documentos.getRg())) {
            if (!NUMERO_PATTERN.matcher(documentos.getRg()).matches()) {
                errors.rejectValue("confraternista.pessoa.documentos.rg", "errors.invalid");
            }
        }
    }

    protected void validateEndereco(Endereco endereco, Errors errors, String path, boolean validateContato) {
        if (endereco.getNumero() == null) {
            errors.rejectValue(path + ".numero", "errors.required");
        }
        if (endereco.getCidade() == null) {
            errors.rejectValue(path + ".cidade", "errors.required");
        }
        if (CharSequenceUtils.isBlank(endereco.getLogradouro())) {
            errors.rejectValue(path + ".logradouro", "errors.required");
        }
        if (CharSequenceUtils.isBlank(endereco.getBairro())) {
            errors.rejectValue(path + ".bairro", "errors.required");
        }
        if (CharSequenceUtils.isBlank(endereco.getCep())) {
            errors.rejectValue(path + ".cep", "errors.required");
        }
        if (validateContato) {
            if (CharSequenceUtils.isBlank(endereco.getEmail())) {
                errors.rejectValue(path + ".email", "errors.required");
            } else if (!EMAIL_PATTERN.matcher(endereco.getEmail()).matches()) {
                errors.rejectValue(path + ".email", "errors.invalid");
            }
            if (CharSequenceUtils.isBlank(endereco.getTelefone())) {
                errors.rejectValue(path + ".telefone", "errors.required");
            }
            if (CharSequenceUtils.isBlank(endereco.getTelefoneEvento())) {
                errors.rejectValue(path + ".telefoneEvento", "errors.required");
            }
        }
    }

    protected void validateInscricao(Inscricao inscricao, Errors errors) {
        final boolean isNova = inscricao.getId() == null;
        final Edicao edicao = inscricao.getEdicaoEvento();
        final Documento documentos = inscricao.getConfraternista().getPessoa().getDocumentos();
        final String email = inscricao.getConfraternista().getPessoa().getEndereco().getEmail();
        final String emailPath = "confraternista.pessoa.endereco.email";
        final Inscricao inscricaoDocumentos = inscricaoService.findByEdicaoDocumentos(edicao.getId(), documentos);

        if(isNova) {
            if (inscricaoDocumentos != null) {
                errors.rejectValue("id", "errors.alreadyExists");
            }
            if (!errors.hasFieldErrors(emailPath) && !enderecoService.findByProperty("email", email).isEmpty()) {
                errors.rejectValue(emailPath, "errors.alreadyExists");
            }
        } else {
            final Inscricao inscricaoAtual = inscricaoService.findById(inscricao.getId());
            if (inscricaoDocumentos != null && !inscricaoDocumentos.getId().equals(inscricao.getId())) {
                errors.rejectValue("id", "errors.alreadyExists");
            }
            final String emailAtual = inscricaoAtual.getConfraternista().getPessoa().getEndereco().getEmail();
            if(!errors.hasFieldErrors(emailPath)) {
                if(!email.equals(emailAtual) && !enderecoService.findByProperty("email", email).isEmpty()) {
                    errors.rejectValue(emailPath, "errors.alreadyExists");
                }
            }
        }
    }

    protected boolean isValidCPF(String cpf) {
        if (!CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }
        final String cpfNoMask = cpf.replace(".", "").replace("-", "");
        final String numero = cpfNoMask.substring(0, 9);
        int[] digitos = new int[11];
        int peso, soma, resto;

        //calculo do 1º digito
        soma = 0;
        peso = 10;
        for (int i = 0; i < 9; i++) {
            digitos[i] = Character.digit(numero.charAt(i), 10);
            soma += digitos[i] * peso--;
        }
        resto = soma % 11;
        resto = resto < 2 ? 0 : 11 - resto;
        digitos[9] = resto;

        //calculo do 2º digito
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * peso--;
        }
        resto = soma % 11;
        resto = resto < 2 ? 0 : 11 - resto;
        digitos[10] = resto;

        return cpfNoMask.substring(9).equals(String.valueOf(digitos[9]).concat(String.valueOf(digitos[10])));
    }

    //TODO: Novamente idade fixa para 16 anos.
    protected Integer validaIdade(Calendar dataNascimento){

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataEvento = null;
        try {
            dataEvento = df.parse("29/03/2013");
        } catch (ParseException ex) {
            Logger.getLogger(InscricaoValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dataEvento);

        int year1 = dataNascimento.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int month1 = dataNascimento.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        int day1 = dataNascimento.get(Calendar.DAY_OF_MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int idade = year2 - year1;
        if ((month2 < month1)
                || ((month2 == month1) && (day2 < day1))) {
            idade -= 1;
        }
        return idade;
    }
}
