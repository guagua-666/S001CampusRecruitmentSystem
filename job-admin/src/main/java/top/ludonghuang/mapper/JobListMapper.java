package top.ludonghuang.mapper;

import top.ludonghuang.entity.Experience;
import top.ludonghuang.entity.JobList;

import java.util.List;

public interface JobListMapper {

	int create(JobList jobList);

	int delete(Integer id);

	int update(JobList jobList);

	List<JobList> query(JobList jobList);

	Experience detail(Integer id);

	int count(JobList JobList);

	List<JobList> getByIdQuery(Integer id);
}