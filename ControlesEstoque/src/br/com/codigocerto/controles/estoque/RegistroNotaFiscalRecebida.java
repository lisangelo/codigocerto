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

import br.com.codigocerto.controles.*;
import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.NotaFiscalRecebida;
import br.com.codigocerto.modelos.NotaFiscalRecebidaParcela;
import br.com.codigocerto.modelos.NotaFiscalRecebidaProduto;
import br.com.codigocerto.modelos.financeiro.TituloAPagar;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lis
 */
public class RegistroNotaFiscalRecebida extends Registro {

    public interface BD {
        String  ID = "cc_notasfiscaisrecebidas_id", 
                OPERACAO_FISCAL = "cc_notasfiscaisrecebidas_operacoesfiscais_id",
                INSCRICAO_ESTADUAL_ST = "cc_notasfiscaisrecebidas_inscricaoestadualst",
                TERCEIRO = "cc_notasfiscaisrecebidas_terceiros_id",
                EMISSAO = "cc_notasfiscaisrecebidas_emissao",
                ICMS_BASE = "cc_notasfiscaisrecebidas_icmsbase",
                ICMS_VALOR = "cc_notasfiscaisrecebidas_icmsvalor",
                ICMS_ST_BASE = "cc_notasfiscaisrecebidas_icmsstbase",
                ICMS_ST_VALOR = "cc_notasfiscaisrecebidas_icmsstvalor",
                VALOR_PRODUTOS = "cc_notasfiscaisrecebidas_valorprodutos",
                VALOR_FRETE = "cc_notasfiscaisrecebidas_valorfrete",
                VALOR_SEGURO = "cc_notasfiscaisrecebidas_valorseguro",
                OUTRAS_DESPESAS = "cc_notasfiscaisrecebidas_outrasdespesas",
                VALOR_IPI = "cc_notasfiscaisrecebidas_ipivalor",
                VALOR_TOTAL = "cc_notasfiscaisrecebidas_valortotal",
                NUMERO = "cc_notasfiscaisrecebidas_numero",
                SERIE = "cc_notasfiscaisrecebidas_serie",
                ENTRADA = "cc_notasfiscaisrecebidas_entrada",
                ARQUIVO = "cc_notasfiscaisrecebidas_arquivo";
    }

    private RegistroTerceiro regTerceiro = new RegistroTerceiro();
    private RegistroOperacaoFiscal regOperacao = new RegistroOperacaoFiscal();
    private RegistroNotaFiscalRecebidaParcela regParcelas = new RegistroNotaFiscalRecebidaParcela();
    private RegistroNotaFiscalRecebidaProduto regProdutos = new RegistroNotaFiscalRecebidaProduto();

