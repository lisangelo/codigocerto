/*
 * BotaoPrimeiro.java
 *
 * Criado em 31 de Maio de 2007, 10:27
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
public class BotaoPrimeiro extends JButton {
    
    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoPrimeiro */
    public BotaoPrimeiro() {
        setText("");
        setIcone(Recursos.IconesBotoes.PRIMEIRO);
        setToolTipText("Primeiro");
        setName("BotaoPrimeiro");
    }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_primeiro.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
} ///:~
