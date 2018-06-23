package com.onemenu.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.onemenu.entity.LoginUserEntity;
import com.onemenu.mapper.LoginUserMapper;
import com.onemenu.web.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.onemenu.entity.CookEntity;
import com.onemenu.entity.CookMaterial;
import com.onemenu.entity.UserEntity;
import com.onemenu.mapper.CookInfoMapper;
import com.onemenu.mapper.UserMapper;

import static org.aspectj.util.LangUtil.split;


@RestController()
@RequestMapping("/api")
public class BackendController {

	@Autowired
	private CookInfoMapper cookMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LoginUserMapper loginUserMapper;
	
	private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";

    @Autowired
    //private UserRepository userRepository;

    @RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = "*")
	public @ResponseBody
	JSONObject login(@RequestBody JSONObject requestJson) {
    	String account = requestJson.getString("account");
		String psw = requestJson.getString("password");
		JSONObject resJson = new JSONObject();
		LoginUserEntity user = loginUserMapper.getUser(account);
		if (user == null) {
			LoginUserEntity _user = new LoginUserEntity();
			_user.setLogin_user_account(account);
			_user.setLogin_user_password(psw);
			loginUserMapper.insert(_user);
			resJson.put("code", 0);
			resJson.put("msg", "register success");
			resJson.put("data",_user);
		} else {
			if (user.getLogin_user_password().equals(psw)) {
				resJson.put("code", 0);
				resJson.put("msg", "login success");
				resJson.put("data",user);
			} else {
				resJson.put("code", -1);
				resJson.put("msg", "login failure,please retry");
			}
		}
		return resJson;
	}

	@RequestMapping(path = "/saveUserToLogin",method =  RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = "*")
	public @ResponseBody JSONObject saveToLogin(@RequestBody JSONObject requestJson){
		JSONObject resJson = new JSONObject();
		String id = requestJson.getString("login_user_id");
		List<Integer> ids = (List)requestJson.get("selected_ids");
		UserEntity user = new UserEntity();
		user.setGroupId(id);
		userMapper.clearGroupId();
		for (int i = 0; i < ids.size(); i++) {
			userMapper.update_groupId(id,ids.get(i));
		}
		resJson.put("code",0);
		resJson.put("msg","add groupId to user success");
		return resJson;
	}

