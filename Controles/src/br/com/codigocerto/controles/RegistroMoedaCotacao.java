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
import br.com.codigocerto.modelos.MoedaCotacao;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lis
 */
public class RegistroMoedaCotacao extends RegistroMovimentoChave {

    public interface BD {
        String  DATA = "cc_moedascotacoes_data", 
                   MOEDA = "cc_moedascotacoes_moedas_id",
                   COMPRA = "cc_moedascotacoes_valorcompra",
                   VENDA = "cc_moedascotacoes_valorvenda";
    }

    /**
    * Gerar nova instancia de RegistroMoedaCotacao
    */
    public RegistroMoedaCotacao() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        MoedaCotacao modelo = (MoedaCotacao) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getMoeda().getId());
            sql.insereColuna(BD.MOEDA, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.COMPRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorCompra(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VENDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorVenda(), 
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
        MoedaCotacao modelo = (MoedaCotacao) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getMoeda().getId());
            sql.insereColuna(BD.MOEDA, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.COMPRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorCompra(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VENDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorVenda(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereCondicao(BD.DATA + " = " 
                                         + FormatacaoSQL.getDadoFormatado(modelo.getData(), TiposDadosQuery.DATA));
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
    public MoedaCotacao getDados(Object chave) {
        MoedaCotacao modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.DATA + " = " + FormatacaoSQL.getDadoFormatado((Date) chave, TiposDadosQuery.DATA));
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new MoedaCotacao();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.MOEDA).toString());
            RegistroMoeda regMoeda = new RegistroMoeda();
            modelo.setMoeda(regMoeda.getDados(conversor.getLong()));
            modelo.setData((Date) tabela.getColuna(BD.DATA));
            if (tabela.getColuna(BD.COMPRA) != null) {
                modelo.setValorCompra((BigDecimal) tabela.getColuna(BD.COMPRA));
            }
            if (tabela.getColuna(BD.VENDA) != null) {
                modelo.setValorVenda((BigDecimal) tabela.getColuna(BD.VENDA));
            }
        }
        
        return modelo;
    }

    public BigDecimal getUltimaCotacao() {
        BigDecimal ultimaCotacao = null;
        Date hoje = new Date(); 
        MoedaCotacao modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.VENDA);
        sql.insereCondicao(BD.DATA + " < " + FormatacaoSQL.getDadoFormatado(hoje, TiposDadosQuery.DATA));
        sql.insereCondicao("not isnull(" + BD.VENDA + ")", "and");
        sql.insereOrdem("-" + BD.DATA + " limit 1");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            if (tabela.getColuna(BD.VENDA) != null) {
                ultimaCotacao = (BigDecimal) tabela.getColuna(BD.VENDA);
            }
        }
        
        return ultimaCotacao;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_moedascotacoes");
        setCampoChave(BD.DATA, TiposDadosQuery.DATA);
    }

}///~
