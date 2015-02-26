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


package br.com.codigocerto.mail;

import br.com.codigocerto.modelos.Arquivo;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author lis
 */
public class Mail {

    public enum Conteudo {
        TEXTO, HTML, BMP, CSS, CSV, GIF, JPEG, PDF, PNG, RSS, TIFF, WBMP, ICS, VCARD
    }
    
    public enum Seguranca {
        NENHUMA, TLS, SSL
    }

    private interface TipoConteudo {
        String TEXTO = "text/plain", 
               HTML = "text/html",
               BMP = "image/x-ms-bmp",
               CSS = "text/css",
               CSV = "text/comma-separated-values",
               GIF = "image/gif",
               JPEG = "image/jpeg",
               PDF = "application/pdf",
               PNG = "image/png",
               RSS = "application/rss+xml",
               TIFF = "image/tiff",
               WBMP = "image/vnd.wap.wbmp",
               ICS = "text/calendar",
               VCARD = "text/x-vcard";
    }

    private final String PROPRIEDADE_SMTP_HOST = "mail.smtp.host";
    private final String PROPRIEDADE_SMTP_AUTH = "mail.smtp.auth";
    private final String PROPRIEDADE_STMP_PORTA = "mail.smtp.port";
    private final String PROPRIEDADE_SMTP_USER = "mail.smtp.user";
    private final String PROPRIEDADE_STMP_PASSWORD = "mail.smtp.password";
    private final String PROPRIEDADE_SSL_FACTORY = "mail.smtp.socketFactory.class";
    private final String PROPRIEDADE_SSL_CLASS = "javax.net.ssl.SSLSocketFactory";
    private final String PROPRIEDADE_SSL_PORT = "mail.smtp.socketFactory.port";
    private final String PROPRIEDADE_STARTTLS = "mail.smtp.starttls.enable";

    private Session _sessao;
    private Properties _propriedades;
    private String _texto;
    private Conteudo _tipoMensagem;
    private ArrayList<String> _anexosArquivo = new ArrayList<String>();
    private ArrayList<ConteudoAnexo> _anexosConteudo = new ArrayList<ConteudoAnexo>();
    private boolean _servidorSMTPRequerLogin = false;
    private boolean _existemAnexos = false;

    /**
    * Gerar nova instancia
    */
    public Mail() {
    }
    
    /**
     * Configura o servidor SMTP para envio
     * @param host - nome do host ou ip
     * @param porta 
     */
    public void setSMTP(String host, int porta) {
        _propriedades = new Properties();
        _propriedades.put(PROPRIEDADE_SMTP_HOST, host);
        _propriedades.put(PROPRIEDADE_STMP_PORTA, String.valueOf(porta));
        _sessao = Session.getDefaultInstance(_propriedades, null);
    }

    /**
     * Configura o servidor SMTP para envio com login
     * @param host - nome do host ou ip
     * @param porta
     * @param usuario
     * @param senha
     */
    public void setSMTP(String host, int porta, String usuario, String senha) {
        _propriedades = new Properties();
        _propriedades.put(PROPRIEDADE_SMTP_HOST, host);
        _propriedades.put(PROPRIEDADE_STMP_PORTA, String.valueOf(porta));
        _propriedades.put(PROPRIEDADE_SMTP_USER, usuario);
        _propriedades.put(PROPRIEDADE_STMP_PASSWORD, senha);
        _servidorSMTPRequerLogin = true;
        _sessao = Session.getDefaultInstance(_propriedades, null);
    }

    /**
     * Configura o servidor SMTP para envio com login
     * @param host - nome do host ou ip
     * @param porta
     * @param usuario
     * @param senha
     * @param seguranca
     */
    public void setSMTP(String host, int porta, String usuario, String senha, String seguranca) {
        _propriedades = new Properties();
        _propriedades.put(PROPRIEDADE_SMTP_HOST, host);
        _propriedades.put(PROPRIEDADE_STMP_PORTA, String.valueOf(porta));
        _propriedades.put(PROPRIEDADE_SMTP_USER, usuario);
        _propriedades.put(PROPRIEDADE_STMP_PASSWORD, senha);
        if (seguranca.equals(Seguranca.SSL.toString())) {
            _propriedades.put(PROPRIEDADE_SSL_FACTORY, PROPRIEDADE_SSL_CLASS);
            _propriedades.put(PROPRIEDADE_SMTP_AUTH, "true");
            _propriedades.put(PROPRIEDADE_SSL_PORT, String.valueOf(porta));
        } else if(seguranca.equals(Seguranca.TLS.toString())) {
            _propriedades.put(PROPRIEDADE_SMTP_AUTH, "true");
            _propriedades.put(PROPRIEDADE_STARTTLS, "true");            
        }
        _servidorSMTPRequerLogin = true;
        _sessao = Session.getDefaultInstance(_propriedades, null);
    }

    /**
     * Configura a mensagem
     * @param texto - corpo da mensagem
     * @param tipo - tipo do conteudo da mensagem, texto puro ou html
     */
    public void setMensagem(String texto, Conteudo tipo) {
        _texto = texto;
        _tipoMensagem = tipo;
    }

