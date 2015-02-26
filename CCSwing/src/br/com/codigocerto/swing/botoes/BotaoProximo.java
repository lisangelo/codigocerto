/*
 * BotaoProximo.java
 *
 * Criado em 31 de Maio de 2007, 10:31
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
public class BotaoProximo extends JButton {

    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoProximo */
    public BotaoProximo() {
        setText("");
        setIcone(Recursos.IconesBotoes.PROXIMO);
        setToolTipText("Pr\u00f3ximo");
        setName("BotaoProximo");
    }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_proximo.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }

    
} ///:~
