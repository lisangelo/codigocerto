/*
 * Codigo Certo
 * RegistroGrupoTributario.java
 * Criado em 26 de Junho de 2007, 13:16
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.GrupoTributario;
import java.math.BigDecimal;

/**
 * @author lis
 */
public class RegistroGrupoTributario extends Registro {
    
    public interface BD {
        String  ID = "cc_grupostributarios_id", 
                NOME = "cc_grupostributarios_nome",
                IPI_ALIQUOTA = "cc_grupostributarios_ipialiquota",
                ICMS_ALIQUOTA = "cc_grupostributarios_icmsaliquota",
                ICMS_REDUCAOBASE = "cc_grupostributarios_icmsreducaobase",
                ICMS_BASECALCULOSUBST = "cc_grupostributarios_icmsbasecalculosubst", 
                ICMS_ST_MARGEM = "cc_grupostributarios_icmsstmargem",
                ICMS_ST_MARGEMFE = "cc_grupostributarios_icmsstmargemfe",
                ORIGEM_MERCADORIA = "cc_grupostributarios_origensmercadoria_id", 
                TRIBUTACAO_ICMS = "cc_grupostributarios_tributacoesicms_id",
                INFO_ADICIONAL = "cc_grupostributarios_infoadicional";
    }
    
    /** Criar nova instancia de RegistroGrupoTributario */
    public RegistroGrupoTributario() {
    }
    
    @Override
    protected void estabelecerLigacao() {
        
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_grupostributarios");
        ligacao.setCampoId(BD.ID);
        
    }
    
    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    @Override
    public GrupoTributario getDados(long id) {
        
        GrupoTributario modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new GrupoTributario();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            
            modelo.setIpiAliquota((BigDecimal) tabela.getColuna(BD.IPI_ALIQUOTA));
            modelo.setIcmsAliquota((BigDecimal) tabela.getColuna(BD.ICMS_ALIQUOTA));
            modelo.setIcmsReducaoBase((BigDecimal) tabela.getColuna(BD.ICMS_REDUCAOBASE));
            modelo.setIcmsBaseCalculoST((BigDecimal) tabela.getColuna(BD.ICMS_BASECALCULOSUBST));
            modelo.setIcmsSTMargem((BigDecimal) tabela.getColuna(BD.ICMS_ST_MARGEM));
            modelo.setIcmsSTMargemFE((BigDecimal) tabela.getColuna(BD.ICMS_ST_MARGEMFE));
            if (tabela.getColuna(BD.INFO_ADICIONAL) != null) {
                modelo.setInfoAdicional(LerCampo.getString(tabela.getColuna(BD.INFO_ADICIONAL)));
            }
            
            long idOrigem = LerCampo.getLong(tabela.getColuna(BD.ORIGEM_MERCADORIA));
            if (idOrigem > 0) {
                RegistroOrigemMercadoria regOrigem = new RegistroOrigemMercadoria();
                modelo.setOrigemMercadoria(regOrigem.getDados(idOrigem));
            }
            
            long idTributacao = LerCampo.getLong(tabela.getColuna(BD.TRIBUTACAO_ICMS));
            if (idTributacao > 0) {
                RegistroTributacaoIcms regTributacao = new RegistroTributacaoIcms();
                modelo.setTributacaoIcms(regTributacao.getDados(idTributacao));
            }
        }
        tabela.fechar();
        
        return modelo;
        
    }        

    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    @Override
    public boolean setDadosInsercao(Object dados) {
        
        boolean ok;
        GrupoTributario modelo = (GrupoTributario) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.IPI_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIpiAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_BASECALCULOSUBST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBaseCalculoST(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_MARGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTMargem(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ST_MARGEMFE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsSTMargemFE(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_REDUCAOBASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsReducaoBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getInfoAdicional(),
                                                            TiposDadosQuery.TEXTO)
                                                            );

            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getOrigemMercadoria().getId());
            sql.insereColuna(BD.ORIGEM_MERCADORIA, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            conv.setValorBase(modelo.getTributacaoIcms().getId());
            sql.insereColuna(BD.TRIBUTACAO_ICMS, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
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

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        GrupoTributario modelo = (GrupoTributario) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.IPI_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIpiAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_ALIQUOTA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsAliquota(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_BASECALCULOSUBST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsBaseCalculoST(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_REDUCAOBASE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIcmsReducaoBase(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getIcmsSTMargem() != null) {
                sql.insereColuna(BD.ICMS_ST_MARGEM, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsSTMargem(),
                                                                TiposDadosQuery.NUMERO)
                                                                );
            } else {
                sql.insereColuna(BD.ICMS_ST_MARGEM, FormatacaoSQL.getDadoFormatado(
                                                                "null",
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            if (modelo.getIcmsSTMargemFE() != null) {
                sql.insereColuna(BD.ICMS_ST_MARGEMFE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIcmsSTMargemFE(),
                                                                TiposDadosQuery.NUMERO)
                                                                );
            } else {
                sql.insereColuna(BD.ICMS_ST_MARGEMFE, FormatacaoSQL.getDadoFormatado(
                                                                "null",
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getOrigemMercadoria().getId());
            sql.insereColuna(BD.ORIGEM_MERCADORIA, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            conv.setValorBase(modelo.getTributacaoIcms().getId());
            sql.insereColuna(BD.TRIBUTACAO_ICMS, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            if (modelo.getInfoAdicional() != null) {
                sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getInfoAdicional(),
                                                                TiposDadosQuery.TEXTO)
                                                                );
            } else {
                sql.insereColuna(BD.INFO_ADICIONAL, FormatacaoSQL.getDadoFormatado(
                                                                "null",
                                                                TiposDadosQuery.NUMERO)
                                                                );
            }
            
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
