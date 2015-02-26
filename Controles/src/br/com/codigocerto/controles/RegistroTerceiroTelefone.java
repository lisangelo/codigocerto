/*
 * Codigo Certo
 * RegistroTerceiroTelefone.java
 * Criado em 24 de Outubro de 2007, 12:52
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Telefone;

/**
 * @author lis
 */
public class RegistroTerceiroTelefone extends RegistroFilho {
    
    public interface BD {
        String  ID = "cc_terceirosfones_id", 
                AREA = "cc_terceirosfones_area",
                NUMERO = "cc_terceirosfones_numero",
                RAMAL = "cc_terceirosfones_ramal",
                TIPO = "cc_terceirosfones_tipo_id",
                TERCEIRO = "cc_terceirosfones_terceiros_id";
    }

    /** Criar nova instancia de RegistroTerceiroTelefone */
    public RegistroTerceiroTelefone() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TERCEIRO);
    }

    public Telefone[] getFilhos() {
       Telefone[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TERCEIRO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       Telefone filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new Telefone[tabela.getTotalRegistros()];
           do {
                filho = new Telefone();
                filho.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                if (tabela.getColuna(BD.AREA) != null) {
                    filho.setArea(tabela.getColuna(BD.AREA).toString());
                }
                filho.setNumero(tabela.getColuna(BD.NUMERO).toString());
                if (tabela.getColuna(BD.RAMAL) != null) {
                    filho.setRamal(tabela.getColuna(BD.RAMAL).toString());
                }
                if (tabela.getColuna(BD.TIPO) != null) {
                    filho.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
                }
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
        Telefone modelo = (Telefone) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.AREA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getArea(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.RAMAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getRamal(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        Telefone modelo = (Telefone) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.AREA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getArea(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.RAMAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getRamal(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Telefone getDados(long id) {
        Telefone modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Telefone();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            if (tabela.getColuna(BD.AREA) != null) {
                modelo.setArea(tabela.getColuna(BD.AREA).toString());
            }
            modelo.setNumero(tabela.getColuna(BD.NUMERO).toString());
            if (tabela.getColuna(BD.RAMAL) != null) {
                modelo.setRamal(tabela.getColuna(BD.RAMAL).toString());
            }
            if (tabela.getColuna(BD.TIPO) != null) {
                modelo.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
            }
        }
        tabela.fechar();
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceirosfones");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Telefone.Campos.ID);
        campo.setIU(Telefone.Dicas.ID);
        estabelecerCampo(campo);
        /* Area */
        campo = new Campo();
        campo.setBanco(BD.AREA);
        campo.setModelo(Telefone.Campos.AREA);
        campo.setIU(Telefone.Dicas.AREA);
        estabelecerCampo(campo);
        /* Numero */
        campo = new Campo();
        campo.setBanco(BD.NUMERO);
        campo.setModelo(Telefone.Campos.NUMERO);
        campo.setIU(Telefone.Dicas.NUMERO);
        estabelecerCampo(campo);
        /* Ramal */
        campo = new Campo();
        campo.setBanco(BD.RAMAL);
        campo.setModelo(Telefone.Campos.RAMAL);
        campo.setIU(Telefone.Dicas.RAMAL);
        estabelecerCampo(campo);
        /* Tipo */
        campo = new Campo();
        campo.setBanco(BD.TIPO);
        campo.setModelo(Telefone.Campos.TIPO);
        campo.setIU(Telefone.Dicas.TIPO);
        estabelecerCampo(campo);
    }
    
} ///~
