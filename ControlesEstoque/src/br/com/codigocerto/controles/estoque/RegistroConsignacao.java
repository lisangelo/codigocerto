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

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.estoque.Consignacao;
import br.com.codigocerto.modelos.estoque.ConsignacaoMovimento;
import java.util.ArrayList;

/**
 * @author lis
 */
public class RegistroConsignacao extends Registro {

    public interface BD {
        String  ID = "cc_consignacao_id", 
                PRODUTO = "cc_consignacao_produtos_id",
                CODIGO = "cc_consignacao_codigo",
                NOTA_RECEBIDA = "cc_consignacao_notasfiscaisrecebidas_id",
                NOTA_RECEBIDA_ITEM = "cc_consignacao_notasfiscaisrecebidasprodutos_id",
                DATA = "cc_consignacao_data",
                TERCEIRO = "cc_consignacao_terceiros_id",
                QUANTIDADE = "cc_consignacao_quantidade",
                QUANTIDADE_ATENDIDA = "cc_consignacao_quantidadeatendida",
                SALDO = "cc_consignacao_saldo";
    }

    private RegistroConsignacaoMovimento _regMovimento = new RegistroConsignacaoMovimento();

    /**
    * Gerar nova instancia
    */
    public RegistroConsignacao() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Consignacao modelo = (Consignacao) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getProduto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.NOTA_RECEBIDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOTA_RECEBIDA_ITEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getItem(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTerceiro(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE_ATENDIDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidadeAtendida(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.SALDO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSaldo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            TransacaoBD trans = getTransacao();
            if (trans == null) {
                trans = ServidorBD.getTransacao();
            }
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                long id = localizar(modelo, trans);
                if (id > 0) {
                    modelo.setId(id);
                    insereMovimentos(modelo, trans);
                }
                trans.commit();
            }
            else {
                ok = false;
                trans.rollback();
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
        Consignacao modelo = (Consignacao) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getProduto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.NOTA_RECEBIDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOTA_RECEBIDA_ITEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getItem(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTerceiro(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE_ATENDIDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidadeAtendida(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.SALDO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSaldo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());

            TransacaoBD trans = getTransacao();
            boolean transPropria = false;
            if (trans == null) {
                trans = ServidorBD.getTransacao();
                transPropria = true;
            }
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaMovimentos(modelo.getId(), trans);
                insereMovimentos(modelo, trans);
                if (transPropria) {
                    trans.commit();
                }
            }
            else {
                ok = false;
                if (transPropria) {
                    trans.rollback();
                }
            }
            if (transPropria) {
                trans.fechar();
            }
        }
        else {
            ok = false;
        }
        
        return ok;
    }

    @Override
    public Consignacao getDados(long id) {
        Consignacao modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();
        
        return modelo;
    }
    
    /**
     * Recupera consignacoes com saldo em aberto
     * @param idTerceiro
     * @return lista de consignacoes
     */
    public ArrayList<Consignacao> getSaldos(long idTerceiro) {
        ArrayList<Consignacao> lista = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.TERCEIRO + " = " + idTerceiro);
        sql.insereCondicao(BD.SALDO + " > 0", "and");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            lista = new ArrayList<Consignacao>(tabela.getTotalRegistros());
            do {
                lista.add(getDados(LerCampo.getLong(tabela.getColuna(BD.ID))));
            } while(tabela.proximo());
        }
        tabela.fechar();
        
        return lista;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_consignacao");
        ligacao.setCampoId(BD.ID);
    }
    
    @Override
    public boolean excluir(long id) {
        boolean ok = super.excluir(id);
        if (ok) {

            TransacaoBD trans = getTransacao();
            boolean transPropria = false;
            if (trans == null) {
                trans = ServidorBD.getTransacao();
                transPropria = true;
            }

            if (eliminaMovimentos(id, trans)) {
                 ok = true;
                 if (transPropria) {
                    trans.commit();
                 }
             } else {
                 ok = false;
                 if (transPropria) {
                    trans.rollback();
                 }
             }
             if (transPropria) {
                trans.fechar();
             }
            
        }
        
        return ok;
    }
    
    /**
     * Eliminar registro de consignacao a partir do id do item da nota fiscal recebida
     * @param id 
     */
    public boolean eliminar(long id) {
        boolean ok = true;
        
        long idConsignacao = 0;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.NOTA_RECEBIDA_ITEM + " = '" + id + "'");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            idConsignacao = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
        tabela.fechar();
        
        if (idConsignacao > 0) {
            ok = excluir(idConsignacao);
        }
        
        return ok;
    }

    private boolean insereMovimentos(Consignacao modelo, TransacaoBD trans) {
       boolean ok = true;
       _regMovimento.setTransacao(trans);
       _regMovimento.setIdPai(modelo.getId());

       if (modelo.getNumeroMovimentos() > 0) {
           for (int i = 0; i < modelo.getNumeroMovimentos(); i++) {
                ok = _regMovimento.setDadosInsercao(modelo.getMovimento(i));
           }
       }

       return ok;
   }

   private boolean eliminaMovimentos(long id, TransacaoBD trans) {
       boolean ok = false;

       _regMovimento.setIdPai(id);
       _regMovimento.setTransacao(trans);
       if (_regMovimento.eliminaFilhos() > 0) {
           ok = true;
       }

       return ok;
   }

   private void leMovimentos(Consignacao modelo) {

       _regMovimento.setIdPai(modelo.getId());
       ConsignacaoMovimento[] itens = _regMovimento.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.incluiMovimento(itens[i]);
           }
       }
   }

   private Consignacao lerRegistro(TabelaBD tabela) {
        Consignacao modelo = new Consignacao();
        modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
        modelo.setNota(LerCampo.getLong(tabela.getColuna(BD.NOTA_RECEBIDA)), 
                LerCampo.getLong(tabela.getColuna(BD.NOTA_RECEBIDA_ITEM)), 
                LerCampo.getData(tabela.getColuna(BD.DATA)), 
                LerCampo.getLong(tabela.getColuna(BD.TERCEIRO)));
        modelo.setProduto(LerCampo.getLong(tabela.getColuna(BD.PRODUTO)), 
                LerCampo.getString(tabela.getColuna(BD.CODIGO)), 
                LerCampo.getDecimal(tabela.getColuna(BD.QUANTIDADE)));

        leMovimentos(modelo);

        return modelo;

   }

    private long localizar(Consignacao modelo, TransacaoBD trans) {
        long id = 0;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.NOTA_RECEBIDA + " = '" + modelo.getNota() + "'");
        sql.insereCondicao(BD.NOTA_RECEBIDA_ITEM + " = '" + modelo.getItem() + "'", "AND");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
        tabela.fechar();
        
        return id;
    }

}///:~
