package com.onemenu.controller;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.onemenu.entity.*;
import com.onemenu.mapper.LoginUserMapper;
import com.onemenu.web.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
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
    public @ResponseBody
    String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    JSONObject login(@RequestBody JSONObject requestJson) {
        JSONObject resJson = null;
        try {
            String account = requestJson.getString("account");
            String psw = requestJson.getString("password");
            resJson = new JSONObject();
            LoginUserEntity user = loginUserMapper.getUser(account);
            if (user == null) {
                LoginUserEntity _user = new LoginUserEntity();
                _user.setLogin_user_account(account);
                _user.setLogin_user_password(psw);
                loginUserMapper.insert(_user);
                resJson.put("code", 0);
                resJson.put("msg", "register success");
                resJson.put("data", _user);
                LOG.info(_user.toString() + "resister success");
            } else {
                if (user.getLogin_user_password().equals(psw)) {
                    resJson.put("code", 0);
                    resJson.put("msg", "login success");
                    resJson.put("data", user);
                    LOG.info(account + "login success");
                } else {
                    resJson.put("code", -1);
                    resJson.put("msg", "login failure,please retry");
                    LOG.info(account + "login failure");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resJson;
    }

    @RequestMapping(path = "/saveUserToLogin", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    JSONObject saveToLogin(@RequestBody JSONObject requestJson) {
        JSONObject resJson = null;
        try {
            resJson = new JSONObject();
            String id = requestJson.getString("login_user_id");
            List<Integer> ids = (List) requestJson.get("selected_ids");
            UserEntity user = new UserEntity();
            user.setGroupId(id);
            userMapper.clearGroupId();
            for (int i = 0; i < ids.size(); i++) {
                userMapper.update_groupId(id, ids.get(i));
            }
            resJson.put("code", 0);
            resJson.put("msg", "add groupId to user success");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @RequestMapping(path = "/selectcook", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    int selectCook(@RequestBody String selectList) {
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

    @GetMapping(path = "/getselectcooks")
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    List<CookEntity> getSelectCooks() {
        SelectCookList cooklist = null;
        try {
            SelectCook selectCook = cookMapper.geSelectCook();
            String selectStr = selectCook.getSelectCooks();
            cooklist = JSONObject.parseObject(selectStr, SelectCookList.class);
            LOG.info("get cooklist success");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        return cooklist.getFoods();
    }

    @GetMapping(path = "/getmaterial")
    //@CrossOrigin(origins = "http://192.168.1.106:8080")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    List<CookEntity> getmaterial() {
        List<CookEntity> selectCookList = null;
        try {
            SelectCook selectCook = cookMapper.geSelectCook();
            String selectStr = selectCook.getSelectCooks();
            SelectCookList cooklist = JSONObject.parseObject(selectStr, SelectCookList.class);
            selectCookList = cooklist.getFoods();
            Map materialMap = new HashMap();
            for (int i = 0; i < selectCookList.size(); i++) {
                List<MaterialEntity> materialEntityList = new ArrayList<MaterialEntity>();
                CookEntity cook = selectCookList.get(i);
                CookMaterial cookMaterial = cookMapper.getCookMaterialById(cook.getCookId());
                if (cookMaterial != null) {
                    int id1 = cookMaterial.getId1();
                    int id2 = cookMaterial.getId2();
                    int id3 = cookMaterial.getId3();
                    int id4 = cookMaterial.getId4();
                    if (id1 != 0) {
                        MaterialEntity ma1 = cookMapper.getMaterialInfo(id1);
                        ma1.setQuality(cookMaterial.getQuality1()/5);
                        materialEntityList.add(ma1);

                    }
                    if (id2 != 0) {
                        MaterialEntity ma2 = cookMapper.getMaterialInfo(id2);
                        ma2.setQuality(cookMaterial.getQuality2()/5);
                        materialEntityList.add(ma2);

                    }
                    if (id3 != 0) {
                        MaterialEntity ma3 = cookMapper.getMaterialInfo(id3);
                        ma3.setQuality(cookMaterial.getQuality3()/5);
                        materialEntityList.add(ma3);

                    }
                    if (id4 != 0) {
                        MaterialEntity ma4 = cookMapper.getMaterialInfo(id4);
                        ma4.setQuality(cookMaterial.getQuality4());
                        materialEntityList.add(ma4);
                    }
                }
                cook.setCookMaterial(materialEntityList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectCookList;
    }

    @GetMapping(path = "/cooks")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    List<Cooks> getCooks() {
        List<String> cookTypeList = cookMapper.getCookType();
        List<Cooks> goods = new ArrayList<Cooks>();
        for (String key : cookTypeList) {
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
    public JSONObject addCook(@RequestBody JSONObject food) {
        JSONObject resJson = new JSONObject();
        CookEntity cookEntity = new CookEntity();
        cookEntity.setCookType(food.getString("type"));
        cookEntity.setName(food.getString("name"));
        cookMapper.addCookInfo(cookEntity);
        JSONArray itemList = food.getJSONArray("items");
        resJson.put("code", 0);
        resJson.put("msg", "add cook success");
        return resJson;
    }

    @GetMapping(path = "/analysiscook")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    List<CookEntity> analysiscook() {
        List<CookEntity> cookList = this.getmaterial();
        int ma0 = 0, ma1 = 0, ma2 = 0, ma3 = 0, ma4 = 0;
        for (int i = 0; i < cookList.size(); i++) {
            CookEntity cookEntity = cookList.get(i);
            List<MaterialEntity> materialList = cookEntity.getCookMaterial();
            int count = cookEntity.getCount();
            for (int j = 0; j < materialList.size(); j++) {
                MaterialEntity materialEntity = materialList.get(j);
                ma0 += materialEntity.getMaterial0() * count;
                ma1 += materialEntity.getMaterial1() * count;
                ma2 += materialEntity.getMaterial2() * count;
                ma3 += materialEntity.getMaterial3() * count;
                ma4 += materialEntity.getMaterial4() * count;
            }
        }
        List dataList = new ArrayList();
        dataList.add(ma0);
        dataList.add(ma1);
        dataList.add(ma2);
        dataList.add(ma3);
        dataList.add(ma4);
        return dataList;
    }

    @RequestMapping(path = "/getdefaultDataByUser")
    @CrossOrigin(origins = "*")
    public @ResponseBody List<CookEntity> getList(@RequestBody JSONObject json){
        int ma0 = 0, ma1 = 0, ma2 = 0, ma3 = 0, ma4 = 0;
        try {
            String login_user_id = json.getString("login_user_id");
            List<UserEntity> users = userMapper.getUsersById(login_user_id);
            for (int i = 0; i < users.size(); i++) {
                UserEntity user = users.get(i);
                double base_value = 655, weight_value = 9.6, height_value = 1.8, age_value = 4.7;
                if (user.getSex().equals(UserSexEnum.male)) {
                    base_value = 66;
                    weight_value = 13.7;
                    height_value = 5;
                    age_value = 6.8;
                }
                if (StringUtils.isEmpty(user.getBirthday()) || ("..").equals(user.getBirthday())) {
                    continue;
                }
                int age = getAge(parse(user.getBirthday()));
                DecimalFormat df =new DecimalFormat("#.00");
                BigDecimal BMR = BigDecimal.valueOf(base_value).add(BigDecimal.valueOf(weight_value).multiply(new BigDecimal(user.getHeight()))).
                        add(BigDecimal.valueOf(height_value).multiply(new BigDecimal(user.getHeight())))
                        .subtract(BigDecimal.valueOf(age_value).multiply(BigDecimal.valueOf(age)));
                double sports_value = 1.2;
                BigDecimal _ma0 = BMR.multiply(BigDecimal.valueOf(sports_value));
                BigDecimal _ma1 = _ma0.multiply(BigDecimal.valueOf(0.2)).divide(BigDecimal.valueOf(4),2,BigDecimal.ROUND_HALF_UP);
                BigDecimal _ma2 = _ma0.multiply(BigDecimal.valueOf(0.2)).divide(BigDecimal.valueOf(9),2,BigDecimal.ROUND_HALF_UP);
                BigDecimal _ma3 = _ma0.multiply(BigDecimal.valueOf(0.6)).divide(BigDecimal.valueOf(4),2,BigDecimal.ROUND_HALF_UP);
                ma0 += _ma0.intValue();
                ma1 += _ma1.intValue();
                ma2 += _ma2.intValue();
                ma3 += _ma3.intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List dataList = new ArrayList();
        dataList.add(ma0);
        dataList.add(ma1);
        dataList.add(ma2);
        dataList.add(ma3);
        return dataList;
    }

    @RequestMapping(path = "/getUsers")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    List<UserEntity> getUsers() {
        return userMapper.getAll();
    }

    @RequestMapping(path = "/getUserById")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    UserEntity getUsers(String id) {
        return userMapper.getOneById(id);
    }

    @RequestMapping(path = "/setUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    JSONObject setUser(@RequestBody UserEntity userEntity) {
        userMapper.insert(userEntity);
        JSONObject create = new JSONObject();
        create.put("code", "0");
        create.put("message", "success");
        return create;
    }

    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    JSONObject updateUser(@RequestBody UserEntity userEntity) {
        userMapper.update(userEntity);
        JSONObject create = new JSONObject();
        create.put("code", "0");
        create.put("message", "success");
        return create;
    }


    @RequestMapping(path = "/delUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public @ResponseBody
    JSONObject delUser(@RequestBody JSONObject id) {
        userMapper.delete(id.getString("id"));
        JSONObject create = new JSONObject();
        create.put("code", "0");
        create.put("message", "success");
        return create;
    }

    //由出生日期获得年龄
    private int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    private Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
        return sdf.parse(strDate);
    }


}
