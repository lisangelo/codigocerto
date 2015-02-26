/*
 * Codigo Certo
 * Zeros.java
 * @author lis
 * Criado em 10 de Janeiro de 2008, 12:20
 */

package br.com.codigocerto.conversores;

public class Zeros {
    
    /**
     * Formata string com zeros a esquerda de acordo com tamanho especificado
     * @param int tamanho final da string a ser formatada
     * @return string formatada com zeros a esquerda
     */
    public static String alinharEsquerda(String numero, int tamanho) {
        StringBuilder zeros = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            zeros.append("0");
        }
        StringBuilder numeroFormatado = new StringBuilder(zeros + numero);
        
        return numeroFormatado.substring(numeroFormatado.length() - tamanho);
    }
    
    
} ///~
