/*
 * Codigo Certo
 * RegistroEstado.java
 * Criado em 24 de Outubro de 2007, 10:03
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.modelos.Estado;
import java.math.BigDecimal;

/**
 * @author lis
 */
public class RegistroEstado extends Registro {
    
    public interface BD {
        String  ID = "cc_estados_id", 
                NOME = "cc_estados_nome",
                ICMS_COMPRA = "cc_estados_icmscompra",
                ICMS_VENDA = "cc_estados_icmsvenda",
                ICMS_INTERNO = "cc_estados_icmsinterno",
                SIGLA = "cc_estados_sigla",
                CODIGO_IBGE = "cc_estados_codigoibge",
                CONVENIO_ST = "cc_estados_conveniost";
    }

    /** Criar nova instancia de RegistroEstado */
    public RegistroEstado() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Estado modelo = (Estado) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SIGLA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSigla(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ICMS_COMPRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSCompra(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VENDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSVenda(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_INTERNO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSInterno(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.CODIGO_IBGE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoIBGE(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CONVENIO_ST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isConvenioST(),
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

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok;
        Estado modelo = (Estado) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.SIGLA, 
                              FormatacaoSQL.getDadoFormatado(modelo.getSigla(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.ICMS_COMPRA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSCompra(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_VENDA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSVenda(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ICMS_INTERNO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getICMSInterno(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.CODIGO_IBGE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCodigoIBGE(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CONVENIO_ST, FormatacaoSQL.getDadoFormatado(
                                                            modelo.isConvenioST(),
                                                            TiposDadosQuery.LOGICO)
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
    public Estado getDados(long id) {
        Estado modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();
        
        return modelo;
    }

    /**
     * Obter o Estado atraves da sigla
     * @param sigla
     * @return estado
     */
    public Estado getDados(String sigla) {
        Estado modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.SIGLA + " = '" + sigla + "'");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();

        return modelo;
    }

    /**
     * Obter todos os estados
     * @param ordem
     * @return lista de estados
     */
    public Estado[] getTodos(String ordem) {
        Estado[] lista = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao("not isnull(" + BD.ICMS_VENDA + ")");
        sql.insereOrdem(ordem);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            lista = new Estado[tabela.getTotalRegistros()];
            int i = 0;
            do {
                lista[i] = lerRegistro(tabela);
                i++;
            } while (tabela.proximo());
        }
        tabela.fechar();

        return lista;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_estados");
        ligacao.setCampoId(BD.ID);
        
    }

    private Estado lerRegistro(TabelaBD tabela) {
        Estado modelo = new Estado();

        modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
        modelo.setNome(tabela.getColuna(BD.NOME).toString());
        modelo.setSigla(tabela.getColuna(BD.SIGLA).toString());
        if (tabela.getColuna(BD.ICMS_COMPRA) != null) {
            modelo.setICMSCompra((BigDecimal) tabela.getColuna(BD.ICMS_COMPRA));
        }
        if (tabela.getColuna(BD.ICMS_VENDA) != null) {
            modelo.setICMSVenda((BigDecimal) tabela.getColuna(BD.ICMS_VENDA));
        }
        modelo.setICMSInterno(LerCampo.getDecimal(tabela.getColuna(BD.ICMS_INTERNO)));
        if (tabela.getColuna(BD.CODIGO_IBGE) != null) {
            modelo.setCodigoIBGE(tabela.getColuna(BD.CODIGO_IBGE).toString());
        }
        modelo.setConvenioST(LerCampo.getBoolean(tabela.getColuna(BD.CONVENIO_ST)));

        return modelo;
    }

} ///:~
