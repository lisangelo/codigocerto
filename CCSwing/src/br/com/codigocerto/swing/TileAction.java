/*
 * TileAction.java
 *
 * Criado em 28 de Maio de 2007, 16:36
 *
 */

package br.com.codigocerto.swing;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.beans.*;

/**
 *
 * @author lis
 */
public class TileAction extends AbstractAction {
    
    private JDesktopPane _areaTrabalho;
    
    /** Cria uma nova instância de TileAction */
    public TileAction() {
        super("Organizar janelas");
    }
    
    /**
     * Configura pane com area de trabalho 
     * @param JDesktopPane com area de trabalho
     */
    public void setAreaTrabalho(JDesktopPane area) {
        _areaTrabalho = area;
    }
    
    public void actionPerformed(ActionEvent ev) {

        // quantos frames temos?
        JInternalFrame[] todosFrames = _areaTrabalho.getAllFrames();
        int contagem = todosFrames.length;
        if (contagem == 0) {
            return;
        }

        // determinar o tamanho necessario
        int sqrt = (int)Math.sqrt(contagem);
        int linhas = sqrt;
        int colunas = sqrt;
        if (linhas * colunas < contagem) {
            colunas++;
            if (linhas * colunas < contagem) {
                linhas++;
            }
        }

        // valores iniciais
        Dimension tamanho = _areaTrabalho.getSize( );

        int w = tamanho.width / colunas;
        int h = tamanho.height / linhas;
        int x = 0;
        int y = 0;

        // reposicionar os frames internos
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas && ((i * colunas) + j < contagem); j++) {
                JInternalFrame f = todosFrames[(i * colunas) + j];

                if (!f.isClosed( ) && f.isIcon( )) {
                    try {
                        f.setIcon(false);
                    }
                    catch (PropertyVetoException ignored) {}
                }

                _areaTrabalho.getDesktopManager( ).resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // proxima linha
            x = 0;

        }
    }
    
} ///:~



