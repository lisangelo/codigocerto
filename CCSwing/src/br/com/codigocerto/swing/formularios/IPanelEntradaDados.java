/*
 * Codigo Certo
 * IPanelEntradaDados.java
 * Criado em 27 de Julho de 2007, 10:29
 */

package br.com.codigocerto.swing.formularios;

import java.awt.event.ActionListener;

/**
 * @author lis
 */
public interface IPanelEntradaDados {
   
    /**
     *Configura acao para botao fechar
     *@param acao para fechar formulario
     */
    public void setAcaoFechar(ActionListener acao);

    /**
     *Configura acao para botao proximo
     *@param acao para proximo registro
     */
    public void setAcaoProximo(ActionListener acao);

    /**
     *Configura acao para botao anterior
     *@param acao para registro anterior
     */
    public void setAcaoAnterior(ActionListener acao);
    
    /**
     *Configura acao para botao primeiro
     *@param acao para primeiro registro
     */
    public void setAcaoPrimeiro(ActionListener acao);
    
    /**
     *Configura acao para botao ultimo
     *@param acao para ultimo registro
     */
    public void setAcaoUltimo(ActionListener acao);

    /**
     *Configura acao para botao novo
     *@param acao para novo registro
     */
    public void setAcaoNovo(ActionListener acao);

    /**
     *Configura acao para botao excluir
     *@param acao para excluir registro
     */
    public void setAcaoExcluir(ActionListener acao);

    /**
     *Configura acao para botao pesquisar
     *@param acao para pesquisar cadastro
     */
    public void setAcaoPesquisar(ActionListener acao);
    
    /**
     *Configura estado de alteracao
     *@param boolean com estado
     */
    public void setAlterado(boolean alterado);
    
    /**
     *Informa estado de alteracao de dados no painel
     *@return boolean com estado
     */
    public boolean getAlterado();

    /**
     *Configura estado de Novo 
     *@param boolean com estado
     */
    public void setNovo(boolean novo);
    
    /**
     *Informa estado de alteracao de dados no painel
     *@return boolean com estado
     */
    public boolean getNovo();

    /**
     * Configura dica em determinado componente
     *@param nomeComponente string com nome do componente
     *@param dica string com dica
     */
    public void setDica(String nomeComponente, String dica);
    
    /**
     *Informa valor setado em determinado componente
     *@return Object valor inserido
     */
    public Object getValor(String nomeComponente);

    /**
     *Focar componente 
     *@param String nome do campo associado ao componente
     */
    public void focarCampo(String nomeCampo);
    
    /**
     *Elimina conteudo dos campos
     */
    public void limparDados();
    
    /**
     * Exibe conteudo dos campos a partir do modelo atual
     */
    public void exibirDados();
} ///~
