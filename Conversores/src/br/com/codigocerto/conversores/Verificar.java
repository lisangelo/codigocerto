/*
 * Codigo Certo
 * Verificar.java
 * Criado em 2 de Julho de 2007, 11:44
 */

package br.com.codigocerto.conversores;

import java.util.Date;

/**
 * @author lis
 */
public class Verificar {

    /**
     * Informa se string eh nula ou nao possui conteudo
     * @param texto com string a ser analisada
     * @return boolean para string vazia
     */
    public static boolean isNuloOuVazio(String texto) {
        boolean vazio = false;
        
        if (texto == null) {
            vazio = true;
        }
        else {
            if (texto.length() == 0 || texto.equals("")) {
                vazio = true;
            }
        }
        
        return vazio;
    }
    
    /**
     * Informa se data eh nula ou nao possui conteudo
     * @param data com date a ser analisada
     * @return boolean para data vazia
     */
    public static boolean isNuloOuVazio(Date data) {
        boolean vazio = false;
        
        if (data == null) {
            vazio = true;
        }
        
        return vazio;
    }
} ///~
