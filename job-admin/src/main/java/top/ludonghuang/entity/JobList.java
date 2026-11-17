package top.ludonghuang.entity;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import top.ludonghuang.utils.Entity;

import java.util.Date;

@Data
public class JobList extends Entity {

	private Integer id;
	private String name;
	private Date jobDate;
	private String jobPlace;
	private String jobUrl;
	private String jobCompany;
	private Integer fatherId;
}