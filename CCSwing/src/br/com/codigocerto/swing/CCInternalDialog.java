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

package br.com.codigocerto.swing;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * @author lis
 */
public class CCInternalDialog extends CCInternalFrame {

    private boolean _modal = true;
    
    /**
    * Gerar nova instancia de CCInternalDialog
    */
    public CCInternalDialog() {
        configurar();
    }
    /**
     * Gerar nova instancia configurando origem
     * @param origem
     */
    public CCInternalDialog(CCInternalFrame origem) {
        configurar();
    }
    
    /**
     * Configura se dialogo deve ser modal
     * @param modo - verdadeiro para modal
     */
    public void setModal(boolean modo) {
        _modal = modo;
    }

    /**
     * Informa se dialogo esta configurado como modal
     * @return verdadeiro para modal
     */
    public boolean isModal() {
        return _modal;
    }

    private void configurar() {
        setMaximizable(false);
        configuraSelecao();
    }
    
    private void configuraSelecao() {
        
        addInternalFrameListener(new InternalFrameListener() {

            public void internalFrameOpened(InternalFrameEvent arg0) {
            }

            public void internalFrameClosing(InternalFrameEvent arg0) {
            }

            public void internalFrameClosed(InternalFrameEvent arg0) {
            }

            public void internalFrameIconified(InternalFrameEvent arg0) {
            }

            public void internalFrameDeiconified(InternalFrameEvent arg0) {
            }

            public void internalFrameActivated(InternalFrameEvent arg0) {
            }

            public void internalFrameDeactivated(InternalFrameEvent arg0) {
                moveToFront();
            }
        });
    }

}///~
