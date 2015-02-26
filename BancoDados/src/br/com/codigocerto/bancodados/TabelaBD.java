/*
 * TabelaBD.java
 *
 * Criado em 26 de Agosto de 2006, 14:02
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sinapse
 */
public class TabelaBD {
    
    private ResultSet registros;
    private int posicao = 0; // ponteiro que guarda o row atual
    
    /** Creates a new instance of TabelaBD */
    public TabelaBD() {
    }
    
    /**
     * Associa o ResultSet
     * @param ResultSet a ser associado
     */
    public void setResultadoQuery(ResultSet r) {
        registros = r;
    }

    /**
     * Fecha a tabela
     */
    public void fechar() {
        if (registros != null) {
            try {
                registros.getStatement().close();
            } catch (SQLException ex) {
                Logger.getLogger(TabelaBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Retorna o total existente de registros
     * @return int numero total de registros
     */
    public int getTotalRegistros() {
        
        int numero = 0;
        if (registros != null)
        {
            try {
                registros.last();
                numero = registros.getRow();
                if (posicao == 0) {
                    registros.first();
                }
                else {
                    registros.absolute(posicao);
                }
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return numero;
    }    
    
    /**
     * Posiciona a tabela no primeiro registro
     * @return ok se registro foi posicionado com sucesso
     */
    public boolean primeiro() {
        boolean ok = false;
        try {
            ok = registros.first();
            if (ok) {
                posicao = registros.getRow();
            }
            else {
                posicao = 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }

    /**
     * Posiciona a tabela no último registro
     * @return ok se registro foi posicionado com sucesso
     */
    public boolean ultimo() {
        boolean ok = false;
        try {
            ok = registros.last();
            if (ok) {
                posicao = registros.getRow();
            }
            else {
                posicao = 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }

    /**
     * Posiciona a tabela no próximo registro
     * @return ok se registro foi posicionado com sucesso
     */
    public boolean proximo() {
        boolean ok = false;
        try {
            ok = registros.next();
            if (ok) {
                posicao = registros.getRow();
            }
            else {
                posicao = 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }
    
    /**
     * Posiciona a tabela no registro anterior
     * @return ok se registro foi posicionado com sucesso
     */
    public boolean anterior() {
        boolean ok = false;
        try {
            ok = registros.previous();
            if (ok) {
                posicao = registros.getRow();
            }
            else {
                posicao = 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ok;
    }

    /**
     * Retorna conteudo da coluna do registro atual
     * @param int posicao da coluna no registro
     * @return object conteudo do campo
     */
    public Object getColuna(int col) {

        Object retorno = null;
        
        try {
            retorno = registros.getObject(++col);
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return retorno;
    }

    /**
     * Retorna conteudo da coluna do registro atual
     * @param String nome da coluna
     * @return object conteudo do campo
     */
    public Object getColuna(String col) {

        Object retorno = null;
        
        try {
            retorno = registros.getObject(col);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return retorno;
    }
    
    /**
     *Posiciona tabela em determinado registro
     *@param int numero do registro
     *@return boolean verdadeiro para registro posicionado com sucesso
     */
    public boolean irPara(int numeroRegistro) {
        boolean ok = false;

        if (numeroRegistro >= 0 && numeroRegistro < getTotalRegistros()) {
            try {
                registros.absolute(numeroRegistro + 1);
                posicao = numeroRegistro;
                ok = true;
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return ok;
    }

}
