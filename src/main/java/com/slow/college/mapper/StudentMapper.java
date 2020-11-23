package com.slow.college.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.slow.college.model.Student;
import com.slow.college.param.user.UserDataItem;

public interface StudentMapper {
	
	@Select(" select id, name, phone, code, password, image, score, token, "
		+ " DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%s') createTime "
		+ " from student where phone = #{phone} ")
	Student searchStudentByPhone(@Param("phone") String phone);
	
	@Select(" select id, name, phone, code, password, image, score, token, "
		+ " DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%s') createTime "
		+ " from student where token = #{token} ")
	Student searchStudentByToken(@Param("phone") String token);
	
	@Update(" update student set token = #{token} where id = ${id} ")
	void updateStudentById(@Param("id") Integer id, @Param("token") String token);

	@Select(" select * from student s left join class_has_studentg ")
	UserDataItem searchUserDataItemByToken(@Param("token") String token);
	
}
