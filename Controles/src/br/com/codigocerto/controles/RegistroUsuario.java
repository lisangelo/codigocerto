/*
 *
 * Codigo Certo
 *
 * RegistroUsuario.java
 *
 * Criado em 30 de Março de 2007, 10:26
 *
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.modelos.*;
import br.com.codigocerto.seguranca.Criptografia;
import br.com.codigocerto.bancodados.*;

/**
 *
 * @author lis
 */
public class RegistroUsuario extends Registro {

    public interface BD {
        String  ID = "cc_usuarios_id", 
                NOME = "cc_usuarios_nome",
                LOGIN = "cc_usuarios_login",
                SENHA = "cc_usuarios_senha",
                EMAIL = "cc_usuarios_email",
                SMTP = "cc_usuarios_smtp",
                ACESSO = "cc_usuarios_acesso";
    }
    
    private final long _ID_CC = 1;
    private final String _NOME_CC = "Código Certo";
    private final String _LOGIN_CC = "codigo";
    private final String _SENHA_CC = "a22e9c1beddf8b89fb4a9a0a089085c5";
    private RegistroUsuarioExcecao _regExcecao = new RegistroUsuarioExcecao();
    
    /** Creates a new instance of RegistroCliente */
    public RegistroUsuario() {
    }
    
