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

import java.math.BigDecimal;
import java.util.Date;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.controles.RegistroMoeda;
import br.com.codigocerto.controles.RegistroTerceiro;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.NotaFiscalRecebidaParcela;
import br.com.codigocerto.modelos.financeiro.Despesa;

/**
 * @author lis
 */
public class RegistroDespesa extends Registro {

    public interface BD {
        String  ID = "cc_despesas_id", 
                TERCEIRO = "cc_despesas_terceiros_id",
                EMISSAO = "cc_despesas_emissao",
                DESCRICAO = "cc_despesas_descricao",
                ENTRADA = "cc_despesas_entrada",
                VALOR_TOTAL = "cc_despesas_valortotal",
                VALOR_TOTAL_MOEDA_ESTRANGEIRA = "cc_despesas_valortotalmoedaestrangeira",
                MOEDA_ESTRANGEIRA = "cc_despesas_moedas_id",
                DOCUMENTO = "cc_despesas_documento",
                PREVISAO = "cc_despesas_previsao";
    }

    private RegistroTerceiro _regTerceiro = new RegistroTerceiro();
    private RegistroMoeda _regMoeda = new RegistroMoeda();
    private RegistroDespesaParcela _regParcelas = new RegistroDespesaParcela();

    /**
    * Gerar nova instancia de RegistroDespesa
    */
    public RegistroDespesa() {
        configurar();
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = true;
        Despesa modelo = (Despesa) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL_MOEDA_ESTRANGEIRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotalMoedaEstrangeira(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.PREVISAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isPrevisao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            if (modelo.getMoedaEstrangeira() != null) {
                conv.setValorBase(modelo.getMoedaEstrangeira().getId());
                sql.insereColuna(BD.MOEDA_ESTRANGEIRA, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                modelo.setId(getId(modelo.getTerceiro().getId(), modelo.getDocumento(), trans));
                insereParcelas(modelo, trans);
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        Despesa modelo = (Despesa) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL_MOEDA_ESTRANGEIRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotalMoedaEstrangeira(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.PREVISAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isPrevisao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            if (modelo.getMoedaEstrangeira() != null) {
                conv.setValorBase(modelo.getMoedaEstrangeira().getId());
                sql.insereColuna(BD.MOEDA_ESTRANGEIRA, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            TransacaoBD trans = ServidorBD.getTransacao();
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaParcelas(modelo.getId(), trans);
                insereParcelas(modelo, trans);
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
    public Despesa getDados(long id) {
        Despesa modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = leDespesa(tabela);
        }
        
        return modelo;
    }

    /**
     * Recupera despesa a partir do terceiro e documento
     * @param idTerceiro
     * @param documento
     * @return
     */
    public Despesa getDados(long idTerceiro, String documento) {
        Despesa modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.TERCEIRO + " = " + idTerceiro);
        sql.insereCondicao(BD.DOCUMENTO + " = '" + documento + "'", "and");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = leDespesa(tabela);
        }
        
        return modelo;
    }
    
    private Despesa leDespesa(TabelaBD tabela) {
        Despesa modelo = new Despesa();
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(tabela.getColuna(BD.ID).toString());
        modelo.setId(conv.getLong());
        modelo.setTerceiro(_regTerceiro.getDados(LerCampo.getLong(tabela.getColuna(BD.TERCEIRO))));
        modelo.setEmissao((Date) tabela.getColuna(BD.EMISSAO));
        modelo.setEntrada((Date) tabela.getColuna(BD.ENTRADA));
        if (tabela.getColuna(BD.VALOR_TOTAL) != null) {
            modelo.setValorTotal((BigDecimal) tabela.getColuna(BD.VALOR_TOTAL));
        }
        if (tabela.getColuna(BD.VALOR_TOTAL_MOEDA_ESTRANGEIRA) != null) {
            modelo.setValorTotalMoedaEstrangeira((BigDecimal) tabela.getColuna(BD.VALOR_TOTAL_MOEDA_ESTRANGEIRA));
            modelo.setMoedaEstrangeira(
                    _regMoeda.getDados(LerCampo.getLong(tabela.getColuna(BD.MOEDA_ESTRANGEIRA))));
        }
        modelo.setDocumento(tabela.getColuna(BD.DOCUMENTO).toString());
        modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
        modelo.setPrevisao((Boolean) tabela.getColuna(BD.PREVISAO));

        leParcelas(modelo);
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_despesas");
        ligacao.setCampoId(BD.ID);
    }

    @Override
    public boolean excluir(long id) {
         boolean ok = false;
         
         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(ligacao.getCampoId() + " = " + id);

         TransacaoBD trans = ServidorBD.getTransacao();
         
         eliminaParcelas(id, trans);
         if (trans.executaUpdate(sql.getQuery()) == 1) {
             ok = true;
         }
         if (ok) {
             trans.commit();
         } else {
             trans.rollback();
         }
         trans.fechar();
        
         return ok;
    }
    
   private long getId(long idTerceiro, String documento, TransacaoBD trans) {
       long id = 0;
       
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.TERCEIRO + " = " + idTerceiro);
        sql.insereCondicao(BD.DOCUMENTO + " = '" + documento + "'", "and");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
       
       return id;
   }
   
   private boolean insereParcelas(Despesa modelo, TransacaoBD trans) {
       boolean ok = true;
       _regParcelas.setTransacao(trans);
       _regParcelas.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroParcelas(); i++) {
            ok = _regParcelas.setDadosInsercao(modelo.getParcela(i));
       }
       
       return ok;
   }
    
   private boolean eliminaParcelas(long id, TransacaoBD trans) {
       boolean ok = false;
       
       _regParcelas.setIdPai(id);
       _regParcelas.setTransacao(trans);
       if (_regParcelas.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }

   private void leParcelas(Despesa modelo) {
       
       _regParcelas.setIdPai(modelo.getId());
       NotaFiscalRecebidaParcela[] itens = _regParcelas.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaParcela(itens[i]);
           }
       }
   }
   
   private void configurar() {
        _regTerceiro.setTipoTerceiro(0);
   }
    
}///~
