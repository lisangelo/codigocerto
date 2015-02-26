/*
 * Codigo Certo
 * UIMensagem.java
 * Criado em 9 de Julho de 2007, 17:24
 */

package br.com.codigocerto.swing.formularios;

import br.com.codigocerto.swing.CCDialogo;

/**
 * @author lis
 */
public class UIMensagem extends CCDialogo {
    
    PanelMensagem _panel = new PanelMensagem();

    /** Cria uma nova instância de UIMensagem */
    public UIMensagem() {
        setUndecorated(true);
        setModal(false);
        incluirComponentes();
        setLocationRelativeTo(null); // centraliza 
    }

    /**
     * Configura mensagem a ser apresentada
     * @param mensagem texto com mensagem
     */
    public void setMensagem(String mensagem) {
        _panel.setMensagem(mensagem);
        pack();
    }
    
    private void incluirComponentes() {
        add(_panel);
    }

    @Override
    protected boolean verificarOk() {
        return true;
    }
    
} ///~
