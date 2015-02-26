/*
 * InsertSqlBD.java
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
public class InsertSqlBD {
    
    private String tabela;                                // tabela a ser alterada
    private ArrayList<String> colunas = new ArrayList<String>();  // colunas a serem atualizadas
    private ArrayList<String> valores = new ArrayList<String>();  // valores a serem incluidos
    
    /** Creates a new instance of InsertSqlBD */
    public InsertSqlBD() {
    }
    
    /**
     * Insere coluna a ser atualizada
     *@param campo string com nome do campo da tabela
     *@param valor string com valor a ser gravado
     */
    public void insereColuna(String campo, String valor) {
        colunas.add(campo);
        valores.add(valor);
    }

    /**
     * Configura o nome da tabela a ser alterada
     */ 
    public void setTabela (String nome) {
        tabela = nome;
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
                             + this.valores()
                           ); 
         }
             
         return retorno.toString();    
         
     }
     
     private String tabela() {
         final String INCLUIR = "INSERT INTO ";
         String retorno = null;
         if (tabela != null) {
             retorno = INCLUIR + tabela + " ";
         }
         
         return retorno;
     }
     
     private String colunas() {
         
         return montarLista(colunas, "");
     }

     private String valores() {
         final String VALUES = "VALUES ";
         return montarLista(valores, VALUES);
     }

     
     private String montarLista(ArrayList<String> lista, String nomeSecao) {
         final String SEPARADOR = ", ";
         StringBuilder retorno = new StringBuilder("");
         
         if (tabela != null && (!lista.isEmpty()) ) {
             retorno.append(nomeSecao + "(");
             
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

             retorno.append(") ");
         }
         
         return retorno.toString();
         
     }
    
}
