/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.util;

import br.esp.sysevent.core.model.ImagemArquivo;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
public class ImagemArquivoPropertyeditor extends PropertyEditorSupport{

    @Override
    public String getAsText() {
        return ((ImagemArquivo) getValue()).getNome();
    }

    @Override
    public void setAsText(String string) {
        if ("nao enviar".equals(string)) {
            super.setValue(null);
        }
    }

    @Override
    public void setValue(final Object object) {
        if (object instanceof ImagemArquivo) {
            super.setValue(object);
            return;
        }
        MultipartFile file = (MultipartFile) object;
        if (file.getSize() > 10240 * 1024) {
            super.setValue(new ImagemArquivo(file.getOriginalFilename(), null));
            return;
        }
        try {
            super.setValue(new ImagemArquivo(file.getOriginalFilename(), file.getBytes()));
        } catch (IOException e) {
        }
    }

}
