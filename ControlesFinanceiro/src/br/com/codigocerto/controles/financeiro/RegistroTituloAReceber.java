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
import br.com.codigocerto.modelos.DocumentosTerceiro;
import br.com.codigocerto.modelos.Terceiro;
import br.com.codigocerto.modelos.Titulo;
import br.com.codigocerto.modelos.TituloHistorico;
import br.com.codigocerto.modelos.TituloPagamento;
import br.com.codigocerto.modelos.financeiro.Portador;
import br.com.codigocerto.modelos.financeiro.TituloAReceber;

/**
 * @author lis
 */
public class RegistroTituloAReceber extends Registro {

    public interface BD {
        String  ID = "cc_titulosareceber_id", 
                   ORIGEM = "cc_titulosareceber_origem",
                   NUMERO = "cc_titulosareceber_numero",
                   PARCELA = "cc_titulosareceber_parcela",
                   EMISSAO = "cc_titulosareceber_emissao",
                   VENCIMENTO_TIPO = "cc_titulosareceber_vencimentotipo",
                   VENCIMENTO_DATA = "cc_titulosareceber_vencimentodata",
                   TERCEIRO = "cc_titulosareceber_terceiros_id",
                   ESPECIE = "cc_titulosareceber_especie",
                   VALOR = "cc_titulosareceber_valor",
                   VALOR_PAGO = "cc_titulosareceber_valorpago",
                   VALOR_ABERTO = "cc_titulosareceber_valoremaberto",
                   JUROS = "cc_titulosareceber_juros",
                   MULTA = "cc_titulosareceber_multa",
                   DESCONTO = "cc_titulosareceber_desconto",
                   PORTADOR = "cc_titulosareceber_carteiras_id",
                   DIAS_QUITACAO = "cc_titulosareceber_diasquitacao",
                   NOME = "cc_titulosareceber_nomesacado",
                   ENDERECO = "cc_titulosareceber_enderecosacado",
                   BAIRRO = "cc_titulosareceber_bairrosacado",
                   CEP = "cc_titulosareceber_cepsacado",
                   CIDADE = "cc_titulosareceber_cidadesacado",
                   UF = "cc_titulosareceber_ufsacado",
                   DOCUMENTO = "cc_titulosareceber_documentosacado",
                   SERIE = "cc_titulosareceber_serie",
                   NUMERO_BANCO = "cc_titulosareceber_numerobanco",
                   SITUACAO = "cc_titulosareceber_situacao",
                   IMPRESSO = "cc_titulosareceber_impresso",
                   PRACA = "cc_titulosareceber_praca",
                   ND = "cc_titulosareceber_nd";
    }

    private RegistroTituloAReceberHistorico regHistorico = new RegistroTituloAReceberHistorico();
    private RegistroTituloAReceberPagamento regPagto = new RegistroTituloAReceberPagamento();

