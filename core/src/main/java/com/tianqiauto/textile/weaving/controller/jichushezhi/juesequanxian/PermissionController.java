package com.tianqiauto.textile.weaving.controller.jichushezhi.juesequanxian;

import com.tianqiauto.textile.weaving.model.base.Permission;
import com.tianqiauto.textile.weaving.repository.PermissionRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.PermissionService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PermissionController
 * @Description 权限管理
 * @Author lrj
 * @Date 2019/3/21 10:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/juesequanxian/permission")
@Api(description = "权限管理")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有权限")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "permissionName");
        List<Permission> list = permissionRepository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("findAllByParent_id")
    @ApiOperation(value = "根据节点查询权限")
    public Result findAllByParent_id(String id){
        List<Permission> list = permissionRepository.findAllByParentId(Long.parseLong(id));
        return Result.ok("查询成功!",list);
    }

    @PostMapping("savePermission")
    @ApiOperation(value = "新增权限")
    public Result savePermission(@Valid @RequestBody Permission permission){
        boolean flag = permissionRepository.existsByParentIdAndPermissionCodeAndPermissionName(
                permission.getParentId(),
                permission.getPermissionCode(),
                permission.getPermissionName()
        );
        if(!flag){
            permissionRepository.save(permission);
            return Result.ok("新增成功!",permission);
        }else{
            return Result.error("该权限已存在！",permission);
        }
    }

    @PostMapping("updatePermission")
    @ApiOperation(value = "修改权限",notes = "只修改权限名称，code和父节点不能修改")
    public Result updatePermission(@Valid @RequestBody Permission permission){
        permissionRepository.updatePermission(permission.getPermissionName(),permission.getId());
        return Result.ok("修改成功!",permission);
    }

    //级联删除，权限删除后，角色权限表也需删除base_role_permission
    @GetMapping("deletePermission")
    @ApiOperation(value = "删除权限")
    public Result deletePermission(Long id){
        List<Permission> list = permissionRepository.findAllByParentId(id);
        if(list.size()==0){
            permissionRepository.deleteById(id);
            permissionRepository.deleteRoleByPermission(id);
            return Result.ok("删除成功!",id);
        }else {
            return Result.error("请先删除子节点!",id);
        }
    }

    //移除角色列表
    @GetMapping("deleteRole")
    @ApiOperation(value = "从该权限中移除角色")
    public Result deleteRole(Long permission_id,Long role_id){
        permissionRepository.deleteRole(permission_id,role_id);
        return Result.ok("移除成功!",true);
    }

    @GetMapping("getTree")
    @ApiOperation(value = "获取权限树")
    public Result getTree(){
        List<Map<String,Object>> list = permissionService.getTree();
        return Result.ok("查询成功!",list);
    }

    @GetMapping("getSelectTree")
    @ApiOperation(value = "获取下拉框的树")
    public List<Map<String,Object>> getSelectTree(){
        List<Map<String,Object>> list = permissionService.getSelectTree();
        return list;
    }



}