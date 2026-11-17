package top.ludonghuang.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.ludonghuang.entity.StuJobInfo;
import top.ludonghuang.mapper.StuJobInfoMapper;

import java.util.List;

@Service
public class StuJobInfoService {

    @Autowired
    private StuJobInfoMapper stuJobInfoMapper;

    public int create(StuJobInfo stuJobInfo) {
        return stuJobInfoMapper.create(stuJobInfo);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if (!StringUtils.isEmpty(s)) {
                stuJobInfoMapper.delete(Integer.parseInt(s));
                row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return stuJobInfoMapper.delete(id);
    }

    public int update(StuJobInfo stuJobInfo) {
        return stuJobInfoMapper.update(stuJobInfo);
    }

    public PageInfo<StuJobInfo> query(StuJobInfo stuJobInfo) {
        if (stuJobInfo != null && stuJobInfo.getPage() != null) {
            PageHelper.startPage(stuJobInfo.getPage(), stuJobInfo.getLimit());
        }
        return new PageInfo<>(stuJobInfoMapper.query(stuJobInfo));
    }

    public StuJobInfo detail(Integer id) {
        return stuJobInfoMapper.detail(id);
    }

    public StuJobInfo detailByStuId(String stuId) {
        return stuJobInfoMapper.detailByStuId(stuId);
    }

    public int count(StuJobInfo stuJobInfo) {
        return stuJobInfoMapper.count(stuJobInfo);
    }
}
