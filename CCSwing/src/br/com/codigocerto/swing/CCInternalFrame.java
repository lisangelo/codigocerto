/*
 * CCInternalFrame.java
 *
 * Criado em 31 de Maio de 2007, 13:50
 *
 */

package br.com.codigocerto.swing;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.net.URL;
import java.awt.Cursor;

import br.com.codigocerto.configuracao.Recursos;
import java.util.ArrayList;

/**
 *
 * @author lis
 */
public class CCInternalFrame extends JInternalFrame {
    
    private Recursos _recursos = new Recursos();
    private ArrayList<CCInternalFrame> _filhos;
    private boolean _iniciarMaximizado = false;
    
    /** Cria uma nova instância de CCInternalFrame */
    public CCInternalFrame() {
        configurar();
    }
    
    /**
     *Configura titulo do cadastro
     *@param string com titulo
     */
    public void setTitulo(String titulo) {
        setTitle(titulo);
    }

    public void exibir() {
        GerenciadorAcoesDesktop.exibir(this);
    }
    
    public void exibirExclusivo() {
        GerenciadorAcoesDesktop.exibirExclusivo(this);
    }

    /**
     *Torna visivel o formulario
     */
    public void inicializar() {
        pack();
       
        if (isIniciarMaximizado()) {
            try {
                setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(CCInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            int alturaDesktop = getDesktopPane().getHeight();
            int larguraDesktop = getDesktopPane().getWidth();
            int altura = getHeight();
            int largura = getWidth();
            int posInicialY = (alturaDesktop / 2) - (altura / 2);
            int posInicialX = (larguraDesktop /2) - (largura / 2);
            if (posInicialX < 0) {
                posInicialX = 0;
            }
            if (posInicialY < 0) {
                posInicialY = 0;
            }
            setLocation(posInicialX, posInicialY);
        }
        
        setVisible(true);
    }
    
    /**
     * Configura inicializador do formulario
     * @param indicador - verdadeiro para iniciar maximizado
     */
    public void setIniciarMaximizado(boolean indicador) {
        _iniciarMaximizado = indicador;
    }
    
    /**
     * Indica se formulario deve ser exibido inicialmente maximizado
     * @return - verdadeiro para exibir maximizado
     */
    public boolean isIniciarMaximizado() {
        return _iniciarMaximizado;
    }
    
    /**
     * Adiciona frame interno como filho
     * @param filho
     */
    protected void adicionaFilho(CCInternalFrame filho) {
        if (filho != null) {
            _filhos.add(filho);
        }
    }
    
    protected void removeFillho(CCInternalFrame filho) {
        if (filho != null) {
            _filhos.remove(filho);
        }
    }
    
    /**
     *Finaliza o formulario
     */
    protected void fechar() {
        fecharFilhos();
        doDefaultCloseAction();
    }

    /**
     * Alterar o ponteiro do cursor para ocupado
     */
    protected void alterarCursorOcupado() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    
    /**
     * Alterar o ponteiro do cursor para normal
     */
    protected void alterarCursorNormal() {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void setIcone(int indiceIcone) {

        URL urlIcone = _recursos.getIcone(indiceIcone);
        if (urlIcone == null) {
            setFrameIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/logo_cc_32.gif"));
        }
        else {
            setFrameIcon(new ImageIcon(urlIcone));
        }
    }
    
    private void configurar() {
        setTitle("Código Certo");
        setClosable(true);
        setResizable(true);
        setIcone(Recursos.Icones.FRAME_INTERNO);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setMaximizable(true);
        _filhos = new ArrayList<CCInternalFrame>();
    }
    
    private boolean fecharFilhos() {
        boolean ok = true;
        
        int numFilhos = _filhos.size();
        int i = 0;
        while (ok && (i < numFilhos)) {
            if (_filhos.get(i) != null) {
                _filhos.get(i).fechar();
            }
            i++;
        }
        
        return ok;
    }
    
} ///:~
