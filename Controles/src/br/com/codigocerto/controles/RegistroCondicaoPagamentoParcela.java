/*
 * Codigo Certo
 * RegistroCondicaoPagamentoParcela.java
 * @author lis
 * Criado em 14 de Novembro de 2007, 16:52
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.Campo;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.controles.RegistroProduto;
import br.com.codigocerto.modelos.ParcelaCondicaoPagamento;

public class RegistroCondicaoPagamentoParcela extends Registro {
    
    private long _idMestre = 0;
    private TransacaoBD _trans = null;
    
    public interface BD {
        String  ID = "cc_condicoespagamentoparcelas_id", 
                CONDICAO_PAGAMENTO = "cc_condicoespagamentoparcelas_condicoespagamento_id",
                PRAZO = "cc_condicoespagamentoparcelas_prazo";
    }
    
/** Criar nova instancia de RegistroCondicaoPagamentoParcela */
    public RegistroCondicaoPagamentoParcela() {
    }
    
    /**
     * Configura o id mestre
     * @param id mestre
     */
    public void setIdMestre(long id) {
        _idMestre = id;
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
        ParcelaCondicaoPagamento modelo = (ParcelaCondicaoPagamento) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONDICAO_PAGAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idMestre), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRAZO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrazo(), 
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
        ParcelaCondicaoPagamento modelo = (ParcelaCondicaoPagamento) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.CONDICAO_PAGAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idMestre), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.PRAZO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getPrazo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Object getDados(long id) {
        ParcelaCondicaoPagamento modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new ParcelaCondicaoPagamento();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setPrazo(LerCampo.getInt(tabela.getColuna(BD.PRAZO)));
        }
        
        return modelo;
    }

    /**
     * Recupera todas as parcelas de uma condicao de pagamento
     * @return array com parcelas
     */
    public ParcelaCondicaoPagamento[] getParcelasCondicao() {
        ParcelaCondicaoPagamento[] itens = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.CONDICAO_PAGAMENTO + " = " + _idMestre);
        sql.insereOrdem(BD.PRAZO);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        ParcelaCondicaoPagamento item;
        int num = 0;
        if (tabela.primeiro()) {
            itens = new ParcelaCondicaoPagamento[tabela.getTotalRegistros()];
            do {
                item = new ParcelaCondicaoPagamento();
                item.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                item.setPrazo(LerCampo.getInt(tabela.getColuna(BD.PRAZO)));
                itens[num] = item;
                num++;
            }
            while (tabela.proximo());
        }
        
        return itens;
    }
    
    /**
     * Elimina todas as parcelas de uma condicao
     * @return numero de parcelas eliminadas
     */
    public int eliminaParcelasCondicao() {
       DeleteSqlBD sql = new DeleteSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.CONDICAO_PAGAMENTO + " = " + _idMestre);
       return _trans.executaUpdate(sql.getQuery());
    }
        
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_condicoespagamentoparcelas");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(ParcelaCondicaoPagamento.Campos.ID);
        campo.setIU(ParcelaCondicaoPagamento.Dicas.ID);
        estabelecerCampo(campo);
        /* Prazo */
        campo = new Campo();
        campo.setBanco(BD.PRAZO);
        campo.setModelo(ParcelaCondicaoPagamento.Campos.PRAZO);
        campo.setIU(ParcelaCondicaoPagamento.Dicas.PRAZO);
        estabelecerCampo(campo);
    }
    
} ///~
