package com.slow.college.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.slow.college.param.training.WaitTrainingItem;
import com.slow.college.param.user.StudentTrainingsItem;

public interface TrainingTaskMapper {
	
	@Select(" select s.id studentId, sht.task_id Id, tt.name, t.done state, "
		+ " t.achievement, sht.target, t.unit, t.desc videoUrl, t.desc, "
		+ " t.id trainingId "
		+ " from student s "
		+ " left join student_has_trainingtask sht on sht.student_id = s.id "
		+ " left join training_task tt on sht.task_id = tt.id "
		+ " left join training t on (t.student_has_trainingtask_id = sht.id "
		+ " 	and t.date = #{time}) "
		+ " where s.id in (${ids}) group by sht.id ")
	List<StudentTrainingsItem> searchStudentTrainingsItemByStudentIds(
		@Param("ids") String ids, @Param("time") String time);
	
	@Select(" select t.id trainingId, ti.imageUrl videoUrl "
		+ " from student s "
		+ " left join student_has_trainingtask sht on sht.student_id = s.id "
		+ " left join training_task tt on sht.task_id = tt.id "
		+ " left join training t on (t.student_has_trainingtask_id = sht.id "
		+ " 	and t.date = #{time}) "
		+ " left join training_image ti on t.id = ti.training_id "
		+ " where s.id in (${ids}) group by t.id ")
	List<StudentTrainingsItem> searchStudentTrainingsItemImgByStudentIds(
		@Param("ids") String ids, @Param("time") String time);
	
	@Select(" select tt.id, tt.name, sht.unit, sht.target, t.done state, "
		+ " t.achievement "
		+ " from student_has_trainingtask sht "
		+ " left join training_task tt on sht.task_id = tt.id "
		+ " left join training t on (t.student_has_trainingtask_id = sht.id "
		+ " 	and t.date = #{time}) "
		+ " where sht.student_id = ${id} ")
	List<WaitTrainingItem> searchWaitTrainingItemByStudentIdAndTime(
		@Param("id") Integer id, @Param("time") String time);

}
