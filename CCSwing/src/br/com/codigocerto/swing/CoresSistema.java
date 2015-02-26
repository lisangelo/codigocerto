/*
 * CoresSistema.java
 *
 * Criado em 24 de Maio de 2007, 10:20
 *
 */

package br.com.codigocerto.swing;

import java.awt.Color;

/**
 *
 * @author lis
 */
public class CoresSistema {
    
    public final static int CAMPO_NORMAL = 0; 
    public final static int CAMPO_SELECIONADO = 1;
    public final static int TABELA_NORMAL = 2;
    public final static int TABELA_SELECIONADO = 3;
    public final static int LINK_NORMAL = 4; 
    public final static int LINK_SELECIONADO = 5;
    public final static int CHECK_NORMAL = 6; 
    public final static int CHECK_SELECIONADO = 7;
    public final static int TABELA_SELECIONADO_FONTE = 8;
    public final static int AVISO = 9;
    
    public static Color corPadrao(int padrao) {
        
        int red, green, blue;
        
        switch (padrao) {
            case CAMPO_NORMAL:
                red = 255; green = 255; blue = 255;
                break;
            case CAMPO_SELECIONADO:
                red = 255; green = 255; blue = 153;
                break;
            case TABELA_NORMAL:
                red = 184; green = 207; blue = 229;
                break;
            case TABELA_SELECIONADO:
                red = 255; green = 255; blue = 153;
                break;
            case TABELA_SELECIONADO_FONTE:
                red = 0; green = 0; blue = 0;
                break;
            case LINK_NORMAL:
                red = 51; green = 51; blue = 51;
                break;
            case LINK_SELECIONADO:
                red = 51; green = 51; blue = 255;
                break;
            case CHECK_NORMAL:
                red = 238; green = 238; blue = 238;
                break;
            case CHECK_SELECIONADO:
                red = 255; green = 255; blue = 153;
                break;
            case AVISO:
                red = 255; green = 51; blue = 51;
                break;
            default:
                red = 255; green = 255; blue = 255;
                break;
        }
        
        return new Color(red, green, blue);
    }
    
} ///:~
