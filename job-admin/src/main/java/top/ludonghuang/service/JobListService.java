package top.ludonghuang.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.ludonghuang.entity.Experience;
import top.ludonghuang.entity.JobList;
import top.ludonghuang.mapper.ExperienceMapper;
import top.ludonghuang.mapper.JobListMapper;

import java.util.List;

@Service
public class JobListService {

    @Autowired
    private JobListMapper jobListMapper;

    public int create(JobList jobList) {
        return jobListMapper.create(jobList);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if (!StringUtils.isEmpty(s)) {
                jobListMapper.delete(Integer.parseInt(s));
                row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return jobListMapper.delete(id);
    }

    public int update(JobList jobList) {
        return jobListMapper.update(jobList);
    }

    public PageInfo<JobList> query(JobList jobList) {
        if (jobList != null && jobList.getPage() != null) {
            PageHelper.startPage(jobList.getPage(), jobList.getLimit());
        }
        return new PageInfo<>(jobListMapper.query(jobList));
    }

    public Experience detail(Integer id) {
        return jobListMapper.detail(id);
    }

    public int count(JobList jobList) {
        return jobListMapper.count(jobList);
    }

    public List<JobList> getByIdQuery(Integer id){

        return jobListMapper.getByIdQuery(id);
    }
}
