/*
 * ControleExcecoes.java
 *
 * Criado em 24 de Maio de 2007, 15:22
 *
 */

package br.com.codigocerto.swing;

/**
 *
 * @author lis
 */
public class ControleExcecoes {
    
    /** Cria uma nova instância de ControleExcecoes */
    public static void capturarExcecao(Exception e) {
        if (e != null) {
            System.out.println(e.getMessage());
        }
    }
    
} ///:~
