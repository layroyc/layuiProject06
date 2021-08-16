package com.hp.service;

import com.hp.bean.User;
import com.hp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //��¼
    public Map login(String username, String password, HttpServletRequest request){
        Map map = new HashMap();
        //service ��Ҫ����dao��
        UserDao userDao = new UserDao();
        User userFromDB = userDao.login(username, password);
        if(null == userFromDB){
            //û������������˻��������벻��ȷ
            map.put("code",4001);
            map.put("msg","�˻������벻��ȷ");
            return map;
        }else{
           //����½�ɹ��󣬰���Ϣ���뵽session��������
            HttpSession session = request.getSession();
            session.setAttribute("user",userFromDB);
            map.put("code",0);
            map.put("msg","��½�ɹ�");
            return map;
        }
    }

    //�������ķ�ҳ��ѯ
    public Map selectAllByParam(Map map1){
       UserDao userDao = new UserDao();
        List<User> users = userDao.selectAllParam(map1);
        int i = userDao.selectCount(map1);
        Map map = new HashMap();
        //map.put("code",0);//�����layui��json���صĸ�ʽһ������һ�����ݲ�����
        map.put("code111",200);//���ص����ݲ����Ϲ淶
        map.put("msg111","��ѯ�ɹ�");
        map.put("count111",i);//������д�ɻ��
        map.put("data111",users);
        //����layui�ķ��ص�json���ݸ�ʽ ȥ ���������� ����Ҫ layui����
       /* {
            code:0,
            msg:"",
            count:1000,
            data:[ÿ������]
        }*/

       Map map2 = new HashMap();
       map2.put("number",20001);
       map2.put("message","���ݲ�ѯ�ɹ�");
       map2.put("object",map);
        return map2;
    }

    //�޸� �Ƿ����ʹ��
    public Map updateUserById(Integer sfDel,Integer userId){
        UserDao dao = new UserDao();
        int i = dao.updateSelectById(sfDel, userId);
        Map map = new HashMap();
        if(i==1){
            map.put("code",0);
            map.put("msg","�޸ĳɹ�");
        }else{
            map.put("code",400);
            map.put("msg","�޸Ĳ��ɹ�");
        }
        return map;
    }

    //�޸�ȫ��
    public Map updateUser(User user){
        Map codeMap = new HashMap();
        UserDao dao = new UserDao();
        int i = dao.update(user);
        if(i==1){
            codeMap.put("code",0);
            codeMap.put("msg","�޸ĳɹ�");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","�޸Ĳ��ɹ�");
        }
        return codeMap;
    }

    //����id ��ѯ1��user
    public Map selectUserById(Integer id){
        UserDao dao = new UserDao();
        User user = dao.selectUserById(id);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",user);
        return codeMap;

    }
}
