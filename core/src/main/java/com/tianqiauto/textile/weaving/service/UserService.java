package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.base.Permission;
import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-27 20:40
 * @Version 1.0
 **/
@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;

    public List<Permission> getPermissions(Long id){
        List<Permission>  list = new ArrayList<>();
        User user = userRepository.getOne(id);
        for(Role role : user.getRoles()){
           for(Permission permission : role.getPermissions()){
               if(!list.contains(permission)){
                   list.add(permission);
               }
           }
        }
        return list;
    }

    public List<Permission> getPermissions(User user){
        List<Permission>  list = new ArrayList<>();
        for(Role role : user.getRoles()){
           for(Permission permission : role.getPermissions()){
               if(!list.contains(permission)){
                   list.add(permission);
               }
           }
        }
        return list;
    }


}
