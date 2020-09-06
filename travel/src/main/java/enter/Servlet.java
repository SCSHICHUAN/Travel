package enter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.println("-----------servlet--" + path + "----------");
        path = path.substring(1);
        String pathClass = path.substring(0, path.indexOf("/"));
        String doMethod = path.substring(pathClass.length() + 1, path.indexOf("."));


        try {

            Class tClass = Class.forName("enter."+pathClass);
            Constructor constructor = tClass.getConstructor();
            Object object = constructor.newInstance();
            Method method = object.getClass().getDeclaredMethod(doMethod,HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(object,request,response);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (
                InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
