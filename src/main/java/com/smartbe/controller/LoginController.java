/*
* The MIT License
* 
* Copyright: Copyright (C) 2017 T2Ti.COM
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
* 
* The author may be contacted at: t2ti.com@gmail.com
*
* @author Claudio de Barros (T2Ti.com)
* 
 */
package com.smartbe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.smartbe.model.bean.cadastros.PapelFuncao;
import com.smartbe.model.bean.cadastros.Usuario;
import com.smartbe.model.dao.DaoGenerico;

import javax.persistence.NoResultException;

public class LoginController implements AuthenticationProvider {

    private DaoGenerico<Usuario> daoUsuario;
    private DaoGenerico<PapelFuncao> daoPapelFuncao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String nomeUsuario = authentication.getName();
        String senha = authentication.getCredentials().toString();
        try {
            daoUsuario = new DaoGenerico<>(Usuario.class);
            daoPapelFuncao = new DaoGenerico<>(PapelFuncao.class);

            Md5PasswordEncoder enc = new Md5PasswordEncoder();
            senha = enc.encodePassword(nomeUsuario + senha, null);
            Usuario usuario = daoUsuario.getBean("login", nomeUsuario, "senha", senha);
            if (usuario != null) {
                List<PapelFuncao> funcoes = daoPapelFuncao.listaBeans("papel", usuario.getPapel());
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                for (PapelFuncao p : funcoes) {
                    if (p.getFuncao().getNome().equals("ADMIN")) {
                        grantedAuths.add(new SimpleGrantedAuthority(p.getFuncao().getNome()));
                    } else {
                        if (p.getPodeConsultar().equals("S")) {
                            grantedAuths.add(new SimpleGrantedAuthority(p.getFuncao().getNome() + "_CONSULTA"));
                        }
                        if (p.getPodeInserir().equals("S")) {
                            grantedAuths.add(new SimpleGrantedAuthority(p.getFuncao().getNome() + "_INSERE"));
                        }
                        if (p.getPodeAlterar().equals("S")) {
                            grantedAuths.add(new SimpleGrantedAuthority(p.getFuncao().getNome() + "_ALTERA"));
                        }
                        if (p.getPodeExcluir().equals("S")) {
                            grantedAuths.add(new SimpleGrantedAuthority(p.getFuncao().getNome() + "_EXCLUI"));
                        }
                    }
                }
                Authentication auth = new UsernamePasswordAuthenticationToken(nomeUsuario, senha, grantedAuths);

                return auth;
            }
        } catch (Exception e) {
            if (!(e instanceof NoResultException)) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
