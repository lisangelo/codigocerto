/*
 * CCDialog.java
 *
 * Criado em 23 de Maio de 2007, 15:42
 *
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.configuracao.Recursos;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

/**
 *
 * @author lis
 */
public class CCDialog extends JDialog {
    
    private Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de CCDialog */
    public CCDialog() {
        setTitle("Código Certo");
        ImageIcon icone = new ImageIcon(_recursos.getIcone(Recursos.Icones.DIALOGO));
        setIconImage(icone.getImage());
        setModalityType(ModalityType.APPLICATION_MODAL);
    }
    public CCDialog(Window owner, String titulo, ModalityType tipo) {
        super(owner, titulo, tipo);
        ImageIcon icone = new ImageIcon(_recursos.getIcone(Recursos.Icones.DIALOGO));
        setIconImage(icone.getImage());
    }

    /**
     * Torna visivel o dialogo
     */
    public void visualizar() {
        setVisible(true);
    }
    

} ///:~
