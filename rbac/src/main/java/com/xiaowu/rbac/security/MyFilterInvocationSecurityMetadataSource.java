package com.xiaowu.rbac.security;

import com.xiaowu.rbac.dao.PermissionMapper;
import com.xiaowu.rbac.dao.RoleMapper;
import com.xiaowu.rbac.dao.RolesPermissonsMapper;
import com.xiaowu.rbac.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    // /error
    // /favicon.ico
    // /login

//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
//    {
//        System.out.println("MyFilterInvocationSecurityMetadataSource = " + object);
//        Collection<ConfigAttribute> co = new ArrayList<>();
//        co.add(new SecurityConfig("null"));
//        return co;
//    }


    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
    // 用来判定用户是否有此权限。如果不在权限表中则放行。
    //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
    // 我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
    //所以此方法的返回值不能返回 null 此处我就随便返回一下。


    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolesPermissonsMapper rolesPermissonsMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        System.out.println("enter MyFilterInvocationSecurityMetadataSource = " + object);
        // object 是一个URL，被用户请求的url。
        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();
//        System.out.println(method);
//        System.out.println("url" + url);   //url/index  url/favicon.ico
        // 错误页面就不需要权限
        if("/error".equals(url)){
            return null;
        }
        // 登录页面不走这里
        // 登录页面就不需要权限
//        if("/login".equals(requestUrl) || "/auth/login".equals(requestUrl)){
//            return null;
//        }
        // 用来存储访问路径需要的角色或权限信息
        List<String> tempList = new ArrayList<>();

        // 获取资源权限列表
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria permissionExampleCriteria = permissionExample.createCriteria();
        permissionExampleCriteria.andUrlEqualTo(url).andMethodEqualTo(method);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        // 如果不在权限表中  就不需要权限 不在权限表里面，就不过判定
        if (permissionList.size() == 0) {
            return null;
        }
        // 存在访问url,方法却不对，应该提示方法异常
        // 而且要支持 “ALL” 访问权限
//         TO/DO: 存在逻辑问题, 我们可以把验证放在下一层中
        // 这一层难以实现
        // 这样就不存在ALL method了
//        for (Permission permission : permissionList){
//            if(permission.getMethod().equals("ALL")){
//
//            }
//        }

//        // 加上请求方法
//        permissionExampleCriteria.andMethodEqualTo(method);
//        List<Permission> permissionList2 = permissionMapper.selectByExample(permissionExample);
//        // 如果不在权限表中 说明请求方法不对
//        if (permissionList2.size() == 0) {
//
//        }



        // 获取角色列表
        List<Integer> permissionIds = permissionList.stream().map(Permission::getId).distinct().collect(Collectors.toList());
        RolesPermissonsExample rolesPermissonsExample = new RolesPermissonsExample();
        RolesPermissonsExample.Criteria rolesPermissonsExampleCriteria= rolesPermissonsExample.createCriteria();
        rolesPermissonsExampleCriteria.andPermissionIdIn(permissionIds);
        List<RolesPermissons> rolesPermissonsList = rolesPermissonsMapper.selectByExample(rolesPermissonsExample);

        List<Integer> roleIds = rolesPermissonsList.stream().map(RolesPermissons::getRoleId).distinct().collect(Collectors.toList());
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
        roleExampleCriteria.andIdIn(roleIds);
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        // 这是在拦截器中行动的 所以会出现两次吗？
        // 添加权限
        for (Role role : roleList){
            tempList.add(role.getRolename());
        }
        String[] tempArray = tempList.toArray(new String[0]);
//        System.out.println(SecurityConfig.createList(String.valueOf(tempList)).toString() );
//        return SecurityConfig.createList(String.valueOf(tempList));
//        System.out.println(tempArray);
        return SecurityConfig.createList(tempArray);


//        Collection<ConfigAttribute> co = new ArrayList<>();
//        co.add(new SecurityConfig("null"));
//        return co;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
