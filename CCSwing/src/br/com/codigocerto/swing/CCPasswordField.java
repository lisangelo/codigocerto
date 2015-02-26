/*
 * CCPasswordField.java
 *
 * Criado em 24 de Maio de 2007, 13:05
 *
 */

package br.com.codigocerto.swing;

import java.awt.event.*;
import javax.swing.JPasswordField;

/**
 *
 * @author lis
 */
public class CCPasswordField extends JPasswordField {
    
    /** Cria uma nova instância de CCPasswordField */
    public CCPasswordField() {
        configurar();
    }
    
    /**
     * Informa senha digitada
     * @return string com senha informada
     */
    public String getTexto() {
        return charArray2String(getPassword());
    }
    
    private String charArray2String(char[] sequencia) {
        StringBuilder stringFinal = new StringBuilder("");
        for (int i = 0; i < sequencia.length; i++) {
            stringFinal.append(sequencia[i]);
        }
        return stringFinal.toString();
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
