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
package br.com.codigocerto.controles.sistema;

import br.com.codigocerto.modelos.ArquivoTexto;
import br.com.codigocerto.modelos.sistema.PastasSistema;
import br.com.codigocerto.xml.GeradorXML;
import br.com.codigocerto.xml.ParserXML;

/**
 * @author lis
 */
public class RegistroPastasSistema {
    
    String URL_XMl = "etc/pastas.xml";

    /**
     * Gerar nova instancia
     */
    public RegistroPastasSistema() {
    }
    
    /**
     * Salva as configuracoes
     * @param modelo
     * @return verdadeiro para informacoes salvas
     */
    public boolean salvar(PastasSistema modelo) {
        boolean salvo = false;
        
        try {
            GeradorXML xml = new GeradorXML(PastasSistema.class);
            String textoXML = xml.object2XML(modelo);
            ArquivoTexto arq = new ArquivoTexto(URL_XMl);
            arq.abrirEdicao();
            arq.setTexto(textoXML);
            arq.fechar();
            salvo = true;
        } catch (Exception e) {
            System.out.println("Problemas ao gerar arquivo de pastas!");
        }

        return salvo;
    }
    
    /**
     * Ler as configuracoes salvas
     * @return configuracao de email
     */
    public PastasSistema ler() {
        PastasSistema pastas = null;
        
        try {
            ParserXML parser = new ParserXML();
            parser.setNomeArquivoXml(URL_XMl);
            parser.Iniciar();
            parser.ProcessarArquivoXml();
            pastas = new PastasSistema();
            pastas.setNFeRecebida(parser.getValor("nferecebida"));
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Problemas ao ler arquivo de pastas!");
        }

        return pastas;
    }

}///:~

