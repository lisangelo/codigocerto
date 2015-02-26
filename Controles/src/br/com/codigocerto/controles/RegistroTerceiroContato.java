/*
 * Codigo Certo
 * RegistroTerceiroContato.java
 * Criado em 24 de Outubro de 2007, 13:22
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Contato;
import br.com.codigocerto.modelos.Email;
import br.com.codigocerto.modelos.Telefone;

/**
 * @author lis
 */
public class RegistroTerceiroContato extends RegistroFilho {
    
    public interface BD {
        String  ID = "cc_terceiroscontatos_id", 
                NOME = "cc_terceiroscontatos_nome",
                CARGO = "cc_terceiroscontatos_cargo",
                FONE = "cc_terceiroscontatos_fone",
                EMAIL = "cc_terceiroscontatos_email",
                TERCEIRO = "cc_terceiroscontatos_terceiros_id";
    }
    
    /** Criar nova instancia de RegistroTerceiroContato */
    public RegistroTerceiroContato() {
    }
    
    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TERCEIRO);
    }

    public Contato[] getFilhos() {
        Contato[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TERCEIRO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       Contato filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new Contato[tabela.getTotalRegistros()];
           do {
                filho = new Contato();
                filho.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                filho.setNome(tabela.getColuna(BD.NOME).toString());
                filho.setCargo(tabela.getColuna(BD.CARGO).toString());
                filho.setFone(tabela.getColuna(BD.FONE).toString());
                filho.setEmail(tabela.getColuna(BD.EMAIL).toString());
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
       tabela.fechar();
        
       return filhos;
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        Contato modelo = (Contato) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CARGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCargo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.FONE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getFone(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.EMAIL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmail(), 
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
        Contato modelo = (Contato) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CARGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCargo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.FONE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getFone(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.EMAIL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEmail(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Contato getDados(long id) {
        Contato modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Contato();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setCargo(tabela.getColuna(BD.CARGO).toString());
            modelo.setFone(tabela.getColuna(BD.FONE).toString());
            modelo.setEmail(tabela.getColuna(BD.EMAIL).toString());
        }
        tabela.fechar();
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceiroscontatos");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Contato.Campos.ID);
        campo.setIU(Contato.Dicas.ID);
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(Contato.Campos.NOME);
        campo.setIU(Contato.Dicas.NOME);
        estabelecerCampo(campo);
        /* Cargo */
        campo = new Campo();
        campo.setBanco(BD.CARGO);
        campo.setModelo(Contato.Campos.CARGO);
        campo.setIU(Contato.Dicas.CARGO);
        estabelecerCampo(campo);
        /* Telefone */
        campo = new Campo();
        campo.setBanco(BD.FONE);
        campo.setModelo(Contato.Campos.FONE);
        campo.setIU(Contato.Dicas.FONE);
        estabelecerCampo(campo);
        /* Email */
        campo = new Campo();
        campo.setBanco(BD.EMAIL);
        campo.setModelo(Contato.Campos.EMAIL);
        campo.setIU(Contato.Dicas.EMAIL);
        estabelecerCampo(campo);
    }
    
    
} ///~
