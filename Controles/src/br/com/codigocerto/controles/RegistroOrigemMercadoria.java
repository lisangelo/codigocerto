/*
 * Codigo Certo
 * RegistroOrigemMercadoria.java
 * Criado em 22 de Junho de 2007, 14:04
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.OrigemMercadoria;

/**
 * @author lis
 */
public class RegistroOrigemMercadoria extends Registro {
    
    public interface BD {
        String  ID = "cc_origensmercadoria_id", 
                NOME = "cc_origensmercadoria_nome",
                CODIGO = "cc_origensmercadoria_codigo",
                ESTRANGEIRO = "cc_origensmercadoria_estrangeiro",
                MERCADOINTERNO = "cc_origensmercadoria_mercadointerno";
    }
    
    /** Criar nova instancia de RegistroOrigemMercadoria */
    public RegistroOrigemMercadoria() {
    }

    /*
     *Metodo para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    protected void estabelecerLigacao() {
        
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_origensmercadoria");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
        
    }

    /*
     *Metodo para estabelecer vinculos entre campos da tabela e propriedades da classe
     */
    protected void estabelecerCampos() {
        
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(OrigemMercadoria.Campos.ID);
        campo.setIU(OrigemMercadoria.Dicas.ID);
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(OrigemMercadoria.Campos.NOME);
        campo.setIU(OrigemMercadoria.Dicas.NOME);
        estabelecerCampo(campo);
        /* Codigo */
        campo = new Campo();
        campo.setBanco(BD.CODIGO);
        campo.setModelo(OrigemMercadoria.Campos.CODIGO);
        campo.setIU(OrigemMercadoria.Dicas.CODIGO);
        estabelecerCampo(campo);
        /* Estrangeiro */
        campo = new Campo();
        campo.setBanco(BD.ESTRANGEIRO);
        campo.setModelo(OrigemMercadoria.Campos.ESTRANGEIRO);
        campo.setIU(OrigemMercadoria.Dicas.ESTRANGEIRO);
        estabelecerCampo(campo);
        /* Mercado interno */
        campo = new Campo();
        campo.setBanco(BD.MERCADOINTERNO);
        campo.setModelo(OrigemMercadoria.Campos.MERCADOINTERNO);
        campo.setIU(OrigemMercadoria.Dicas.MERCADOINTERNO);
        estabelecerCampo(campo);
    }
    
    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    @Override
    public OrigemMercadoria getDados(long id) {
        
        OrigemMercadoria modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new OrigemMercadoria();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setCodigo(
                    LerCampo.getString(tabela.getColuna(BD.CODIGO)));
            
            modelo.setEstrangeiro(
                    LerCampo.getBoolean(tabela.getColuna(BD.ESTRANGEIRO)));
            modelo.setMercadoInterno(
                    LerCampo.getBoolean(tabela.getColuna(BD.MERCADOINTERNO)));
        }
        tabela.fechar();
        
        return modelo;
        
    }        

    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    public boolean setDadosInsercao(Object dados) {
        
        boolean ok;
        OrigemMercadoria modelo = (OrigemMercadoria) dados;
        
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
            sql.insereColuna(BD.ESTRANGEIRO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEstrangeiro(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna(BD.MERCADOINTERNO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMercadoInterno(), 
                                                            TiposDadosQuery.LOGICO)
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

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        OrigemMercadoria modelo = (OrigemMercadoria) dados;
        
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
            sql.insereColuna(BD.ESTRANGEIRO, 
                              FormatacaoSQL.getDadoFormatado(modelo.getEstrangeiro(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna(BD.MERCADOINTERNO, 
                              FormatacaoSQL.getDadoFormatado(modelo.getMercadoInterno(), 
                                                             TiposDadosQuery.LOGICO
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
    
} ///~
