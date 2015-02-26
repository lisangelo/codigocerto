/*
 * Codigo Certo
 * RegistroOrcamentoItem.java
 * Criado em 2 de Outubro de 2007, 14:02
 */

package br.com.codigocerto.controles;

import java.math.BigDecimal;
import java.util.Date;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Campo;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.controles.RegistroProduto;
import br.com.codigocerto.modelos.ItemOrcamento;
import br.com.codigocerto.modelos.Produto;

/**
 * @author lis
 */
public class RegistroOrcamentoItem extends Registro {
 
    private long _idOrcamento = 0;
    private TransacaoBD _trans = null;
    
    public interface BD {
        String  ID = "cc_orcamentositens_id", 
                ORCAMENTO = "cc_orcamentositens_orcamentos_id",
                PRODUTO = "cc_orcamentositens_produtos_id",
                ENTREGA = "cc_orcamentositens_entrega",
                QUANTIDADE = "cc_orcamentositens_quantidade",
                UNITARIO = "cc_orcamentositens_precounitario",
                VALOR_DESCONTO = "cc_orcamentositens_valordesconto",
                VALOR_TOTAL = "cc_orcamentositens_valortotal",
                OBS = "cc_orcamentositens_observacao";
    }
    
    /**
     * Criar nova instancia de RegistroOrcamentoItem
     */
    public RegistroOrcamentoItem() {
    }

    /**
     * Configura o id do orcamento
     * @param id do orcamento
     */
    public void setIdOrcamento(long id) {
        _idOrcamento = id;
    }
    
    /**
     * Configura a transacao do bd a ser utilizada
     * @param transacao
     */
    public void setTransacao(TransacaoBD trans) {
        _trans = trans;
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        ItemOrcamento modelo = (ItemOrcamento) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ORCAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idOrcamento), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(modelo.getProduto().getId()), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            sql.insereColuna(BD.VALOR_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorDesconto().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.UNITARIO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorUnitario(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTREGA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrega(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObs(), 
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
        ItemOrcamento modelo = (ItemOrcamento) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ORCAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idOrcamento), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(modelo.getProduto().getId()), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.QUANTIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getQuantidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            sql.insereColuna(BD.VALOR_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorDesconto().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.UNITARIO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorUnitario(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTREGA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrega(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObs(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Object getDados(long id) {
        ItemOrcamento modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new ItemOrcamento();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            
            conversor.setValorBase(tabela.getColuna(BD.PRODUTO).toString());
            modelo.setProduto(getProduto(conversor.getLong()));
            modelo.setEntrega(LerCampo.getInt(tabela.getColuna(BD.ENTREGA)));
            modelo.setQuantidade((BigDecimal)tabela.getColuna(BD.QUANTIDADE));
            modelo.setValorUnitario((BigDecimal)tabela.getColuna(BD.UNITARIO));
            modelo.setValorDesconto((BigDecimal)tabela.getColuna(BD.VALOR_DESCONTO));
            modelo.setObs(tabela.getColuna(BD.OBS).toString());
        }
        
        return modelo;
    }
    
    /**
     * Recupera todos os itens de um orcamento
     * @return array com itens
     */
    public ItemOrcamento[] getItensOrcamento() {
        ItemOrcamento[] itens = null;
        
       RegistroProduto regProduto = new RegistroProduto();
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.ORCAMENTO + " = " + _idOrcamento);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       ItemOrcamento item;
       long idProduto;
       int num = 0;
       if (tabela.primeiro()) {
           itens = new ItemOrcamento[tabela.getTotalRegistros()];
           do {
                item = new ItemOrcamento();
                item.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                idProduto = LerCampo.getLong(tabela.getColuna(BD.PRODUTO));
                item.setProduto(regProduto.getDados(idProduto));
                item.setQuantidade( 
                        (BigDecimal) tabela.getColuna(BD.QUANTIDADE));
                item.setValorDesconto( 
                        (BigDecimal) tabela.getColuna(BD.VALOR_DESCONTO));
                item.setValorUnitario( 
                        (BigDecimal) tabela.getColuna(BD.UNITARIO));
                item.setEntrega( 
                        LerCampo.getInt(tabela.getColuna(BD.ENTREGA)));
                if (tabela.getColuna(BD.OBS) != null) {
                    item.setObs(tabela.getColuna(BD.OBS).toString());
                }
                itens[num] = item;
                num++;
           }
           while (tabela.proximo());
       }
        
       return itens;
    }
    
    /**
     * Elimina todos os itens de um orcamento
     * @return numero de itens eliminados
     */
    public int eliminaItensOrcamento() {
       DeleteSqlBD sql = new DeleteSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.ORCAMENTO + " = " + _idOrcamento);
       return _trans.executaUpdate(sql.getQuery());
    }

    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_orcamentositens");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(ItemOrcamento.Campos.ID);
        campo.setIU(ItemOrcamento.Dicas.ID);
        estabelecerCampo(campo);
        /* Produto */
        campo = new Campo();
        campo.setBanco(BD.PRODUTO);
        campo.setModelo(ItemOrcamento.Campos.PRODUTO);
        campo.setIU(ItemOrcamento.Dicas.PRODUTO);
        estabelecerCampo(campo);
        /* Quantidade */
        campo = new Campo();
        campo.setBanco(BD.QUANTIDADE);
        campo.setModelo(ItemOrcamento.Campos.QUANTIDADE);
        campo.setIU(ItemOrcamento.Dicas.QUANTIDADE);
        estabelecerCampo(campo);
        /* Preco Unitario */
        campo = new Campo();
        campo.setBanco(BD.UNITARIO);
        campo.setModelo(ItemOrcamento.Campos.UNITARIO);
        campo.setIU(ItemOrcamento.Dicas.UNITARIO);
        estabelecerCampo(campo);
        /* Valor de desconto */
        campo = new Campo();
        campo.setBanco(BD.VALOR_DESCONTO);
        campo.setModelo(ItemOrcamento.Campos.DESCONTO);
        campo.setIU(ItemOrcamento.Dicas.DESCONTO);
        estabelecerCampo(campo);
        /* Valor total */
        campo = new Campo();
        campo.setBanco(BD.VALOR_TOTAL);
        campo.setModelo(ItemOrcamento.Campos.TOTAL);
        campo.setIU(ItemOrcamento.Dicas.TOTAL);
        estabelecerCampo(campo);
        /* Entrega */
        campo = new Campo();
        campo.setBanco(BD.ENTREGA);
        campo.setModelo(ItemOrcamento.Campos.ENTREGA);
        campo.setIU(ItemOrcamento.Dicas.ENTREGA);
        estabelecerCampo(campo);
        /* Observacao */
        campo = new Campo();
        campo.setBanco(BD.OBS);
        campo.setModelo(ItemOrcamento.Campos.OBS);
        campo.setIU(ItemOrcamento.Dicas.OBS);
        estabelecerCampo(campo);
    }
    
    private Produto getProduto(long idProduto) {
        RegistroProduto regProduto = new RegistroProduto();
        Produto prod = regProduto.getDados(idProduto);
        
        return prod;
    }
    
} ///~
