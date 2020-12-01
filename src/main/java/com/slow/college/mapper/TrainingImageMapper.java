package com.slow.college.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TrainingImageMapper {
	
	@Insert("<script>"
		+ " insert into training_image (training_id, imageUrl) values "
		+ " <foreach collection='list' item='item' index='index' separator=','>"
		+ " 	(${id}, #{item}) "
		+ " </foreach> "
		+ "</script>")
	void insertList(List<String> data, @Param("id") Long id);
	
	@Insert("<script>"
		+ " insert into training_image (training_id, imageUrl) values "
		+ " (${id}, #{img}) "
		+ "</script>")
	void insertTrainingImage(@Param("img") String imd, @Param("id") Long id);
	
	@Select(" select imageUrl from training_image "
		+ " where training_id = ${trainingId} ")
	List<String> searchTrainingImageByTrainingId(
		@Param("trainingId") Integer trainingId);
	

}