//    @RequestMapping(path = "/user", method = RequestMethod.POST)
////    @ResponseStatus(HttpStatus.CREATED)
////    public @ResponseBody long addNewUser (@RequestParam String firstName, @RequestParam String lastName) {
////        User user = new User(firstName, lastName);
////        userRepository.save(user);
////
////        LOG.info(user.toString() + " successfully saved into DB");
////
////        return user.getId();
////    }
////
////    @GetMapping(path="/user/{id}")
//    public @ResponseBody User getUserById(@PathVariable("id") long id) {
//        return userRepository.findOne(id);
//    }
    
    @RequestMapping(path="/selectcook", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
    public @ResponseBody  int  selectCook(@RequestBody String selectList) {
    	String jsonStr = selectList;
        Date currentTime = new Date();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateString = formatter.format(currentTime);  
        SelectCook selectCook = new SelectCook();
        selectCook.setSelectTime(dateString);
        selectCook.setSelectCooks(jsonStr);
        int ret = cookMapper.addSelectCook(selectCook);
    	return ret;
    }

    @GetMapping(path="/getselectcooks")
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
    public @ResponseBody List<CookEntity> getSelectCooks() {
       SelectCook selectCook = cookMapper.geSelectCook();
       String selectStr = selectCook.getSelectCooks();
       SelectCookList cooklist = JSONObject.parseObject(selectStr,SelectCookList.class) ;
    	return cooklist.getFoods();
    }
    
    @GetMapping(path="/getmaterial")
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
	public @ResponseBody List<FoodMaterial> getmaterial() {
		SelectCook selectCook = cookMapper.geSelectCook();
		String selectStr = selectCook.getSelectCooks();
		SelectCookList cooklist = JSONObject.parseObject(selectStr, SelectCookList.class);
		List<CookEntity> selectCookList = cooklist.getFoods();
		Map materialMap = new HashMap();
		for (int i = 0; i < selectCookList.size(); i++) {
			CookEntity cook = selectCookList.get(i);
			CookMaterial cookMaterial = cookMapper.getCookMaterialById(cook.getCookId());
			if (cookMaterial != null) {
				int id1 = cookMaterial.getId1();
				int id2 = cookMaterial.getId2();
				int id3 = cookMaterial.getId3();
				int id4 = cookMaterial.getId4();
				if (id1 != 0) {
					if (materialMap.containsKey(cookMaterial.getId1())) {
						int quality = (int) materialMap.get(cookMaterial.getId1());
						int newvalue = quality + cookMaterial.getQuality1();
						materialMap.replace(id1, newvalue);
					} else {
						materialMap.put(id1, (int) cookMaterial.getQuality1());
					}

				}
				if (id2 != 0) {
					if (materialMap.containsKey(cookMaterial.getId2())) {
						int quality = (int) materialMap.get(cookMaterial.getId2());
						int newvalue = quality + cookMaterial.getQuality2();
						materialMap.replace(id2, newvalue);
					} else {
						materialMap.put(id2, (int) cookMaterial.getQuality2());
					}
				}
				if (id3 != 0) {
					if (materialMap.containsKey(cookMaterial.getId3())) {
						int quality = (int) materialMap.get(cookMaterial.getId3());
						int newvalue = quality + cookMaterial.getQuality3();
						materialMap.replace(id3, newvalue);
					} else {
						materialMap.put(id3, (int) cookMaterial.getQuality3());
					}

				}
				if (id4 != 0) {
					if (materialMap.containsKey(cookMaterial.getId1())) {
						int quality = (int) materialMap.get(cookMaterial.getId4());
						int newvalue = quality + cookMaterial.getQuality4();
						materialMap.replace(id4, newvalue);
					} else {
						materialMap.put(id4, (int) cookMaterial.getQuality4());
					}
				}
			}
		}
		List<FoodMaterial> foodMaterialList = new ArrayList();
		Iterator iterator = materialMap.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			String materialName = cookMapper.getMaterialName((int) key);
			FoodMaterial foodMaterial = new FoodMaterial();
			foodMaterial.setName(materialName);
			int quality = (int) materialMap.get(key);
			foodMaterial.setQuality(Integer.toString(quality));
			foodMaterialList.add(foodMaterial);
		}
		return foodMaterialList;
	}
    
    @GetMapping(path="/cooks")
    @CrossOrigin(origins = "*")
    public @ResponseBody List<Cooks> getCooks() {
        List<String> cookTypeList = cookMapper.getCookType();
        List<Cooks> goods = new ArrayList<Cooks>();
        for(String key : cookTypeList) {
        	List<CookEntity> foods = cookMapper.getCookByType(key);
        	Cooks cooks = new Cooks();
        	cooks.setName(key);
        	cooks.setFoods(foods);
        	goods.add(cooks);
        }
    	return goods;
    }

    @RequestMapping(path = "addCook", method = RequestMethod.POST)
	@CrossOrigin(origins = "*")
	public JSONObject addCook(@RequestBody JSONObject food){
    	JSONObject resJson = new JSONObject();
    	CookEntity cookEntity = new CookEntity();
    	cookEntity.setCookType(food.getString("type"));
		cookEntity.setName(food.getString("name"));
		cookMapper.addCookInfo(cookEntity);
		JSONArray itemList = food.getJSONArray("items");
    	resJson.put("code",0);
    	resJson.put("msg","add cook success");
    	return resJson;
	}

    @GetMapping(path="/analysiscook")
    @CrossOrigin(origins = "*")
    public @ResponseBody List<Cooks> analysiscook(String materiallist) {
        List<String> cookTypeList = cookMapper.getCookType();
        List<Cooks> goods = new ArrayList<Cooks>();
        for(String key : cookTypeList) {
        	List<CookEntity> foods = cookMapper.getCookByType(key);
        	Cooks cooks = new Cooks();
        	cooks.setName(key);
        	cooks.setFoods(foods);
        	goods.add(cooks);
        }
    	return goods;
    }

    @GetMapping(path="/getUsers")
    @CrossOrigin(origins = "*")
    public @ResponseBody List<UserEntity> getUsers() {
    	return userMapper.getAll();
    }

	@GetMapping(path="/getUserById")
	@CrossOrigin(origins = "*")
	public @ResponseBody UserEntity getUsers(String id) {
		return userMapper.getOneById(id);
	}

    @RequestMapping(path="/setUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody  JSONObject  setUser(@RequestBody UserEntity userEntity) {
        userMapper.insert(userEntity);
        JSONObject create = new JSONObject();
		create.put("code", "0");
		create.put("message", "success");
    	return create;
    }

	@RequestMapping(path="/updateUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = "*")
	public @ResponseBody  JSONObject  updateUser(@RequestBody UserEntity userEntity) {
		userMapper.update(userEntity);
		JSONObject create = new JSONObject();
		create.put("code", "0");
		create.put("message", "success");
		return create;
	}


	@RequestMapping(path="/delUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = "*")
	public @ResponseBody  JSONObject  delUser(@RequestBody JSONObject id) {
		userMapper.delete(id.getString("id"));
		JSONObject create = new JSONObject();
		create.put("code", "0");
		create.put("message", "success");
		return create;
	}
    
}
