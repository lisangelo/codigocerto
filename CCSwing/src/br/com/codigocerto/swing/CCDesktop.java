/*
 * CCDesktop.java
 *
 * Criado em 28 de Maio de 2007, 16:10
 *
 */

package br.com.codigocerto.swing;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.beans.*;

/**
 *
 * @author lis
 */
public class CCDesktop extends CCFrame {
    
    public interface Nivel {
        int NORMAL = 1,
             DIALOGO = 2,
             EXCLUSIVO = 3;
    }
    
    private JDesktopPane _areaTrabalho;
    private JToolBar _ferramentas;
    private IconPolice _iconPolice = new IconPolice( );
    private CCDesktopManager _desktopManager;
    
    /** Cria uma nova instância de CCDesktop */
    public CCDesktop() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        incluirComponentes();
        setMinimumSize(new Dimension(300, 200));
        setSize(600, 480);
        GerenciadorAcoesDesktop.getInstance();
        GerenciadorAcoesDesktop.iniciar(this);
    }
    
    private void incluirComponentes() {
        _areaTrabalho = new JDesktopPane();
        _desktopManager = new CCDesktopManager();
        
        _ferramentas = new JToolBar();
        _ferramentas.setVisible(false);
        _ferramentas.setBounds(0, 0, 1600, 30);
        _ferramentas.setFloatable(false);
        setContentPane(_areaTrabalho);
        _areaTrabalho.setBackground(Color.GRAY);
        _areaTrabalho.setDesktopManager(_desktopManager);

        _areaTrabalho.add(_ferramentas, javax.swing.JLayeredPane.DEFAULT_LAYER);

    }

    /**
     * Torna visivel a janela
     */
    public void visualizar() {
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Configura o titulo da janela principal
     */
    public void setTitulo(String titulo) {
        setTitle(titulo);
    }
    
    /**
     * Inclui botao na barra de ferramentas
     * @param string com legenda do botao
     */
    public void incluirFerramenta(JButton novoBotao) {
        if (_ferramentas != null) {
            _ferramentas.add (novoBotao);
            if ( ! _ferramentas.isVisible()) {
                _ferramentas.setVisible(true);
                _desktopManager.setAlturaBarraFerramentas(_ferramentas.getHeight());
            }
        }
    }
    
    /**
     *Exibir formulario no desktop
     *@param UICadastro
     *@param int nivel (definido em Nivel)
     */
    public void exibir(CCInternalFrame internalFrame, int nivel) {
        adicionarInternalFrame(internalFrame, nivel);
    }

    /**
     * Cria menu com utilitarios
     */
    public void setMenu(JMenuBar mb) {
        
        setJMenuBar(mb);
    }
    
    /**
     * Informa qual frame esta ativo e selecionado no momento
     * @return frame ativo
     */
    public JInternalFrame getFrameAtivo() {
        return _areaTrabalho.getSelectedFrame();
    }
    
    private void adicionarInternalFrame(CCInternalFrame internalFrame, int nivel) {
        
        if ( ! isFrameAtivo(internalFrame)) {
            internalFrame.addVetoableChangeListener(_iconPolice);
            _areaTrabalho.setLayer(internalFrame, nivel);
            _areaTrabalho.add(internalFrame);
            internalFrame.inicializar();
        }
        else {
            internalFrame.dispose();
        }
        
    }
    
    private boolean isFrameAtivo(CCInternalFrame frame) {
        
        boolean ok = false;
        
        JInternalFrame[] framesAtivos =  _areaTrabalho.getAllFrames();
        for (JInternalFrame frameAtivo : framesAtivos) {
            if (frameAtivo.getTitle().equals(frame.getTitle())) {
                if ( ! frameAtivo.isFocusOwner()) {
                    frameAtivo.requestFocusInWindow();
                    ok = true;
                }
            }
        }
        
        return ok;
        
    }

    /** Um vetoable change listener para controlar frames minimizados
     */
    class IconPolice implements VetoableChangeListener {
        @Override
        public void vetoableChange(PropertyChangeEvent ev) throws PropertyVetoException {
            String nome = ev.getPropertyName( );
            if (nome.equals(JInternalFrame.IS_ICON_PROPERTY)
                && (ev.getNewValue( ) == Boolean.TRUE)) {
                JInternalFrame[] frames = _areaTrabalho.getAllFrames( );
                int count = frames.length;
                int nonicons = 0;
                for (int i = 0; i < count; i++) {
                    if (!frames[i].isIcon( )) {
                        nonicons++;
                    }
                }
                if (nonicons <= 1) {
                    throw new PropertyVetoException("Minimizar inválido", ev);
                }
            }
        }
    }
    
} ///~