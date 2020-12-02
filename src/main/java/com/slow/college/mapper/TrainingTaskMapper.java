package com.slow.college.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.slow.college.param.training.CheckTraningItem;
import com.slow.college.param.training.WaitTrainingItem;
import com.slow.college.param.user.StudentClassItem;
import com.slow.college.param.user.StudentTrainingsItem;

public interface TrainingTaskMapper {
	
	@Select(" select count(*) trainingDays,a.id, sum(a.score) score from "
		+ " ( select t.date, s.id, sum(t.score) score "
		+ " 	from student s "
		+ " 	left join student_has_trainingtask sht on sht.student_id = s.id "
		+ " 	left join training t on t.student_has_trainingtask_id = sht.id "
		+ " where s.id in (${ids}) and t.`done` = 1 "
		+ " group by t.`date`, s.id) as a group by a.id ")
	List<StudentClassItem> searchStudentSourceByIds(@Param("ids") String ids);
	
	@Select(" select s.id studentId, sht.task_id Id, tt.name, t.done state, "
		+ " t.achievement, sht.target, sht.unit, t.video videoUrl, t.desc, "
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
		+ " where s.id in (${ids}) ")
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

	@Select(" select sht.task_id taskId, sht.target, sht.unit, "
		+ " sht.id trainingtaskId, t.done, t.achievement, t.desc, t.video, "
		+ " t.id dayTrainingtaskId, DATE_FORMAT(t.date, '%Y-%m-%d') createTime, "
		+ " t.score "
		+ " from student_has_trainingtask sht "
		+ " left join training t on (t.student_has_trainingtask_id = sht.id "
		+ " 	and t.date = #{time}) "
		+ " where sht.student_id = ${id} ")
	List<CheckTraningItem> searchCheckTraningItemByStudentIdAndTime(
		@Param("id") Integer id, @Param("time") String time);

}
