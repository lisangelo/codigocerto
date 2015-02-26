/*
 * Codigo Certo
 * Criado em 5 de Julho de 2007, 14:04
 */

package br.com.codigocerto.controles.faturamento;

import br.com.codigocerto.bancodados.FormatacaoSQL;
import br.com.codigocerto.bancodados.InsertSqlBD;
import br.com.codigocerto.bancodados.LerCampo;
import br.com.codigocerto.bancodados.SelectSqlBD;
import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TabelaBD;
import br.com.codigocerto.bancodados.TiposDadosQuery;
import br.com.codigocerto.bancodados.UpdateSqlBD;
import br.com.codigocerto.controles.Ligacao;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.modelos.faturamento.MargemST;

/**
 * @author lis
 */
public class RegistroMargemST extends Registro {
    
    public interface BD {
        String  ID = "cc_margemst_id", 
                NCM = "cc_margemst_ncm",
                MARGEM = "cc_margemst_margem",
                MARGEM_FE = "cc_margemst_margemfe"; 
    }
    
    /** Criar nova instancia */
    public RegistroMargemST() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        MargemST modelo = (MargemST) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.NCM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNCM(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.MARGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMargem(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MARGEM_FE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMargemFE(), 
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

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        MargemST modelo = (MargemST) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NCM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNCM(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.MARGEM, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMargem(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.MARGEM_FE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMargemFE(), 
                                                            TiposDadosQuery.NUMERO)
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

    @Override
    public MargemST getDados(long id) {
        MargemST modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new MargemST();
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            modelo.setNCM(LerCampo.getString(tabela.getColuna(BD.NCM)));
            modelo.setMargens(LerCampo.getDecimal(tabela.getColuna(BD.MARGEM)),
                    LerCampo.getDecimal(tabela.getColuna(BD.MARGEM_FE)));
        }
        tabela.fechar();
        
        return modelo;
    }

    public MargemST getDados(String ncm) {
        MargemST modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.NCM + " = '" + ncm + "'");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new MargemST();
            modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
            modelo.setNCM(LerCampo.getString(tabela.getColuna(BD.NCM)));
            modelo.setMargens(LerCampo.getDecimal(tabela.getColuna(BD.MARGEM)),
                    LerCampo.getDecimal(tabela.getColuna(BD.MARGEM_FE)));
        }
        tabela.fechar();
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_margemst");
        ligacao.setCampoId(BD.ID);
    }
    
    /**
     * Exclui todos os registros
     */
    public void excluir() {
        ServidorBD.executaUpdate("delete from " + ligacao.getTabela());
    }
    
} ///:~
