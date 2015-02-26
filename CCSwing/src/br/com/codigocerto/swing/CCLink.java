/*
 * Codigo Certo
 * CCLink.java
 * Criado em 22 de Junho de 2007, 16:08
 */

package br.com.codigocerto.swing;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author lis
 */
public class CCLink extends JLabel {
    
    private JButton _botaoAcionaForm = new JButton();
    
    /** Criar nova instancia de CCLink */
    public CCLink() {
        configurar();
    }
    
    public void setAcaoForm(ActionListener acao) {
        _botaoAcionaForm.addActionListener(acao);
    }
    
    private void configurar() {
        addMouseListener(new PonteiroMouse());
        _botaoAcionaForm.setVisible(false);
    }
    
    private void mudaCorControle(int padrao) {
        setForeground(CoresSistema.corPadrao(padrao));
    }
    
    class PonteiroMouse extends MouseAdapter {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            mudaCorControle(CoresSistema.LINK_SELECIONADO);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            mudaCorControle(CoresSistema.LINK_NORMAL);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                if (evt.getClickCount() == 1) {
                    _botaoAcionaForm.doClick();
                }
            }
        }
    }
    
    
} ///~
