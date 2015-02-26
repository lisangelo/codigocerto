/*
 *
 * Codigo Certo
 *
 * ConversorTipos.java
 *
 * Criado em 29 de Março de 2007, 09:00
 *
 */

package br.com.codigocerto.conversores;

import java.math.BigDecimal;

/**
 *
 * @author lis
 */
public class ConversorTipos {
    
    final String PONTO_DECIMAL = ".";
    final String SIMBOLO_DECIMAL = ",";

    private String strValorBase;
    private Long longValorBase;
    
    /** Creates a new instance of ConversorTipos */
    public ConversorTipos() {
    }
    
    /*
     *Informa um valor base a ser convertido
     *@param String numerica
     */
    public void setValorBase(String numero) {
        strValorBase = numero;
    }

    /*
     *Informa um valor base a ser convertido
     *@param long
     */
    public void setValorBase(long numero) {
        longValorBase = numero;
    }
    
    
    /*
     *Retorna um long a partir do valor informado
     *@return long
     */
    public long getLong() {
        long valor;
        
        try {
            Long valorLong = new Long(strValorBase);
            valor = valorLong.longValue();
        } catch (Exception e) {
            valor = 0; //deu problema...
        }
        
        return valor;
    }

    /*
     *Retorna uma string a partir do valor informado
     *@return string
     */
    public String getString() {
        String valor;
        
        try {
            valor = longValorBase.toString();
        } catch (Exception e) {
            valor = ""; //deu problema...
        }
        
        return valor;
    }
    
    /*
     *Retorna um integer a partir do valor informado
     *@return int
     */
    public int getInteger() {
        int valor;
        
        try {
            Integer valorInt = new Integer(strValorBase);
            valor = valorInt.intValue();
        } catch (Exception e) {
            valor = 0; //deu problema...
        }
        
        return valor;
    }
    
    /*
     *Retorna BigDecimal a partir do valor informado
     *@return BigDecimal
     */
    public BigDecimal getMonetario() {
        BigDecimal valor;
        String valorVerificado;
        
        try {
            if (strValorBase.indexOf(SIMBOLO_DECIMAL) > 0) {
                valorVerificado = verificaDecimal(strValorBase);
            } else {
                valorVerificado = strValorBase;
            }
            valor = new BigDecimal(valorVerificado);
        } catch (Exception e) {
            valor = new BigDecimal(0); //deu problema...
        }
        
        return valor;
    }
    
    private String verificaDecimal(String valor) {

        StringBuilder valorFormatado = new StringBuilder(valor);
        
        int pos = valor.indexOf(PONTO_DECIMAL);
        while (pos > 0) {
            valorFormatado.deleteCharAt(pos);
            pos = valor.indexOf(PONTO_DECIMAL);
        }
        
        pos = valor.indexOf(SIMBOLO_DECIMAL);
        if (pos > 0) {
            valorFormatado.setCharAt(pos, PONTO_DECIMAL.charAt(0));
        }
        
        return valorFormatado.toString();
    }
}
