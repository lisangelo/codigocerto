/*
 * Avisos.java
 *
 * Criado em 24 de Maio de 2007, 17:24
 *
 */

package br.com.codigocerto.swing.formularios;

import java.awt.Component;
import javax.swing.*;

import br.com.codigocerto.configuracao.Recursos;
import br.com.codigocerto.swing.CCDialogoSimNao;

/**
 *
 * @author lis
 */
public class Avisos {
    
    /**
     * Exibe para o usuario uma mensagem de erro
     * @param origem - componente pai
     * @param String com mensagem
     */
    public static void exibirAviso(Component origem, String mensagem) {
        
        Recursos recursos = new Recursos();
        
        ImageIcon iconeAviso = new ImageIcon(recursos.getIconeAviso(Recursos.IconesAvisos.AVISO));
        JOptionPane.showMessageDialog(origem, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE, iconeAviso);
    }

    /**
     * Exibe para o usuario uma mensagem de erro dentro da aplicacao
     * @param origem - componente pai
     * @param String com mensagem
     */
    public static void exibirAvisoInterno(Component origem, String mensagem) {
        
        Recursos recursos = new Recursos();
        
        ImageIcon iconeAviso = new ImageIcon(recursos.getIconeAviso(Recursos.IconesAvisos.AVISO));
        JOptionPane.showInternalMessageDialog(origem, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE, iconeAviso);
    }

    /**
     * Exibe uma pergunta para o usuario
     * @param origem
     * @param mensagem
     * @return
     */
    public static boolean exibirPergunta(Component origem, String mensagem) {

        boolean ok = false;
        
        CCDialogoSimNao uiSimNao = new CCDialogoSimNao() {};
        uiSimNao.setMensagem(mensagem);
        uiSimNao.visualizar();
        ok = uiSimNao.isOk();
        
        return ok;
    }
    
} ///:~