/*
 * Codigo Certo
 * Texto.java
 * @author lis
 * Criado em 10 de Janeiro de 2008, 12:51
 */

package br.com.codigocerto.conversores;

public class Texto {
    

    /**
     * Converte um texto para texto simples (apenas letras e numeros) eliminando caracteres especiais
     * @param String com texto a ser convertido
     * @return String com texto simples
     */
    public static String converteTextoSimples(String texto) {
       StringBuilder textoSimples = new StringBuilder(texto.length());
       
       for (int i = 0; i < texto.length(); i++) {
           textoSimples.append(converter(texto.charAt(i)));
       }
       
       return textoSimples.toString();
    }
    
    /**
     * Controla o tamanho do texto
     * @param String com texto a ser convertido
     * @param tamanho final do texto
     * @return texto
     */
    public static String tamanhoFixo(String texto, int tamanho) {
       StringBuilder textoSimples = new StringBuilder(tamanho);

       if (texto.length() > tamanho) {
           textoSimples.append(texto.substring(0, tamanho));
       } else {
           textoSimples.append(texto);
           while (textoSimples.length() < tamanho) {
               textoSimples.append(" ");
           }
       }

       return textoSimples.toString();
    }

    private static char converter(char caracter) {
        char letraNumero = br.com.codigocerto.simbolos.Texto.ESPACO;
        switch (caracter) {
            case 'Á': case 'À': case 'Ä': case 'Â': case 'Ã': case 'A':
            case 'á': case 'à': case 'ä': case 'â': case 'ã': case 'a':
                letraNumero = 'A';
                break;
            case 'B':
            case 'b':
                letraNumero = 'B';
                break;
            case 'C': case 'Ç':
            case 'c': case 'ç':
                letraNumero = 'C';
                break;
            case 'D':
            case 'd':
                letraNumero = 'D';
                break;
            case 'É': case 'È': case 'Ë': case 'Ê': case 'Ẽ': case 'E':
            case 'é': case 'è': case 'ë': case 'ê': case 'ẽ': case 'e':
                letraNumero = 'E';
                break;
            case 'F':
            case 'f':
                letraNumero = 'F';
                break;
            case 'G':
            case 'g':
                letraNumero = 'G';
                break;
            case 'H':
            case 'h':
                letraNumero = 'H';
                break;
            case 'Í': case 'Ì': case 'Ï': case 'Î': case 'Ĩ': case 'I':
            case 'í': case 'ì': case 'ï': case 'î': case 'ĩ': case 'i':
                letraNumero = 'I';
                break;
            case 'J':
            case 'j':
                letraNumero = 'J';
                break;
            case 'K':
            case 'k':
                letraNumero = 'K';
                break;
            case 'L':
            case 'l':
                letraNumero = 'L';
                break;
            case 'M':
            case 'm':
                letraNumero = 'M';
                break;
            case 'Ñ': case 'N':
            case 'ñ': case 'n':
                letraNumero = 'N';
                break;
            case 'Ó': case 'Ò': case 'Ö': case 'Ô': case 'Õ': case 'O':
            case 'ó': case 'ò': case 'ö': case 'ô': case 'õ': case 'o':
                letraNumero = 'O';
                break;
            case 'P':
            case 'p':
                letraNumero = 'P';
                break;
            case 'Q':
            case 'q':
                letraNumero = 'Q';
                break;
            case 'R':
            case 'r':
                letraNumero = 'R';
                break;
            case 'S':
            case 's':
                letraNumero = 'S';
                break;
            case 'T':
            case 't':
                letraNumero = 'T';
                break;
            case 'Ú': case 'Ù': case 'Ü': case 'Û': case 'Ũ': case 'U':
            case 'ú': case 'ù': case 'ü': case 'û': case 'ũ': case 'u':
                letraNumero = 'U';
                break;
            case 'V':
            case 'v':
                letraNumero = 'V';
                break;
            case 'W':
            case 'w':
                letraNumero = 'W';
                break;
            case 'X':
            case 'x':
                letraNumero = 'X';
                break;
            case 'Y':
            case 'y':
                letraNumero = 'Y';
                break;
            case 'Z':
            case 'z':
                letraNumero = 'Z';
                break;
            case '0': case '1': case '2': case '3': case '4': case '5': 
            case '6': case '7': case '8': case '9':
                letraNumero = caracter;
                break;
            case '&':
                letraNumero = caracter;
                break;
        }
        
        return letraNumero;
        
    }
    
} ///~
