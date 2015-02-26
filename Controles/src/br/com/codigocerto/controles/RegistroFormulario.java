/*
 * Codigo Certo
 * RegistroBairro.java
 * Criado em 24 de Outubro de 2007, 10:12
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.modelos.Formulario;

/**
 * @author lis
 */
public class RegistroFormulario extends Registro {
    
    public interface BD {
        String  TIPO = "cc_formularios_tipo",
                DESCRICAO = "cc_formularios_descricao",
                GERAL = "cc_formularios_geral",
                LOCAL = "cc_formularios_local";
    }

    /** Criar nova instancia */
    public RegistroFormulario() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Formulario modelo = (Formulario) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.GERAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getGeral(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOCAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLocal(),
                                                            TiposDadosQuery.NUMERO)
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
        Formulario modelo = (Formulario) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.DESCRICAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDescricao(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.GERAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getGeral(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOCAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLocal(),
                                                            TiposDadosQuery.NUMERO)
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
    public Formulario getDados(long id) {
        Formulario modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        
        return modelo;
    }

    public Formulario[] getDados() {
        Formulario[] lista = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereOrdem(BD.TIPO + ", " + BD.DESCRICAO);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            lista = new Formulario[tabela.getTotalRegistros()];
            int i = 0;
            do {
                lista[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }

        return lista;
    }

    private Formulario lerRegistro(TabelaBD tabela) {
        Formulario form = new Formulario();

        form.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
        form.setDescricao(LerCampo.getString(tabela.getColuna(BD.DESCRICAO)));
        form.setGeral(LerCampo.getInt(tabela.getColuna(BD.GERAL)));
        form.setLocal(LerCampo.getInt(tabela.getColuna(BD.LOCAL)));

        return form;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_formularios");
    }

} ///:~
