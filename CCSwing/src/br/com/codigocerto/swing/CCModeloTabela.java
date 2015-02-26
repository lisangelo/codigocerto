/*
 * Codigo Certo
 * CCModeloTabela.java
 * Criado em 4 de Setembro de 2007, 11:18
 */

package br.com.codigocerto.swing;

import javax.swing.table.AbstractTableModel;

/**
 * @author lis
 */
public class CCModeloTabela extends AbstractTableModel {
    
    private String[] _titulos;
    private String[][] _celulas;

    public void setTitulos(String[] titulos) {
        _titulos = titulos;
    }

    public void setConteudo(String[][] celulas) {
        _celulas = celulas;
        fireTableDataChanged();
    }

    public String[][] getConteudo() {
        return _celulas;
    }

    @Override
    public int getColumnCount() {
        return _titulos.length;
    }

    @Override
    public int getRowCount() {
        int numeroCelulas = 0;
        if (_celulas != null) {
            numeroCelulas = _celulas.length;
        }
        return numeroCelulas;
    }

    @Override
    public String getColumnName(int col) {
        return _titulos[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        StringBuilder conteudo = new StringBuilder("");
        if (_celulas != null) {
            if (_celulas[row][col] != null) {
                conteudo.append(_celulas[row][col]);
            }
        }
        return conteudo.toString();
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (value != null) {
            _celulas[row][col] = (String) value;
        } else {
            _celulas[row][col] = "";
        }
        fireTableCellUpdated(row, col);
    }   
    
} ///:~
