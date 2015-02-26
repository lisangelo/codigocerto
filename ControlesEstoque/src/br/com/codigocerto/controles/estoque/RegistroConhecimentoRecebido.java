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

package br.com.codigocerto.controles.estoque;

import java.math.BigDecimal;
import java.util.Date;

import br.com.codigocerto.controles.*;
import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.NotaFiscalRecebida;
import br.com.codigocerto.modelos.NotaFiscalRecebidaParcela;
import br.com.codigocerto.modelos.estoque.ConhecimentoRecebido;

/**
 * @author lis
 */
public class RegistroConhecimentoRecebido extends Registro {

    public interface BD {
        String  ID = "cc_conhecimentosrecebidos_id", 
                TERCEIRO = "cc_conhecimentosrecebidos_terceiros_id",
                EMISSAO = "cc_conhecimentosrecebidos_emissao",
                OPERACAO_FISCAL = "cc_conhecimentosrecebidos_operacoesfiscais_id",
                ICMS_BASE = "cc_conhecimentosrecebidos_icmsbase",
                ICMS_VALOR = "cc_conhecimentosrecebidos_icmsvalor",
                ICMS_ALIQUOTA = "cc_conhecimentosrecebidos_icmsaliquota",
                VALOR_TOTAL = "cc_conhecimentosrecebidos_valortotal",
                NUMERO = "cc_conhecimentosrecebidos_numero",
                ENTRADA = "cc_conhecimentosrecebidos_entrada";
    }

    private RegistroTerceiro regTerceiro = new RegistroTerceiro();
    private RegistroOperacaoFiscal regOperacao = new RegistroOperacaoFiscal();
    private RegistroConhecimentoRecebidoParcela regParcelas = new RegistroConhecimentoRecebidoParcela();
    private RegistroConhecimentoRecebidoNota regNotas = new RegistroConhecimentoRecebidoNota();

    /**
    * Gerar nova instancia de RegistroNotaFiscalRecebida
    */
    public RegistroConhecimentoRecebido() {
        configurar();
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = true;
        ConhecimentoRecebido modelo = (ConhecimentoRecebido) dados;
        
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
            conv.setValorBase(modelo.getOperacaoFiscal().getId());
            sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(modelo.getNumero()), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                modelo.setId(getId(modelo.getTerceiro().getId(), modelo.getNumero(), trans));
                insereParcelas(modelo, trans);
                insereNotas(modelo, trans);
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
        ConhecimentoRecebido modelo = (ConhecimentoRecebido) dados;
        
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
            conv.setValorBase(modelo.getOperacaoFiscal().getId());
            sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(modelo.getNumero()), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            TransacaoBD trans = ServidorBD.getTransacao();
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaParcelas(modelo.getId(), trans);
                insereParcelas(modelo, trans);
                eliminaNotas(modelo.getId(), trans);
                insereNotas(modelo, trans);
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
    public ConhecimentoRecebido getDados(long id) {
        ConhecimentoRecebido modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new ConhecimentoRecebido();
            ConversorTipos conv = new ConversorTipos();
            modelo.setId(id);
            modelo.setTerceiro(regTerceiro.getDados(LerCampo.getLong(tabela.getColuna(BD.TERCEIRO))));
            modelo.setEmissao((Date) tabela.getColuna(BD.EMISSAO));
            modelo.setOperacaoFiscal(regOperacao.getDados(LerCampo.getLong(tabela.getColuna(BD.OPERACAO_FISCAL))));
            if (tabela.getColuna(BD.ICMS_BASE) != null) {
                modelo.setIcmsBase((BigDecimal) tabela.getColuna(BD.ICMS_BASE));
            }
            if (tabela.getColuna(BD.ICMS_VALOR) != null) {
                modelo.setIcmsValor((BigDecimal) tabela.getColuna(BD.ICMS_VALOR));
            }
            if (tabela.getColuna(BD.ICMS_ALIQUOTA) != null) {
                modelo.setIcmsAliquota((BigDecimal) tabela.getColuna(BD.ICMS_ALIQUOTA));
            }
            if (tabela.getColuna(BD.VALOR_TOTAL) != null) {
                modelo.setValorTotal((BigDecimal) tabela.getColuna(BD.VALOR_TOTAL));
            }
            conv.setValorBase(tabela.getColuna(BD.NUMERO).toString());
            modelo.setNumero(conv.getLong());
            modelo.setEntrada((Date) tabela.getColuna(BD.ENTRADA));
            
            leParcelas(modelo);
            leNotas(modelo);

        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_conhecimentosrecebidos");
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
         eliminaNotas(id, trans);
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

    private void configurar() {
        regTerceiro.setTipoTerceiro(0);
    }

   private boolean insereParcelas(ConhecimentoRecebido modelo, TransacaoBD trans) {
       boolean ok = true;
       regParcelas.setTransacao(trans);
       regParcelas.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroParcelas(); i++) {
            ok = regParcelas.setDadosInsercao(modelo.getParcela(i));
       }
       
       return ok;
   }
    
   private boolean eliminaParcelas(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regParcelas.setIdPai(id);
       regParcelas.setTransacao(trans);
       if (regParcelas.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }

   private void leParcelas(ConhecimentoRecebido modelo) {
       
       regParcelas.setIdPai(modelo.getId());
       NotaFiscalRecebidaParcela[] itens = regParcelas.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaParcela(itens[i]);
           }
       }
   }
   
   private boolean insereNotas(ConhecimentoRecebido modelo, TransacaoBD trans) {
       boolean ok = true;
       regNotas.setTransacao(trans);
       regNotas.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroNotas(); i++) {
            ok = regNotas.setDadosInsercao(modelo.getNota(i));
       }
       
       return ok;
   }
    
   private boolean eliminaNotas(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regNotas.setIdPai(id);
       regNotas.setTransacao(trans);
       if (regNotas.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }

   private void leNotas(ConhecimentoRecebido modelo) {
       
       regNotas.setIdPai(modelo.getId());
       NotaFiscalRecebida[] itens = regNotas.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaNota(itens[i]);
           }
       }
   }

   private long getId(long idTerceiro, long numero, TransacaoBD trans) {
       long id = 0;
       
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.TERCEIRO + " = " + idTerceiro);
        sql.insereCondicao(BD.NUMERO + " = " + numero, "and");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
       
       return id;
   }
   
}///~
