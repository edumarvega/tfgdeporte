package com.springboot.app.models.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Role;
import com.springboot.app.models.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario user = usuarioDao.findByUsuario(userName);
		if(user==null) {
			throw new UsernameNotFoundException("El usuario no existe!!!");
		} else {
			Set<Role> userRoles = new HashSet<>();
			userRoles.add(user.getRole());
	        List<GrantedAuthority> authorities = getUserAuthority(userRoles);
	        return buildUserForAuthentication(user, authorities);
		}
		
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getPassword(),
                true, true, true, true, authorities);
    }

}
