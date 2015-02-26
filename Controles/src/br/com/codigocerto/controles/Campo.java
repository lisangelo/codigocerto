/*
 * Campo.java
 *
 * Created on 26 de Março de 2007, 16:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.codigocerto.controles;

/**
 *
 * @author lis
 */
public class Campo {
    
    private String campoIU;       // nome do objeto na interface com o usuario
    private String campoModelo;   // nome do objeto no modelo de classe  
    private String campoBanco;    // nome do campo na tabela de dados
    
    /** Creates a new instance of Campo */
    public Campo() {
    }

    /*
     *Ajusta campo responsavel pela interface com usuario
     *@param string com nome do controle
     */
    public void setIU(String iu) {
        campoIU = iu;
    }
    
    /*
     *Informa nome do campo responsavel pela interface com usuario
     *@return string com nome do controle
     */
    public String getIU() {
        return campoIU;
    }
    
    /*
     *Ajusta campo com nome da propriedade da classe
     *@param string com nome da propriedade
     */
    public void setModelo(String modelo) {
        campoModelo = modelo;
    }

    /*
     *Informa nome da propriedade da classe
     *@return string com nome da propriedade
     */
    public String getModelo() {
        return campoModelo;
    }

    /*
     *Ajusta nome do campo na base de dados
     *@param string com nome do campo
     */
    public void setBanco(String banco) {
        campoBanco = banco;
    }

    /*
     *Informa nome do campo na base de dados
     *@return string com nome do campo
     */
    public String getBanco() {
        return campoBanco;
    }

}
