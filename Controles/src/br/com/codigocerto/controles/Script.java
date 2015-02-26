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


package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.modelos.ArquivoTexto;
import br.com.codigocerto.modelos.Pasta;
import java.io.File;

/**
 * @author lis
 */
public class Script {

    private String _pasta;

    /**
    * Gerar nova instancia
    */
    public Script() {
    }
    public Script(String pasta) {
        setPasta(pasta);
    }

    /**
     * Configura pasta onde se encontra os scripts de atualizacao
     * @param pasta
     */
    public void setPasta(String pasta) {
        if (pasta != null && (!pasta.isEmpty())) {
            _pasta = pasta;
            System.out.println("Pasta de scripts: " + _pasta);
        }
    }

    public void processar() {
        if (_pasta != null) {
            Pasta pasta = new Pasta(_pasta);
             if (verificarExistencia(pasta)) {
                String[] arquivos = pasta.getNomeArquivos();
                for (int i = 0; i < arquivos.length; i++) {
                    lerArquivo(arquivos[i]);
                }
             }
        }
    }
    
    private void lerArquivo(String arquivoNome) {
        System.out.println("Lendo " + arquivoNome);
        ArquivoTexto arq = new ArquivoTexto();
        arq.setEndereco(_pasta + "/" + arquivoNome);
        if (arq.abrir()) {
            String texto = arq.getTexto();
            arq.fechar();
            if (executarQuery(texto)) {
                eliminarArquivo(arquivoNome);
            }
        } else {
            System.out.println("Falha na leitura do arquivo " + arquivoNome);
        }
        
    }
    
    private void eliminarArquivo(String nome) {
        File arq = new File(_pasta + "/" + nome);
        if (!arq.delete()) {
            System.out.println("Nao foi possivel deletar arquivo " + nome);
        }
    }
    
    private boolean executarQuery(String query) {
        boolean ok = false;
        int num = ServidorBD.executaUpdate(query);
        if (num == -1) {
            System.out.println("Problemas ao executar query " + query);
        } else {
            ok = true;
        }
        
        return ok;
    }

    private boolean verificarExistencia(Pasta pasta) {
        boolean ok = true;
        if (!pasta.existe()) {
            System.out.println(_pasta + " nao existe! Criando diretorio...");
            File dir = new File(_pasta);
            if (dir.mkdir()) {
                System.out.println("Diretorio criado com sucesso!");
            } else {
                ok = false;
                System.out.println("Erro ao criar diretorio!");
            }
        } else {
            if (!pasta.isDiretorio()) {
                System.out.println("Endereco " + _pasta + " invalido!");
                ok = false;
            }
        }

        return ok;
    }

}///:~
