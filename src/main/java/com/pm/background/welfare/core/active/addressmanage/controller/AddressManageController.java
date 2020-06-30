package com.pm.background.welfare.core.active.addressmanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.pm.background.common.base.controller.BaseController;
import com.pm.background.common.entity.CommonOkResponseEntity;
import com.pm.background.common.enumeration.BaseEnum;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.StringUtils;
import com.pm.background.welfare.core.active.addressmanage.entity.AddressManageEntity;
import com.pm.background.welfare.core.active.addressmanage.entity.swagger.AddressManageSwaggerListEntity;
import com.pm.background.welfare.core.active.addressmanage.entity.swagger.AddressManageSwaggerPageBaseEntity;
import com.pm.background.welfare.core.active.addressmanage.service.AddressManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:地区设置
 * author: Larry
 * date: 2020-05-28
 */
@RestController
@Api(value = "地区设置",tags = {"地区设置模块"})
@RequestMapping(value="/addressManage")
public class AddressManageController extends BaseController {

    @Autowired
    private AddressManageService addressManageService;

    /**
    * 描述:   地区设置分页查询方法
    * author: Larry
    * date: 2020-05-28
    */
    @RequestMapping(value="/page/list",method = RequestMethod.POST)
    @ApiOperation(value="地区设置分页查询",response = AddressManageSwaggerPageBaseEntity.class)
    public R pageList(@ApiParam(name ="addressManage",required = true,value = "根据传入的实体类查询") @RequestBody AddressManageEntity addressManage){
        QueryWrapper<AddressManageEntity> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("parent_id",0);
        if (addressManage.getName()!=null){
            queryWrapper.like("name", addressManage.getName());
        }
        IPage<AddressManageEntity> page = addressManageService.page(new Page(addressManage.getCurrentPage(),addressManage.getPageSize()),queryWrapper);
        if (page.getRecords().size()==0&&page.getTotal()!=0) {
            page=addressManageService.page(new Page(addressManage.getCurrentPage()-1,addressManage.getPageSize()),queryWrapper);
        }

        List<AddressManageEntity> list = page.getRecords();
        if (list.size()>0){
                   list.stream().forEach(item->
                item.setChildren(addressManageService.list(new QueryWrapper<AddressManageEntity>().
                        lambda().eq(AddressManageEntity::getParentId,item.getId()))));
    }
        return R.ok(page);
     }


