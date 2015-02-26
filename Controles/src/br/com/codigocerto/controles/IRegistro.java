/*
 * IRegistro.java
 *
 * Created on 26 de Março de 2007, 14:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.codigocerto.controles;

/**
 *
 * @author lis
 */
public interface IRegistro {
    
    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    boolean setDadosInsercao(Object dados);

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    boolean setDadosAlteracao(Object dados);

    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    Object getDados(long id);
    
    /*
     *Obtem tabela da base de dados
     *@return String com nome da tabela na base de dados
     */ 
    String getTabela();
    
    /*
     *Obtem campo id da tabela no bd
     *@return String com nome do campo id
     */ 
    String getCampoId();

    /*
     *Buscar posicao do primeiro registro
     *@return id para primeiro registro encontrado
     */
    long buscarPrimeiro();
    
    /*
     *Buscar posicao do ultimo registro
     *@return id para ultimo registro encontrado
     */
    long buscarUltimo();
    
    /*
     *Buscar posicao do proximo registro
     *@param Id do registro referencia
     *@return id para proximo registro encontrado
     */
    long buscarProximo(long id);
    
    /*
     *Buscar posicao do registro anterior
     *@param Id do registro referencia
     *@return id para registro anterior encontrado
     */
    long buscarAnterior(long id);
    
    /*
     *Excluir registro da base de dados
     *@param Id do registro a ser excluido
     *@return verdadeiro para exclusao bem sucedida
     */
    boolean excluir(long id);
    
    /**
     * Buscar registro na tabela pesquisando determinado campo
     * @param campo no bd a ser pesquisado
     * @param valor a ser pesquisado
     * @param string com nome campo usado para descricao
     * @return array de objects com id e campo descricao
     */
    Object[][] procurarCampo(String nomeCampo, String chave, String campoDescricao);
    
    
}

