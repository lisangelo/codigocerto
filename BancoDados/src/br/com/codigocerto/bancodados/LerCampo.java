/*
 * Codigo Certo
 * LerCampo.java
 * Criado em 21 de Junho de 2007, 17:02
 */

package br.com.codigocerto.bancodados;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lis
 */
public class LerCampo {

    public static boolean getBoolean(Object campo) {
        boolean resultado = false;
        
        try {
            if (campo != null) {
                resultado = (Boolean) campo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public static String getString(Object campo) {
        StringBuilder resultado = new StringBuilder("");
        
        try {
            String c = (String) campo;
            if (c != null) {
                resultado.append(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado.toString();
    }

    public static long getLong(Object campo) {
        long conteudo = 0L;
        
        try {
            Long c = (Long) campo;
            if (c != null) {
                conteudo = c.longValue();
            }
        } catch (Exception e) {
            conteudo = -1L;
            e.printStackTrace();
        }
        
        return conteudo;
    }
    
    public static int getInt(Object campo) {
        int conteudo = 0;
        
        try {
            Integer c = (Integer) campo;
            if (c != null) {
                conteudo = c.intValue();
            }
        } catch (Exception e) {
            conteudo = -1;
            e.printStackTrace();
        }
        
        return conteudo;
    }
    
    public static BigDecimal getDecimal(Object campo) {
        BigDecimal resultado = null;

        try {
            resultado = (BigDecimal) campo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public static Date getData(Object campo) {
        Date resultado = null;

        try {
            resultado = (Date) campo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

} ///:~
