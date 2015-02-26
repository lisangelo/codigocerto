/*
 * BotaoPesquisar.java
 *
 * Criado em 31 de Maio de 2007, 10:24
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
public class BotaoPesquisar extends JButton {
    
    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoPesquisar */
    public BotaoPesquisar() {
        setIcone(Recursos.IconesBotoes.PESQUISAR);
        setText("Pesquisar");
        setToolTipText("<F3> Pesquisar registros existentes");
        setName("BotaoPesquisar");
    }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_pesquisar.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
    
} ///:~
