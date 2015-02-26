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
import br.com.codigocerto.modelos.NotaFiscalRecebidaProduto;
import java.math.BigDecimal;

/**
 * @author lis
 */
public class RegistroNotaFiscalRecebidaProduto extends RegistroFilho {

    public interface BD {
        String  ID = "cc_notasfiscaisrecebidasprodutos_id", 
                NOTA_FISCAL = "cc_notasfiscaisrecebidasprodutos_notasfiscaisrecebidas_id",
                CODIGO = "cc_notasfiscaisrecebidasprodutos_codigo", 
                DESCRICAO = "cc_notasfiscaisrecebidasprodutos_descricao",
                OPERACAO_FISCAL = "cc_notasfiscaisrecebidasprodutos_operacoesfiscais_id",
                ICMS_BASE = "cc_notasfiscaisrecebidasprodutos_icmsbase",
                ICMS_REDUCAO = "cc_notasfiscaisrecebidasprodutos_icmsreducao",
                ICMS_VALOR = "cc_notasfiscaisrecebidasprodutos_icmsvalor",
                ICMS_PERCENTUAL = "cc_notasfiscaisrecebidasprodutos_icmspercentual",
                ICMS_ST_BASE = "cc_notasfiscaisrecebidasprodutos_icmsstbase",
                ICMS_ST_VALOR = "cc_notasfiscaisrecebidasprodutos_icmsstvalor",
                ICMS_ST_PERCENTUAL = "cc_notasfiscaisrecebidasprodutos_icmsstpercentual",
                VALOR_TOTAL = "cc_notasfiscaisrecebidasprodutos_valortotal",
                PRODUTO = "cc_notasfiscaisrecebidasprodutos_produtos_id", 
                UNIDADE = "cc_notasfiscaisrecebidasprodutos_unidadesmedida_id",
                QUANTIDADE = "cc_notasfiscaisrecebidasprodutos_quantidade",
                VALOR_UNITARIO = "cc_notasfiscaisrecebidasprodutos_valorunitario",
                IPI_PERCENTUAL = "cc_notasfiscaisrecebidasprodutos_ipipercentual",
                IPI_VALOR = "cc_notasfiscaisrecebidasprodutos_ipivalor",
                SITUACAO_TRIBUTARIA = "cc_notasfiscaisrecebidasprodutos_situacaotributaria",
                EMBALAGEM_QUANTIDADE = "cc_notasfiscaisrecebidasprodutos_embalagemquantidade",
                PRECO_CUSTO = "cc_notasfiscaisrecebidasprodutos_precocusto",
                PRECO_CUSTO_FINAL = "cc_notasfiscaisrecebidasprodutos_precocustofinal";
    }

    private RegistroOperacaoFiscal regOperacao = new RegistroOperacaoFiscal();
    private RegistroProduto regProduto = new RegistroProduto();
    private RegistroUnidadeMedida regUnidade = new RegistroUnidadeMedida();
    
