package com.slow.college.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.slow.college.param.user.StudentTrainingsItem;

public interface TrainingTaskMapper {
	
	@Select(" select * "
		+ " from student s "
		+ " left join student_has_trainingtask sht on sht.student_id = s.id "
		+ " left join training_task tt on sht.task_id = tt.id "
		+ " left join training t on t.student_has_trainingtask_id = sht.id "
		+ " where s.id in (${ids}) and t.date = #{time} group by id ")
	List<StudentTrainingsItem> searchStudentTrainingsItemByStudentIds(
		@Param("ids") String ids, @Param("time") String time);

}
