/*
 * CCDesktopManager.java
 *
 * Criado em 28 de Maio de 2007, 16:55
 *
 */

package br.com.codigocerto.swing;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.beans.*;

/**
 *
 * @author lis
 */
public class CCDesktopManager extends DefaultDesktopManager {
    
    int _alturaBarra = 0;

    /**
     *Maximiza o frame interno preservando o espaco da barra
     *@param JInternalFrame o frame a ser maximizado
     */
    @Override
    public void maximizeFrame(JInternalFrame frame) {
        JInternalFrame _frame = (JInternalFrame) frame;
        JDesktopPane _areaTrabalho = _frame.getDesktopPane( );
        Dimension d = _areaTrabalho.getSize( );
        int alturaBarra = _alturaBarra + 1;

        setPreviousBounds(frame, frame.getBounds());
        
        frame.setBounds(0, alturaBarra, d.width, d.height - alturaBarra);
    }
    
    /** Chamado apenas quando um frame se move. Esta implementacao impede que se mova
     * para fora da area de trabalho
     * @param frame movido 
     * @param x coordenada x pretendida
     * @param y coordenada y pretendida
     */
    @Override
    public void dragFrame(JComponent frame, int x, int y) {
        if (frame instanceof JInternalFrame) {  // lida somente com frames internos
            JInternalFrame _frame = (JInternalFrame) frame;
            JDesktopPane _areaTrabalho = _frame.getDesktopPane( );
            Dimension d = _areaTrabalho.getSize( );
      
            int alturaBarra = _alturaBarra + 1; 
            
            if (x < 0) { 
                x = 0;   
            }
            else {
                if (x + _frame.getWidth( ) > d.width) {
                    x = d.width - _frame.getWidth( );
                }
            }
            if (y < alturaBarra) {
                y = alturaBarra; 
            }
            else {
                if (y + _frame.getHeight( ) > d.height) {
                    y = d.height - _frame.getHeight( );
                }
            }
        }

        super.dragFrame(frame, x, y);
    }
    
    /**
     *Informa a altura da barra de ferramentas para apresentacao dos frames internos
     *@return int com altura
     */
    public void setAlturaBarraFerramentas(int altura) {
        _alturaBarra = altura;
    }

    
} ///:~
