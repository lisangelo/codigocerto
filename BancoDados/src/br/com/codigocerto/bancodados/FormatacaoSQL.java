/*
 *
 * Codigo Certo
 *
 * FormatacaoSQL.java
 *
 * Criado em 3 de Abril de 2007, 13:31
 *
 */

package br.com.codigocerto.bancodados;

import br.com.codigocerto.conversores.ConversorTipos;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lis
 */

public class FormatacaoSQL {

    /**
     * Retorna texto formatado para insercao em query
     * @param pDado string com dados
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(String pDado, TiposDadosQuery pTipo) {
        String dadoFormatado;
        if (pDado != null) {
            switch (pTipo)
            {
                case TEXTO:
                    dadoFormatado = "'" + verificaAspaSimples(pDado) + "'";
                    break;
                case DATA:
                    dadoFormatado = "'" + pDado + "'";
                    break;
                default:
                    dadoFormatado = pDado;    
                    break;
            }
        }
        else {
            dadoFormatado = "null";
        }
        
        return dadoFormatado;
        
    }

    /**
     * Retorna texto formatado para insercao em query
     * @param pDado boolean
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(boolean pDado, TiposDadosQuery pTipo) {
        StringBuilder dadoFormatado = new StringBuilder("");
        
        if (pDado) {
            dadoFormatado.append("true");
        }
        else {
            dadoFormatado.append("false");
        }
        
        return dadoFormatado.toString();
        
    }

    /**
     * Retorna texto formatado para insercao em query
     * @param pDado BigDecimal com dados
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(BigDecimal pDado, TiposDadosQuery pTipo) {
        StringBuilder dadoFormatado = new StringBuilder("");
        
        if (pDado == null) {
            dadoFormatado.append("null");
        }
        else {
            dadoFormatado.append(pDado.toPlainString());
        }
        
        return dadoFormatado.toString();
        
    }
    
    /**
     * Retorna texto formatado para insercao em query
     * @param pDado int com dados
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(int pDado, TiposDadosQuery pTipo) {
        StringBuilder dadoFormatado = new StringBuilder("");
        
        dadoFormatado.append(Integer.toString(pDado).toString());
        
        return dadoFormatado.toString();
        
    }

    /**
     * Retorna texto formatado para insercao em query
     * @param pDado long com dados
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(long pDado, TiposDadosQuery pTipo) {
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(pDado);
        return conv.getString();
    }

    /**
     * Retorna texto formatado para insercao em query
     * @param pDado Date com dados
     * @param ptipo indicador do formato a ser utilizado
     * @return string com texto formatado
     */
    public static String getDadoFormatado(Date pDado, TiposDadosQuery pTipo) {
        StringBuilder dadoFormatado = new StringBuilder("");
        
        if (pDado == null) {
            dadoFormatado.append("null");
        }
        else {
            dadoFormatado.append("'");
            dadoFormatado.append(new SimpleDateFormat("yyyy-MM-dd").format(pDado));
            dadoFormatado.append("'");
        }
        
        return dadoFormatado.toString();
        
    }

    private static String verificaAspaSimples(String pDado) {
        
        return pDado.replaceAll("'", "''");    
    }
    
}
