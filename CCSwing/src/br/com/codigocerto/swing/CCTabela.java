/*
 * CCTabela.java
 *
 * Criado em 12 de Junho de 2007, 15:11
 *
 */

package br.com.codigocerto.swing;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author lis
 */
public class CCTabela extends JTable {
    
    private final int NADA_SELECIONADO = -1;
    
    /** Cria uma nova instância de CCTabela */
    public CCTabela() {
        configurar();
    }
    
    private void configurar() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                mudaCorControle(CoresSistema.TABELA_SELECIONADO);
                if (getRowCount() > 0) {
                    if (getSelectedRow() == NADA_SELECIONADO) { // se nao ha linha selecionada
                        setRowSelectionInterval(0, 0);
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.TABELA_NORMAL);
            }
        });
    }
    
    private void mudaCorControle(int padrao) {
        setSelectionBackground(CoresSistema.corPadrao(padrao));
        if (padrao == CoresSistema.TABELA_SELECIONADO) {
            setSelectionForeground(CoresSistema.corPadrao(CoresSistema.TABELA_SELECIONADO_FONTE));
        }
    }
    
} ///:~
