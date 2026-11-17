package top.ludonghuang.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ludonghuang.entity.Channel;
import top.ludonghuang.entity.Experience;
import top.ludonghuang.entity.JobList;
import top.ludonghuang.entity.Resume;
import top.ludonghuang.mapper.JobListMapper;
import top.ludonghuang.service.ExperienceService;
import top.ludonghuang.service.JobListService;
import top.ludonghuang.service.ResumeService;
import top.ludonghuang.service.UserDataService;
import top.ludonghuang.utils.Result;
import top.ludonghuang.vo.UserData;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/joblist")
public class JobListController {

    @Autowired
    private JobListService jobListService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private UserDataService userDataService;


    @PostMapping("/create")
    public Result create(@RequestBody JobList jobList) {
        System.out.println(jobList);
        int flag = jobListService.create(jobList);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/delete")
    public Result delete(String ids) {
        int flag = jobListService.delete(ids);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody JobList jobList) {
        int flag = jobListService.update(jobList);
        if (flag > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/detail")
    public Result detail(Integer id) {
        return Result.success(jobListService.detail(id));
    }

    @PostMapping("/query")
    public Map<String, Object> query(@RequestBody JobList jobList) {
        PageInfo<JobList> pageInfo = jobListService.query(jobList);
        return Result.success(pageInfo);
    }

    @PostMapping("/getByIdQuery")
    public Result query(Integer id) {
        List<JobList> list = jobListService.getByIdQuery(id);
        System.out.printf(list.toString());
        return Result.success(list);
    }

}
