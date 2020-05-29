package com.xiaowu.rbac.security;

import com.xiaowu.rbac.dao.*;
import com.xiaowu.rbac.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 重写springsecurity 核心接口
 * 现在要获取，用户，角色，权限
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UsersRolesMapper usersRolesMapper;

    @Autowired
    private RolesPermissonsMapper rolesPermissonsMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("enter MyUserDetailsService");

        User user = userMapper.findByUsername(username);
//        System.out.println(user.toString());
        if (user == null)
        {
            throw new UsernameNotFoundException("user: " + username + " do not exist!");
        }
        // 多表联查 不知道是不是对的
        UsersRolesExample usersRolesExample = new UsersRolesExample();
        UsersRolesExample.Criteria usersRolesExampleCriteria = usersRolesExample.createCriteria();
        usersRolesExampleCriteria.andUserIdEqualTo(user.getId());
        List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
//        System.out.println(usersRolesList.toString());

        List<Integer> roleIds = usersRolesList.stream().map(UsersRoles::getRoleId).distinct().collect(Collectors.toList());
//        System.out.println(roleIds.toString());

        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
        roleExampleCriteria.andIdIn(roleIds);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
//        System.out.println(roleList.toString());

        if (roleList == null || roleList.size() == 0)
        {
            throw new UsernameNotFoundException("user: " + username + " do not exist!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role : roleList){
            RolesPermissonsExample rolesPermissonsExample = new RolesPermissonsExample();
            RolesPermissonsExample.Criteria rolesPermissonsExampleCriteria = rolesPermissonsExample.createCriteria();
            rolesPermissonsExampleCriteria.andRoleIdEqualTo(role.getId());
            List<RolesPermissons> rolesPermissonsList = rolesPermissonsMapper.selectByExample(rolesPermissonsExample);
//            System.out.println(rolesPermissonsList.toString());

            List<Integer> permissonsIds = rolesPermissonsList.stream().map(RolesPermissons::getPermissionId).distinct().collect(Collectors.toList());
//            System.out.println(permissonsIds.toString());

            PermissionExample permissionExample = new PermissionExample();
            PermissionExample.Criteria permissionExampleCriteria = permissionExample.createCriteria();
            permissionExampleCriteria.andIdIn(permissonsIds);
            List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
//            System.out.println(permissionList.toString());

            for (Permission permission : permissionList)
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(), permission.getMethod(), role.getRolename());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }


        }
        return new MyUserDetails(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}
