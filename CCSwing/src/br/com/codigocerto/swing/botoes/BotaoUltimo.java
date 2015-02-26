/*
 * BotaoUltimo.java
 *
 * Criado em 31 de Maio de 2007, 10:33
 *
 */

package br.com.codigocerto.swing.botoes;

import java.net.URL;
import javax.swing.*;

import br.com.codigocerto.configuracao.Recursos;

/**
 *
 * @author lis
 */
public class BotaoUltimo extends JButton {
    
    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoUltimo */
    public BotaoUltimo() {
        setText("");
        setIcone(Recursos.IconesBotoes.ULTIMO);
        setToolTipText("\u00daltimo");
        setName("BotaoUltimo");
    }
    
    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_ultimo.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }

    
} ///:~
