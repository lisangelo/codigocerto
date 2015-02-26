/*
Copyright (c) 2008, Codigo Certo Sistemas www.codigocerto.com.br
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following 
conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, 
       this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of the Codigo Certo nor the names of its contributors may be used to endorse or promote products 
       derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package br.com.codigocerto.swing.botoes;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.com.codigocerto.configuracao.Recursos;

/**
 * @author lis
 */
public class BotaoImprimirConsulta extends JButton {
    
    Recursos _recursos = new Recursos();
    
    /** Cria uma nova instância de BotaoImprimirConsulta */
    public BotaoImprimirConsulta() {
        setText("");
        setIcone(Recursos.IconesBotoes.IMPRIMIR_CONSULTA);
        setText("Imprimir");
        setMnemonic('I');
        setToolTipText("Gerar e imprimir consulta <Alt+I>");
        setName("BotaoImprimirConsulta");
    }

    private void setIcone(int indiceIcone) {
        
        URL urlIcone = _recursos.getIconeBotao(indiceIcone);
        if (urlIcone == null) {
            setIcon(new ImageIcon("/Publico/CodigoCerto/Imagens/bt_imprimirconsulta.gif"));
        }
        else {
            setIcon(new ImageIcon(urlIcone));
        }
    }
    
} ///:~