/*
 * Codigo Certo
 * GerenciadorAcoesDesktop.java
 * Criado em 29 de Junho de 2007, 17:21
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.swing.formularios.Avisos;

/**
 * @author lis
 */
public class GerenciadorAcoesDesktop {
    
    private static GerenciadorAcoesDesktop instance; // singleton
    private static CCDesktop _desktop = null;

    private GerenciadorAcoesDesktop() {
    }

    /**
     * iniciar singleton
     */
    public synchronized static GerenciadorAcoesDesktop getInstance() 
    {
        if( instance == null ) {
            instance = new GerenciadorAcoesDesktop();
        }
        return instance;
    }

    /**
     * Inicia o gerenciamento apos receber informacao de qual desktop esta ativo
     * @param desktop CCDescktop ativo no momento
     */
    public static void iniciar(final CCDesktop desktop) {
        _desktop = desktop;
    }
    
    /**
     * Exibir o form solicitado
     * @param frameInterno CCInternalFrame a ser exibido
     */
    public static void exibir(CCInternalFrame frameInterno) {
        _desktop.exibir(frameInterno, CCDesktop.Nivel.NORMAL);
    }
    
    /**
     * Exibir o form solicitado como dialogo
     * @param frameInterno CCInternalFrame a ser exibido
     */
    public static void exibirDialogo(CCInternalFrame frameInterno) {
        _desktop.exibir(frameInterno, CCDesktop.Nivel.DIALOGO);
    }

    /**
     * Exibir o form solicitado com atencao exclusiva
     * @param frameInterno CCInternalFrame a ser exibido
     */
    public static void exibirExclusivo(CCInternalFrame frameInterno) {
        _desktop.exibir(frameInterno, CCDesktop.Nivel.EXCLUSIVO);
    }
    
    /**
     * Exibe para o usuario uma mensagem de erro
     * @param String com mensagem
     */
    public static void exibirAviso(String mensagem) {
        if (_desktop.getFrameAtivo() == null) {
            Avisos.exibirAviso(_desktop.getContentPane(), mensagem);
        } else {
            Avisos.exibirAviso(_desktop.getFrameAtivo(), mensagem);
        }
    }
    
    public static CCDesktop getAreaDeAtrabalho() {
        return _desktop;
    }
    
} ///:~