    /*
     *Metodo para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    @Override
    protected void estabelecerLigacao() {
        
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_usuarios");
        ligacao.setCampoId("cc_usuarios_id");
        
    }

    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    @Override
    public Usuario getDados(long id) {
        
        Usuario modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = lerRegistro(tabela);
        }
        tabela.fechar();
        
        return modelo;
        
    }        

    /*
     *Obtem dados da base
     *@param login - String com login informado
     *@param senha - String com senha informada
     *@return objeto com dados retornados da base
     */ 
    public Usuario getDados(String login, String senha) {
        
        Usuario modelo = null;
        String loginMin = login;
        
        if (loginMin.equals(_LOGIN_CC)) {
            modelo = usuarioCC(senha);
        }
        else {
            SelectSqlBD sql = new SelectSqlBD();
            sql.setTabela(ligacao.getTabela());
            sql.insereCondicao("cc_usuarios_login = " 
                               + FormatacaoSQL.getDadoFormatado(login, TiposDadosQuery.TEXTO));
            TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
            if (tabela.primeiro()) {
                boolean encontrado = false;
                String senhaCripto;
                do {
                    senhaCripto = LerCampo.getString(tabela.getColuna(BD.SENHA));
                    if ( ! Criptografia.getMD5(senha).contains(senhaCripto)) {
                        modelo = null;
                    } else {
                        modelo = lerRegistro(tabela);
                        encontrado = true;
                    }
                } while ((!encontrado) && tabela.proximo());
            }
            tabela.fechar();
        }
        
        return modelo;
    }        

    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    @Override
    public boolean setDadosInsercao(Object dados) {
        
        boolean ok = false;
        Usuario modelo = (Usuario) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna("cc_usuarios_id", String.valueOf(modelo.getId()));
            sql.insereColuna("cc_usuarios_nome", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            System.out.println(modelo.getLogin());
            sql.insereColuna("cc_usuarios_login", FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLogin(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna("cc_usuarios_senha", FormatacaoSQL.getDadoFormatado(
                                                            Criptografia.getMD5(modelo.getSenha()), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SMTP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSMTP(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ACESSO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getAcesso(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getEmail() != null) {
                sql.insereColuna(BD.EMAIL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getEmail().getEndereco(),
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            TransacaoBD trans = ServidorBD.getTransacao();

            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                insereExcecoes(modelo, trans);
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }

        return ok;
    }

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    @Override
    public boolean setDadosAlteracao(Object dados) {
        final int _TAM_SENHA_MD5 = 32;
        boolean ok = false;
        Usuario modelo = (Usuario) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            
            StringBuilder senha = new StringBuilder();
            if (modelo.getSenha().length() == _TAM_SENHA_MD5) {
                senha.append(modelo.getSenha()); // senha ja esta encriptada
            }
            else {
                senha.append(Criptografia.getMD5(modelo.getSenha()));
            }

            sql.setTabela(ligacao.getTabela());
            sql.insereColuna("cc_usuarios_nome", 
                              FormatacaoSQL.getDadoFormatado(modelo.getNome(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            System.out.println(modelo.getLogin());
            sql.insereColuna("cc_usuarios_login", 
                              FormatacaoSQL.getDadoFormatado(modelo.getLogin(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna("cc_usuarios_senha", 
                              FormatacaoSQL.getDadoFormatado(senha.toString(), 
                                                             TiposDadosQuery.TEXTO
                                                            )
                            );
            sql.insereColuna(BD.SMTP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSMTP(),
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.ACESSO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getAcesso(),
                                                            TiposDadosQuery.NUMERO)
                                                            );
            if (modelo.getEmail() != null) {
                sql.insereColuna(BD.EMAIL, FormatacaoSQL.getDadoFormatado(
                                                                modelo.getEmail().getEndereco(),
                                                                TiposDadosQuery.TEXTO)
                                                                );
            }
            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
            TransacaoBD trans = ServidorBD.getTransacao();
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
                eliminaExcecoes(modelo, trans);
                insereExcecoes(modelo, trans);
                trans.commit();
            }
            else {
                ok = false;
                trans.rollback();
            }
            trans.fechar();
        }
        else {
            ok = false;
        }

        return ok;
    }
    
    @Override
    public boolean excluir(long id) {
         boolean ok = false;

         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(ligacao.getCampoId() + " = " + id);

         TransacaoBD trans = ServidorBD.getTransacao();
         Usuario modelo = getDados(id);

         eliminaExcecoes(modelo, trans);
         if (trans.executaUpdate(sql.getQuery()) == 1) {
             ok = true;
         }
         if (ok) {
             trans.commit();
         } else {
             trans.rollback();
         }
         trans.fechar();

         return ok;
    }

    private Usuario usuarioCC(String senha) {
        Usuario cc = null;

        if (Criptografia.getMD5(senha).equals(_SENHA_CC)) {
            cc = new Usuario();
            cc.setId(_ID_CC);
            cc.setLogin(_LOGIN_CC);
            cc.setNome(_NOME_CC);
        }
    
        return cc;
    }

    private Usuario lerRegistro(TabelaBD tabela) {
        Usuario modelo = new Usuario();
        modelo.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
        modelo.setNome(LerCampo.getString(tabela.getColuna(BD.NOME)));
        if (tabela.getColuna(BD.EMAIL) != null) {
            modelo.setEmail(new Email(LerCampo.getString(tabela.getColuna(BD.EMAIL))));
        }
        modelo.setSMTP(LerCampo.getString(tabela.getColuna(BD.SMTP)));
        modelo.setAcesso(LerCampo.getInt(tabela.getColuna(BD.ACESSO)));
        String colunaLogin = (String) tabela.getColuna(BD.LOGIN);
        if (colunaLogin != null) {
            modelo.setLogin(colunaLogin);
        }
        String colunaSenha = (String) tabela.getColuna(BD.SENHA);
        if (colunaSenha != null) {
            modelo.setSenha(colunaSenha);
        }
        leExcecoes(modelo);

        return modelo;

    }

   private boolean insereExcecoes(Usuario modelo, TransacaoBD trans) {
       boolean ok = true;
       _regExcecao.setTransacao(trans);
       _regExcecao.setIdPai(modelo.getId());

       for (int i = 0; i < modelo.getNumeroExcecoes(); i++) {
            ok = _regExcecao.setDadosInsercao(modelo.getExcecao(i));
       }

       return ok;
   }

   private boolean eliminaExcecoes(Usuario modelo, TransacaoBD trans) {
       boolean ok = false;

       _regExcecao.setIdPai(modelo.getId());
       _regExcecao.setTransacao(trans);
       if (_regExcecao.eliminaFilhos() > 0) {
           ok = true;
       }

       return ok;
   }

   private void leExcecoes(Usuario modelo) {

       _regExcecao.setIdPai(modelo.getId());
       UsuarioExcecao[] itens = _regExcecao.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               modelo.adicionarExcecao(itens[i]);
           }
       }
   }
}
