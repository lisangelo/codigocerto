/*
 * Codigo Certo
 * FormatoNumerico.java
 * Criado em 23 de Agosto de 2007, 11:04
 */

package br.com.codigocerto.conversores;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author lis
 */
public class FormatoNumerico {
    
    private static final String PONTO_DECIMAL = ".";
    private static final String SIMBOLO_DECIMAL = ",";

    public static String aplicar(String numero, String mascara) {
        String numeroFormatado = null;
        String numeroFinal = null;
        if (numero != null) {
            String numeroComPonto = verificarVirgula(numero);

            try {
                numeroFormatado = getFormat().format(new BigDecimal(numeroComPonto));
                numeroFinal = verificarDecimais(numeroFormatado, mascara);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            numeroFinal = "";
        }
        
        return numeroFinal;
    }
    
    public static String aplicarZeros(String numero, String mascara) {
        String numeroFormatado = null;
        StringBuilder numeroFinal = new StringBuilder("");
        if (numero != null) {
            String numeroComPonto = verificarVirgula(numero);

            try {
                numeroFormatado = getFormat().format(new BigDecimal(numeroComPonto));
                numeroFinal.append(verificarDecimais(numeroFormatado, mascara));
                while (numeroFinal.length() < mascara.length()) {
                    numeroFinal.insert(0, "0");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return numeroFinal.toString();
    }

    public static String aplicar(BigDecimal numero, String mascara) {
        String numeroFormatado = null;
        String numeroFinal = null;
        if (numero != null) {

            try {
                numeroFormatado = getFormat().format(numero);
                numeroFinal = verificarDecimais(numeroFormatado, mascara);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            numeroFinal = "";
        }

        return numeroFinal;
    }

    private static String verificarDecimais(String numero, String mascara) {
        
        StringBuilder numeroFormatado = new StringBuilder(numero);
        int posPontoMascara = mascara.indexOf(PONTO_DECIMAL);
        if (posPontoMascara != -1) { // mascara possue ponto decimal, e casas apos a virgula
            int posPontoNumero = numeroFormatado.indexOf(SIMBOLO_DECIMAL);
            if (posPontoNumero == -1) { //  numero formatado nao possue virgula
                numeroFormatado.append(SIMBOLO_DECIMAL);
                posPontoNumero = numeroFormatado.length() - 1;
            }
            int numCasas = mascara.substring(posPontoMascara + 1).length();
            int numCasasNumero = numeroFormatado.substring(posPontoNumero + 1).length();
            
            for (int i = numCasasNumero; i < numCasas; i++) {
                numeroFormatado.append('0');
            }
        }
        
        return numeroFormatado.toString();
    }    
    
    private static DecimalFormat getFormat() {
        
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("0.##########");
        
        return format;
    }
    
    private static String verificarVirgula(String numero) {
        StringBuilder numeroAcertado = new StringBuilder(numero);
        int posVirgula = numeroAcertado.indexOf(SIMBOLO_DECIMAL);
        if (posVirgula > 0) {
            numeroAcertado.setCharAt(posVirgula, PONTO_DECIMAL.toCharArray()[0]);
        }
        
        return numeroAcertado.toString();
    }
    
    private static String verificarEspacos(String numero, String mascara) {
        StringBuilder numeroAcertado = new StringBuilder();
        
        for(int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) == ' ') {
                numeroAcertado.append('0');
            } else {
                numeroAcertado.append(numero.charAt(i));
            }
        }
        while (mascara.length() > numeroAcertado.length()) {
            numeroAcertado.insert(0, '0');
        }
        
        return numeroAcertado.toString();
    }

    
} ///~
