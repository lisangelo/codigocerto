/*
 * Codigo Certo
 * RegistroFuncionario.java
 * @author lis
 * Criado em 22 de Novembro de 2007, 14:07
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Funcionario;

public class RegistroFuncionario extends Registro {
    
    public interface BD {
        String  ID = "cc_funcionarios_id", 
                NOME = "cc_funcionarios_nome";
    }

    /** Criar nova instancia de RegistroFuncionario */
    public RegistroFuncionario() {
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Funcionario modelo = (Funcionario) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
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
        Funcionario modelo = (Funcionario) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
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
    public Funcionario getDados(long id) {
        Funcionario modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Funcionario();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_funcionarios");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Funcionario.Campos.ID);
        campo.setIU("");
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(Funcionario.Campos.NOME);
        campo.setIU("");
        estabelecerCampo(campo);
    }
    
} ///~
