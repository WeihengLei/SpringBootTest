package com.example.test.service;

import com.example.test.dao.RoleRepository;
import com.example.test.dao.UserRepository;
import com.example.test.dao.UserRoleRepository;
import com.example.test.entity.User;
import com.example.test.entity.UserRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LightSwordUserDetailService implements UserDetailsService {

    @Autowired
    private UserRoleRepository userRoleDao;
    @Autowired
    private UserRepository userDao;
    @Autowired
    private RoleRepository roleDao;


    @Override
    public UserDetails loadUserByUsername(String username){

//    val user = userDao.findByUsername(username) // 直接调用jpa自动生成的方法
        User user = userDao.findUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username + " not found");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> userRoles = userRoleDao.findAllByUserId(user.getId());

        for (UserRole userRole:userRoles) {
            Integer roleId = userRole.getRoleId();
            String roleName = roleDao.findById(roleId).getRole();
            if (!StringUtils.isEmpty(roleName)) {
                authorities.add(new SimpleGrantedAuthority(roleName));
            }
            System.err.println("username is " + username + ", " + roleName);
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

    }
}