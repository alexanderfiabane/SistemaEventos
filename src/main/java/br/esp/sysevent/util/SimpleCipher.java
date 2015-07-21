/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.util;

import com.javaleks.commons.text.HexUtils;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SimpleCipher {
    private static final String DEFAULT_TRANSF = "AES";
    private static final Key DEFAULT_KEY = new SecretKeySpec("gmg68#74g7!j$kgj".getBytes(), "AES");
    private final Key key;
    private Cipher cipher;
    /**
     * Cria um Cipher que utilizará o algoritmo {@link DEFAULT_TRANSF} ("AES") e a
     * {@link DEFAULT_KEY} por padrão.
     */
    public SimpleCipher() throws Exception {
        this(DEFAULT_KEY, DEFAULT_TRANSF);
    }
    /**
     * Cria um Cipher que utilizará o algoritmo {@link DEFAULT_TRANSF} ("AES") e a
     * chave AES dada.
     */
    public SimpleCipher(Key aesKey) throws Exception {
        this(aesKey, DEFAULT_TRANSF);
    }
    /**
     * Cria um Cipher que utilizará o algoritmo e a chave informados.
     */
    public SimpleCipher(Key key, String transformation) throws Exception {
        this.cipher = Cipher.getInstance(transformation);
        //this.coder = new Base64(32, linebreak, true);
        this.key = key;
    }
    /**
     * Encripta o conteúdo, retornando um array de bytes que representa
     * esse conteúdo codificado.
     */
    public byte[] encrypt(String conteudo) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(conteudo.getBytes());
    }
    /**
     * Encripta o conteúdo, retornando uma String que representa
     * esse conteúdo codificado em formato hexadecimal.
     */
    public String encryptToHexString(String conteudo) throws Exception {
        return HexUtils.toHexString(encrypt(conteudo));
    }
    /**
     * Decripta um array de bytes gerado por {@link #encrypt(String)}.
     */
    public String decrypt(byte[] conteudoEncriptado) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(conteudoEncriptado);
        return new String(decrypted);
    }
    /**
     * Decripta uma String hexa gerada por {@link #encryptToHexString(String)}.
     */
    public String decryptFromHexString(String conteudoEncriptadoHex) throws Exception {
        return decrypt(HexUtils.toBytes(conteudoEncriptadoHex));
    }
}
