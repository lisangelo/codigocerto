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

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.modelos.Moeda;

/**
 * @author lis
 */
public class RegistroMoeda extends Registro {

    public interface BD {
        String  ID = "cc_moedas_id", 
                   NOME = "cc_moedas_nome",
                   SIGLA = "cc_moedas_sigla";
    }

    /**
    * Gerar nova instancia de RegistroMoeda
    */
    public RegistroMoeda() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Moeda modelo = (Moeda) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SIGLA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSigla(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            
            if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
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
        Moeda modelo = (Moeda) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.SIGLA, 
                              FormatacaoSQL.getDadoFormatado(modelo.getSigla(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
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
    public Moeda getDados(long id) {
        Moeda modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = getModelo(tabela);
        }
        
        return modelo;
    }

    /**
     * Obtem todas as moedas cadastradas
     * @return matriz de moedas
     */
    public Moeda[] getMoedas() {
        Moeda[] moedas = null;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        Moeda moeda;
        if (tabela.primeiro()) {
            moedas = new Moeda[tabela.getTotalRegistros()];
            int i = 0;
            do {
                moeda = getModelo(tabela);
                moedas[i] = moeda;
                i++;
            } while (tabela.proximo());
        }

        return moedas;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_moedas");
        ligacao.setCampoId(BD.ID);
    }

    private Moeda getModelo(TabelaBD tabela) {
        Moeda modelo = new Moeda();
        modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
        modelo.setNome(tabela.getColuna(BD.NOME).toString());
        modelo.setSigla(tabela.getColuna(BD.SIGLA).toString());

        return modelo;
    }

}///:~
