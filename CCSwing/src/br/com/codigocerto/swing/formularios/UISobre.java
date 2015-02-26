/*
 * Codigo Certo
 * UISobre.java
 * Criado em 9 de Julho de 2007, 11:41
 */

package br.com.codigocerto.swing.formularios;

import br.com.codigocerto.configuracao.Recursos;
import br.com.codigocerto.swing.CCDialogo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 * @author lis
 */
public class UISobre extends CCDialogo {

    PanelSobre _panel = new PanelSobre();

    /** Cria uma nova instância de UISobre */
    public UISobre() {
        incluirComponentes();
        setLocationRelativeTo(null); // centraliza 
    }

    private void incluirComponentes() {
        Recursos recursos = new Recursos();
        _panel.setLogotipo(new ImageIcon(recursos.getLogotipo(Recursos.Logotipos.PEQUENO)));
        _panel.setAcaoFechar(new AcaoFechar());
        add(_panel);
        pack();
        
    }

    @Override
    protected boolean verificarOk() {
        return true;
    }
    
    class AcaoFechar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            sair();
        }
        
    }
    
    
} ///~
