package com.pm.background.common.base.controller;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.pm.background.admin.common.shiro.ShiroUtils;
import com.pm.background.admin.sys.service.IUserService;
import com.pm.background.common.base.entity.DataEntity;
import com.pm.background.common.base.warpper.BaseControllerWarpper;
import com.pm.background.common.enumeration.InfoEnum;
import com.pm.background.common.enumeration.UserRoleEnum;
import com.pm.background.common.support.HttpKit;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.StringUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseController<T extends DataEntity,Q extends AbstractWrapper> {

    @Autowired
    private IUserService iUserService;


    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }


    /**
     * 包装一个list，让list增加额外属性  限制数据权限
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    /**
     * @param [t]
     * @return void
     * @author Larry
     * @date 2020/5/15 0015 17:40
     * @description 小程序用户 权限校验的方法
     **/
    public void setDataPermission(Q q) {
        Long userId = ShiroUtils.getUserId();
        Subject subject = ShiroUtils.getSubject();
           if (subject.hasRole(UserRoleEnum.LITTLEAPPUSER.getValue())){
               q.eq("user_id",userId);
           }

    }
    /**
     * @author Larry
     * @date 2020/5/16 0015 17:40
     * @param [t]
     * @return void
     * @description 逻辑删除的通用方法
     *
    public R  logicDelete(T t, IService iService){
    if (t.getId()==null){
    return R.fail("主键信息缺失");
    }
    t.setDelFlag(1);
    iService.update(t,new QueryWrapper<T>().eq("id",t.getId()));
    return R.ok("删除成功");
    }
     */

    /**
     * @param [t]
     * @return void
     * @author Larry
     * @date 2020/5/15 0015 17:40
     * @description 新增个人信息调用的方法
     **/
    public void setDataUserId(T t) {
        try {
           if (ShiroUtils.getSubject().hasRole(UserRoleEnum.LITTLEAPPUSER.getValue())){
               t.setUserId(ShiroUtils.getUserId());
           }
        } catch (Exception e) {

        }
    }

    /**
     * @param [t]
     * @return void
     * @author Larry
     * @date 2020/5/15 0015 17:40
     * @description 新增个人信息调用的方法
     **/
    public void updateToNull(T t) {
        t.setUpdateTime(null);
        t.setUpdateBy(null);
    }


    public R savePic(MultipartFile file) {
        String path = InfoEnum.FILESAVEPATH.getDesc();
        DateFormat format = new SimpleDateFormat("MMddHHmmss");
        String dateString = format.format(new Date());
        //获取原文件的文件名
        String fileFileName = file.getOriginalFilename();
        //原文件的类型
        String[] fileStrings = StringUtils.split(fileFileName, "\\.");
        fileFileName = fileStrings[0] + dateString + "." + fileStrings[fileStrings.length - 1];
        File targetFile = new File(path, fileFileName);
        try {
            file.transferTo(targetFile);
            //获取图片大小  如果大于500  进行压缩
            long size = file.getSize();
            if (size > 2048000L) {
                Thumbnails.of(targetFile).size(700, 700).outputQuality(0.7f).toFile(targetFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
        String absolutePath = targetFile.getAbsolutePath();
        String x = InfoEnum.SPLIT.getDesc();
        String s = "/file/" + absolutePath.split(x)[1];
        Map map = new HashMap<>();
        map.put("newPath", s);
        map.put("oldPath", absolutePath);
        return R.ok(map);
    }


}
