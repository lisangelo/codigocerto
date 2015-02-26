/*
Copyright (c) 2009, Codigo Certo Sistemas www.codigocerto.com.br
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


package br.com.codigocerto.swing.formularios;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author lis
 */
public class CCSelecionaPasta extends JFileChooser {

    public CCSelecionaPasta() {
        configurar();
    }
    public CCSelecionaPasta(String diretorioAtual) {
        super(diretorioAtual);
        configurar();
    }
    public CCSelecionaPasta(File diretorioAtual) {
        super(diretorioAtual);
        configurar();
    }
    public CCSelecionaPasta(FileSystemView fsv) {
        super(fsv);
        configurar();
    }
    public CCSelecionaPasta(File diretorioAtual, FileSystemView fsv) {
        super(diretorioAtual, fsv);
        configurar();
    }
    public CCSelecionaPasta(String diretorioAtual, FileSystemView fsv) {
        super(diretorioAtual, fsv);
        configurar();
    }

    private void configurar() {
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setApproveButtonText("OK");
        setDialogTitle("Selecionar pasta");
        setMultiSelectionEnabled(false);
    }

}///:~
