/*
 * Codigo Certo
 * RegistroGrupo.java
 * Criado em 5 de Julho de 2007, 14:04
 */

package br.com.codigocerto.controles.financeiro;

import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.financeiro.Banco;

/**
 * @author lis
 */
public class RegistroBanco extends Registro {
    
    public interface BD {
        String  ID = "cc_bancos_id", 
                NOME = "cc_bancos_nome",
                CODIGO = "cc_bancos_codigo"; 
    }
    
    /** Criar nova instancia */
    public RegistroBanco() {
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Banco modelo = (Banco) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigo(), 
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

    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        Banco modelo = (Banco) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.CODIGO, 
                              FormatacaoSQL.getDadoFormatado(modelo.getCodigo(), 
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

    public Banco getDados(long id) {
        Banco modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Banco();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setCodigo(tabela.getColuna(BD.CODIGO).toString());
        }
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_bancos");
        ligacao.setCampoId(BD.ID);
        
    }

} ///~