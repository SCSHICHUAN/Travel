package enter;

import DAO.DaoFactoryXML;
import models.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class DaoUserXML {


    public static boolean addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

//        String str = request.getParameter("username");
//        System.out.println("dd:"+str);


        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "\t" + value);
        }

        JSONObject responseJsonObject = new JSONObject();



        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            List<FileItem> items = fileUpload.parseRequest(request);

            Iterator<FileItem> itemIterator = items.iterator();
            while (itemIterator.hasNext()) {

                FileItem fileItem = itemIterator.next();
                String name = fileItem.getName();
                String fileName = fileItem.getFieldName();

                System.out.println("-----------------");
                System.out.println("name:" + name);
                System.out.println("fileName:" + fileName);

                if (fileItem.isFormField()) {

                    if (fileName.equals("username")) {
//                        System.out.println("item:" + fileItem.getString("utf-8"));

                    }
                    if (fileName.equals("password")) {
//                        System.out.println("item:" + fileItem.getString("utf-8"));

                    }

                } else {


//                    String filePath = "/Users/stan/Desktop/"+name;
                    String filePath = "../webapps/image/schttpdome/" + name;
                    String resFilePath = "https://stanserver.cn/image/schttpdome/"+name;


                    responseJsonObject.put(name,resFilePath);




                    try {

                        OutputStream out = new FileOutputStream(new File(filePath));
                        out.write(fileItem.get());
                        out.flush();

                    }catch (Exception e){
                        System.out.println(e);
//                        response.getOutputStream().write(e.toString().getBytes("utf-8"));
                    }



//                    if (fileName.equals("picture1")) {
//                        OutputStream out = new FileOutputStream(new File("/Users/SHICHUAN/Desktop/item.png"));
//                        out.write(fileItem.get());
//                        out.flush();
//                    }

                }


            }

            response.getOutputStream().write(responseJsonObject.toString().getBytes("utf-8"));

        }else{






           String fileName = request.getHeader("filename");

            //获取复杂表单的输入流
            InputStream in =request.getInputStream();



            String fileFullPath = "../webapps/image/schttpdome/" + fileName;
            File file = new File("../webapps/image/schttpdome/");
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(fileFullPath);
            int size = 0;
            byte[] buffer = new byte[1024];
            while ((size = in.read(buffer,0,1024)) != -1) {
                fos.write(buffer, 0, size);
            }


            String responsePath =  "https://stanserver.cn/image/schttpdome/"+fileName;
            System.out.println();


            JSONObject json = new JSONObject();
            json.put("path",responsePath);


            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }


//
//        SqlSession sqlSession = DaoFactoryXML.OpenSqlSession();
//        try {
//            int row = sqlSession.insert("addUser", null);
//            sqlSession.commit();
//            System.out.println("row:" + row);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DaoFactoryXML.CloseSqlSession();
//        }
        return false;
    }

    public static List<User> getUsers(HttpServletRequest request, HttpServletResponse response) {
        SqlSession sqlSession = DaoFactoryXML.OpenSqlSession();
        try {
            return sqlSession.selectList("findUser");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactoryXML.CloseSqlSession();
        }
        return null;
    }


    public void getJsonTest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "\t" + value);
        }



        Map reqMap =  request.getParameterMap();

        JSONObject json = new JSONObject();
        json.put("name:","石川");
        json.put("post","ios deverper");
        json.put("age","32");
        json.put("phone","18717791650");
        json.put("new","今天可以打球");



       String name = request.getParameter("name");

        Map map = request.getParameterMap();
        JSONObject reqJson =  JSONObject.fromObject(map);


        System.out.println(reqJson);


        String height = reqJson.get("scbody[身高]").toString();
        System.out.println("height:"+height);

        json.put("json",reqJson);


       response.getOutputStream().write(json.toString().getBytes("utf-8"));
    }




    public void getJsonTest2(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "\t" + value);
        }



        Map reqMap =  request.getParameterMap();

        JSONObject json = new JSONObject();
        json.put("name:","石川");
        json.put("post","ios deverper");
        json.put("age","32");
        json.put("phone","18717791650");
        json.put("new","今天可以打球");




        request.setCharacterEncoding("utf-8");


        String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");



        Map map = request.getParameterMap();
        JSONObject reqJson =  JSONObject.fromObject(map);
        String marStr =  new String(reqJson.toString().getBytes("iso-8859-1"), "utf-8");
        reqJson = JSONObject.fromObject(marStr);


        System.out.println(reqJson);


//        String height = reqJson.get("scbody[身高]").toString();
//        System.out.println("height:"+height);

        json.put("json",reqJson);


       response.getOutputStream().write(json.toString().getBytes("utf-8"));

        System.out.println("name:"+name);
    }


}


