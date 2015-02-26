/*
 * CCComboBox.java
 *
 * Criado em 11 de Junho de 2007, 14:44
 *
 */

package br.com.codigocerto.swing;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;

/**
 *
 * @author lis
 */
public class CCComboBox extends JComboBox {

    /** Cria uma nova instância de CCComboBox */
    public CCComboBox() {
        configurar();
    }
    
    private void configurar() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            @Override
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
            }
        });
        
    }
    
    private void mudaCorControle(int padrao) {
        setBackground(CoresSistema.corPadrao(padrao));
    }
    

} ///:~
