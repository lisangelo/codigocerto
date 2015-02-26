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
import br.com.codigocerto.controles.RegistroTerceiro;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.conversores.DataHora;
import br.com.codigocerto.conversores.FormatoNumerico;
import br.com.codigocerto.modelos.Terceiro;
import br.com.codigocerto.modelos.Titulo.Origem;
import br.com.codigocerto.modelos.TituloHistorico;
import br.com.codigocerto.modelos.TituloPagamento;
import br.com.codigocerto.modelos.financeiro.TituloAPagar;

/**
 * @author lis
 */
public class RegistroTituloAPagar extends Registro {

    public interface BD {
        String  ID = "cc_titulosapagar_id", 
                   ORIGEM = "cc_titulosapagar_origem",
                   NUMERO = "cc_titulosapagar_numero",
                   PARCELA = "cc_titulosapagar_parcela",
                   EMISSAO = "cc_titulosapagar_emissao",
                   VENCIMENTO_TIPO = "cc_titulosapagar_vencimentotipo",
                   VENCIMENTO_DATA = "cc_titulosapagar_vencimentodata",
                   PORTADOR = "cc_titulosapagar_carteiras_id",
                   TERCEIRO = "cc_titulosapagar_terceiros_id",
                   ESPECIE = "cc_titulosapagar_especie",
                   VALOR = "cc_titulosapagar_valor",
                   VALOR_PAGO = "cc_titulosapagar_valorpago",
                   VALOR_ABERTO = "cc_titulosapagar_valoremaberto",
                   JUROS = "cc_titulosapagar_juros",
                   MULTA = "cc_titulosapagar_multa",
                   SERIE = "cc_titulosapagar_serie",
                   DESCONTO = "cc_titulosapagar_desconto";
    }

    private RegistroTituloAPagarHistorico regHistorico = new RegistroTituloAPagarHistorico();
    private RegistroTituloAPagarPagamento regPagto = new RegistroTituloAPagarPagamento();

