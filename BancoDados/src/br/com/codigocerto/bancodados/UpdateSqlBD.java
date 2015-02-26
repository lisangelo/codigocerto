/*
 * UpdateSqlBD.java
 *
 * Criado em 2 de Setembro de 2006, 13:47
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.util.ArrayList;

/**
 *
 * @author lisangelo.berti
 */
public class UpdateSqlBD {
    
    private String tabela;                          // tabela a ser alterada
    private ArrayList<String> colunas = new ArrayList<String>();    // colunas a serem atualizadas
    private ArrayList<String> condicoes = new ArrayList<String>();  // condições a serem filtradas
    
    /** Creates a new instance of UpdateSqlBD */
    public UpdateSqlBD() {
    }
    
    /**
     * Insere coluna a ser atualizada
     *@param campo string com nome do campo da tabela
     *@param valor string com valor a ser gravado
     */
    public void insereColuna(String campo, String valor) {
        final String RECEBE = " = ";
        colunas.add(campo + RECEBE + valor);
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
                             + this.colunas() 
                             + this.condicoes());
         }
             
         return retorno.toString();    
         
     }
     
     private String tabela() {
         final String ATUALIZAR = "UPDATE ";
         String retorno = null;
         if (tabela != null) {
             retorno = ATUALIZAR + tabela + " ";
         }
         
         return retorno;
     }
     
     private String colunas() {
         
         final String SELECIONAR = "SET ";
         return montarLista(colunas, SELECIONAR);
     }
     
     private String condicoes() {
         return montarListaCondicoes(condicoes);
     }

     private String montarListaCondicoes(ArrayList<String> lista) {
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null && (!lista.isEmpty()) ) {
             retorno.append("WHERE ");
             
             for (int i = 0; i < lista.size(); i++) {
                 try {
                    if (i > 0) {
                        retorno.append(" ");
                    }
                    retorno.append(lista.get(i));
                 } catch (Exception e) {
                     System.out.println(e.getMessage());
                 }
             }

             retorno.append(" ");
         }
         
         return retorno.toString();
         
     }
    
     private String montarLista(ArrayList<String> lista, String nomeSecao) {
         final String SEPARADOR = ", ";
         StringBuilder retorno = new StringBuilder("");

         if (tabela != null && (!lista.isEmpty()) ) {
             retorno.append(nomeSecao);

             for (int i = 0; i < lista.size(); i++) {
                 try {
                    if (i > 0) {
                        retorno.append(SEPARADOR);
                    }
                    retorno.append(lista.get(i));
                 } catch (Exception e) {
                     System.out.println(e.getMessage());
                 }
             }

             retorno.append(" ");
         }

         return retorno.toString();

     }

}///:~
