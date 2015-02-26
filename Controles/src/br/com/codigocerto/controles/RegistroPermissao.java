/*
 *
 * Codigo Certo
 *
 * RegistroPermissao.java
 *
 * Criado em 2 de Abril de 2007, 13:18
 *
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.SelectSqlBD;
import br.com.codigocerto.bancodados.InsertSqlBD;
import br.com.codigocerto.bancodados.UpdateSqlBD;
import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TabelaBD;
import br.com.codigocerto.bancodados.TiposDadosQuery;
import br.com.codigocerto.bancodados.FormatacaoSQL;

import br.com.codigocerto.seguranca.Permissao;

/**
 *
 * @author lis
 */
public class RegistroPermissao {
    
    private final String _TABELA = "cc_permissoes";
    
    /** Creates a new instance of RegistroPermissao */
    public RegistroPermissao() {
    }
    
    /*
     *Examina tabela de permissoes
     *@param Permissao sendo examinada
     *@return verdadeiro para permissao concedida
     */ 
    public boolean getOk(Permissao permissao) {
        
        boolean ok = true;
        /*if ( permissao != null) {
            SelectSqlBD sql = new SelectSqlBD();
            sql.setTabela(_TABELA);
            sql.insereCondicao("cc_permiss_usuarios_id = " + permissao.getUsuario());
            sql.insereCondicao("cc_permiss_componente = " 
                               + FormatacaoSQL.getDadoFormatado(permissao.getComponente(), 
                                                                TiposDadosQuery.TEXTO), 
                                                                "and"
                                                                );
            sql.insereCondicao("cc_permiss_acao = " 
                               + FormatacaoSQL.getDadoFormatado(permissao.getAcao(), 
                                                                TiposDadosQuery.TEXTO), "and");
            TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
            if (tabela.primeiro()) {
                ok = false;
            }
        }*/
        
        return ok;
    }        

    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    public boolean setDadosInsercao(Object dados) {
        boolean ok;
        Permissao modelo = (Permissao) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(_TABELA);
            
            sql.insereColuna("cc_permiss_usuarios_id", String.valueOf(modelo.getUsuario()));
            sql.insereColuna("cc_permiss_componente", FormatacaoSQL.getDadoFormatado(
                                                             modelo.getComponente(), 
                                                             TiposDadosQuery.TEXTO)
                                                             );
            sql.insereColuna("cc_permiss_acao", FormatacaoSQL.getDadoFormatado(modelo.getAcao(), 
                                                    TiposDadosQuery.TEXTO));
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

}
