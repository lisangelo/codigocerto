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
import br.com.codigocerto.modelos.UsuarioExcecao;

/**
 * @author lis
 */
public class RegistroUsuarioExcecao extends RegistroFilho {

    public interface BD {
        String  TIPO = "cc_usuariosexcecoes_tipo",
                GERAL = "cc_usuariosexcecoes_formgeral",
                LOCAL = "cc_usuariosexcecoes_formlocal",
                USUARIO = "cc_usuariosexcecoes_usuarios_id";
    }

    /**
    * Gerar nova instancia 
    */
    public RegistroUsuarioExcecao() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        long idTitulo = 0;
        UsuarioExcecao modelo = (UsuarioExcecao) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.USUARIO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.GERAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getGeral(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOCAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLocal(),
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
        UsuarioExcecao modelo = (UsuarioExcecao) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.GERAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getGeral(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOCAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLocal(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }
    
    @Override
    public Object getDados(long id) {
        UsuarioExcecao modelo = null;
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
        ligacao.setTabela("cc_usuariosexcecoes");
    }
    
    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.USUARIO);
    }

    @Override
    public UsuarioExcecao[] getFilhos() {
       UsuarioExcecao[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.USUARIO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       UsuarioExcecao filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new UsuarioExcecao[tabela.getTotalRegistros()];
           do {
                filho = lerRegistro(tabela);
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
        
       return filhos;
    }
    
    private UsuarioExcecao lerRegistro(TabelaBD tabela) {
        UsuarioExcecao modelo = null;
        if (tabela != null) {
            modelo = new UsuarioExcecao();
            modelo.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
            modelo.setLocal(LerCampo.getInt(tabela.getColuna(BD.LOCAL)));
            modelo.setGeral(LerCampo.getInt(tabela.getColuna(BD.GERAL)));
        }
        return modelo;
    } 

}///:~