    /**
    * Gerar nova instancia de RegistroTituloAPagar
    */
    public RegistroTituloAPagar() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        TituloAPagar modelo = (TituloAPagar) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ORIGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOrigem().toString(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.PARCELA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getParcela(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VENCIMENTO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipoVencimento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VENCIMENTO_DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDataVencimento(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.SERIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSerie(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ESPECIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEspecie(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_ABERTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorEmAberto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PAGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorPago(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MULTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorMulta(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.JUROS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorJuros(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorDesconto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getPortador() != null) {
                sql.insereColuna(BD.PORTADOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getPortador().getId(),
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            TransacaoBD trans = getTransacao();
            if (trans == null) {
                trans = ServidorBD.getTransacao();
            }
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                long id = localizarTitulo(modelo, trans);
                if (id > 0) {
                    modelo.setId(id);
                    incluirTituloEmAberto(id, trans);
                    insereHistoricos(modelo, trans);
                    inserePagtos(modelo, trans);
                }
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
        TituloAPagar modelo = (TituloAPagar) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.ORIGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOrigem().toString(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.PARCELA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getParcela(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.EMISSAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmissao(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VENCIMENTO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipoVencimento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VENCIMENTO_DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDataVencimento(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            conv.setValorBase(modelo.getTerceiro().getId());
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.SERIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSerie(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ESPECIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEspecie(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_ABERTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorEmAberto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PAGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorPago(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MULTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorMulta(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.JUROS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorJuros(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorDesconto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getPortador() != null) {
                sql.insereColuna(BD.PORTADOR, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getPortador().getId(),
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());

            TransacaoBD trans = getTransacao();
            boolean transPropria = false;
            if (trans == null) {
                trans = ServidorBD.getTransacao();
                transPropria = true;
            }
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaHistoricos(modelo.getId(), trans);
                insereHistoricos(modelo, trans);
                eliminaPagtos(modelo.getId(), trans);
                inserePagtos(modelo, trans);
                if (modelo.getValorEmAberto().compareTo(BigDecimal.ZERO) == 0) {
                    excluirTituloEmAberto(modelo.getId(), trans);
                } else {
                    incluirTituloEmAberto(modelo.getId(), trans);
                }
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
    public TituloAPagar getDados(long id) {
        TituloAPagar modelo = null;
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

    public TituloAPagar getDados(String origem, String serie, String numero, String parcela,
                                     long terceiro) {
        TituloAPagar modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.NUMERO + " = '" + numero + "'");
        sql.insereCondicao(BD.PARCELA + " = '" + parcela + "'", "and");
        sql.insereCondicao(BD.ORIGEM + " = '" + origem + "'", "and");
        sql.insereCondicao(BD.TERCEIRO + " = '" + terceiro + "'", "and");
        System.out.println(sql.getQuery());
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();

        return modelo;
    }


    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_titulosapagar");
        ligacao.setCampoId(BD.ID);
    }
    
    /**
     * Localiza o id de um titulo a partir de seus dados
     * @param titulo
     * @return id do titulo
     */
    public long localizarTitulo(TituloAPagar titulo, TransacaoBD trans) {
        long id = 0;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.ORIGEM + " = '" + titulo.getOrigem() + "'");
        sql.insereCondicao(BD.NUMERO + " = '" + titulo.getNumero() + "'", "AND");
        sql.insereCondicao(BD.PARCELA + " = " + titulo.getParcela(), "AND");
        sql.insereCondicao(BD.TERCEIRO + " = " + titulo.getTerceiro().getId(), "AND");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
        tabela.fechar();
        
        return id;
    }
    
    @Override
    public boolean excluir(long id) {
        boolean ok = super.excluir(id);
        if (ok) {
             DeleteSqlBD sql = new DeleteSqlBD();
             sql.setTabela("cc_titulosapagaremaberto");
             sql.insereCondicao(ligacao.getCampoId() + " = " + id);

            TransacaoBD trans = getTransacao();
            boolean transPropria = false;
            if (trans == null) {
                trans = ServidorBD.getTransacao();
                transPropria = true;
            }

             eliminaHistoricos(id, trans);

            if (trans.executaUpdate(sql.getQuery()) == 1) {
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
     * Informa titulos a vencer a partir de uma data
     * @param data
     * @return titulos
     */
    public TituloAPagar[] getTitulos(Date data) {
        TituloAPagar[] titulos = null;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.EMISSAO + " = "
                + FormatacaoSQL.getDadoFormatado(data, TiposDadosQuery.DATA));
        sql.insereOrdem(BD.TERCEIRO + ", " + BD.SERIE + ", " + BD.NUMERO + ", " + BD.PARCELA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAPagar[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulos[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return titulos;
    }

    /**
     * Informa titulos a vencer a partir de uma data
     * @param data
     * @return titulos
     */
    public TituloAPagar[] getTitulosVencto(Date data) {
        TituloAPagar[] titulos = null;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.VENCIMENTO_DATA + " = "
                + FormatacaoSQL.getDadoFormatado(data, TiposDadosQuery.DATA));
        sql.insereCondicao(BD.VALOR_ABERTO + " > 0 ", "and");
        sql.insereOrdem(BD.SERIE + ", " + BD.NUMERO);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAPagar[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulos[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return titulos;
    }


    /**
     * Informa titulos vencidos
     * @param terceiro
     * @return titulos
     */
    public TituloAPagar[] getVencidos(Terceiro terceiro) {
        TituloAPagar[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosapagaremaberto");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        sql.insereCondicao("cc_titulosapagar_vencimentotipo = 'D' "
                           + "and cc_titulosapagar_vencimentodata < '"
                                   + hoje.getData() + "'", "and");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAPagar[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulos[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return titulos;
    }

    /**
     * Informa titulos a vencer
     * @param terceiro
     * @return titulos
     */
    public TituloAPagar[] getAVencer(Terceiro terceiro) {
        TituloAPagar[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosapagaremaberto");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        sql.insereCondicao("((cc_titulosapagar_vencimentotipo = 'A' "
                           + "or cc_titulosapagar_vencimentotipo = 'V') "
                           + "or (cc_titulosapagar_vencimentotipo = 'D' "
                           + "and cc_titulosapagar_vencimentodata >= '"
                                   + hoje.getData() + "'))", "and");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAPagar[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulos[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return titulos;
    }

    
    /**
     * Informa pagamentos
     * @param terceiro
     * @param data
     * @return pagamentos
     */
    public String[][] getPagamentos(Terceiro terceiro, Date data) {
        String[][] pagtos = null;
        DataHora dataHora = new DataHora();
        dataHora.setData(data);

        String sql = "select cc_titulosapagar_id, cc_titulosapagarpagtos_data, cc_titulosapagarpagtos_valor, "
                + "cc_titulosapagarpagtos_pagtotipo "
                + "from cc_titulosapagarpagtos "
                + "left join cc_titulosapagar on cc_titulosapagarpagtos_titulosapagar_id = cc_titulosapagar_id "
                + "where cc_titulosapagarpagtos_data >= '" + dataHora.getData() + "' "
                + "and cc_titulosapagar_terceiros_id = " + String.valueOf(terceiro.getId())
                + " order by 2 desc";
        System.out.println(sql);
        TabelaBD tabela = ServidorBD.executaQuery(sql);
        if (tabela.primeiro()) {
            pagtos = new String[tabela.getTotalRegistros()][4];
            int i = 0;
            do {
                pagtos[i][0] = String.valueOf(LerCampo.getLong(tabela.getColuna(0)));
                dataHora.setData((Date) tabela.getColuna(1));
                pagtos[i][1] = dataHora.getDataFormatada();
                pagtos[i][2] = FormatoNumerico.aplicar((BigDecimal) tabela.getColuna(2), "#######.##");
                pagtos[i][3] = LerCampo.getString(tabela.getColuna(3));
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return pagtos;
    }

    private void incluirTituloEmAberto(long id, TransacaoBD trans) {
        InsertSqlBD sql = new InsertSqlBD();
        sql.setTabela("cc_titulosapagaremaberto");
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(id);
        sql.insereColuna(BD.ID, FormatacaoSQL.getDadoFormatado(
                                                        conv.getString(), 
                                                        TiposDadosQuery.NUMERO)
                                                        );

        trans.executaUpdate(sql.getQuery());
    }

    private void excluirTituloEmAberto(long id, TransacaoBD trans) {
        DeleteSqlBD sql = new DeleteSqlBD();
        sql.setTabela("cc_titulosapagaremaberto");
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(id);
        sql.insereCondicao(BD.ID + " = " + id);
        
        trans.executaUpdate(sql.getQuery());
    }

    private boolean insereHistoricos(TituloAPagar modelo, TransacaoBD trans) {
       boolean ok = true;
       regHistorico.setTransacao(trans);
       regHistorico.setIdPai(modelo.getId());

       if (modelo.getNumeroHistoricos() > 0) {
           for (int i = 0; i < modelo.getNumeroHistoricos(); i++) {
                ok = regHistorico.setDadosInsercao(modelo.getHistorico(i));
           }
           if (ok) {
               TituloHistorico ultimoHistorico = modelo.getHistorico(modelo.getNumeroHistoricos() - 1);
               if (ultimoHistorico.getDescricao().equals(TituloHistorico.Historico.QUITAÇÃO.toString())) {
                   excluirTituloEmAberto(modelo.getId(), trans);
               }
           }
       }
       
       return ok;
   }
    
   private boolean eliminaHistoricos(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regHistorico.setIdPai(id);
       regHistorico.setTransacao(trans);
       if (regHistorico.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }

   private void leHistoricos(TituloAPagar modelo) {
       
       regHistorico.setIdPai(modelo.getId());
       TituloHistorico[] itens = regHistorico.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaHistorico(itens[i]);
           }
       }
   }

    private boolean inserePagtos(TituloAPagar modelo, TransacaoBD trans) {
       boolean ok = true;
       regPagto.setTransacao(trans);
       regPagto.setIdPai(modelo.getId());

       if (modelo.getNumeroPagamentos() > 0) {
           for (int i = 0; i < modelo.getNumeroPagamentos(); i++) {
                ok = regPagto.setDadosInsercao(modelo.getPagamento(i));
           }
       }

       return ok;
   }

   private boolean eliminaPagtos(long id, TransacaoBD trans) {
       boolean ok = false;

       regPagto.setIdPai(id);
       regPagto.setTransacao(trans);
       if (regPagto.eliminaFilhos() > 0) {
           ok = true;
       }

       return ok;
   }

   private void lePagtos(TituloAPagar modelo) {

       regPagto.setIdPai(modelo.getId());
       TituloPagamento[] itens = regPagto.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaPagamento(itens[i]);
           }
       }
   }

   private TituloAPagar lerRegistro(TabelaBD tabela) {
        TituloAPagar modelo = new TituloAPagar();
        RegistroPortador regPortador = new RegistroPortador();
        RegistroTerceiro regTerceiro = new RegistroTerceiro();
        regTerceiro.setTipoTerceiro(0);
        modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));

        modelo.setOrigem(Origem.valueOf(tabela.getColuna(BD.ORIGEM).toString()));
        modelo.setNumero(tabela.getColuna(BD.NUMERO).toString());
        modelo.setParcela(LerCampo.getInt(tabela.getColuna(BD.PARCELA)));
        modelo.setEmissao((Date) tabela.getColuna(BD.EMISSAO));
        modelo.setTipoVencimento(tabela.getColuna(BD.VENCIMENTO_TIPO).toString());
        modelo.setTerceiro(regTerceiro.getDados(LerCampo.getLong(tabela.getColuna(BD.TERCEIRO))));
        modelo.setValor((BigDecimal) tabela.getColuna(BD.VALOR));
        modelo.setSerie(LerCampo.getString(tabela.getColuna(BD.SERIE)));
        modelo.setPortador(regPortador.getDados(LerCampo.getLong(tabela.getColuna(BD.PORTADOR))));
        if (tabela.getColuna(BD.VENCIMENTO_DATA) != null) {
            modelo.setDataVencimento((Date) tabela.getColuna(BD.VENCIMENTO_DATA));
        }
        if (tabela.getColuna(BD.ESPECIE) != null) {
            modelo.setEspecie(tabela.getColuna(BD.ESPECIE).toString());
        }
        if (tabela.getColuna(BD.VALOR_PAGO) != null) {
            modelo.setValorPago((BigDecimal) tabela.getColuna(BD.VALOR_PAGO));
        }
        if (tabela.getColuna(BD.JUROS) != null) {
            modelo.setValorJuros((BigDecimal) tabela.getColuna(BD.JUROS));
        }
        if (tabela.getColuna(BD.MULTA) != null) {
            modelo.setValorMulta((BigDecimal) tabela.getColuna(BD.MULTA));
        }
        if (tabela.getColuna(BD.DESCONTO) != null) {
            modelo.setValorDesconto((BigDecimal) tabela.getColuna(BD.DESCONTO));
        }

        leHistoricos(modelo);
        lePagtos(modelo);

        return modelo;

   }


}///:~
