/*
 * Codigo Certo
 * RegistroCidade.java
 * Criado em 24 de Outubro de 2007, 10:21
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Cidade;

/**
 * @author lis
 */
public class RegistroCidade extends Registro {
    
    public interface BD {
        String  ID = "cc_cidades_id", 
                NOME = "cc_cidades_nome",
                ESTADO = "cc_cidades_estados_id",
                CODIGO_IBGE = "cc_cidades_codigoibge";
    }

    /** Criar nova instancia de RegistroCidade */
    public RegistroCidade() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Cidade modelo = (Cidade) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
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
        Cidade modelo = (Cidade) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            sql.setTabela(ligacao.getTabela());
            sql.insereColuna(BD.NOME, 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
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

    public Cidade getDados(long id) {
        Cidade modelo = null;
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
     * Obter cidade a partir do nome e sigla do estado
     * @param cidade - nome da cidade
     * @param idEstado - codigo do estado
     * @return cidade
     */
    public Cidade getDados(String cidade, String idEstado) {
        Cidade modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.NOME + " = " + FormatacaoSQL.getDadoFormatado(cidade, TiposDadosQuery.TEXTO));
        sql.insereCondicao(BD.ESTADO + " = " + idEstado, "and");
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();

        return modelo;
    }

    private Cidade lerRegistro(TabelaBD tabela) {
        RegistroEstado regUf = new RegistroEstado();
        Cidade modelo = new Cidade();
        ConversorTipos conversor = new ConversorTipos();
        conversor.setValorBase(tabela.getColuna(BD.ID).toString());
        modelo.setId(conversor.getLong());
        modelo.setNome(tabela.getColuna(BD.NOME).toString());
        modelo.setEstado(regUf.getDados(LerCampo.getLong(tabela.getColuna(BD.ESTADO))));
        if (tabela.getColuna(BD.CODIGO_IBGE) != null) {
            modelo.setCodigoIBGE(tabela.getColuna(BD.CODIGO_IBGE).toString());
        }

        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_cidades");
        ligacao.setCampoId(BD.ID);
        
    }

} ///:~
