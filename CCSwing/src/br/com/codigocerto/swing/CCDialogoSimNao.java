/*
 * CCDialogoSimNao.java
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
public abstract class CCDialogoSimNao extends CCDialogo {
    
    private PanelMensagem _panelMensagem;

    /** Cria uma nova instância de CCDialogoSimNao */
    public CCDialogoSimNao() {
        incluirComponentes();
        setLocationRelativeTo(null); // centraliza 
    }
    
    /**
     * Exibe mensagem mensagem de texto
     * @param texto
     */
    public void setMensagem(String texto) {
        _panelMensagem.setTexto(texto);
        pack();
        setLocationRelativeTo(null); // centraliza 
    }

    /**
     * Fornece uma referencia ao objeto panel utilizado
     * @return JPanel utilizado
     */
    protected JPanel getPanelMensagem() {
        return _panelMensagem;
    }
    
    protected boolean verificarOk() {
        return true;
    }
    
    private void incluirComponentes() {
        setTitle("Confirmação");
        JPanel panelSuperior = new JPanel(new BorderLayout());
        _panelMensagem = new PanelMensagem();
        panelSuperior.add(_panelMensagem, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
        JPanel panelBotoes = new JPanel(new BorderLayout());
        PanelSimNao panelSimNao = new PanelSimNao();
        panelBotoes.add(panelSimNao, BorderLayout.EAST);
        add(panelBotoes, BorderLayout.SOUTH);
        
        panelSimNao.botaoCancelar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                cancelar();
            }
        });
        
        panelSimNao.botaoOk().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                sair();
            }
        });
    }
    
} ///:~
