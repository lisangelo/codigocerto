/*
Copyright (c) 2008, Codigo Certo Sistemas www.codigocerto.com.br
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following 
conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, 
       this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of the Codigo Certo nor the names of its contributors may be used to endorse or promote products 
       derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import java.util.Date;

/**
 * @author lis
 */
public abstract class RegistroMovimentoChave {
    
    protected Ligacao ligacao;
    private String _campoChave;
    private TiposDadosQuery _tipoDadoCampoChave;

    public RegistroMovimentoChave() {
        estabelecerLigacao();
    }
    
    /**
     * Configura o campo chave
     * @param campo - nome do campo
     * @param tipoDado - tipo de dado sql do campo
     */
    public void setCampoChave(String campo, TiposDadosQuery tipoDado) {
        _campoChave = campo;
        _tipoDadoCampoChave = tipoDado;
    }
    
    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    abstract public boolean setDadosInsercao(Object dados);

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    abstract public boolean setDadosAlteracao(Object dados);

    /*
     *Obtem dados da base
     *@param object com chave do modelo
     *@return objeto com dados retornados da base
     */ 
    abstract public Object getDados(Object chave);
    
    /*
     *Metodo padrao para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    abstract protected void estabelecerLigacao();
    
    /*
     *Buscar posicao do primeiro registro
     *@return chave para primeiro registro encontrado
     */
    public Object buscarPrimeiro() {
        
        Object primeiro = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(_campoChave, "chave", "min");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            primeiro = reg.getColuna("chave");
        }
        
        return primeiro;
    }
    
    /*
     *Buscar posicao do ultimo registro
     *@return chave para ultimo registro encontrado
     */
    public Object buscarUltimo() {
        Object ultimo = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(_campoChave, "chave", "max");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna("chave") != null) {
            ultimo = reg.getColuna("chave");
        }
        
        return ultimo;
    }

    /*
     *Buscar posicao do proximo registro
     *@param chave do registro referencia
     *@return chave para proximo registro encontrado
     */
    public Object buscarProximo(Object chave) {
        return buscarPosicao(chave, " > ", "min");
    }
    
    /*
     *Buscar posicao do registro anterior
     *@param chave do registro referencia
     *@return chave para registro anterior encontrado
     */
    public Object buscarAnterior(Object chave) {
        return buscarPosicao(chave, " < ", "max");
    }
    
    /*
     *Excluir registro da base de dados
     *@param chave do registro a ser excluido
     *@return verdadeiro para exclusao bem sucedida
     */
     public boolean excluir(Object chave) {
         boolean ok = false;
         String chaveFormatada = chaveFormatada(chave);
         
         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(_campoChave + " = " + chaveFormatada);
         
         if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
             ok = true;
         }
         
         return ok;
    }

    /**
     * Procurar campo especifico baseado em seu conteudo
     * @param nomeCampo
     * @param chave
     * @param campoDescricao
     * @return
     */
    public Object[][] procurarCampo(String nomeCampo, String chave, String campoDescricao) {
        Object[][] resultado = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(_campoChave);
        sql.insereColuna(campoDescricao);
        sql.insereCondicao(nomeCampo + " LIKE " + chave);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        int numRegistros = reg.getTotalRegistros();
        
        if (numRegistros > 0) {
            resultado = new Object[numRegistros][2];
            reg.primeiro();
            for (int i = 0; i < numRegistros; i++) {
                resultado[i][0] = chaveFormatada(reg.getColuna(0));
                resultado[i][1] = reg.getColuna(1);
                reg.proximo();
            }
        }

        return resultado;
    }
    
    /**
     * Informa a tabela base
     * @return nome da tabela
     */
    public String getTabela() {
        return ligacao.getTabela();
    }
 
    protected String chaveFormatada(Object chave) {
        String chaveTexto;
        switch (_tipoDadoCampoChave) {
            case TEXTO:
                chaveTexto = FormatacaoSQL.getDadoFormatado(chave.toString(), TiposDadosQuery.TEXTO);
                break;
            case NUMERO:
                chaveTexto = FormatacaoSQL.getDadoFormatado(chave.toString(), TiposDadosQuery.NUMERO);
                break;
            case DATA:
                chaveTexto = FormatacaoSQL.getDadoFormatado((Date) chave, TiposDadosQuery.DATA);
                break;
            default:    
                chaveTexto = FormatacaoSQL.getDadoFormatado(chave.toString(), TiposDadosQuery.TEXTO);
                break;
        }
        
        return chaveTexto;
    }

    private Object buscarPosicao(Object chave, String criterio, String condicao) {
        Object registro = null;
        String chaveFormatada = chaveFormatada(chave);
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(_campoChave, "chave", condicao);
        sql.insereCondicao(_campoChave + criterio + chaveFormatada);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna("chave") != null) {
            registro = reg.getColuna("chave");
        }
        
        return registro;
    }

}///~
