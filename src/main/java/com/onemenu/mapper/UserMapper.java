package com.onemenu.mapper;

import java.util.List;


import org.apache.ibatis.annotations.*;

import com.onemenu.entity.UserEntity;
import com.onemenu.entity.UserSexEnum;

@Mapper
public interface UserMapper {


	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "sex",  column = "sex", javaType = UserSexEnum.class),
		@Result(property = "id", column = "id"),
		@Result(property = "userName", column = "userName"),
		@Result(property = "height", column = "height"),
		@Result(property = "weight", column = "weight"),
		@Result(property = "birthday", column = "birthday"),
		@Result(property = "nickName", column = "nick_name"),
		@Result(property = "groupId", column = "groupid")

	})
	List<UserEntity> getAll();

	@Select("SELECT * FROM users WHERE groupid=#{groupId}")
	@Results({
			@Result(property = "sex",  column = "sex", javaType = UserSexEnum.class),
			@Result(property = "id", column = "id"),
			@Result(property = "userName", column = "userName"),
			@Result(property = "height", column = "height"),
			@Result(property = "weight", column = "weight"),
			@Result(property = "birthday", column = "birthday"),
			@Result(property = "nickName", column = "nick_name"),
			@Result(property = "groupId", column = "groupid")

	})
	List<UserEntity> getUsersById(String groupId);

	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "sex",  column = "sex", javaType = UserSexEnum.class),
		@Result(property = "userName", column = "userName"),
		@Result(property = "height", column = "height"),
		@Result(property = "weight", column = "weight"),
		@Result(property = "birthday", column = "birthday"),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOneById(String id);

	@Insert("INSERT INTO users(userName,height,weight,birthday,sex,creatuserid) VALUES(#{userName}, #{height}, #{weight},#{birthday},#{sex},#{creatuserid})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},height=#{height},weight=#{weight},birthday=#{birthday} WHERE id =#{id}")
	void update(UserEntity user);

	@Update("UPDATE users SET groupId=NUll")
	void clearGroupId();

	@Update("UPDATE users SET groupId=#{groupId} WHERE id=#{id}")
	void update_groupId(@Param("groupId") String groupId, @Param("id") int id);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(String id);

	@Select("SELECT * FROM users WHERE userName = #{userName} and passWord = #{passWord}")
	@Results({
		@Result(property = "sex",  column = "sex", javaType = UserSexEnum.class),
		@Result(property = "userName", column = "userName")
	})
	UserEntity getUser(UserEntity user);

}