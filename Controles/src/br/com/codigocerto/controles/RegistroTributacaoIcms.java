/*
 * Codigo Certo
 * RegistroTributacaoIcms.java
 * Criado em 21 de Junho de 2007, 16:24
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.TributacaoIcms;

/**
 * @author lis
 */
    
public class RegistroTributacaoIcms extends Registro {

    public interface BD {
        String  ID = "cc_tributacoesicms_id", 
                NOME = "cc_tributacoesicms_nome",
                CODIGO = "cc_tributacoesicms_codigo",
                SUBSTITUICAO = "cc_tributacoesicms_substrib",
                SUBSANTERIOR = "cc_tributacoesicms_substribcobanterior",
                REDUCAO = "cc_tributacoesicms_reducao", 
                ISENTO = "cc_tributacoesicms_isento", 
                NAOTRIBUTADO = "cc_tributacoesicms_naotributado", 
                SUPENSAO = "cc_tributacoesicms_suspensao", 
                DIFERIMENTO = "cc_tributacoesicms_diferimento", 
                OUTRAS = "cc_tributacoesicms_outras";
    }
    
    /** Creates a new instance of TributacaoIcms */
    public RegistroTributacaoIcms() {
    }
    
    /*
     *Metodo para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    protected void estabelecerLigacao() {
        
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_tributacoesicms");
        ligacao.setCampoId("cc_tributacoesicms_id");
        
        estabelecerCampos();
        
    }

    /*
     *Metodo para estabelecer vinculos entre campos da tabela e propriedades da classe
     */
    protected void estabelecerCampos() {
        
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_id");
        campo.setModelo("Id");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_nome");
        campo.setModelo("Nome");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Codigo */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_codigo");
        campo.setModelo("Codigo");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Substituicao */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_substrib");
        campo.setModelo("Substituicao");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Substituicao Anterior */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_substribcobanterior");
        campo.setModelo("SubsAnterior");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Reducao */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_reducao");
        campo.setModelo("Reducao");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Isento */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_isento");
        campo.setModelo("Isento");
        campo.setIU("");
        estabelecerCampo(campo);
        /* NaoTributado */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_naotributado");
        campo.setModelo("NaoTributado");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Suspensao */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_suspensao");
        campo.setModelo("Suspensao");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Diferimento */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_diferimento");
        campo.setModelo("Diferimento");
        campo.setIU("");
        estabelecerCampo(campo);
        /* Outras */
        campo = new Campo();
        campo.setBanco("cc_tributacoesicms_outras");
        campo.setModelo("Outras");
        campo.setIU("");
        estabelecerCampo(campo);
    }
    
    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    public TributacaoIcms getDados(long id) {
        
        TributacaoIcms modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new TributacaoIcms();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(getBanco("Id")).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(getBanco("Nome")).toString());
            modelo.setCodigo(
                    LerCampo.getString(tabela.getColuna(getBanco(TributacaoIcms.Campos.CODIGO))));
            
            modelo.setSubstituicao(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.SUBSTITUICAO))));
            modelo.setSubsAnterior(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.SUBSANTERIOR))));
            modelo.setReducao(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.REDUCAO))));
            modelo.setIsento(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.ISENTO))));
            modelo.setNaoTributado(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.NAOTRIBUTADO))));
            modelo.setSuspensao(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.SUPENSAO))));
            modelo.setDiferimento(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.DIFERIMENTO))));
            modelo.setOutras(
                    LerCampo.getBoolean(tabela.getColuna(getBanco(TributacaoIcms.Campos.OUTRAS))));
        
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
        TributacaoIcms modelo = (TributacaoIcms) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna("cc_tributacoesicms_id", String.valueOf(modelo.getId()));
            sql.insereColuna("cc_tributacoesicms_nome", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_codigo", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_substrib", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSubstituicao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_substribcobanterior", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSubsAnterior(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_reducao", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getReducao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_isento", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getIsento(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_naotributado", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNaoTributado(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_suspensao", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSuspensao(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_diferimento", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getDiferimento(), 
                                                            TiposDadosQuery.LOGICO)
                                                            );
            sql.insereColuna("cc_tributacoesicms_outras", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getOutras(), 
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
        TributacaoIcms modelo = (TributacaoIcms) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna("cc_tributacoesicms_nome", 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_codigo", 
                              FormatacaoSQL.getDadoFormatado(modelo.getCodigo(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_substrib", 
                              FormatacaoSQL.getDadoFormatado(modelo.getSubstituicao(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_substribcobanterior", 
                              FormatacaoSQL.getDadoFormatado(modelo.getSubsAnterior(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_reducao", 
                              FormatacaoSQL.getDadoFormatado(modelo.getReducao(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_isento", 
                              FormatacaoSQL.getDadoFormatado(modelo.getIsento(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_naotributado", 
                              FormatacaoSQL.getDadoFormatado(modelo.getNaoTributado(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_suspensao", 
                              FormatacaoSQL.getDadoFormatado(modelo.getSuspensao(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_diferimento", 
                              FormatacaoSQL.getDadoFormatado(modelo.getDiferimento(), 
                                                             TiposDadosQuery.LOGICO
                                                            )
                            );
            sql.insereColuna("cc_tributacoesicms_outras", 
                              FormatacaoSQL.getDadoFormatado(modelo.getOutras(), 
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
