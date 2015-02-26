/*
 * Codigo Certo
 * Criado em 4 de Setembro de 2007, 11:18
 */

package br.com.codigocerto.swing;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 * @author lis
 */
public class CCModeloLista extends AbstractTableModel {
    
    private String[] _titulos;
    private ArrayList<String[]> _celulas;

    public void setTitulos(String[] titulos) {
        _titulos = titulos;
        TableColumn coluna;
    }

    public void setConteudo(ArrayList<String[]> celulas) {
        _celulas = celulas;
        fireTableDataChanged();
    }

    public ArrayList<String[]> getConteudo() {
        return _celulas;
    }

    public void atualizar() {
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return _titulos.length;
    }

    @Override
    public int getRowCount() {
        int numeroCelulas = 0;
        if (_celulas != null) {
            numeroCelulas = _celulas.size();
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
            if (_celulas.get(row)[col] != null) {
                conteudo.append(_celulas.get(row)[col]);
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
            _celulas.get(row)[col] = (String) value;
        } else {
            _celulas.get(row)[col] = "";
        }
        fireTableCellUpdated(row, col);
    }   
    
} ///:~
