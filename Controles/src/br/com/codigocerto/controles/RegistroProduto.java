/*
 * Codigo Certo
 * RegistroProduto.java
 * Criado em 3 de Julho de 2007, 16:10
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Produto;
import java.util.Date;

/**
 * @author lis
 */
public class RegistroProduto extends Registro {
    
    public interface BD {
        String  ID = "cc_produtos_id", 
                NOME = "cc_produtos_nome",
                CODIGO = "cc_produtos_codigo",
                VALIDADE_INICIO = "cc_produtos_validadeinicio",
                VALIDADE_FIM = "cc_produtos_validadefim",
                CODIGO_NCM = "cc_produtos_codigoncm", 
                UNIDADE_MEDIDA = "cc_produtos_unidadesmedida_id", 
                GRUPO_TRIBUTARIO = "cc_produtos_grupostributarios_id",
                GRUPO_TRIBUTARIO_ENTRADA = "cc_produtos_grupostributarios_entrada_id";
    }
    
    /** Criar nova instancia de RegistroProduto */
    public RegistroProduto() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Produto modelo = (Produto) dados;
        
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
            sql.insereColuna(BD.CODIGO_NCM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoNCM(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALIDADE_INICIO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidadeInicio(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALIDADE_FIM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidadeFim(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getUnidade().getId());
            sql.insereColuna(BD.UNIDADE_MEDIDA, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            conv.setValorBase(modelo.getGrupoTributario().getId());
            sql.insereColuna(BD.GRUPO_TRIBUTARIO, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            if (modelo.getGrupoTributarioEntrada() != null) {
                sql.insereColuna(BD.GRUPO_TRIBUTARIO_ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                           modelo.getGrupoTributarioEntrada().getId(),
                                                           TiposDadosQuery.NUMERO)
                                                           );
            } else {
                sql.insereColuna(BD.GRUPO_TRIBUTARIO_ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                           "null",
                                                           TiposDadosQuery.NUMERO)
                                                           );
            }
            
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
        Produto modelo = (Produto) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.CODIGO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CODIGO_NCM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoNCM(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALIDADE_INICIO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidadeInicio(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.VALIDADE_FIM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidadeFim(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            ConversorTipos conv = new ConversorTipos();
            conv.setValorBase(modelo.getUnidade().getId());
            sql.insereColuna(BD.UNIDADE_MEDIDA, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            conv.setValorBase(modelo.getGrupoTributario().getId());
            sql.insereColuna(BD.GRUPO_TRIBUTARIO, FormatacaoSQL.getDadoFormatado(
                                                       conv.getString(), 
                                                       TiposDadosQuery.NUMERO)
                                                       );
            if (modelo.getGrupoTributarioEntrada() != null) {
                sql.insereColuna(BD.GRUPO_TRIBUTARIO_ENTRADA, FormatacaoSQL.getDadoFormatado(
                                                           modelo.getGrupoTributarioEntrada().getId(),
                                                           TiposDadosQuery.NUMERO)
                                                           );
            } else {
                sql.insereColuna(BD.GRUPO_TRIBUTARIO_ENTRADA, FormatacaoSQL.getDadoFormatado(
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

    @Override
    public Produto getDados(long id) {
        RegistroGrupoTributario regGrupoTrib = new RegistroGrupoTributario();
        Produto modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Produto();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            if (tabela.getColuna(BD.CODIGO) != null) {
                modelo.setCodigo(tabela.getColuna(BD.CODIGO).toString());
            }
            if (tabela.getColuna(BD.CODIGO_NCM) != null) {
                modelo.setCodigoNCM(tabela.getColuna(BD.CODIGO_NCM).toString());
            }
            
            modelo.setValidadeInicio((Date) tabela.getColuna(BD.VALIDADE_INICIO));
            modelo.setValidadeFim((Date) tabela.getColuna(BD.VALIDADE_FIM));

            long idUnidade = LerCampo.getLong(tabela.getColuna(BD.UNIDADE_MEDIDA));
            if (idUnidade > 0L) {
                RegistroUnidadeMedida regUnidade = new RegistroUnidadeMedida();
                modelo.setUnidade(regUnidade.getDados(idUnidade));
            }
            
            long idGrupoTrib = LerCampo.getLong(tabela.getColuna(BD.GRUPO_TRIBUTARIO));
            if (idGrupoTrib > 0L) {
                modelo.setGrupoTriburario(regGrupoTrib.getDados(idGrupoTrib));
            }
            modelo.setGrupoTriburarioEntrada(
                    regGrupoTrib.getDados(LerCampo.getLong(tabela.getColuna(BD.GRUPO_TRIBUTARIO_ENTRADA))));

        }
        
        return modelo;
        
    }        

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_produtos");
        ligacao.setCampoId(BD.ID);
        
    }

} ///~
