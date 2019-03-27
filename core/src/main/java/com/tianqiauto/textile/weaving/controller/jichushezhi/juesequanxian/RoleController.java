package com.tianqiauto.textile.weaving.controller.jichushezhi.juesequanxian;


import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.repository.RoleRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.RoleService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleController
 * @Description 角色管理
 * @Author lrj
 * @Date 2019/3/21 10:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/juesequanxian/role")
@Api(description = "角色管理")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有角色")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<Role> list = roleRepository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @PostMapping("saveRole")
    @ApiOperation(value = "新增角色",notes = "name,beizhu")
    public Result saveRole(@RequestBody Role role){
        boolean flag = roleRepository.existsByName(role.getName());
        if(!flag){
            roleRepository.save(role);
            return Result.ok("新增成功!",role);
        }else{
            return Result.error("该角色名称已存在！",role);
        }
    }

    @PostMapping("updateRole")
    @ApiOperation(value = "修改角色")
    public Result updateRole(@RequestBody Role role){
        boolean flag = roleService.existRoleByName(role.getName());
        if(!flag){
            roleRepository.updateRole(role.getName(),role.getBeizhu(),role.getId());
            return Result.ok("修改成功!",role);
        }else{
            return Result.error("该角色名称已存在！",role);
        }
    }

    @GetMapping("updateRolePermission")
    @ApiOperation(value = "修改角色对应的权限")
    public Result updateRolePermission(String role_id,String[] permission_ids){
        roleService.updateRolePermission(role_id, permission_ids);
        return Result.ok("修改成功!",role_id);
    }

    @GetMapping("deleteRole")
    @ApiOperation(value = "删除角色",notes = "对应的权限，用户角色也删除掉")
    public Result deleteRole(String role_id){
        roleService.deleteRole(role_id);
        return Result.ok("删除成功!",role_id);
    }

    @GetMapping("deleteUserFromRole")
    @ApiOperation(value = "将用户从角色中移除",notes = "用户id，角色id")
    public Result deleteUserFromRole(String role_id,String user_id){
        roleService.deleteUserFromRole(role_id,user_id);
        return Result.ok("移除成功!");
    }

    @GetMapping("getTree")
    @ApiOperation(value = "获取角色对应的权限树")
    public Result getTree(String role_id){
        List<Map<String,Object>> list = roleService.getTree(role_id);
        return Result.ok("查询成功!",list);
    }



}
