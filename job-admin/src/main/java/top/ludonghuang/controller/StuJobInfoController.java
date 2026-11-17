package top.ludonghuang.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ludonghuang.entity.StuJobInfo;
import top.ludonghuang.framework.redis.RedisUtil;
import top.ludonghuang.framework.role.RequiresRoles;
import top.ludonghuang.framework.role.Role;
import top.ludonghuang.service.StuJobInfoService;
import top.ludonghuang.utils.Result;
import top.ludonghuang.utils.UserThreadLocal;
import top.ludonghuang.vo.UserData;

import java.util.Map;

@RestController
@RequestMapping("/stuJobInfo")
public class StuJobInfoController {

    @Autowired
    private StuJobInfoService stuJobInfoService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/create")
    public Result create(@RequestBody StuJobInfo stuJobInfo) {
        String token = UserThreadLocal.get();
        UserData userData = (UserData) redisUtil.get(token);
        stuJobInfo.setStuId(String.valueOf(userData.getId()));
        stuJobInfo.setName(String.valueOf(userData.getName()));
        int flag = stuJobInfoService.create(stuJobInfo);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/delete")
    public Result delete(String ids) {
        int flag = stuJobInfoService.delete(ids);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody StuJobInfo stuJobInfo) {
        int flag = stuJobInfoService.update(stuJobInfo);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/detail")
    public Result detail(Integer id) {
        return Result.success(stuJobInfoService.detail(id));
    }

    @PostMapping("/query")
    public Map<String, Object> query(@RequestBody StuJobInfo stuJobInfo) {
        PageInfo<StuJobInfo> pageInfo = stuJobInfoService.query(stuJobInfo);
        return Result.success(pageInfo);
    }

    @PostMapping("/info")
    @RequiresRoles(type = Role.STUDENT)
    public Result info(){
        String token = UserThreadLocal.get();
        UserData userData = (UserData) redisUtil.get(token);
        return  Result.success(stuJobInfoService.detailByStuId(String.valueOf(userData.getId())));
    }
}