    /**
    * Gerar nova instancia 
    */
    public RegistroTituloAReceber() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        TituloAReceber modelo = (TituloAReceber) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
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
            sql.insereColuna(BD.SERIE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSerie(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ENDERECO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEndereco(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.BAIRRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getBairro(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCidade(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CEP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCep(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.UF, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getUf(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO_BANCO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumeroBanco(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITUACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSituacao().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ORIGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOrigem().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.PRACA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPraca(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            
            TransacaoBD trans = ServidorBD.getTransacao();
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
                trans.rollback();
            }
            if (ok) {
                trans.commit();
            }
            trans.fechar();
        }
        else {
            ok = false;
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        TituloAReceber modelo = (TituloAReceber) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
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
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ENDERECO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEndereco(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.BAIRRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getBairro(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCidade(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CEP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCep(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.UF, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getUf(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO_BANCO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumeroBanco(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITUACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSituacao().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ND, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getND(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRACA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPraca(),
                                                            TiposDadosQuery.TEXTO)
                                                            );

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            TransacaoBD trans = ServidorBD.getTransacao();            
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
            } else {
                ok = false;
                trans.rollback();
            }
            if (ok) {
                trans.commit();
            }
            trans.fechar();
        }
        else {
            ok = false;
        }
        
        return ok;
    }

    @Override
    public TituloAReceber getDados(long id) {
        TituloAReceber modelo = null;
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

    public TituloAReceber getDados(String origem, String serie, String numero, String parcela) {
        TituloAReceber modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.SERIE + " = '" + serie + "'");
        sql.insereCondicao(BD.NUMERO + " = '" + numero + "'", "and");
        sql.insereCondicao(BD.PARCELA + " = '" + parcela + "'", "and");
        sql.insereCondicao(BD.ORIGEM + " = '" + origem + "'", "and");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();

        return modelo;
    }

    /**
     * Informa o maior atraso em dias
     * @param terceiro
     * @return numero de dias
     */
    public int getMaiorAtraso(Terceiro terceiro) {
        int dias = 0;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.DIAS_QUITACAO, "dias", "max");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            if (tabela.getColuna("dias") != null) {
                dias = LerCampo.getInt(tabela.getColuna("dias"));
            }
        }
        tabela.fechar();

        return dias;
    }

    /**
     * Informa a media de atraso em dias
     * @param terceiro
     * @return numero de dias
     */
    public int getMediaAtraso(Terceiro terceiro) {
        int dias = 0;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.DIAS_QUITACAO, "dias", "avg");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            if (tabela.getColuna("dias") != null) {
                dias = ((BigDecimal) tabela.getColuna("dias")).intValue();
            }
        }
        tabela.fechar();

        return dias;
    }

    /**
     * Informa titulos vencidos
     * @param terceiro
     * @return titulos
     */
    public TituloAReceber[] getVencidos(Terceiro terceiro) {
        TituloAReceber[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosareceberemaberto");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        sql.insereCondicao("cc_titulosareceber_vencimentotipo = 'D' "
                           + "and cc_titulosareceber_vencimentodata < '"
                                   + hoje.getData() + "'", "and");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAReceber[tabela.getTotalRegistros()];
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
     * @return titulos
     */
    public TituloAReceber[] getVencidos() {
        TituloAReceber[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosareceberemaberto");
        sql.insereCondicao("cc_titulosareceber_vencimentotipo = 'D' "
                           + "and cc_titulosareceber_vencimentodata < '"
                                   + hoje.getData() + "'");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAReceber[tabela.getTotalRegistros()];
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
    public TituloAReceber[] getAVencer(Terceiro terceiro) {
        TituloAReceber[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosareceberemaberto");
        sql.insereCondicao(BD.TERCEIRO + " = " + terceiro.getId());
        sql.insereCondicao("((cc_titulosareceber_vencimentotipo = 'A' "
                           + "or cc_titulosareceber_vencimentotipo = 'V') "
                           + "or (cc_titulosareceber_vencimentotipo = 'D' "
                           + "and cc_titulosareceber_vencimentodata >= '"
                                   + hoje.getData() + "'))", "and");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAReceber[tabela.getTotalRegistros()];
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
     * @return titulos
     */
    public TituloAReceber[] getAVencer() {
        TituloAReceber[] titulos = null;
        DataHora hoje = new DataHora();
        hoje.setData(new Date());

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_titulosareceberemaberto");
        sql.insereCondicao("((cc_titulosareceber_vencimentotipo = 'A' "
                           + "or cc_titulosareceber_vencimentotipo = 'V') "
                           + "or (cc_titulosareceber_vencimentotipo = 'D' "
                           + "and cc_titulosareceber_vencimentodata >= '"
                                   + hoje.getData() + "'))");
        sql.insereOrdem(BD.VENCIMENTO_DATA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAReceber[tabela.getTotalRegistros()];
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

        String sql = "select cc_titulosareceber_id, cc_titulosareceberpagtos_data, cc_titulosareceberpagtos_valor, "
                + "cc_titulosareceberpagtos_pagtotipo "
                + "from cc_titulosareceberpagtos "
                + "left join cc_titulosareceber on cc_titulosareceberpagtos_titulosareceber_id = cc_titulosareceber_id "
                + "where cc_titulosareceberpagtos_data >= '" + dataHora.getData() + "' "
                + "and cc_titulosareceber_terceiros_id = " + String.valueOf(terceiro.getId())
                + " order by 2 desc";
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

    /**
     * Informa pagamentos
     * @param terceiro
     * @param portador
     * @return pagamentos
     */
    public TituloAReceber[] getPagamentos(Terceiro terceiro, Portador portador) {
        TituloAReceber[] pagtos = null;

        String sql = "select cc_titulosareceber_id "
                + "from cc_titulosareceber "
                + "where cc_titulosareceber_terceiros_id = " + String.valueOf(terceiro.getId())
                + " and cc_titulosareceber_valoremaberto = 0";
        TabelaBD tabela = ServidorBD.executaQuery(sql);
        if (tabela.primeiro()) {
            pagtos = new TituloAReceber[tabela.getTotalRegistros()];
            int i = 0;
            do {
                pagtos[i] = getDados(LerCampo.getLong(tabela.getColuna(0)));
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return pagtos;
    }

    /**
     * Informa pagamentos em cartorio
     * @param terceiro
     * @return pagamentos
     */
    public TituloAReceber[] getPagamentosCartorio(Terceiro terceiro) {
        TituloAReceber[] pagtos = null;

        String sql = "select cc_titulosareceber_id "
                + "from cc_titulosareceber "
                + "where cc_titulosareceber_terceiros_id = " + String.valueOf(terceiro.getId())
                + " and cc_titulosareceber_valoremaberto = 0 "
                + "order by 1 desc limit 200";
        TabelaBD tabela = ServidorBD.executaQuery(sql);
        if (tabela.primeiro()) {
            TituloAReceber titulo;
            pagtos = new TituloAReceber[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulo = getDados(LerCampo.getLong(tabela.getColuna(0)));
                boolean pagoCartorio = false;
                TituloHistorico histo;
                for (int j = 0; j < titulo.getNumeroHistoricos(); j++) {
                    histo = titulo.getHistorico(j);
                    if (histo.getDescricao().equalsIgnoreCase("Pagamento em Cartório")) {
                        pagoCartorio = true;
                    }

                }
                if (pagoCartorio) {
                    pagtos[i] = titulo;
                    i++;
                }

            } while (tabela.proximo());
        }
        tabela.fechar();

        return pagtos;
    }

    /**
     * Informa titulos a vencer a partir de uma data
     * @param data
     * @return titulos
     */
    public TituloAReceber[] getTitulos(Date data) {
        TituloAReceber[] titulos = null;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.EMISSAO + " = "
                + FormatacaoSQL.getDadoFormatado(data, TiposDadosQuery.DATA));
        sql.insereOrdem(BD.SERIE + ", " + BD.NUMERO + ", " + BD.PARCELA);

        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            titulos = new TituloAReceber[tabela.getTotalRegistros()];
            int i = 0;
            do {
                titulos[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return titulos;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_titulosareceber");
        ligacao.setCampoId(BD.ID);
    }
    
    /**
     * Localiza o id de um titulo a partir de seus dados
     * @param titulo
     * @return id do titulo
     */
    public long localizarTitulo(TituloAReceber titulo, TransacaoBD trans) {
        long id = 0;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.NUMERO + " = '" + titulo.getNumero() + "'");
        sql.insereCondicao(BD.PARCELA + " = " + titulo.getParcela(), "AND");
        sql.insereCondicao(BD.SERIE + " = '" + titulo.getSerie() + "'", "AND");
        sql.insereCondicao(BD.ORIGEM + " = '" + titulo.getOrigem().toString() + "'", "AND");
        TabelaBD tabela = trans.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            id = LerCampo.getLong(tabela.getColuna(BD.ID));
        }
        tabela.fechar();
        
        return id;
    }
    
    /**
     * Informa o ultimo numero utilizado
     * @param origem
     * @param serie
     * @return numero
     */
    public long getUltimoNumero(String origem, String serie) {
        long numero = 0;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.NUMERO);
        sql.insereCondicao(BD.SERIE + " = '" + serie + "'");
        sql.insereCondicao(BD.ORIGEM + " = '" + origem + "'", "AND");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(tabela.getColuna(BD.NUMERO).toString());
            numero = conv.getLong();
        }
        tabela.fechar();

        return numero;
    }

    /**
     * Marca o titulo como impresso
     * @param titulo
     */
    public void marcarImpresso(TituloAReceber titulo) {
        if (titulo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            sql.insereCondicao(ligacao.getCampoId() + " = " + titulo.getId());
            sql.insereColuna(BD.IMPRESSO, FormatacaoSQL.getDadoFormatado(
                                                            true,
                                                            TiposDadosQuery.LOGICO)
                                                            );

            ServidorBD.executaUpdate(sql.getQuery());
        }
    }

    @Override
    public boolean excluir(long id) {
        boolean ok = super.excluir(id);
        if (ok) {
             DeleteSqlBD sql = new DeleteSqlBD();
             sql.setTabela("cc_titulosareceberemaberto");
             sql.insereCondicao(ligacao.getCampoId() + " = " + id);

             TransacaoBD trans = ServidorBD.getTransacao();

             eliminaHistoricos(id, trans);
             excluirTituloEmAberto(id, trans);

            if (trans.executaUpdate(sql.getQuery()) == 1) {
                 ok = true;
                 trans.commit();
             } else {
                 ok = false;
                 trans.rollback();
             }
             trans.fechar();
            
        }
        
        return ok;
    }
     
    private void incluirTituloEmAberto(long id, TransacaoBD trans) {
        InsertSqlBD sql = new InsertSqlBD();
        sql.setTabela("cc_titulosareceberemaberto");
        sql.insereColuna(BD.ID, FormatacaoSQL.getDadoFormatado(
                                                        id,
                                                        TiposDadosQuery.NUMERO)
                                                        );

        trans.executaUpdate(sql.getQuery());
    }

    private void excluirTituloEmAberto(long id, TransacaoBD trans) {
        DeleteSqlBD sql = new DeleteSqlBD();
        sql.setTabela("cc_titulosareceberemaberto");
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(id);
        sql.insereCondicao(BD.ID + " = " + id);
        
        trans.executaUpdate(sql.getQuery());
    }

    private boolean insereHistoricos(TituloAReceber modelo, TransacaoBD trans) {
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

   private void leHistoricos(TituloAReceber modelo) {
       
       regHistorico.setIdPai(modelo.getId());
       TituloHistorico[] itens = regHistorico.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaHistorico(itens[i]);
           }
       }
   }

    private boolean inserePagtos(TituloAReceber modelo, TransacaoBD trans) {
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

   private void lePagtos(TituloAReceber modelo) {

       regPagto.setIdPai(modelo.getId());
       TituloPagamento[] itens = regPagto.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionaPagamento(itens[i]);
           }
       }
   }

   private TituloAReceber lerRegistro(TabelaBD tabela) {
        TituloAReceber modelo = new TituloAReceber();
        ConversorTipos conversor = new ConversorTipos();
        RegistroTerceiro regTerceiro = new RegistroTerceiro();
        RegistroPortador regPortador = new RegistroPortador();
        regTerceiro.setTipoTerceiro(0);
        Terceiro terceiro = (Terceiro) regTerceiro.getDados(LerCampo.getLong(tabela.getColuna(BD.TERCEIRO)));
        conversor.setValorBase(tabela.getColuna(BD.ID).toString());
        modelo.setId(conversor.getLong());

        modelo.setNumero(tabela.getColuna(BD.NUMERO).toString());
        modelo.setParcela(LerCampo.getInt(tabela.getColuna(BD.PARCELA)));
        modelo.setEmissao((Date) tabela.getColuna(BD.EMISSAO));
        modelo.setTipoVencimento(tabela.getColuna(BD.VENCIMENTO_TIPO).toString());
        modelo.setTerceiro(terceiro);
        modelo.setValor((BigDecimal) tabela.getColuna(BD.VALOR));
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
        if (tabela.getColuna(BD.PRACA) != null) {
            modelo.setPraca(LerCampo.getString(tabela.getColuna(BD.PRACA)));
        }
        modelo.setPortador(regPortador.getDados(LerCampo.getLong(tabela.getColuna(BD.PORTADOR))));
        if (tabela.getColuna(BD.NOME) != null) {
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
        } else {
            if (terceiro != null) {
                modelo.setNome(terceiro.getNome());
            }
        }
        if (tabela.getColuna(BD.ENDERECO) != null) {
            modelo.setEndereco(tabela.getColuna(BD.ENDERECO).toString());
        } else {
            if (terceiro != null && terceiro.getEnderecoFinanceiro() != null) {
                StringBuilder end = new StringBuilder();
                end.append(terceiro.getEnderecoFinanceiro().getLogradouro() + " "
                        + terceiro.getEnderecoFinanceiro().getNumero() + " "
                        + terceiro.getEnderecoFinanceiro().getComplemento());
                modelo.setEndereco(end.toString());
            }
        }
        if (tabela.getColuna(BD.BAIRRO) != null) {
            modelo.setBairro(tabela.getColuna(BD.BAIRRO).toString());
        } else {
            if (terceiro != null && terceiro.getEnderecoFinanceiro() != null) {
                modelo.setBairro(terceiro.getEnderecoFinanceiro().getBairro().getNome());
            }
        }
        if (tabela.getColuna(BD.CIDADE) != null) {
            modelo.setCidade(tabela.getColuna(BD.CIDADE).toString());
        } else {
            if (terceiro != null && terceiro.getEnderecoFinanceiro() != null) {
                if (terceiro.getEnderecoFinanceiro().getCidade() != null) {
                    modelo.setCidade(terceiro.getEnderecoFinanceiro().getCidade().getNome());
                }
            }
        }
        if (tabela.getColuna(BD.CEP) != null) {
            modelo.setCep(tabela.getColuna(BD.CEP).toString());
        } else {
            if (terceiro != null && terceiro.getEnderecoFinanceiro() != null) {
                modelo.setCep(terceiro.getEnderecoFinanceiro().getCEP());
            }
        }
        if (tabela.getColuna(BD.UF) != null) {
            modelo.setUf(tabela.getColuna(BD.UF).toString());
        } else {
            if (terceiro != null && terceiro.getEnderecoFinanceiro() != null) {
                if (terceiro.getEnderecoFinanceiro().getEstado() != null) {
                    modelo.setUf(terceiro.getEnderecoFinanceiro().getEstado().getSigla());
                }
            }
        }
        if (tabela.getColuna(BD.DOCUMENTO) != null) {
            modelo.setDocumento(tabela.getColuna(BD.DOCUMENTO).toString());
        } else {
            if (terceiro != null) {
                DocumentosTerceiro doc = terceiro.getDocumentos();
                if (doc != null) {
                    if (doc.getPessoa().equalsIgnoreCase(DocumentosTerceiro.Pessoa.JURIDICA)) {
                        modelo.setDocumento(doc.getCNPJ().getDocumento());
                    } else {
                        modelo.setDocumento(doc.getCPF().getDocumento());
                    }
                }
            }
        }
        if (tabela.getColuna(BD.ORIGEM) != null) {
            modelo.setOrigem(TituloAReceber.Origem.valueOf(tabela.getColuna(BD.ORIGEM).toString()));
        }
        if (tabela.getColuna(BD.SERIE) != null) {
            modelo.setSerie(tabela.getColuna(BD.SERIE).toString());
        }
        if (tabela.getColuna(BD.SITUACAO) != null) {
            modelo.setSituacao(Titulo.Situacao.valueOf(tabela.getColuna(BD.SITUACAO).toString()));
        }
        if (tabela.getColuna(BD.NUMERO_BANCO) != null) {
            modelo.setNumeroBanco(tabela.getColuna(BD.NUMERO_BANCO).toString());
        }
        modelo.setImpresso(LerCampo.getBoolean(tabela.getColuna(BD.IMPRESSO)));
        if (tabela.getColuna(BD.ND) != null) {
            modelo.setND(LerCampo.getLong(tabela.getColuna(BD.ND)));
        }

        leHistoricos(modelo);
        lePagtos(modelo);

        return modelo;

   }

}///:~
