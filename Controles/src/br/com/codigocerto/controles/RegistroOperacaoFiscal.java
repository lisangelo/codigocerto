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
import br.com.codigocerto.modelos.OperacaoFiscal;

/**
 * @author lis
 */
public class RegistroOperacaoFiscal extends Registro {

    public interface BD {
        String  ID = "cc_operacoesfiscais_id", 
                NOME = "cc_operacoesfiscais_nome",
                CFOP = "cc_operacoesfiscais_cfop_id",
                ESTOQUE = "cc_operacoesfiscais_estoque",
                CONSIGNACAO = "cc_operacoesfiscais_consignacao",
                FINANCEIRO = "cc_operacoesfiscais_financeiro",
                GERA_ICMS = "cc_operacoesfiscais_geraicms",
                GERA_ISS = "cc_operacoesfiscais_geraiss",
                GERA_IPI = "cc_operacoesfiscais_geraipi",
                ALTERA_CUSTO = "cc_operacoesfiscais_alteracusto",
                INFO_ADICIONAL = "cc_operacoesfiscais_infoadicional";
    }

    /**
    * Gerar nova instancia de RegistroOperacaoFiscal
    */
    public RegistroOperacaoFiscal() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        OperacaoFiscal modelo = (OperacaoFiscal) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ESTOQUE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isMovimentaEstoque(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.CONSIGNACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isConsignacao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.FINANCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraFinanceiro(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_ICMS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraICMS(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_ISS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraISS(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_IPI, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraIPI(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.CFOP, FormatacaoSQL.getDadoFormatado(
                                                       modelo.getCFOP().getId(),
                                                       TiposDadosQuery.NUMERO)
                                                       );
            sql.insereColuna(BD.ALTERA_CUSTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isAlteraCusto(),
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getInfoAdicional(),
                                                            TiposDadosQuery.TEXTO)
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
        OperacaoFiscal modelo = (OperacaoFiscal) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.ESTOQUE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isMovimentaEstoque(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.CONSIGNACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isConsignacao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.FINANCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraFinanceiro(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_ICMS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraICMS(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_ISS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraISS(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.GERA_IPI, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isGeraIPI(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.ALTERA_CUSTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isAlteraCusto(),
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.CFOP, FormatacaoSQL.getDadoFormatado(
                                                       modelo.getCFOP().getId(),
                                                       TiposDadosQuery.NUMERO)
                                                       );
            if (modelo.getInfoAdicional() != null) {
                sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getInfoAdicional(),
                                                                TiposDadosQuery.TEXTO)
                                                                );
            } else {
                sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                                "null",
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            
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
    public OperacaoFiscal getDados(long id) {
        OperacaoFiscal modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new OperacaoFiscal();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            
            modelo.setMovimentaEstoque((Boolean) tabela.getColuna(BD.ESTOQUE));
            modelo.setConsignacao(LerCampo.getBoolean(tabela.getColuna(BD.CONSIGNACAO)));
            modelo.setGeraFinanceiro((Boolean) tabela.getColuna(BD.FINANCEIRO));
            modelo.setGeraICMS((Boolean) tabela.getColuna(BD.GERA_ICMS));
            modelo.setGeraIPI((Boolean) tabela.getColuna(BD.GERA_IPI));
            modelo.setGeraISS((Boolean) tabela.getColuna(BD.GERA_ISS));
            modelo.setAlteraCusto((Boolean) tabela.getColuna(BD.ALTERA_CUSTO));
            if (tabela.getColuna(BD.INFO_ADICIONAL) != null) {
                modelo.setInfoAdicional(LerCampo.getString(tabela.getColuna(BD.INFO_ADICIONAL)));
            }
            
            long idCFOP = LerCampo.getLong(tabela.getColuna(BD.CFOP));
            if (idCFOP > 0) {
                RegistroCFOP regCFOP = new RegistroCFOP();
                modelo.setCFOP(regCFOP.getDados(idCFOP));
            }
        }
        tabela.fechar();
        
        return modelo;
    }

    /**
     * Fornece a primeira operacao fiscal cadastrada a partir de um CFOP
     * @param cfop
     * @return operacao
     */
    public OperacaoFiscal getDados(CFOP cfop) {
        OperacaoFiscal modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.CFOP + " = " + cfop.getId());
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new OperacaoFiscal();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            
            modelo.setMovimentaEstoque((Boolean) tabela.getColuna(BD.ESTOQUE));
            modelo.setConsignacao(LerCampo.getBoolean(tabela.getColuna(BD.CONSIGNACAO)));
            modelo.setGeraFinanceiro((Boolean) tabela.getColuna(BD.FINANCEIRO));
            modelo.setGeraICMS((Boolean) tabela.getColuna(BD.GERA_ICMS));
            modelo.setGeraIPI((Boolean) tabela.getColuna(BD.GERA_IPI));
            modelo.setGeraISS((Boolean) tabela.getColuna(BD.GERA_ISS));
            modelo.setAlteraCusto((Boolean) tabela.getColuna(BD.ALTERA_CUSTO));
            if (tabela.getColuna(BD.INFO_ADICIONAL) != null) {
                modelo.setInfoAdicional(LerCampo.getString(tabela.getColuna(BD.INFO_ADICIONAL)));
            }
            modelo.setCFOP(cfop);
        }
        tabela.fechar();
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_operacoesfiscais");
        ligacao.setCampoId(BD.ID);
        
    }

}///:~
