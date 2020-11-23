package com.slow.college.mapper;

import java.util.List;

import com.slow.college.param.item.UserItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.slow.college.model.Student;
import com.slow.college.param.user.ClassStudentItem;
import com.slow.college.param.user.StudentClassItem;

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

	@Select(" select c.id classId, c.name className, c.desc classDesc "
		+ " from student s "
		+ " 	left join class_has_student chs on s.id = chs.student_id "
		+ " 	left join class c on chs.class_id = c.id "
		+ " where s.token = #{token} group by c.id ")
	ClassStudentItem searchUserDataItemByToken(@Param("token") String token);

	@Select(" select s.id, s.code, s.name, "
		+ " 	CASE WHEN c.monitor_id = s.id THEN '班长' "
		+ " 		WHEN c.vicemonitor_id = s.id THEN '副班长' "
		+ " 		ELSE '成员' END duty, "
		+ " DATE_FORMAT(s.create_time, '%Y-%m-%d') createTime, s.score "
		+ " from class c "
		+ " 	left join class_has_student chs on c.id = chs.class_id "
		+ " 	left join student s on chs.student_id = s.id "
		+ " where c.id = ${classId} group by s.id ")
	List<StudentClassItem> searchUserDataItemByClassId(
		@Param("classId") Integer classId);




    /**
     * 根据token查询student
     * @param token
     * @return
     */
    @Select("select s.id, s.name,s.image,s.code,s.score,c.name as className from student s " +
            " inner join class_has_student chs on s.id = chs.student_id " +
            " inner join class c on chs.class_id = c.id where  s.token=#{token}")
    UserItem selectUserItemByToken(String token);
}
