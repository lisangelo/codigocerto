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
import br.com.codigocerto.modelos.sistema.CFOPEmissao;
import br.com.codigocerto.xml.GeradorXML;
import br.com.codigocerto.xml.ParserXML;

/**
 * @author lis
 */
public class RegistroCFOPEmissao {
    
    String URL_XMl = "etc/cfop.xml";

    /**
     * Gerar nova instancia
     */
    public RegistroCFOPEmissao() {
    }
    
    /**
     * Salva as configuracoes
     * @param modelo
     * @return verdadeiro para informacoes salvas
     */
    public boolean salvar(CFOPEmissao modelo) {
        boolean salvo = false;
        
        try {
            GeradorXML xml = new GeradorXML(CFOPEmissao.class);
            String textoXML = xml.object2XML(modelo);
            ArquivoTexto arq = new ArquivoTexto(URL_XMl);
            arq.abrirEdicao();
            arq.setTexto(textoXML);
            arq.fechar();
            salvo = true;
        } catch (Exception e) {
            System.out.println("Problemas ao gerar arquivo de configuracao de CFOPs de emissão!");
        }

        return salvo;
    }
    
    /**
     * Ler as configuracoes salvas
     * @return configuracao da empresa base
     */
    public CFOPEmissao ler() {
        CFOPEmissao cfop = null;
        
        try {
            ParserXML parser = new ParserXML();
            parser.setNomeArquivoXml(URL_XMl);
            parser.Iniciar();
            parser.ProcessarArquivoXml();
            cfop = new CFOPEmissao();
            cfop.setVenda(parser.getValor("venda"));
            cfop.setVendaFE(parser.getValor("vendafe"));
            cfop.setAmostra(parser.getValor("amostra"));
            cfop.setAmostraFE(parser.getValor("amostrafe"));
            cfop.setVendaST(parser.getValor("vendast"));
            cfop.setVendaSTFE(parser.getValor("vendastfe"));
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Problemas ao ler arquivo de configuracao para CFOPs!");
        }

        return cfop;
    }

}///:~

