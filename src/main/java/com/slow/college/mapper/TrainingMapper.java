package com.slow.college.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.slow.college.model.Training;

public interface TrainingMapper {
	
	@Update("<script> "
		+ " update training set date = #{data}, done = ${done}, "
		+ " achievement = ${achievement}, score = ${score} "
		+ " <if test='desc != null'> "
		+ " 	,`desc` = #{desc} "
		+ " </if> "
		+ " <if test='video != null'> "
		+ " 	,video = #{video} "
		+ " </if> where id = ${id} "
		+ " </script> ")
	void updateTraining(
		@Param("data") String data,
		@Param("done") Byte done,
		@Param("achievement") Integer achievement,
		@Param("score") Integer score,
		@Param("desc") String desc,
		@Param("video") String video,
		@Param("id") Long id);
	
	@Insert(" <script> "
		+ " insert into training (student_has_trainingtask_id, date, `desc`, done, "
		+ " video, image, achievement, unit, score) values "
		+ " (${studentHasTrainingtaskId}, #{date}, "
		+ " <if test='desc != null'> "
		+ " 	#{desc}, "
		+ " </if>"
		+ " <if test='desc == null'> "
		+ " 	null, "
		+ " </if> " 
		+ " ${done}, "
		+ " <if test='video != null'> "
		+ " 	#{video}, "
		+ " </if>"
		+ " <if test='video == null'> "
		+ " 	null, "
		+ " </if> "
		+ " null, ${achievement}, #{unit}, ${score}) "
		+ " </script>")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertTraining(Training t);
	
}
