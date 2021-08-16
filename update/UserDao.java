package com.hp.dao;

import com.hp.bean.User;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//dao �� Ӧ���ǽӿڣ�����ʹ��aop  Ŀǰ����aop���Ϳ���ֱ��ʹ��д����
//dao�� ��� �� ���ݿ� ���Խ� ֪ʶ�����jdbc
//�ܶ��� ���� ������� jdbc ����
//Ҫ�������ݿ⣬����Ҫ�õ��ոյ�DBHelper.getConnection()
//���������Ը��� �� mysql����
public class UserDao {
    //��¼  select * from t_user where username=? and password =?;
    //��¼
    public User login(String username,String password){
        User user =null;
        //��������
        Connection conn=DBHelper.getConnection();
        //����sql ���
        String sql="select * from t_user where username=? and password=?";
        //��ȡԤ�������
        PreparedStatement ps=null;
        //ִ��Ԥ�������
        ResultSet rs=null;
        try {
            //��ȡԤ�������
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            //ִ��Ԥ�������
            rs=ps.executeQuery();
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //��ɾ�Ĳ�
    //��ѯȫ�� select * from t_user
    public List<User> selectAll(){
        ArrayList<User> users = new ArrayList<>();
        //1.������ ���Ӷ���
        Connection connection = DBHelper.getConnection();
        //2.������SQL���
        String sql = "select * from t_user";
        //3.ʹ�����Ӷ��� ��ȡ Ԥ�������
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
           //4.ִ��Ԥ���룬�õ������
            rs = ps.executeQuery();
            //5.���������
            while (rs.next()){
                System.out.println("username = " + rs.getString("username"));
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    //m �� ҳ��page
    //n �� ����limit
    //��̬�Ĵ������ķ�ҳ��ѯ  mybatis�� ���
    public List<User> selectAllParam(Map map){
        System.out.println(" ����dao");
        System.out.println("map dao = " + map);
        for (Object o : map.keySet()) {
            System.out.println("o = " + o);
        }
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        String real_name = (String) map.get("real_name");
        String type = (String) map.get("type");
        String username = (String) map.get("username");
        //���˵real_name ��Ϊ��
        //sql = select * from t_user where real_name like '%��%' limit ?,?
        //���˵real_name ��Ϊ�� type ��Ϊ��
        //sql = select * from t_user where real_name like '%��%' and type=1 limit ?,?
        //���˵����Ϊ��
        //sql = select * from t_user where real_name like '%��%' and type=1 and username='%��%' limit ?,?


        List<User> lists = new ArrayList<>();
        //1.��������
        Connection connection = DBHelper.getConnection();
        //2.��дsql���
        String  sql ="  select  *  from t_user  where 1=1  "; // where 1=1  ��Ϊ �ж���� and
        if (null!=real_name&&real_name.length()>0){
            sql = sql + " and real_name   like  '%"+real_name+"%'   ";
        }
        if (null!=type&&type.length()>0){
            sql = sql + " and type   =  "+type+"   ";
        }
        if (null!=username&&username.length()>0){
            sql = sql + " and username   like  '%"+username+"%'   ";
        }
        sql = sql + " limit  ? ,  ?";
        System.out.println(" dao de limit sql = " + sql);

        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));//��Ϊ��һ����Ҫ?�����
        //3.Ԥ����
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//��������
            ps.setInt(2,Integer.parseInt(limit));
            //4.ִ��sql
            rs = ps.executeQuery();
            //5.���������
            while (rs.next()){
                System.out.println("username = " + rs.getString("username"));
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                lists.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    //��ѯ������
    public int selectCount(Map map1){
        String real_name = (String) map1.get("real_name");
        String type = (String) map1.get("type");
        String username = (String) map1.get("username");

        //1.��������
        Connection connection = DBHelper.getConnection();
        //2.��дsql���
        String  sql = "  select count(*) total  from t_user  where 1=1  ";
        if (null!=real_name&&real_name.length()>0){
            sql = sql + " and real_name   like  '%"+real_name+"%'   ";
        }
        if (null!=type&&type.length()>0){
            sql = sql + " and type   =  "+type+"   ";
        }
        if (null!=username&&username.length()>0){
            sql = sql + " and username   like  '%"+username+"%'   ";
        }
        System.out.println("sql count��= " + sql);

        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        //3.Ԥ����
        try {
            ps = connection.prepareStatement(sql);
            //4.ִ��sql
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    //����
    public int addUser(User user){
        //1.������ ���Ӷ���
        Connection conn = DBHelper.getConnection();
        //2.������SQL���
        String sql = "insert into t_user values (null,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        int i = 0;
        try {
            //3.ʹ�����Ӷ��� ��ȡ Ԥ�������
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            //4.ִ��Ԥ�������
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //ɾ��
    public int del(int id){
        //1.������ ���Ӷ���
        Connection conn = DBHelper.getConnection();
        //2.������SQL���
        String sql = "delete from t_user where id=?";
        //3.����preparedStatement,ִ��sql
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //�޸�
    public int update(User user){
        //1.������ ���Ӷ���
        Connection conn = DBHelper.getConnection();
        //2.������SQL���
        String sql = "update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        //3.����preparedStatement,ִ��sql
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());
            i= ps.executeUpdate();
            System.out.println("�ɹ��޸�"+i+"������");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //�޸�id is_del
    public int updateSelectById(Integer sfDel , Integer userId){
        //1.������ ���Ӷ���
        Connection conn = DBHelper.getConnection();
        //2.������SQL���
        String sql = "update t_user set is_del=? where id=?";
        //3.����preparedStatement,ִ��sql
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,sfDel);
            ps.setInt(2,userId);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return i;
    }

    //���� id ����ѯ
    public User selectUserById(Integer id){
        User user = new User();
        //1.������ ���Ӷ���
        Connection conn = DBHelper.getConnection();
        //2.������SQL���
        String sql ="select * from t_user where id=?";
        //3.����preparedStatement,ִ��sql
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setUsername(rs.getString("username"));
                user.setReal_name(rs.getString("real_name"));
                user.setPassword(rs.getString("password"));
                user.setType(rs.getInt("type"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setId(rs.getInt("id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public static void main(String[] args) {
        //ȫ��
        UserDao userDao = new UserDao();
        /*List<User> users = userDao.selectAll();
        for (User user:users) {
            System.out.println("user = " + user);
        }*/

        //����
        /*User user = new User();
        user.setUsername("caicai");
        user.setPassword("123456");
        user.setReal_name("�̲�");
        user.setImg("xxx");
        user.setType(1);
        user.setIs_del(1);
        user.setCreate_time("1998-08-02");
        user.setModify_time("1998-08-02");
        int i = userDao.addUser(user);
        System.out.println("i = " + i);*/

        //ɾ��
        /*Scanner sc = new Scanner(System.in);
        System.out.println("������Ҫɾ������ţ�");
        int id = sc.nextInt();
        int i = userDao.del(id);
        if(i>0){
            System.out.println("��ϲ��ɾ���ɹ���");
        }else{
            System.out.println("ɾ��ʧ�ܣ�");
        }*/

       /* //�޸�
        Scanner sc= new Scanner(System.in);
        System.out.println("��ѡ����Ҫ�޸ĵ�id��");
        int id = sc.nextInt();

        System.out.println("������Ҫ�޸ĵ��û�����");
        String username = sc.next();
        System.out.println("������Ҫ�޸ĵ��û����룺");
        String password = sc.next();
        System.out.println("������Ҫ�޸ĵ�����������");
        String real_name = sc.next();
        System.out.println("������Ҫ�޸ĵ��û�ͼ��");
        String img = sc.next();
        System.out.println("������Ҫ�޸ĵ��û�����:  1 ����Ա   2 ҵ��Ա��");
        int type = sc.nextInt();
        System.out.println("������Ҫ�޸ĵ� ���Ƿ���Ч: 1 ��Ч   2 ��Ч��");
        int is_del = sc.nextInt();
        System.out.println("������Ҫ�޸��䴴����ʱ�䣺");
        String create_time = sc.next();
        System.out.println("������Ҫ�޸ĵ��޸�ʱ�䣺");
        String modify_time = sc.next();
        User u = new User(id,username,password,real_name,img,type,is_del,create_time,modify_time);
        System.out.println(u.toString());
        int i = userDao.update(u);
        if(i>0){
            System.out.println("��ϲ���޸ĳɹ���");
        }else{
            System.out.println("�޸�ʧ�ܣ�");
        }*/

       //��½�Ĳ���
       /* User abc = userDao.login("abc", "123456");
        System.out.println("abc = " + abc);*/

       //��ҳ��ѯ �Ĳ���
        /*List<User> users = userDao.selectAllParam(2,5);
        System.out.println("users = " + users);
        System.out.println("users.size() = " + users.size());*/

        //��������
       /* int count = userDao.selectCount();
        System.out.println("count = " + count);*/

       //�޸� is_del
//        int up2 = userDao.updateSelectById(2,39);
//        System.out.println("up2 = " + up2);

    }
}
