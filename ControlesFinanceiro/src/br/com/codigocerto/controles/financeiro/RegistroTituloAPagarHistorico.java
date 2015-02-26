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

package br.com.codigocerto.controles.financeiro;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.RegistroFilho;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.TituloHistorico;
import java.util.Date;

/**
 * @author lis
 */
public class RegistroTituloAPagarHistorico extends RegistroFilho {

    public interface BD {
        String  ID = "cc_titulosapagarhistoricos_id", 
                   TITULO = "cc_titulosapagarhistoricos_titulosapagar_id",
                   DESCRICAO = "cc_titulosapagarhistoricos_descricao", 
                   DATA = "cc_titulosapagarhistoricos_data";
    }

    /**
    * Gerar nova instancia de RegistroTituloAPagarHistorico
    */
    public RegistroTituloAPagarHistorico() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TITULO);
    }

    @Override
    public TituloHistorico[] getFilhos() {
       TituloHistorico[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TITULO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       TituloHistorico modelo;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new TituloHistorico[tabela.getTotalRegistros()];
           do {
                modelo = new TituloHistorico();
                modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                modelo.setData((Date)tabela.getColuna(BD.DATA));
                modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
                filhos[num] = modelo;
                num++;
           }
           while (tabela.proximo());
       }
        
       return filhos;
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        TituloHistorico modelo = (TituloHistorico) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        TituloHistorico modelo = (TituloHistorico) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    @Override
    public Object getDados(long id) {
        TituloHistorico modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_titulosapagarhistoricos");
        ligacao.setCampoId(BD.ID);
    }

    private TituloHistorico lerRegistro(TabelaBD tabela) {
        TituloHistorico modelo = null;
        if (tabela != null) {
            modelo = new TituloHistorico();
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
            modelo.setData((Date) tabela.getColuna(BD.DATA));
        }
        return modelo;
    } 

}///~
