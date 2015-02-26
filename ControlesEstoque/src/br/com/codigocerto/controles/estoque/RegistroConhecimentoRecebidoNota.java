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

/**
 * @author lis
 */
public class RegistroConhecimentoRecebidoNota extends RegistroFilho {

    public interface BD {
        String  ID = "cc_conhecimentosrecebidosnotas_id", 
                NOTA_FISCAL = "cc_conhecimentosrecebidosnotas_notasfiscaisrecebidas_id",
                CONHECIMENTO_RECEBIDO = "cc_conhecimentosrecebidosnotas_conhecimentosrecebidos_id";    
    }

    private RegistroNotaFiscalRecebida regNota = new RegistroNotaFiscalRecebida();
    
    /**
    * Gerar nova instancia de RegistroCohecimentoRecebidoNota
    */
    public RegistroConhecimentoRecebidoNota() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.CONHECIMENTO_RECEBIDO);
    }

    @Override
    public NotaFiscalRecebida[] getFilhos() {
       NotaFiscalRecebida[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.CONHECIMENTO_RECEBIDO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new NotaFiscalRecebida[tabela.getTotalRegistros()];
           do {
                filhos[num] = regNota.getDados(LerCampo.getLong(tabela.getColuna(BD.NOTA_FISCAL)));
                num++;
           }
           while (tabela.proximo());
       }
        
       return filhos;
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        NotaFiscalRecebida modelo = (NotaFiscalRecebida) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONHECIMENTO_RECEBIDO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getId());
            sql.insereColuna(BD.NOTA_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
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
        NotaFiscalRecebida modelo = (NotaFiscalRecebida) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONHECIMENTO_RECEBIDO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getId());
            sql.insereColuna(BD.NOTA_FISCAL, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
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
    public NotaFiscalRecebida getDados(long id) {
        NotaFiscalRecebida modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = regNota.getDados(LerCampo.getLong(tabela.getColuna(BD.NOTA_FISCAL)));
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_conhecimentosrecebidosnotas");
        ligacao.setCampoId(BD.ID);
    }

}///~
