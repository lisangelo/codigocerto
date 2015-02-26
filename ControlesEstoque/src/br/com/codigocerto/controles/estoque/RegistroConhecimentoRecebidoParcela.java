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

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.RegistroFilho;
import br.com.codigocerto.controles.financeiro.RegistroTituloAPagar;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.NotaFiscalRecebidaParcela;
import br.com.codigocerto.modelos.financeiro.TituloAPagar;

/**
 * @author lis
 */
public class RegistroConhecimentoRecebidoParcela extends RegistroFilho {

    private enum TipoTitulo {
        PAG, REC;
    }
    
    public interface BD {
        String  ID = "cc_conhecimentosrecebidosparcelas_id", 
                   NUMERO = "cc_conhecimentosrecebidosparcelas_numero", 
                   VALOR = "cc_conhecimentosrecebidosparcelas_valor",
                   TITULO_TIPO = "cc_conhecimentosrecebidosparcelas_titulotipo",
                   TITULO = "cc_conhecimentosrecebidosparcelas_titulo_id",
                   CONHECIMENTO_RECEBIDO = "cc_conhecimentosrecebidosparcelas_conhecimentosrecebidos_id";
    }

    /**
    * Gerar nova instancia de RegistroConhecimentoRecebidoParcela
    */
    public RegistroConhecimentoRecebidoParcela() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        long idTitulo = 0;
        NotaFiscalRecebidaParcela modelo = (NotaFiscalRecebidaParcela) dados;
        RegistroTituloAPagar regTituloAPagar = new RegistroTituloAPagar();
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONHECIMENTO_RECEBIDO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if(modelo.getTitulo() instanceof TituloAPagar) {
                sql.insereColuna(BD.TITULO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                                TipoTitulo.PAG.toString(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
                if (regTituloAPagar.setDadosInsercao(modelo.getTitulo())) {
                    idTitulo = regTituloAPagar.localizarTitulo((TituloAPagar)modelo.getTitulo(), _trans);
                }
            } else {
                sql.insereColuna(BD.TITULO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                                TipoTitulo.REC.toString(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(idTitulo), 
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
        NotaFiscalRecebidaParcela modelo = (NotaFiscalRecebidaParcela) dados;
        RegistroTituloAPagar regTituloAPagar = new RegistroTituloAPagar();
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.CONHECIMENTO_RECEBIDO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValor(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if(modelo.getTitulo() instanceof TituloAPagar) {
                sql.insereColuna(BD.TITULO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                                TipoTitulo.PAG.toString(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
                regTituloAPagar.setDadosAlteracao(modelo.getTitulo());

            } else {
                sql.insereColuna(BD.TITULO_TIPO, FormatacaoSQL.getDadoFormatado(
                                                                TipoTitulo.REC.toString(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            sql.insereColuna(BD.TITULO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(modelo.getTitulo().getId()), 
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
        NotaFiscalRecebidaParcela modelo = null;
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
        ligacao.setTabela("cc_conhecimentosrecebidosparcelas");
        ligacao.setCampoId(BD.ID);
    }
    
    @Override
    public int eliminaFilhos() {
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.CONHECIMENTO_RECEBIDO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       if (tabela.primeiro()) {
           RegistroTituloAPagar regTituloPagar = new RegistroTituloAPagar();
           do {
               TipoTitulo tipo = TipoTitulo.valueOf(tabela.getColuna(BD.TITULO_TIPO).toString());
                if (tipo.equals(TipoTitulo.PAG)) {
                    regTituloPagar.excluir(LerCampo.getLong(tabela.getColuna(BD.TITULO)));
                }
           }
           while (tabela.proximo());
       }
        return eliminaFilhos(BD.CONHECIMENTO_RECEBIDO);
    }

    @Override
    public NotaFiscalRecebidaParcela[] getFilhos() {
       NotaFiscalRecebidaParcela[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.CONHECIMENTO_RECEBIDO + " = " + _idPai);
       sql.insereOrdem(RegistroConhecimentoRecebidoParcela.BD.NUMERO);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       NotaFiscalRecebidaParcela filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new NotaFiscalRecebidaParcela[tabela.getTotalRegistros()];
           do {
                filho = lerRegistro(tabela);
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
        
       return filhos;
    }
    
    private NotaFiscalRecebidaParcela lerRegistro(TabelaBD tabela) {
        ConversorTipos conv = new ConversorTipos();
        NotaFiscalRecebidaParcela modelo = null;
        if (tabela != null) {
            modelo = new NotaFiscalRecebidaParcela();
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            conv.setValorBase(tabela.getColuna(BD.NUMERO).toString());
            modelo.setNumero(conv.getInteger());
            modelo.setValor((BigDecimal) tabela.getColuna(BD.VALOR));
            String textoTipo = tabela.getColuna(BD.TITULO_TIPO).toString();
            if (textoTipo.equals(TipoTitulo.PAG.toString())) {
                RegistroTituloAPagar regTituloPagar = new RegistroTituloAPagar();
                modelo.setTitulo(regTituloPagar.getDados(LerCampo.getLong(tabela.getColuna(BD.TITULO))));
            }
        }
        return modelo;
    } 

}///~
