package com.tianqiauto.textile.weaving.controller.xitongshezhi;

import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.github.biezhi.excel.plus.Writer;
import io.github.biezhi.excel.plus.exception.WriterException;
import io.github.biezhi.excel.plus.writer.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Dict
 * @Description 数据字典
 * @Author xingxiaoshuai
 * @Date 2019-03-16 17:50
 * @Version 1.0
 **/

@RestController
@RequestMapping("xitongshezhi/shujuzidian")
public class DictController {


    @Autowired
    private Dict_TypeRepository dict_typeRepository;


    public Specification getSpecification(Dict_Type dict_type){
        return (Specification<Dict_Type>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(dict_type.getName())) {
                predicates.add(criteriaBuilder
                        .like(root.get("name"), "%" + dict_type.getName() + "%"));
            }
            if(!StringUtils.isEmpty(dict_type.getCode())){
                predicates.add(criteriaBuilder
                        .like(root.get("code"), "%" + dict_type.getCode()+ "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    @GetMapping("query_page")
    public Result query_page(Dict_Type dict_type,Pageable pageable){
        return Result.ok(dict_typeRepository.findAll(getSpecification(dict_type), pageable));
    }



    @GetMapping("edit")
    public Result edit(Dict_Type dict_type){

        Dict_Type former =  dict_typeRepository.getOne(dict_type.getId());

        former.setCode(dict_type.getCode());
        former.setName(dict_type.getName());

        former.setFixed(dict_type.getFixed());


        return Result.ok("修改成功",dict_typeRepository.save(former));

    }

    @GetMapping("edit_fixed")
    public Result edit(String id,String value){

        Dict_Type former =  dict_typeRepository.getOne(Long.parseLong(id.trim()));

        former.setFixed(Integer.parseInt(value));

        return Result.ok("修改成功",dict_typeRepository.save(former));

    }



    @GetMapping("export")
    public void export(Dict_Type dict_type, HttpServletResponse servletResponse) throws WriterException {
        Writer.create()
                .sheet("数据字典类别")
                .headerTitle("数据字典类别")
                .withRows(dict_typeRepository.findAll(getSpecification(dict_type)))
                .to(ResponseWrapper.create(servletResponse, "数据字典类别.xls"));
    }


    //通用方法 select下拉数据加载
    @GetMapping("formSelect")
    public Result formSelect(String code){
        return Result.ok(dict_typeRepository.findByCode(code));
    }


    //获取数据字典类别中的所有code
    @GetMapping("getcodes")
    public Result getCodes(){
        return  Result.ok(dict_typeRepository.findAll());

    }




}
