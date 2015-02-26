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
import br.com.codigocerto.modelos.CFOP;

/**
 * @author lis
 */
public class RegistroCFOP extends Registro {

    public interface BD {
        String  ID = "cc_cfop_id", 
                   NOME = "cc_cfop_nome",
                   OPERACAO = "cc_cfop_operacao",
                   DETALHE = "cc_cfop_detalhe",
                   MOVIMENTO = "cc_cfop_movimento",
                   DENTRO_ESTADO = "cc_cfop_dentroestado",
                   ESTRANGEIRO = "cc_cfop_estrangeiro";
    }

    /**
    * Gerar nova instancia de RegistroCFOP
    */
    public RegistroCFOP() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        CFOP modelo = (CFOP) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.OPERACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoOperacao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DETALHE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoDetalhe(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.MOVIMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMovimento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DENTRO_ESTADO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isDentroEstado(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.ESTRANGEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isEstrangeiro(), 
                                                            TiposDadosQuery.LOGICO)
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
        CFOP modelo = (CFOP) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.OPERACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoOperacao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DETALHE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoDetalhe(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.MOVIMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMovimento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DENTRO_ESTADO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isDentroEstado(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.ESTRANGEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isEstrangeiro(), 
                                                            TiposDadosQuery.LOGICO)
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
    public CFOP getDados(long id) {
        CFOP modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new CFOP();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            conversor.setValorBase(tabela.getColuna(BD.OPERACAO).toString());
            modelo.setCodigoOperacao(conversor.getInteger());
            modelo.setCodigoDetalhe(tabela.getColuna(BD.DETALHE).toString());
            modelo.setMovimento(tabela.getColuna(BD.MOVIMENTO).toString());
            modelo.setDentroEstado((Boolean)tabela.getColuna(BD.DENTRO_ESTADO));
            modelo.setEstrangeiro((Boolean)tabela.getColuna(BD.ESTRANGEIRO));
        }
        tabela.fechar();
        
        return modelo;
    }

    /**
     * Fornece uma cfop a partir de operacao+detalhe
     * @param cfop
     * @return cfop
     */
    public CFOP getDados(String cfop) {
        CFOP modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.OPERACAO + " = '" + cfop.substring(0, 1) + "'");
        sql.insereCondicao(BD.DETALHE + " = '" + cfop.substring(1) + "'", "and");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new CFOP();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            conversor.setValorBase(tabela.getColuna(BD.OPERACAO).toString());
            modelo.setCodigoOperacao(conversor.getInteger());
            modelo.setCodigoDetalhe(tabela.getColuna(BD.DETALHE).toString());
            modelo.setMovimento(tabela.getColuna(BD.MOVIMENTO).toString());
            modelo.setDentroEstado((Boolean)tabela.getColuna(BD.DENTRO_ESTADO));
            modelo.setEstrangeiro((Boolean)tabela.getColuna(BD.ESTRANGEIRO));
        }
        tabela.fechar();
        
        return modelo;
    }
    
    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_cfop");
        ligacao.setCampoId(BD.ID);
        
    }

}///~
