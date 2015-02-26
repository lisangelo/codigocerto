/*
 * Codigo Certo
 * IModeloPesquisaCad.java
 * Criado em 27 de Junho de 2007, 10:35
 */

package br.com.codigocerto.controles.pesquisas;

import br.com.codigocerto.cadastros.PesquisarCadastro;
import br.com.codigocerto.controles.Registro;

/**
 * @author lis
 */
public interface IModeloPesquisaCad {
    
    /**
     * Informa o nome do cadastro pesquisado.
     * Este nome ira aparecer no label da pesquisa e no titulo do form
     * @return string com nome do cadastro
     */
    String getNomeCadastro();
    
    /**
     * Informa o campo descricao desta tabela no bd.
     * Este campo ira aparecer formatado juntamente com o id
     * @return string com nome campo descricao
     */
    String getCampoDescricao();

    /**
     * Informa o indice do campo id nas colunas da pesquisa.
     * Este campo ira aparecer formatado juntamente com a descricao
     * @return int com indice da coluna id
     */
    int getCampoId();

    /**
     * Informa o indice da primeira coluna a ser pesquisada.
     * Esta coluna ira aparecer selecionada para pesquisa no form
     * @return int com indice da coluna 
     */
    int getCampoInicial();

    /**
     * Retorna registro que controla a pesquisa
     * @return registro
     */
    Registro getRegistro();

    /**
     * Retorna pesquisa criada de acordo com o cadastro especificado
     * @return pesquisa
     */
    PesquisarCadastro getPesquisarCadastro();
    
    /**
     * Informa o titulo de cada campo a ser apresentado na forma de coluna
     * @return string[] com titulos das colunas
     */
    String[] getTitulosColunas();
    
    /**
     * Informa a largura de cada coluna a ser apresentada
     * @return int[] com larguras das colunas
     */
    int[] getLarguraColunas();
    
    /**
     * Informa o id do cadastro base da pesquisa
     * @return int id do cadastro
     */
    int getCadastroId();

} ///~
