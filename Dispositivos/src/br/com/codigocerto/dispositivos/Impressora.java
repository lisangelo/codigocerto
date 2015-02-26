/*
 * Codigo Certo
 * Impressora.java
 * Criado em 16 de Outubro de 2007, 18:31
 */

package br.com.codigocerto.dispositivos;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author lis
 */
public class Impressora {

    public interface Epson {
        char EJECT = 12,
             LF = 10,
             CR = 13,
             EXPANDIDO = 14;
    }

    public interface Saidas {
        String LPT1 = "LPT1",
               LPT2 = "LPT2", 
               LPT3 = "LPT3", 
               LPT4 = "LPT4", 
               LPT5 = "LPT5", 
               LPT6 = "LPT6", 
               LPT7 = "LPT7", 
               LPT8 = "LPT8", 
               LPT9 = "LPT9", 
               COM1 = "COM1", 
               COM2 = "COM2", 
               COM3 = "COM3", 
               COM4 = "COM4", 
               COM5 = "COM5", 
               COM6 = "COM6", 
               COM7 = "COM7", 
               COM8 = "COM8", 
               COM9 = "COM9";
    }
    
    private String _saida = null;
    
    /** Criar nova instancia de Impressora */
    public Impressora() {
    }
    
    /**
     * Configura o endereco da porta fisica do sistema a ser utilizada
     * @param porta (No windows lpt1, com1, no Linux /dev/...
     */
    public void setPorta(String porta) {
        _saida = porta;
    }
    
    /**
     * Envia uma string para a impressora selecionada
     * @param texto a ser impresso
     */
    public void imprimirTexto(final String texto) {
        new Thread() {
            @Override
            public void run() {
                try {
                    FileOutputStream outputStream = new FileOutputStream(_saida);
                    PrintStream printStram = new PrintStream(outputStream);
                    printStram.print(texto);
                    outputStream.close();
                }
                catch(Exception e) {
                }
            }
        }.start();
    } 
    
} ///~