    /**
    * Gerar nova instancia de RegistroNotaFiscalRecebida
    */
    public RegistroNotaFiscalRecebida() {
        configurar();
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = true;
        NotaFiscalRecebida modelo = (NotaFiscalRecebida) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getOperacaoFiscal().getId());
            sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.INSCRICAO_ESTADUAL_ST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getInscricaoST(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PRODUTOS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorProdutos(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorFrete(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_SEGURO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorSeguro(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OUTRAS_DESPESAS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOutrasDespesas(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_IPI, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorIpi(), 
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
            sql.insereColuna(BD.SERIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSerie(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ARQUIVO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getArquivo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                modelo.setId(getId(modelo.getTerceiro().getId(), modelo.getNumero(), modelo.getSerie(), trans));
                insereParcelas(modelo, trans);
                insereProdutos(modelo, trans);
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
        NotaFiscalRecebida modelo = (NotaFiscalRecebida) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getOperacaoFiscal().getId());
            sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.INSCRICAO_ESTADUAL_ST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getInscricaoST(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_BASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PRODUTOS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorProdutos(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorFrete(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_SEGURO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorSeguro(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OUTRAS_DESPESAS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOutrasDespesas(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_IPI, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorIpi(), 
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
            sql.insereColuna(BD.SERIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSerie(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrada(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.ARQUIVO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getArquivo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            TransacaoBD trans = ServidorBD.getTransacao();
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaParcelas(modelo.getId(), trans);
                insereParcelas(modelo, trans);
                eliminaProdutos(modelo.getId(), trans);
                insereProdutos(modelo, trans);
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
    public NotaFiscalRecebida getDados(long id) {
        NotaFiscalRecebida modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new NotaFiscalRecebida();
            ConversorTipos conv = new ConversorTipos();
            modelo.setId(id);
            modelo.setOperacaoFiscal(regOperacao.getDados(LerCampo.getLong(tabela.getColuna(BD.OPERACAO_FISCAL))));
            modelo.setInscricaoST(tabela.getColuna(BD.OPERACAO_FISCAL).toString());
            modelo.setTerceiro(regTerceiro.getDados(LerCampo.getLong(tabela.getColuna(BD.TERCEIRO))));
            modelo.setEmissao((Date) tabela.getColuna(BD.EMISSAO));
            if (tabela.getColuna(BD.ICMS_BASE) != null) {
                modelo.setIcmsBase((BigDecimal) tabela.getColuna(BD.ICMS_BASE));
            }
            if (tabela.getColuna(BD.ICMS_VALOR) != null) {
                modelo.setIcmsValor((BigDecimal) tabela.getColuna(BD.ICMS_VALOR));
            }
            if (tabela.getColuna(BD.ICMS_ST_BASE) != null) {
                modelo.setIcmsSTBase((BigDecimal) tabela.getColuna(BD.ICMS_ST_BASE));
            }
            if (tabela.getColuna(BD.ICMS_ST_VALOR) != null) {
                modelo.setIcmsSTValor((BigDecimal) tabela.getColuna(BD.ICMS_ST_VALOR));
            }
            if (tabela.getColuna(BD.VALOR_PRODUTOS) != null) {
                modelo.setValorProdutos((BigDecimal) tabela.getColuna(BD.VALOR_PRODUTOS));
            }
            if (tabela.getColuna(BD.VALOR_SEGURO) != null) {
                modelo.setValorSeguro((BigDecimal) tabela.getColuna(BD.VALOR_SEGURO));
            }
            if (tabela.getColuna(BD.VALOR_FRETE) != null) {
                modelo.setValorFrete((BigDecimal) tabela.getColuna(BD.VALOR_FRETE));
            }
            if (tabela.getColuna(BD.OUTRAS_DESPESAS) != null) {
                modelo.setOutrasDespesas((BigDecimal) tabela.getColuna(BD.OUTRAS_DESPESAS));
            }
            if (tabela.getColuna(BD.VALOR_IPI) != null) {
                modelo.setValorIpi((BigDecimal) tabela.getColuna(BD.VALOR_IPI));
            }
            if (tabela.getColuna(BD.VALOR_TOTAL) != null) {
                modelo.setValorTotal((BigDecimal) tabela.getColuna(BD.VALOR_TOTAL));
            }
            conv.setValorBase(tabela.getColuna(BD.NUMERO).toString());
            modelo.setNumero(conv.getLong());
            modelo.setSerie(tabela.getColuna(BD.SERIE).toString());
            modelo.setEntrada((Date) tabela.getColuna(BD.ENTRADA));
            modelo.setArquivo(LerCampo.getString(tabela.getColuna(BD.ARQUIVO)));
            leParcelas(modelo);
            leProdutos(modelo);

        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_notasfiscaisrecebidas");
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
         eliminaProdutos(id, trans);
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
    
    /**
     * Localizar uma nota a partir de seu cabecalho
     * @param idTerceiro
     * @param numero
     * @param serie
     * @return nota
     */
    public NotaFiscalRecebida getNota(long idTerceiro, long numero, String serie) {
        NotaFiscalRecebida nota = null;

        TransacaoBD trans = ServidorBD.getTransacao();
        long id = getId(idTerceiro, numero, serie, trans);
        if (id > 0) {
            nota = getDados(id);
        }
        trans.fechar();
        
        return nota;
    }

    private void configurar() {
        regTerceiro.setTipoTerceiro(0);
    }

   private boolean insereParcelas(NotaFiscalRecebida modelo, TransacaoBD trans) {
       boolean ok = true;
       regParcelas.setTransacao(trans);
       regParcelas.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroParcelas(); i++) {
           NotaFiscalRecebidaParcela parcela = (NotaFiscalRecebidaParcela) modelo.getParcela(i);
           TituloAPagar titulo = (TituloAPagar) parcela.getTitulo();
           titulo.setSerie(modelo.getSerie());
           ok = regParcelas.setDadosInsercao(parcela);
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

   private void leParcelas(NotaFiscalRecebida modelo) {
       
       regParcelas.setIdPai(modelo.getId());
       NotaFiscalRecebidaParcela[] itens = regParcelas.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaParcela(itens[i]);
           }
       }
   }
   
   private boolean insereProdutos(NotaFiscalRecebida modelo, TransacaoBD trans) {
       boolean ok = true;
       regProdutos.setTransacao(trans);
       regProdutos.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroItens(); i++) {
            ok = regProdutos.setDadosInsercao(modelo.getItem(i));
       }
       
       return ok;
   }
    
   private boolean eliminaProdutos(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regProdutos.setIdPai(id);
       regProdutos.setTransacao(trans);
       if (regProdutos.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }

   private void leProdutos(NotaFiscalRecebida modelo) {
       
       regProdutos.setIdPai(modelo.getId());
       NotaFiscalRecebidaProduto[] itens = regProdutos.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaItem(itens[i]);
           }
       }
   }

   private long getId(long idTerceiro, long numero, String serie, TransacaoBD trans) {
       long id = 0;
       
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.TERCEIRO + " = " + idTerceiro);
        sql.insereCondicao(BD.NUMERO + " = " + numero, "and");
        sql.insereCondicao(BD.SERIE + " = '" + serie + "'", "and");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
       
       return id;
   }
   
}///:~
