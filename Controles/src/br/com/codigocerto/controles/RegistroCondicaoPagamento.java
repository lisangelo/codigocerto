/*
 * Codigo Certo
 * RegistroCondicaoPagamento.java
 * @author lis
 * Criado em 14 de Novembro de 2007, 17:52
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Modelo;
import br.com.codigocerto.modelos.ParcelaCondicaoPagamento;
import br.com.codigocerto.modelos.CondicaoPagamento;
import java.math.BigDecimal;

public class RegistroCondicaoPagamento extends Registro {
    
    public interface BD {
        String  ID = "cc_condicoespagamento_id", 
                NOME = "cc_condicoespagamento_nome",
                NUMERO_PARCELAS = "cc_condicoespagamento_numeroparcelas",
                PERCENTUAL_ACRESCIMO = "cc_condicoespagamento_acrescimo",
                PERCENTUAL_DESCONTO = "cc_condicoespagamento_desconto";
    }
    
    private RegistroCondicaoPagamentoParcela regParcela = new RegistroCondicaoPagamentoParcela();
    private long _id = 0;

    /** Criar nova instancia de RegistroCondicaoPagamento */
    public RegistroCondicaoPagamento() {
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        CondicaoPagamento modelo = (CondicaoPagamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            conv.setValorBase(modelo.getId());
            sql.insereColuna(BD.ID, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO_PARCELAS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumeroParcelas(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PERCENTUAL_ACRESCIMO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPercentualAcrescimo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PERCENTUAL_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPercentualDesconto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                long idMestre = modelo.getId();
                ok = insereParcelas(idMestre, modelo, trans);
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }
        
        return ok;
    }

    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        CondicaoPagamento modelo = (CondicaoPagamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO_PARCELAS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumeroParcelas(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PERCENTUAL_ACRESCIMO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPercentualAcrescimo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PERCENTUAL_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPercentualDesconto(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());

            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = eliminaParcelas(modelo.getId(), trans);
                if (ok) {
                    ok = insereParcelas(modelo.getId(), modelo, trans);
                }
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }
        
        return ok;
    }

    public boolean excluir(long id) {
         boolean ok = false;
         
         TransacaoBD trans = ServidorBD.getTransacao();
         
         eliminaParcelas(id, trans);
         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(ligacao.getCampoId() + " = " + id);
         if (trans.executaUpdate(sql.getQuery()) >= 1) {
             ok = true;
         }
         
         return ok;
    }
        
    public CondicaoPagamento getDados(long id) {
        CondicaoPagamento modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            ConversorTipos conv = new ConversorTipos();
            modelo = new CondicaoPagamento();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setNumeroParcelas(LerCampo.getInt(tabela.getColuna(BD.NUMERO_PARCELAS)));
            modelo.setPercentualAcrescimo((BigDecimal)tabela.getColuna(BD.PERCENTUAL_ACRESCIMO));
            modelo.setPercentualDesconto((BigDecimal)tabela.getColuna(BD.PERCENTUAL_DESCONTO));

            leParcelas(modelo);
        }
        
        return modelo;
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_condicoespagamento");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(CondicaoPagamento.Campos.ID);
        campo.setIU(CondicaoPagamento.Dicas.ID);
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(CondicaoPagamento.Campos.NOME);
        campo.setIU(CondicaoPagamento.Dicas.NOME);
        estabelecerCampo(campo);
        /* Numero de parcelas */
        campo = new Campo();
        campo.setBanco(BD.NUMERO_PARCELAS);
        campo.setModelo(CondicaoPagamento.Campos.NUMERO_PARCELAS);
        campo.setIU(CondicaoPagamento.Dicas.NUMERO_PARCELAS);
        estabelecerCampo(campo);
        /* Acrescimo */
        campo = new Campo();
        campo.setBanco(BD.PERCENTUAL_ACRESCIMO);
        campo.setModelo(CondicaoPagamento.Campos.PERCENTUAL_ACRESCIMO);
        campo.setIU(CondicaoPagamento.Dicas.PERCENTUAL_ACRESCIMO);
        estabelecerCampo(campo);
        /* Desconto */
        campo = new Campo();
        campo.setBanco(BD.PERCENTUAL_DESCONTO);
        campo.setModelo(CondicaoPagamento.Campos.PERCENTUAL_DESCONTO);
        campo.setIU(CondicaoPagamento.Dicas.PERCENTUAL_DESCONTO);
        estabelecerCampo(campo);
    }

   private boolean insereParcelas(long idMestre, CondicaoPagamento condicao, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regParcela.setTransacao(trans);
       regParcela.setIdMestre(idMestre);
       int num = 0;
       
       while (ok && num < condicao.getNumeroParcelas()) {
           ok = regParcela.setDadosInsercao(condicao.getParcela(num));
           num++;
       }
       
       return ok;
   }

   private boolean eliminaParcelas(long idMestre, TransacaoBD trans) {
       boolean ok = false;
       
       regParcela.setIdMestre(idMestre);
       regParcela.setTransacao(trans);
       if (regParcela.eliminaParcelasCondicao() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   private void leParcelas(CondicaoPagamento condicao) {
       
       regParcela.setIdMestre(condicao.getId());
       ParcelaCondicaoPagamento[] itens = regParcela.getParcelasCondicao();
       for (int i = 0; i < itens.length; i++) {
           condicao.adicionaParcela(itens[i]);
       }
       
   }

    
} ///~
