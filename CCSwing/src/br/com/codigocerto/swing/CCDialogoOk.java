/*
 * CCDialogoOkCancelar.java
 *
 * Criado em 23 de Maio de 2007, 18:50
 *
 */

package br.com.codigocerto.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author lis
 */
public abstract class CCDialogoOk extends CCDialogo {
    
    private JPanel _panelDialogo;

    /** Cria uma nova instância de CCDialogoOkCancelar */
    public CCDialogoOk() {
        incluirComponentes();
    }

    /**
     *Insere painel acima dos botoes para edicao
     */
    protected void inserirPanelDialogo(JPanel panel) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        _panelDialogo = panel;
        panelSuperior.add(_panelDialogo, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    /**
     * Fornece uma referencia ao objeto panel utilizado
     * @return JPanel utilizado
     */
    protected JPanel getPanelDialogo() {
        return _panelDialogo;
    }
    
    protected void alterarFoco(String nomeComponente) {
        try {
            Component comp;
            int i = 0;
            int numComponentes = _panelDialogo.getComponentCount();
            do {
                comp = _panelDialogo.getComponent(i);
                if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                    i = numComponentes;
                    comp.requestFocusInWindow();
                }
                i++;
            }
            while (i < numComponentes);
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            ControleExcecoes.capturarExcecao(e);
        }
    }
    
    private void incluirComponentes() {
        JPanel panelBotoes = new JPanel(new BorderLayout());
        PanelOk panelOk = new PanelOk();
        panelBotoes.add(panelOk, BorderLayout.EAST);
        add(panelBotoes, BorderLayout.SOUTH);
        
        panelOk.botaoOk().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                sair();
            }
        });
        
    }
    
} ///:~
