/*
 * BotaoAnterior.java
 *
 * Criado em 31 de Maio de 2007, 10:30
 *
 */

package br.com.codigocerto.swing.botoes;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import br.com.codigocerto.configuracao.Recursos;

/**
 *
 * @author lis
 */
public class BotaoAnterior extends JButton {
    
    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoAnterior */
    public BotaoAnterior() {
        setText("");
        setIcone(Recursos.IconesBotoes.ANTERIOR);
        setToolTipText("Anterior");
        setName("BotaoAnterior");
    }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_anterior.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
} ///:~
