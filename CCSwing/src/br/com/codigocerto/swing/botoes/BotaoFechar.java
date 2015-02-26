/*
 * BotaoFechar.java
 *
 * Criado em 31 de Maio de 2007, 10:36
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
public class BotaoFechar extends JButton {
  
    Recursos _recursos = new Recursos();

    /** Cria uma nova instância de BotaoFechar */
    public BotaoFechar() {
        setIcone(Recursos.IconesBotoes.FECHAR);
        setText("Fechar");
        setMnemonic(getText().charAt(0));
        setToolTipText("Fechar janela <Alt+F>");
        setName("BotaoFechar");
   }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_fechar.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
    
} ///:~
