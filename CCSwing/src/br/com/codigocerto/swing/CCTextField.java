/*
 * CCTextField.java
 *
 * Criado em 24 de Maio de 2007, 09:29
 *
 */

package br.com.codigocerto.swing;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 *
 * @author lis
 */
public class CCTextField extends JTextField {
    
    /** Cria uma nova instância de CCTextField */
    public CCTextField() {
        configurar();
    }

    private void configurar() {
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
            }
        });
        
        addKeyListener(new MyKeyListener());
        
    }
    
    private void mudaCorControle(int padrao) {
        setBackground(CoresSistema.corPadrao(padrao));
    }
    
    class MyKeyListener extends KeyAdapter {
        /* verifica se tecla Enter foi acionada */
        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                evt.consume();
                transferFocus();
            }
        }
 
    }
    
} ///:~
