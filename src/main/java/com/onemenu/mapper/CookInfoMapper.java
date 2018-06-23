package com.onemenu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.onemenu.entity.CookEntity;
import com.onemenu.entity.CookMaterial;
import com.onemenu.web.vo.SelectCook;

@Mapper
public interface CookInfoMapper {
	
	
	@Select("SELECT * FROM cook_info")
	@Results({
		@Result(property = "cookId",  column = "cook_Id"),
		@Result(property = "name",  column = "cook_name"),
		@Result(property = "cookType",  column = "cook_type"),
		@Result(property = "description",  column = "cook_desc"),
		@Result(property = "image", column = "cook_img"),
		@Result(property = "icon", column = "cook_icon")
	})
	List<CookEntity> getAll();
	
	@Select("SELECT * FROM cook_info WHERE cook_type = #{cooktype}")
	@Results({
		@Result(property = "cookId",  column = "cook_Id"),
		@Result(property = "name",  column = "cook_name"),
		@Result(property = "cookType",  column = "cook_type"),
		@Result(property = "description",  column = "cook_desc"),
		@Result(property = "image", column = "cook_img"),
		@Result(property = "icon", column = "cook_icon")
	})
	List<CookEntity> getCookByType(String cooktype);

	@Select("select `cook_type` FROM `cook_info` GROUP BY `cook_type`")
	@Results({
		@Result(property = "cookType",  column = "cook_type"),
	})
	List<String> getCookType();

	@Insert("Insert into cook_info (cook_name,cook_type,cook_desc,cook_img,cook_icon) values(#{name},#{cookType},#{description},#{image},#{icon})")
	public int addCookInfo(CookEntity cookEntity);

	@Insert("Insert into cook_material (cook_id," +
			"material_id1,material_quality1," +
			"material_id2,material_quality2," +
			"material_id3,material_quality3," +
			"material_id4,material_quality4) " +
			"FROM cook_material values(#{cook_id}," +
			"#{ma_id1},#{ma_qa1}," +
			"#{ma_id2},#{ma_qa2}," +
			"#{ma_id3},#{ma_qa3}," +
			"#{ma_id4},#{ma_qa4})")
	public int addMaterial(CookMaterial cookMaterial);

	  @Insert("insert into cook_select (select_time, select_cooks) values(#{selectTime},#{selectCooks})") 
	  public int addSelectCook(SelectCook selectCook); 
	
		@Select("SELECT select_time,select_cooks FROM cook_select  WHERE id = (SELECT max(id) FROM cook_select)")
		@Results({
			@Result(property = "selectTime",  column = "select_time"),
			@Result(property = "selectCooks",  column = "select_cooks"),
		})
		SelectCook geSelectCook();

		
		@Select("SELECT cook_id,material_id1,material_quality1, material_id2,material_quality2,material_id3,material_quality3,material_id4,material_quality4 FROM cook_material WHERE cook_id = #{cookId}")
		@Results({
			@Result(property = "cookId",  column = "cook_id"),
			@Result(property = "id1",  column = "material_id1"),
			@Result(property = "quality1",  column = "material_quality1"),
			@Result(property = "id2",  column = "material_id2"),
			@Result(property = "quality2",  column = "material_quality2"),
			@Result(property = "id3",  column = "material_id3"),
			@Result(property = "quality3",  column = "material_quality3"),
			@Result(property = "id4",  column = "material_id4"),
			@Result(property = "quality4",  column = "material_quality4"),
		})
		CookMaterial getCookMaterialById(int cookId);

		@Select("select name FROM material WHERE id = #{id}")
		String getMaterialName(int id);
		
		
}