package top.ludonghuang.mapper;

import top.ludonghuang.entity.StuJobInfo;

import java.util.List;

public interface StuJobInfoMapper {

	int create(StuJobInfo stuJobInfo);

	int delete(Integer id);

	int update(StuJobInfo stuJobInfo);

	List<StuJobInfo> query(StuJobInfo stuJobInfo);

	StuJobInfo detail(Integer id);

	StuJobInfo detailByStuId(String stuId);

	int count(StuJobInfo stuJobInfo);
}