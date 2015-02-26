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
import br.com.codigocerto.modelos.TotalizadorNaoSujeitoIcms;

/**
 * @author lis
 */
public class RegistroTotalizadorNaoSujeitoIcms extends Registro {

    public interface BD {
        String  ID = "cc_totalizadoresnaosujeitoicms_id", 
                NOME = "cc_totalizadoresnaosujeitoicms_nome",
                POSICAO = "cc_totalizadoresnaosujeitoicms_posicao",
                DESCRICAO = "cc_totalizadoresnaosujeitoicms_descricao"; 
    }

    /**
    * Gerar nova instancia de RegistroTotalizadorNaoSujeitoIcms
    */
    public RegistroTotalizadorNaoSujeitoIcms() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        TotalizadorNaoSujeitoIcms modelo = (TotalizadorNaoSujeitoIcms) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.POSICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPosicao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            
            if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
            else {
                ok = false;
            }
        }
        else {
            ok = false;
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        TotalizadorNaoSujeitoIcms modelo = (TotalizadorNaoSujeitoIcms) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.POSICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPosicao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
            else {
                ok = false;
            }
        }
        else {
            ok = false;
        }
        
        return ok;
    }

    @Override
    public TotalizadorNaoSujeitoIcms getDados(long id) {
        TotalizadorNaoSujeitoIcms modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new TotalizadorNaoSujeitoIcms();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
            modelo.setPosicao(LerCampo.getInt(tabela.getColuna(BD.POSICAO)));
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_totalizadoresnaosujeitoicms");
        ligacao.setCampoId(BD.ID);
        
    }

}///~