    /**
     * Insere um arquivo na mensagem
     * @param url
     * @return verdadeiro para arquivo encontrado e anexado
     */
    public boolean inserirAnexo(String url) {
        boolean ok = false;

        Arquivo arquivo = new Arquivo(url);
        if (arquivo.existe()) {
            if (!arquivo.isDiretorio()) {
                ok = _anexosArquivo.add(url);
                if (ok) {
                    _existemAnexos = true;
                }
            }
        }

        return ok;
    }

    /**
     * Insere conteudo na mensagem como anexo
     * @param nome
     * @param conteudo
     * @param tipo
     * @return verdadeiro para anexado
     */
    public boolean inserirAnexo(String nome, String conteudo, Conteudo tipo) {
        boolean ok = false;

        ConteudoAnexo anexo = new ConteudoAnexo(nome, conteudo, tipo);
        ok = _anexosConteudo.add(anexo);
        if (ok) {
            _existemAnexos = true;
        }

        return ok;
    }

    /**
     * Envia a mensagem
     * @param emissor - endereco do emissor
     * @param destino - endereco do destinatario
     * @param assunto - assunto da mensagem
     * @return verdadeiro para enviado
     */
    public boolean enviar(String emissor, String destino, String assunto) {
        boolean ok = false;

        if (_propriedades != null) {
            if (_texto != null) {
                if (_tipoMensagem != null) {
                    Message mensagem = gerarMensagem(emissor, destino, assunto);
                    if (mensagem != null) {
                        ok = enviarSMTP(mensagem);
                    }
                }
            }
        }

        return ok;
    }

    private boolean enviarSMTP(Message mensagem) {
        boolean ok = true;
        try {
            if (!_servidorSMTPRequerLogin) {
                Transport.send(mensagem);
            } else {
                Transport tr = _sessao.getTransport("smtp");
                tr.connect(_propriedades.getProperty(PROPRIEDADE_SMTP_HOST), 
                        _propriedades.getProperty(PROPRIEDADE_SMTP_USER), 
                        _propriedades.getProperty(PROPRIEDADE_STMP_PASSWORD));
                mensagem.saveChanges();      
                tr.sendMessage(mensagem, mensagem.getAllRecipients());
                tr.close();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            ok = false;
        }

        return ok;
    }

    private Message gerarMensagem(String emissor, String destino, String assunto) {
        Message mensagem = new MimeMessage(_sessao);
        try {
            mensagem.setFrom(new InternetAddress(emissor));
            mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mensagem.setSubject(assunto);

            if (_existemAnexos) {
                MimeBodyPart conteudo = new MimeBodyPart();
                if (_tipoMensagem.equals(Conteudo.HTML)) {
                    conteudo.setContent(_texto, TipoConteudo.HTML);
                } else {
                    conteudo.setContent(_texto, TipoConteudo.TEXTO);
                }
                Multipart divisoesConteudo = new MimeMultipart();
                divisoesConteudo.addBodyPart(conteudo);
                inserirAnexosArquivo(divisoesConteudo);
                inserirAnexosConteudo(divisoesConteudo);
                mensagem.setContent(divisoesConteudo);
            } else {
                if (_tipoMensagem.equals(Conteudo.HTML)) {
                    mensagem.setContent(_texto, TipoConteudo.HTML);
                } else {
                    mensagem.setContent(_texto, TipoConteudo.TEXTO);
                }
            }

        } catch (MessagingException ex) {
                Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
                mensagem = null;
        }

        return mensagem;
    }

    private void inserirAnexosArquivo(Multipart divisoesConteudo) {
        if (_anexosArquivo.size() > 0) {
            MimeBodyPart anexo;
            DataSource origemAnexo;
            for (int i = 0; i < _anexosArquivo.size(); i++) {
                anexo = new MimeBodyPart();
                origemAnexo = new FileDataSource(_anexosArquivo.get(i));
                try {
                    anexo.setDataHandler(new DataHandler(origemAnexo));
                    anexo.setFileName(_anexosArquivo.get(i));
                    divisoesConteudo.addBodyPart(anexo);
                } catch (MessagingException ex) {
                    Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void inserirAnexosConteudo(Multipart divisoesConteudo) {
        if (_anexosConteudo.size() > 0) {
            MimeBodyPart anexo;
            DataSource origemAnexo;
            ConteudoAnexo conteudoAnexo;
            for (int i = 0; i < _anexosConteudo.size(); i++) {
                anexo = new MimeBodyPart();
                conteudoAnexo = _anexosConteudo.get(i);
                try {
                    anexo.setDataHandler(
                            new DataHandler(conteudoAnexo.getConteudo(),
                                            conteudoAnexo.getTipo())
                                            );
                    anexo.setFileName(conteudoAnexo.getNome());
                    divisoesConteudo.addBodyPart(anexo);
                } catch (MessagingException ex) {
                    Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class ConteudoAnexo {

        private String _nome;
        private String _conteudo;
        private Conteudo _tipo;

        public ConteudoAnexo(String nome, String conteudo, Conteudo tipo) {
            _nome = nome;
            _conteudo = conteudo;
            _tipo = tipo;
        }

        public String getConteudo() {
            return _conteudo;
        }

        public String getNome() {
            return _nome;
        }

        public String getTipo() {
            if (_tipo.equals(Conteudo.HTML)) {
                return TipoConteudo.HTML;
            } else {
                return TipoConteudo.TEXTO;
            }
        }

    }

}///:~
