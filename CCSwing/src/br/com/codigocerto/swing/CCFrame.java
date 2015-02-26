/*
 * CCFrame.java
 *
 * Criado em 22 de Maio de 2007, 13:37
 *
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.configuracao.Recursos;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

/**
 *
 * @author lis
 */
public class CCFrame extends JFrame {
    
    private Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de CCFrame */
    public CCFrame() {
        super("Código Certo");
        ImageIcon icone = new ImageIcon(_recursos.getIcone(Recursos.Icones.PRINCIPAL));
        setIconImage(icone.getImage());
    }   
} ///:~