    /**
    * 描述:   地区设置查询list集合方法
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="地区设置集合查询",response = AddressManageSwaggerListEntity.class)
    @RequestMapping(value="/list",method = RequestMethod.POST)
    public R list(@ApiParam(name ="addressManage",required = true,value = "根据传入的实体类查询") @RequestBody AddressManageEntity addressManage){
        QueryWrapper<AddressManageEntity> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("parent_id",0);
        if (addressManage.getName()!=null){
            queryWrapper.like("name", addressManage.getName());
        }
        List<AddressManageEntity> list = addressManageService.list(queryWrapper);
        if (list.size()>0){
            list.stream().forEach(item->
                item.setChildren(addressManageService.list(new QueryWrapper<AddressManageEntity>().
                    lambda().eq(AddressManageEntity::getParentId,item.getId()))));
        }

        return R.ok(list);
     }

    /**
    * 描述:   小程序查询地区
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="小程序查询地区集合",response = AddressManageSwaggerListEntity.class)
    @RequestMapping(value="/smallAppList",method = RequestMethod.POST)
    public R smallAppList(){
        QueryWrapper<AddressManageEntity> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        //全部查询出来
        List<AddressManageEntity> list = addressManageService.list(new QueryWrapper<AddressManageEntity>().lambda().ne(AddressManageEntity::getShowStatus,0));
        List<AddressManageEntity> parents = list.stream().filter(a -> a.getLevel() == 0).collect(Collectors.toList());
        for (AddressManageEntity parent : parents) {
           parent.setChildren( list.stream().filter(a->a.getParentId().equals(parent.getId())).collect(Collectors.toList()));
        }
        return R.ok(parents);
     }


    /**
    * 描述: 地区设置根据id查询方法
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="地区设置根据id查询",response = AddressManageEntity.class)
    @RequestMapping(value="/queryById",method = RequestMethod.POST)
    public R selectById(@ApiParam(name ="addressManage",required = true,value = "根据类中id查询") @RequestBody AddressManageEntity addressManage){
        addressManage = addressManageService.getById(addressManage.getId());
        return R.ok(addressManage);
    }

    /**
    * 描述:  地区设置保存方法
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="地区设置保存",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public R save(@Valid @ApiParam(name ="addressManage",required = true,value = "要保存的实体类") @RequestBody AddressManageEntity addressManage){
        addressManage.setShowStatus(0);
        if (addressManage.getLevel()==null){
            addressManage.setLevel(0);
        }
        if (addressManage.getParentId()==null){
            addressManage.setParentId(0L);
        }else{
            addressManage.setLevel(1);
        }
        QueryWrapper<AddressManageEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(AddressManageEntity::getName,addressManage.getName());
        if(0 != addressManage.getLevel()){
            queryWrapper.lambda().eq(AddressManageEntity::getParentId,addressManage.getParentId());
        }else{
            queryWrapper.lambda().eq(AddressManageEntity::getParentId,0L);
        }
        int count = addressManageService.count(queryWrapper);
        if(count > 0){
            return R.fail("该区域已经存在");
        }
        boolean result = addressManageService.save(addressManage);
        if(result){
            return R.ok("添加成功！");
        } else {
            return R.fail("添加失败！");
        }
    }
    /**
    * 描述:  地区设置更新方法
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="地区设置更新",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public R update(@ApiParam(name ="addressManage",required = true,value = "修改后的实体类") @RequestBody AddressManageEntity addressManage)throws Exception{
         updateToNull(addressManage);
         String name = addressManage.getName();
         AddressManageEntity byId = addressManageService.getById(addressManage.getId());
        if (addressManage.getShowStatus().equals(BaseEnum.YES.getValue())&&byId.getParentId()==0){
            int count = addressManageService.count(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getParentId, addressManage.getId()));
            if (count==0){
                return R.fail("请添加二级地区");
            }
        }
        if (addressManage.getShowStatus().equals(BaseEnum.YES.getValue())&&byId.getParentId()!=0){
            AddressManageEntity one = addressManageService.getOne(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getId, byId.getParentId()));
            if (BaseEnum.NO.getValue().equals(one.getShowStatus())){
                return R.ok("请先开启一级地区");
            }
        }
         if (name!=null&&!name.equals(byId.getName())){
            int count = addressManageService.count(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getName, name).eq(AddressManageEntity::getParentId,byId.getParentId()));
            if (count!=0){
                return R.fail("地区名称重复");
            }
        }
        boolean result = addressManageService.updateById(addressManage);

        Long parentId = byId.getParentId();

        if (parentId==0L){
            AddressManageEntity addressManageEntity = new AddressManageEntity();
            addressManageEntity.setShowStatus(addressManage.getShowStatus());
            addressManageService.update(addressManageEntity,new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getParentId,addressManage.getId()));
        }else{
            if (addressManage.getShowStatus()!=null&&BaseEnum.NO.getValue().equals(addressManage.getShowStatus())){
                int count = addressManageService.count(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getParentId, parentId).eq(AddressManageEntity::getShowStatus,1));
                if (count==0){
                    AddressManageEntity addressManageEntity = new AddressManageEntity();
                    addressManageEntity.setShowStatus(BaseEnum.NO.getValue());
                    addressManageService.update(addressManageEntity,new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getId,parentId));
                }
            }
        }
        if(result){
            return R.ok("修改成功");
        }else{
            return R.fail("修改失败");
        }
    }

    /**
    * 描述:   地区设置删除方法
    * author: Larry
    * date: 2020-05-28
    */
    @ApiOperation(value="地区设置删除",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public R delete(@ApiParam(name ="addressManage",required = true,value = "根据类中id删除") @RequestBody AddressManageEntity addressManage){
        AddressManageEntity byId = addressManageService.getById(addressManage.getId());
        if (byId.getLevel()==1||byId.getParentId()==0L){
            int count = addressManageService.count(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getParentId, addressManage.getId()));
            if (count!=0){
                return R.fail("一级地区中有二级地区时不可删除");
            }
        }
            boolean result = addressManageService.removeById(addressManage);
            Long parentId = byId.getParentId();
        int count = addressManageService.count(new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getParentId, parentId));
        if (count==0){
            AddressManageEntity addressManageEntity = new AddressManageEntity();
            addressManageEntity.setShowStatus(0);
            addressManageService.update(addressManageEntity,new QueryWrapper<AddressManageEntity>().lambda().eq(AddressManageEntity::getId,parentId));
        }
        if(result){
                return R.ok("删除成功");
            }else{
                return R.fail("删除失败");
            }
    }


}
