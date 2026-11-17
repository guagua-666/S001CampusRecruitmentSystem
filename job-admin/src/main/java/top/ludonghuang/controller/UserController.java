package top.ludonghuang.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ludonghuang.entity.Company;
import top.ludonghuang.entity.Student;
import top.ludonghuang.entity.User;
import top.ludonghuang.framework.redis.RedisUtil;
import top.ludonghuang.framework.role.RequiresRoles;
import top.ludonghuang.framework.role.Role;
import top.ludonghuang.service.CompanyService;
import top.ludonghuang.service.StudentService;
import top.ludonghuang.service.UserService;
import top.ludonghuang.utils.Result;
import top.ludonghuang.utils.UserThreadLocal;
import top.ludonghuang.vo.UserData;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public Result create(@RequestBody User user) {
        int row = userService.create(user);
        if (row > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/delete")
    public Result delete(String ids) {
        int row = userService.delete(ids);
        if (row > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        int row = userService.update(user);
        if (row > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody User user) {
        String token = UserThreadLocal.get();
        UserData userData = (UserData) redisUtil.get(token);
        int row = 0;
        if (userData.getType() == 0) {
            User passwordUser = new User();
            passwordUser.setId(userData.getId());
            passwordUser.setPassword(user.getPassword());
            row = userService.update(passwordUser);
        } else if (userData.getType() == 1) {
            Company company = new Company();
            company.setId(userData.getId());
            company.setPassword(user.getPassword());
            row = companyService.update(company);
        } else if (userData.getType() == 2) {
            Student student = new Student();
            student.setId(userData.getId());
            student.setPassword(user.getPassword());
            row = studentService.update(student);
        }
        if (row > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/query")
    @RequiresRoles(type = Role.ADMIN)
    public Map<String, Object> query(@RequestBody User user) {
        PageInfo<User> pageInfo = userService.query(user);
        return Result.success(pageInfo);
    }

    @PostMapping("/detail")
    public Result detail(Integer id) {
        User user = userService.detail(id);
        return Result.success(user);
    }
}
