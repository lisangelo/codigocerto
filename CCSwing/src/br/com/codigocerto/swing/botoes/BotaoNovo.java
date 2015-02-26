/*
 * BotaoNovo.java
 *
 * Criado em 31 de Maio de 2007, 10:15
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
public class BotaoNovo extends JButton {
    
    Recursos _recursos = new Recursos();

    /** Cria uma nova instância de BotaoNovo */
    public BotaoNovo() {
        setIcone(Recursos.IconesBotoes.NOVO);
        setText("Novo");
        setMnemonic(getText().charAt(0));
        setToolTipText("Incluir novo registro <Alt+N>");
        setName("BotaoNovo");
    }
    
    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_novo.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
} ///:~
