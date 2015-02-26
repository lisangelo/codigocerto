/*
 * BotaoExcluir.java
 *
 * Criado em 31 de Maio de 2007, 10:26
 *
 */

package br.com.codigocerto.swing.botoes;

import br.com.codigocerto.configuracao.Recursos;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author lis
 */
public class BotaoExcluir extends JButton {
    
    Recursos _recursos = new Recursos();
    
   /** Cria uma nova instância de BotaoExcluir */
    public BotaoExcluir() {
        setIcone(Recursos.IconesBotoes.EXCLUIR);
        setText("Excluir");
        setMnemonic('x');
        setToolTipText("Excluir registro <Alt+X>");
        setName("BotaoExcluir");
    }
    
    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_excluir.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
    
    
} ///:~
