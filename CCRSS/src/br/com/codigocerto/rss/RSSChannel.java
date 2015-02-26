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


package br.com.codigocerto.rss;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;

/**
 * @author lis
 */
@XStreamAlias("channel")
public class RSSChannel {

    @XStreamAlias("title")
    private String _title;
    @XStreamAlias("link")
    private String _link;
    @XStreamAlias("description")
    private String _description; 
    @XStreamAlias("pubDate")
    private String _pubDate;
    @XStreamAlias("ttl")
    private String _ttl;
    @XStreamImplicit(itemFieldName="item")
    private ArrayList<RSSChannelItem>_itens;

   /**
    * Gerar nova instancia
    */
    public RSSChannel() {
    }

    public String getDescription() {
        return _description;
    }

    public String getLink() {
        return _link;
    }

    public String getPubDate() {
        return _pubDate;
    }

    public String getTitle() {
        return _title;
    }

    public String getTTL() {
        return _ttl;
    }

    public int getNumeroItens() {
        int numero = 0;
        if (_itens != null) {
            numero = _itens.size();
        }

        return numero;
    }

    public RSSChannelItem getItem(int pos) {
        RSSChannelItem item = null;
        if (_itens != null) {
            if (pos < _itens.size()) {
                item = _itens.get(pos);
            }
        }
        return item;
    }

}///:~
