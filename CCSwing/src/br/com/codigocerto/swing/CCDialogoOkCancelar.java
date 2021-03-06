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
import javax.swing.JPanel;

/**
 *
 * @author lis
 */
public abstract class CCDialogoOkCancelar extends CCDialogo {
    
    private JPanel _panelDialogo;

    /** Cria uma nova instância de CCDialogoOkCancelar */
    public CCDialogoOkCancelar() {
        incluirComponentes();
    }
    public CCDialogoOkCancelar(Window owner, String titulo, ModalityType tipo) {
        super(owner, titulo, tipo);
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
        PanelOkCancelar panelOkCancelar = new PanelOkCancelar();
        panelBotoes.add(panelOkCancelar, BorderLayout.EAST);
        add(panelBotoes, BorderLayout.SOUTH);
        
        panelOkCancelar.botaoCancelar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                cancelar();
            }
        });
        
        panelOkCancelar.botaoOk().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                sair();
            }
        });
        
    }
    
} ///:~
