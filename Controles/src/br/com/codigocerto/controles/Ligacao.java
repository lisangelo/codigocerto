/*
 * Ligacao.java
 *
 * Created on 26 de Março de 2007, 13:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.codigocerto.controles;

/**
 *
 * @author lis
 */
public class Ligacao {
    
    private String tabelaDados;
    private String campoId;
    
    /** Creates a new instance of Ligacao */
    public Ligacao() {
    }

    /*
     *Ajusta nome da tabela da base de dados a ser relacionada com objeto
     *@param string com nome da tabela
     */
    public void setTabela(String tabela) {
        tabelaDados = tabela;
    }
    
    /*
     *Informa nome da tabela relacionada com objeto
     *@return string com nome da tabela
     */
    public String getTabela() {
        return tabelaDados;
    }
    
    /*
     *Ajusta nome do campo de Id da tabela
     *@param string com nome do campo
     */
    public void setCampoId(String campo) {
        campoId = campo;
    }
    
    /*
     *Informa nome do campo Id da tabela relacionada com objeto
     *@return string com nome do campo Id
     */
    public String getCampoId() {
        
        return campoId;
    }
    
}

