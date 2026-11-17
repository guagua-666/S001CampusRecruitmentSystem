package top.ludonghuang.entity;

import lombok.Data;
import top.ludonghuang.utils.Entity;

import java.util.Date;

@Data
public class StuJobInfo extends Entity {

	private Integer id;
	private String stuId;
	private String name;
	private String grade;
	private String stuNo;
	private String employment;
	private String companyName;
	private String companyType;
	private String post;
	private String salary;
}