    /**
    * Gerar nova instancia de RegistroNotaFiscalRecebidaProduto
    */
    public RegistroNotaFiscalRecebidaProduto() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.NOTA_FISCAL);
    }

    @Override
    public NotaFiscalRecebidaProduto[] getFilhos() {
       NotaFiscalRecebidaProduto[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.NOTA_FISCAL + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       NotaFiscalRecebidaProduto modelo;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new NotaFiscalRecebidaProduto[tabela.getTotalRegistros()];
           do {
                modelo = new NotaFiscalRecebidaProduto();
                modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                if (tabela.getColuna(BD.CODIGO) != null) {
                    modelo.setCodigo(tabela.getColuna(BD.CODIGO).toString());
                }
                if (tabela.getColuna(BD.DESCRICAO) != null) {
                    modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
                }
                if (tabela.getColuna(BD.OPERACAO_FISCAL) != null) {
                    long idOperacao = LerCampo.getLong(tabela.getColuna(BD.OPERACAO_FISCAL));
                    modelo.setOperacaoFiscal(regOperacao.getDados(idOperacao));
                }
                if (tabela.getColuna(BD.ICMS_BASE) != null) {
                    modelo.setIcmsBase((BigDecimal)tabela.getColuna(BD.ICMS_BASE));
                }
                if (tabela.getColuna(BD.ICMS_REDUCAO) != null) {
                    modelo.setIcmsReducao((BigDecimal)tabela.getColuna(BD.ICMS_REDUCAO));
                }
                if (tabela.getColuna(BD.ICMS_VALOR) != null) {
                    modelo.setIcmsValor((BigDecimal)tabela.getColuna(BD.ICMS_VALOR));
                }
                if (tabela.getColuna(BD.ICMS_PERCENTUAL) != null) {
                    modelo.setIcmsPercentual((BigDecimal)tabela.getColuna(BD.ICMS_PERCENTUAL));
                }
                if (tabela.getColuna(BD.ICMS_ST_BASE) != null) {
                    modelo.setIcmsStBase((BigDecimal)tabela.getColuna(BD.ICMS_ST_BASE));
                }
                if (tabela.getColuna(BD.ICMS_ST_VALOR) != null) {
                    modelo.setIcmsStValor((BigDecimal)tabela.getColuna(BD.ICMS_ST_VALOR));
                }
                if (tabela.getColuna(BD.ICMS_ST_PERCENTUAL) != null) {
                    modelo.setIcmsStPercentual((BigDecimal)tabela.getColuna(BD.ICMS_ST_PERCENTUAL));
                }
                if (tabela.getColuna(BD.IPI_VALOR) != null) {
                    modelo.setIpiValor((BigDecimal)tabela.getColuna(BD.IPI_VALOR));
                }
                if (tabela.getColuna(BD.IPI_PERCENTUAL) != null) {
                    modelo.setIpiPercentual((BigDecimal)tabela.getColuna(BD.IPI_PERCENTUAL));
                }
                if (tabela.getColuna(BD.VALOR_TOTAL) != null) {
                    modelo.setValorTotal((BigDecimal)tabela.getColuna(BD.VALOR_TOTAL));
                }
                if (tabela.getColuna(BD.PRODUTO) != null) {
                    long idProduto = LerCampo.getLong(tabela.getColuna(BD.PRODUTO));
                    modelo.setProduto(regProduto.getDados(idProduto));
                }
                if (tabela.getColuna(BD.UNIDADE) != null) {
                    long idUnidade = LerCampo.getLong(tabela.getColuna(BD.UNIDADE));
                    modelo.setUnidade(regUnidade.getDados(idUnidade));
                }
                if (tabela.getColuna(BD.QUANTIDADE) != null) {
                    modelo.setQuantidade((BigDecimal)tabela.getColuna(BD.QUANTIDADE));
                }
                if (tabela.getColuna(BD.VALOR_UNITARIO) != null) {
                    modelo.setValorUnitario((BigDecimal)tabela.getColuna(BD.VALOR_UNITARIO));
                }
                if (tabela.getColuna(BD.SITUACAO_TRIBUTARIA) != null) {
                    modelo.setSituacaoTributaria(tabela.getColuna(BD.SITUACAO_TRIBUTARIA).toString());
                }
                if (tabela.getColuna(BD.EMBALAGEM_QUANTIDADE) != null) {
                    modelo.setEmbalagemQuantidade(
                            LerCampo.getInt(tabela.getColuna(BD.EMBALAGEM_QUANTIDADE)));
                }
                
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
        NotaFiscalRecebidaProduto modelo = (NotaFiscalRecebidaProduto) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.NOTA_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getCodigo() != null) {
                sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCodigo(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getDescricao() != null) {
                sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getDescricao(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getOperacaoFiscal() != null) {
                conv.setValorBase(modelo.getOperacaoFiscal().getId());
                sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsBase() != null) {
                sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsBase(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsReducao() != null) {
                sql.insereColuna(BD.ICMS_REDUCAO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsReducao(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsValor() != null) {
                sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsPercentual() != null) {
                sql.insereColuna(BD.ICMS_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStBase() != null) {
                sql.insereColuna(BD.ICMS_ST_BASE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStBase(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStValor() != null) {
                sql.insereColuna(BD.ICMS_ST_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStPercentual() != null) {
                sql.insereColuna(BD.ICMS_ST_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIpiValor() != null) {
                sql.insereColuna(BD.IPI_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIpiValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIpiPercentual() != null) {
                sql.insereColuna(BD.IPI_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIpiPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getValorTotal() != null) {
                sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getValorTotal(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getProduto() != null) {
                conv.setValorBase(modelo.getProduto().getId());
                sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getUnidade() != null) {
                conv.setValorBase(modelo.getUnidade().getId());
                sql.insereColuna(BD.UNIDADE, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getQuantidade() != null) {
                sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getQuantidade(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getValorUnitario() != null) {
                sql.insereColuna(BD.VALOR_UNITARIO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getValorUnitario(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getSituacaoTributaria() != null) {
                sql.insereColuna(BD.SITUACAO_TRIBUTARIA, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getSituacaoTributaria(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getEmbalagemQuantidade() > 0) {
                sql.insereColuna(BD.EMBALAGEM_QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getEmbalagemQuantidade(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            sql.insereColuna(BD.PRECO_CUSTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrecoCusto(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRECO_CUSTO_FINAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrecoCustoFinal(),
                                                            TiposDadosQuery.NUMERO)
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
        NotaFiscalRecebidaProduto modelo = (NotaFiscalRecebidaProduto) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.NOTA_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getCodigo() != null) {
                sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCodigo(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getDescricao() != null) {
                sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getDescricao(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getOperacaoFiscal() != null) {
                conv.setValorBase(modelo.getOperacaoFiscal().getId());
                sql.insereColuna(BD.OPERACAO_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsBase() != null) {
                sql.insereColuna(BD.ICMS_BASE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsBase(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsReducao() != null) {
                sql.insereColuna(BD.ICMS_REDUCAO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsReducao(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsValor() != null) {
                sql.insereColuna(BD.ICMS_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsPercentual() != null) {
                sql.insereColuna(BD.ICMS_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStBase() != null) {
                sql.insereColuna(BD.ICMS_ST_BASE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStBase(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStValor() != null) {
                sql.insereColuna(BD.ICMS_ST_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsStPercentual() != null) {
                sql.insereColuna(BD.ICMS_ST_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsStPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIpiValor() != null) {
                sql.insereColuna(BD.IPI_VALOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIpiValor(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIpiPercentual() != null) {
                sql.insereColuna(BD.IPI_PERCENTUAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIpiPercentual(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getValorTotal() != null) {
                sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getValorTotal(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getProduto() != null) {
                conv.setValorBase(modelo.getProduto().getId());
                sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getUnidade() != null) {
                conv.setValorBase(modelo.getUnidade().getId());
                sql.insereColuna(BD.UNIDADE, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getQuantidade() != null) {
                sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getQuantidade(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getValorUnitario() != null) {
                sql.insereColuna(BD.VALOR_UNITARIO, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getValorUnitario(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getSituacaoTributaria() != null) {
                sql.insereColuna(BD.SITUACAO_TRIBUTARIA, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getSituacaoTributaria(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getEmbalagemQuantidade() > 0) {
                sql.insereColuna(BD.EMBALAGEM_QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getEmbalagemQuantidade(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            sql.insereColuna(BD.PRECO_CUSTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrecoCusto(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRECO_CUSTO_FINAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrecoCustoFinal(),
                                                            TiposDadosQuery.NUMERO)
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
        NotaFiscalRecebidaProduto modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new NotaFiscalRecebidaProduto();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));

            if (tabela.getColuna(BD.CODIGO) != null) {
                modelo.setCodigo(tabela.getColuna(BD.CODIGO).toString());
            }
            if (tabela.getColuna(BD.DESCRICAO) != null) {
                modelo.setDescricao(tabela.getColuna(BD.DESCRICAO).toString());
            }
            if (tabela.getColuna(BD.OPERACAO_FISCAL) != null) {
                long idOperacao = LerCampo.getLong(tabela.getColuna(BD.OPERACAO_FISCAL));
                modelo.setOperacaoFiscal(regOperacao.getDados(idOperacao));
            }
            if (tabela.getColuna(BD.ICMS_BASE) != null) {
                modelo.setIcmsBase((BigDecimal)tabela.getColuna(BD.ICMS_BASE));
            }
            if (tabela.getColuna(BD.ICMS_REDUCAO) != null) {
                modelo.setIcmsReducao((BigDecimal)tabela.getColuna(BD.ICMS_REDUCAO));
            }
            if (tabela.getColuna(BD.ICMS_VALOR) != null) {
                modelo.setIcmsValor((BigDecimal)tabela.getColuna(BD.ICMS_VALOR));
            }
            if (tabela.getColuna(BD.ICMS_PERCENTUAL) != null) {
                modelo.setIcmsPercentual((BigDecimal)tabela.getColuna(BD.ICMS_PERCENTUAL));
            }
            if (tabela.getColuna(BD.ICMS_ST_BASE) != null) {
                modelo.setIcmsStBase((BigDecimal)tabela.getColuna(BD.ICMS_ST_BASE));
            }
            if (tabela.getColuna(BD.ICMS_ST_VALOR) != null) {
                modelo.setIcmsStValor((BigDecimal)tabela.getColuna(BD.ICMS_ST_VALOR));
            }
            if (tabela.getColuna(BD.ICMS_ST_PERCENTUAL) != null) {
                modelo.setIcmsStPercentual((BigDecimal)tabela.getColuna(BD.ICMS_ST_PERCENTUAL));
            }
            if (tabela.getColuna(BD.IPI_VALOR) != null) {
                modelo.setIpiValor((BigDecimal)tabela.getColuna(BD.IPI_VALOR));
            }
            if (tabela.getColuna(BD.IPI_PERCENTUAL) != null) {
                modelo.setIpiPercentual((BigDecimal)tabela.getColuna(BD.IPI_PERCENTUAL));
            }
            if (tabela.getColuna(BD.VALOR_TOTAL) != null) {
                modelo.setValorTotal((BigDecimal)tabela.getColuna(BD.VALOR_TOTAL));
            }
            if (tabela.getColuna(BD.PRODUTO) != null) {
                long idProduto = LerCampo.getLong(tabela.getColuna(BD.PRODUTO));
                modelo.setProduto(regProduto.getDados(idProduto));
            }
            if (tabela.getColuna(BD.UNIDADE) != null) {
                long idUnidade = LerCampo.getLong(tabela.getColuna(BD.UNIDADE));
                modelo.setUnidade(regUnidade.getDados(idUnidade));
            }
            if (tabela.getColuna(BD.QUANTIDADE) != null) {
                modelo.setQuantidade((BigDecimal)tabela.getColuna(BD.QUANTIDADE));
            }
            if (tabela.getColuna(BD.VALOR_UNITARIO) != null) {
                modelo.setValorUnitario((BigDecimal)tabela.getColuna(BD.VALOR_UNITARIO));
            }
            if (tabela.getColuna(BD.SITUACAO_TRIBUTARIA) != null) {
                modelo.setSituacaoTributaria(tabela.getColuna(BD.SITUACAO_TRIBUTARIA).toString());
            }
            if (tabela.getColuna(BD.EMBALAGEM_QUANTIDADE) != null) {
                modelo.setEmbalagemQuantidade(
                        LerCampo.getInt(tabela.getColuna(BD.EMBALAGEM_QUANTIDADE)));
            }
        
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_notasfiscaisrecebidasprodutos");
        ligacao.setCampoId(BD.ID);
    }

}///:~
