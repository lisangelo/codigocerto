/*
 * Codigo Certo
 * RegistroTerceiroEmail.java
 * Criado em 24 de Outubro de 2007, 11:02
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Email;

/**
 * @author lis
 */
public class RegistroTerceiroEmail extends RegistroFilho {
    
    public interface BD {
        String  ID = "cc_terceirosemails_id",
                TERCEIRO = "cc_terceirosemails_terceiros_id",
                ENDERECO = "cc_terceirosemails_endereco";
    }

    /** Criar nova instancia de RegistroTerceiroEmail */
    public RegistroTerceiroEmail() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        Email modelo = (Email) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENDERECO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEndereco(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );

            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        Email modelo = (Email) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENDERECO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEndereco(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Email getDados(long id) {
        Email modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Email();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setEndereco(tabela.getColuna(BD.ENDERECO).toString());
        }
        tabela.fechar();
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceirosemails");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Email.Campos.ID);
        campo.setIU(Email.Dicas.ID);
        estabelecerCampo(campo);
        /* Endereco */
        campo = new Campo();
        campo.setBanco(BD.ENDERECO);
        campo.setModelo(Email.Campos.ENDERECO);
        campo.setIU(Email.Dicas.ENDERECO);
        estabelecerCampo(campo);
    }

    public Email[] getFilhos() {
        Email[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TERCEIRO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       Email filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new Email[tabela.getTotalRegistros()];
           do {
                filho = new Email();
                filho.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                filho.setEndereco(tabela.getColuna(BD.ENDERECO).toString());
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
       tabela.fechar();
        
       return filhos;
    }

    public int eliminaFilhos() {
        return eliminaFilhos(BD.TERCEIRO);
    }
    
} ///~
