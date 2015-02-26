/*
 * Codigo Certo
 * RegistroTerceiroDocumentos.java
 * @author lis
 * Criado em 24 de Outubro de 2007, 16:19
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.DocumentosTerceiro;
import br.com.codigocerto.modelos.CNPJ;
import br.com.codigocerto.modelos.CPF;

public class RegistroTerceiroDocumentos extends RegistroFilho {
    
    public interface BD {
        String  ID = "cc_terceirosdocumentos_id", 
                CNPJ = "cc_terceirosdocumentos_cnpj",
                IE = "cc_terceirosdocumentos_ie",
                IDENTIDADE = "cc_terceirosdocumentos_identidade",
                CPF = "cc_terceirosdocumentos_cpf",
                TIPO_PESSOA = "cc_terceirosdocumentos_pessoa_id",
                TERCEIRO = "cc_terceirosdocumentos_terceiros_id";
    }

    /** Criar nova instancia de RegistroTerceiroDocumentos */
    public RegistroTerceiroDocumentos() {
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TERCEIRO);
    }

    @Override
    public DocumentosTerceiro[] getFilhos() {
       DocumentosTerceiro[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TERCEIRO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       DocumentosTerceiro filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new DocumentosTerceiro[tabela.getTotalRegistros()];
           do {
                filho = new DocumentosTerceiro();
                filho.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                if (tabela.getColuna(BD.TIPO_PESSOA) != null) {
                    filho.setPessoa(tabela.getColuna(BD.TIPO_PESSOA).toString());
                }
                if (tabela.getColuna(BD.CNPJ) != null) {
                    filho.setCNPJ(new CNPJ(tabela.getColuna(BD.CNPJ).toString()));
                }
                if (tabela.getColuna(BD.IE) != null) {
                    filho.setInscricaoEstadual(tabela.getColuna(BD.IE).toString());
                }
                if (tabela.getColuna(BD.IDENTIDADE) != null) {
                    filho.setIdentidade(tabela.getColuna(BD.IDENTIDADE).toString());
                }
                if (tabela.getColuna(BD.CPF) != null) {
                    filho.setCPF(new CPF(tabela.getColuna(BD.CPF).toString()));
                }
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
       tabela.fechar();
        
       return filhos;
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        DocumentosTerceiro modelo = (DocumentosTerceiro) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO_PESSOA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPessoa(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            if (modelo.getCNPJ() != null) {                                                    
                sql.insereColuna(BD.CNPJ, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCNPJ().getDocumento(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getInscricaoEstadual() != null) {                                                    
                sql.insereColuna(BD.IE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getInscricaoEstadual(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getIdentidade() != null) {                                                    
                sql.insereColuna(BD.IDENTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIdentidade(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getCPF() != null) {                                                    
                sql.insereColuna(BD.CPF, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCPF().getDocumento(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }

            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        DocumentosTerceiro modelo = (DocumentosTerceiro) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO_PESSOA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPessoa(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            if (modelo.getCNPJ() != null) {                                                    
                sql.insereColuna(BD.CNPJ, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCNPJ().getDocumento(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getInscricaoEstadual() != null) {                                                    
                sql.insereColuna(BD.IE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getInscricaoEstadual(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getIdentidade() != null) {                                                    
                sql.insereColuna(BD.IDENTIDADE, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getIdentidade(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            if (modelo.getCPF() != null) {                                                    
                sql.insereColuna(BD.CPF, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getCPF().getDocumento(), 
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public DocumentosTerceiro getDados(long id) {
        DocumentosTerceiro modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new DocumentosTerceiro();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            if (tabela.getColuna(BD.TIPO_PESSOA) != null) {
                modelo.setPessoa(tabela.getColuna(BD.TIPO_PESSOA).toString());
            }
            if (tabela.getColuna(BD.CNPJ) != null) {
                modelo.setCNPJ(new CNPJ(tabela.getColuna(BD.CNPJ).toString()));
            }
            if (tabela.getColuna(BD.IE) != null) {
                modelo.setInscricaoEstadual(tabela.getColuna(BD.IE).toString());
            }
            if (tabela.getColuna(BD.IDENTIDADE) != null) {
                modelo.setIdentidade(tabela.getColuna(BD.IDENTIDADE).toString());
            }
            if (tabela.getColuna(BD.CPF) != null) {
                modelo.setCPF(new CPF(tabela.getColuna(BD.CPF).toString()));
            }
        }
        tabela.fechar();
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceirosdocumentos");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(DocumentosTerceiro.Campos.ID);
        campo.setIU(DocumentosTerceiro.Dicas.ID);
        estabelecerCampo(campo);
        /* CNPJ */
        campo = new Campo();
        campo.setBanco(BD.CNPJ);
        campo.setModelo(DocumentosTerceiro.Campos.CNPJ);
        campo.setIU(DocumentosTerceiro.Dicas.CNPJ);
        estabelecerCampo(campo);
        /* IE */
        campo = new Campo();
        campo.setBanco(BD.IE);
        campo.setModelo(DocumentosTerceiro.Campos.IE);
        campo.setIU(DocumentosTerceiro.Dicas.IE);
        estabelecerCampo(campo);
        /* Identidade */
        campo = new Campo();
        campo.setBanco(BD.IDENTIDADE);
        campo.setModelo(DocumentosTerceiro.Campos.IDENTIDADE);
        campo.setIU(DocumentosTerceiro.Dicas.IDENTIDADE);
        estabelecerCampo(campo);
        /* CPF */
        campo = new Campo();
        campo.setBanco(BD.CPF);
        campo.setModelo(DocumentosTerceiro.Campos.CPF);
        campo.setIU(DocumentosTerceiro.Dicas.CPF);
        estabelecerCampo(campo);
        /* Pessoa */
        campo = new Campo();
        campo.setBanco(BD.TIPO_PESSOA);
        campo.setModelo(DocumentosTerceiro.Campos.TIPO_PESSOA);
        campo.setIU(DocumentosTerceiro.Dicas.TIPO_PESSOA);
        estabelecerCampo(campo);
    }
    
} ///~
