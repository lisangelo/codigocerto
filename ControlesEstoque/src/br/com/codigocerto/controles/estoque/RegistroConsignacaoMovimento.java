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
import br.com.codigocerto.controles.RegistroFilho;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.estoque.ConsignacaoMovimento;

/**
 * @author lis
 */
public class RegistroConsignacaoMovimento extends RegistroFilho {

    public interface BD {
        String  CONSIGNACAO = "cc_consignacaomovimento_consignacao_id",
                DATA = "cc_consignacaomovimento_data",
                TIPO = "cc_consignacaomovimento_tipo",
                QUANTIDADE = "cc_consignacaomovimento_quantidade",
                ORIGEM = "cc_consignacaomovimento_origem",
                DOCUMENTO = "cc_consignacaomovimento_documento",
                OBS = "cc_consignacaomovimento_obs";
    }

    /**
    * Gerar nova instancia
    */
    public RegistroConsignacaoMovimento() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.CONSIGNACAO);
    }

    @Override
    public ConsignacaoMovimento[] getFilhos() {
       ConsignacaoMovimento[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.CONSIGNACAO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new ConsignacaoMovimento[tabela.getTotalRegistros()];
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
        ConsignacaoMovimento modelo = (ConsignacaoMovimento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONSIGNACAO, FormatacaoSQL.getDadoFormatado(
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
            sql.insereColuna(BD.ORIGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOrigem().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObservacao(),
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
        ConsignacaoMovimento modelo = (ConsignacaoMovimento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.CONSIGNACAO, FormatacaoSQL.getDadoFormatado(
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
            sql.insereColuna(BD.ORIGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOrigem().toString(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.DOCUMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDocumento(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObservacao(),
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
        ConsignacaoMovimento modelo = null;
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

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_consignacaomovimento");
    }

    private ConsignacaoMovimento lerRegistro(TabelaBD tabela) {
        ConsignacaoMovimento modelo = null;
        if (tabela != null) {
            modelo = new ConsignacaoMovimento();
            modelo.setObs(LerCampo.getString(tabela.getColuna(BD.OBS)));
            modelo.setQuantidade(LerCampo.getDecimal(tabela.getColuna(BD.QUANTIDADE)));
            modelo.setOrigem(
                    ConsignacaoMovimento.Origem.valueOf(LerCampo.getString(tabela.getColuna(BD.ORIGEM))),
                    ConsignacaoMovimento.Tipo.valueOf(LerCampo.getString(tabela.getColuna(BD.TIPO))),
                    LerCampo.getString(tabela.getColuna(BD.DOCUMENTO)),
                    LerCampo.getData(tabela.getColuna(BD.DATA))
                    );
        }
        return modelo;
    } 

}///:~
