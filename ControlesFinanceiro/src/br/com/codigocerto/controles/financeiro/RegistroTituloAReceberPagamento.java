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

import java.util.Date;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.RegistroFilho;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.TituloPagamento;
import java.math.BigDecimal;

/**
 * @author lis
 */
public class RegistroTituloAReceberPagamento extends RegistroFilho {

    public interface BD {
        String  ID = "cc_titulosareceberpagtos_id",
                TITULO = "cc_titulosareceberpagtos_titulosareceber_id",
                TIPO = "cc_titulosareceberpagtos_pagtotipo",
                DATA = "cc_titulosareceberpagtos_data",
                FORMA = "cc_titulosareceberpagtos_forma",
                VALOR = "cc_titulosareceberpagtos_valor",
                JUROS = "cc_titulosareceberpagtos_juros",
                MULTA = "cc_titulosareceberpagtos_multa",
                DESCONTO = "cc_titulosareceberpagtos_desconto";
    }

    /**
    * Gerar nova instancia
    */
    public RegistroTituloAReceberPagamento() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TITULO);
    }

    @Override
    public TituloPagamento[] getFilhos() {
       TituloPagamento[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TITULO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new TituloPagamento[tabela.getTotalRegistros()];
           do {
                filhos[num] = lerRegistro(tabela);
                num++;
           }
           while (tabela.proximo());
       }
       tabela.fechar();
        
       return filhos;
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        TituloPagamento modelo = (TituloPagamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.JUROS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getJuros(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MULTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMulta(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDesconto(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.FORMA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getForma(),
                                                            TiposDadosQuery.TEXTO)
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
        TituloPagamento modelo = (TituloPagamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(),
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.JUROS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getJuros(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MULTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMulta(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDesconto(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.FORMA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getForma(),
                                                            TiposDadosQuery.TEXTO)
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
        TituloPagamento modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_titulosareceberpagtos");
        ligacao.setCampoId(BD.ID);
    }

    private TituloPagamento lerRegistro(TabelaBD tabela) {
        TituloPagamento modelo = null;
        if (tabela != null) {
            modelo = new TituloPagamento();
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            modelo.setData((Date)tabela.getColuna(BD.DATA));
            modelo.setTipo(TituloPagamento.Tipo.valueOf(tabela.getColuna(BD.TIPO).toString()));
            modelo.setValor((BigDecimal)tabela.getColuna(BD.VALOR));
            modelo.setJuros((BigDecimal)tabela.getColuna(BD.JUROS));
            modelo.setMulta((BigDecimal)tabela.getColuna(BD.MULTA));
            modelo.setDesconto((BigDecimal)tabela.getColuna(BD.DESCONTO));
            modelo.setForma(LerCampo.getString(tabela.getColuna(BD.FORMA)));
        }
        return modelo;
    } 

}///:~
