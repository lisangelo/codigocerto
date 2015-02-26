/*
 * Codigo Certo
 * CCModeloTabela.java
 * Criado em 4 de Setembro de 2007, 11:18
 */

package br.com.codigocerto.swing;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

/**
 * @author lis
 */
public class CCModeloTabelaSimples extends AbstractTableModel {

    private ArrayList _linhas = null;
    private String [] _colunas = null;

    public CCModeloTabelaSimples(ArrayList dados, String[] colunas){
            setLinhas(dados);
            setColunas(colunas);
    }

    public String[] getColunas() {
        return _colunas;
    }

    public ArrayList getLinhas() {
        return _linhas;
    }

    public void setColunas(String[] strings) {
        _colunas = strings;
    }

    public void setLinhas(ArrayList list) {
        _linhas = list;
    }

    /**
     * Retorna o numero de colunas no modelo
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return getColunas().length;
    }

    /**
     * Retorna o numero de linhas existentes no modelo
     * @see javax.swing.table.TableModel#getRowCount()
    */
    @Override
    public int getRowCount() {
        return getLinhas().size();
    }

    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       // Obtem a linha, que é uma String []
       String [] linha = (String [])getLinhas().get(rowIndex);
       // Retorna o objeto que esta na coluna
       return linha[columnIndex];
    }

    // Implementação do getColumnName

    /**
     * Retorna o nome da coluna.
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int col){
            return getColunas()[col];
    }

    public void removeRow(int row){
            getLinhas().remove(row);
            // informa a jtable que houve dados deletados passando a
            // linha reovida
            fireTableRowsDeleted(row,row);
    }


    /**
     * Remove a linha pelo valor da coluna informada
     * @param val
     * @param col
     * @return
     */
    public boolean removeRow(String val, int col){
            // obtem o iterator
            Iterator i = getLinhas().iterator();
            int linha = 0;
            // Faz um looping em cima das linhas
            while(i.hasNext()){
                    // Obtem as colunas da linha atual
                    String[] linhaCorrente = (String[])i.next();
                    linha++;
                    // compara o conteudo String da linha atual na coluna desejada
                    // com o valor informado
                    if( linhaCorrente[col].equals(val) ){
                            getLinhas().remove(linha);
                            // informa a jtable que houve dados deletados passando a
                            // linha removida
                            fireTableRowsDeleted(linha,linha);
                            return true;
                    }
            }
            // Nao encontrou nada
            return false;
    }

    public void addRow( String [] dadosLinha){
            getLinhas().add(dadosLinha);
            // Informa a jtable de que houve linhas incluidas no modelo
            // COmo adicionamos no final, pegamos o tamanho total do modelo
            // menos 1 para obter a linha incluida.
            int linha = getLinhas().size()-1;
            fireTableRowsInserted(linha,linha);
            return;
    }

    /**
     * Seta o valor na linha e coluna
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(Object value, int row, int col){
            // Obtem a linha, que é uma String []
            String [] linha = (String [])getLinhas().get(row);
            // Altera o conteudo no indice da coluna passado
            linha[col] = (String)value;
            // dispara o evento de celula alterada
            fireTableCellUpdated(row,col);
    }

} ///:~
