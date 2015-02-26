/*
 * Codigo Certo
 * RegistroUnidadeMedida.java
 * Criado em 2 de Julho de 2007, 13:15
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.modelos.UnidadeMedida;
import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;

/**
 * @author lis
 */
public class RegistroUnidadeMedida extends Registro {

    public interface BD {
        String  ID = "cc_unidadesmedida_id", 
                NOME = "cc_unidadesmedida_nome",
                SIGLA = "cc_unidadesmedida_sigla"; 
    }
    
    /** Criar nova instancia de RegistroUnidadeMedida */
    public RegistroUnidadeMedida() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        UnidadeMedida modelo = (UnidadeMedida) dados;
        
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
        UnidadeMedida modelo = (UnidadeMedida) dados;
        
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
    public UnidadeMedida getDados(long id) {
        UnidadeMedida modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new UnidadeMedida();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setSigla(tabela.getColuna(BD.SIGLA).toString());
        }
        tabela.fechar();
        
        return modelo;
        
    }

    public UnidadeMedida getDados(String sigla) {
        UnidadeMedida modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.SIGLA + " = '" + sigla + "'");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new UnidadeMedida();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setSigla(tabela.getColuna(BD.SIGLA).toString());
        }
        tabela.fechar();
        
        return modelo;
        
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_unidadesmedida");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(UnidadeMedida.Campos.ID);
        campo.setIU("");
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(UnidadeMedida.Campos.NOME);
        campo.setIU("");
        estabelecerCampo(campo);
        /* Sigla */
        campo = new Campo();
        campo.setBanco(BD.SIGLA);
        campo.setModelo(UnidadeMedida.Campos.SIGLA);
        campo.setIU("");
        estabelecerCampo(campo);
    }
    
} ///~
