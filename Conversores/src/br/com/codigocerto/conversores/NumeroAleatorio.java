/*
 * Codigo Certo
 * Zeros.java
 * @author lis
 * Criado em 10 de Janeiro de 2008, 12:20
 */

package br.com.codigocerto.conversores;

public class NumeroAleatorio {
    
    /**
     * Cria um numero aleatorio de n digitos
     */
    public static String gerar(int tamanho) {

        long numero = (long) (Math.random() * 100000000);
        StringBuilder zeros = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            zeros.append("0");
        }
        StringBuilder numeroFormatado = new StringBuilder(zeros + String.valueOf(numero));
        
        return numeroFormatado.substring(numeroFormatado.length() - tamanho);
    }
    
    
} ///:~
