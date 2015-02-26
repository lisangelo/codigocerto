/*
 * SelectSqlBD.java
 *
 * Criado em 31 de Agosto de 2006, 21:34
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.util.ArrayList;

/**
 *
 * @author Lis
 */
public class SelectSqlBD {
    
    private String tabela;                          // tabela a ser consultada
    private ArrayList<ColunaSqlBD> colunas = new ArrayList<ColunaSqlBD>();    // colunas a serem recuperadas
    private ArrayList<String> condicoes = new ArrayList<String>();  // condições a serem filtradas
    private ArrayList<String> ordens = new ArrayList<String>();     // ordenação dos registros recuperados
    private int _limiteRegistros = 0;
    
    /** Creates a new instance of SelectSqlBD */
    public SelectSqlBD() {
    }
    
    /**
     * Insere coluna a ser recuperada
     *@param campo string com nome do campo da tabela
     */
    public void insereColuna(String campo) {
        ColunaSqlBD col = new ColunaSqlBD();
        col.setCampo(campo);
        colunas.add(col);
    }

    /**
     * Insere coluna a ser recuperada
     *@param campo string com nome do campo da tabela
     *@param apelido string com apelido a ser aplicado ao campo
     */
    public void insereColuna(String campo, String apelido) {
        ColunaSqlBD col = new ColunaSqlBD();
        col.setCampo(campo);
        col.setApelido(apelido);
        colunas.add(col);
    }

    /**
     * Insere coluna a ser recuperada
     *@param campo string com nome do campo da tabela
     *@param apelido string com apelido a ser aplicado ao campo
     *@param funcao string com nome da funcao a ser aplicado ao campo
     */
     public void insereColuna(String campo, String apelido, String funcao) {
        ColunaSqlBD col = new ColunaSqlBD();
        col.setCampo(campo);
        col.setApelido(apelido);
        col.setFuncao(funcao);
        colunas.add(col);
    }

    /**
     * Configura o nome da tabela a ser pesquisada
     */ 
    public void setTabela (String nome) {
        tabela = nome;
    }

    /**
     * Insere condição de filtragem na query
     *@param condicao string com expressão lógica
     */ 
     public void insereCondicao(String condicao) {
         condicoes.add(condicao);
     }

    /**
     * Insere condição de filtragem na query
     *@param condicao string com expressão lógica
     *@param clausula string com cláusula lógica (AND, OR, NOT)
     */ 
     public void insereCondicao(String condicao, String clausula) {
        condicoes.add(" " + clausula.toUpperCase() + " " + condicao);
     }
     
     /**
      * Elimina todas as condicoes
      */
     public void limpaCondicoes() {
         condicoes.clear();
     }

    /**
     * Insere ordenação na query
     *@param ordem string com nome da coluna
     */ 
     public void insereOrdem(String ordem) {
         ordens.add(ordem);
     }
     
     /**
      *Configura limite para o numero de resultados a ser obtido
      *@param int numero limite de registros
      */
     public void setLimite(int limiteRegistros) {
         if (limiteRegistros >= 0) {
             _limiteRegistros = limiteRegistros;
         }
     }
     
     /**
      * Monta a query
      *@return string com a query montada
      */
     public String getQuery() {
         
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null) {
             retorno.append(this.colunas() 
                             + this.tabela() 
                             + this.condicoes()
                             + this.ordens()
                             + this.limite());
         }
             
         return retorno.toString();    
         
     }
     
     private String tabela() {
         final String ORIGEM = "FROM ";
         String retorno = null;
         if (tabela != null) {
             retorno = ORIGEM + tabela + " ";
         }
         
         return retorno;
     }
     
     private String colunas() {
         
         final String SELECIONAR = "SELECT ";
         final String SEPARADOR = ", ";
         final String TODOS = "*";
         
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null) {
             retorno.append(SELECIONAR);
             
             if (!colunas.isEmpty()) {
                 for (int i = 0; i < colunas.size(); i++) {
                     if (i > 0) {
                         retorno.append(SEPARADOR);
                     }
                     try {
                     retorno.append( (colunas.get(i)).getColuna());
                     } catch (Exception e) {
                         System.out.println(e.getMessage());
                     }
                 }
             } else {
                 retorno.append(TODOS);
             }
             retorno.append(" ");
         }
         
         return retorno.toString();
     }
     
     private String condicoes() {
         final String FILTRAR = "WHERE ";
         return montarLista(condicoes, FILTRAR);
     }

     private String ordens() {
         final String ORDENAR_POR = "ORDER BY ";
         return montarLista(ordens, ORDENAR_POR);
     }
     
     private String limite() {
         StringBuilder limite = new StringBuilder("");
         if (_limiteRegistros > 0) {
             limite.append("LIMIT " + _limiteRegistros);
         }
         
         return limite.toString();
     }
     
     private String montarLista(ArrayList<String> lista, String nomeSecao) {
         final String SEPARADOR = ", ";
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null && (!lista.isEmpty()) ) {
             retorno.append(nomeSecao);
             
             for (int i = 0; i < lista.size(); i++) {
                 try {
                    retorno.append(lista.get(i));
                 } catch (Exception e) {
                     System.out.println(e.getMessage());
                 }
             }

             retorno.append(" ");
         }
         
         return retorno.toString();
         
     }
     
}
