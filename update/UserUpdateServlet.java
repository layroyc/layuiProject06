package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.bean.User;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateServlet",urlPatterns = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.�������
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.����ǰ�˲���
        String username = req.getParameter("username");
        String real_name = req.getParameter("real_name");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        String is_del = req.getParameter("is_del");
        String modify_time = req.getParameter("modify_time");
        //ȱ��һ������  ��������id  �޸���������id�����޸ĵ�
        String id = req.getParameter("id");

        //���޸�֮ǰ���Ȳ�ѯ����ǰ��û�еĲ���
        //����service��
        UserService userService = new UserService();
        Map map = userService.selectUserById(Integer.parseInt(id));
        User data = (User) map.get("data");

        //�Ѳ��� ��ֵ�ɶ���
        User user = new User();
        user.setImg("???");
        user.setCreate_time("????");
        user.setUsername(username);
        user.setReal_name(real_name);
        user.setPassword(password);
        user.setType(Integer.parseInt(type));
        user.setIs_del(Integer.parseInt(is_del));
        user.setModify_time(modify_time);
        user.setId(Integer.parseInt(id));

        Map map1 = userService.updateUser(user);
        //4.��map ���json
        String s = JSONObject.toJSONString(map1);
        System.out.println("s = " + s);
        //5.ʹ�� �����
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
