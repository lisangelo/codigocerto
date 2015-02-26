/*
 * DeleteSqlBD.java
 *
 * Criado em 2 de Setembro de 2006, 13:47
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.util.ArrayList;

/**
 *
 * @author Lis
 */
public class DeleteSqlBD {
    
    private String tabela;                          // tabela a ser alterada
    private ArrayList<String> condicoes = new ArrayList<String>();  // condições a serem filtradas
    
    /** Creates a new instance of DeleteSqlBD */
    public DeleteSqlBD() {
    }
    
    /**
     * Configura o nome da tabela a ser alterada
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
      * Monta a query
      *@return string com a query montada
      */
     public String getQuery() {
         
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null) {
             retorno.append(this.tabela() 
                             + this.condicoes());
         }
             
         return retorno.toString();    
         
     }
     
     private String tabela() {
         final String ATUALIZAR = "DELETE FROM ";
         String retorno = null;
         if (tabela != null) {
             retorno = ATUALIZAR + tabela + " ";
         }
         
         return retorno;
     }
     
     private String condicoes() {
         final String FILTRAR = "WHERE ";
         return montarLista(condicoes, FILTRAR);
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
