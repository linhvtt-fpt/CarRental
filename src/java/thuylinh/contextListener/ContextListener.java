/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.contextListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Thuy Linh
 */
public class ContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FileReader fr= null;
        BufferedReader br = null;
        String line ="";
        
        try {
            ServletContext context = sce.getServletContext();
            String path = context.getRealPath("/WEB-INF");
            fr = new FileReader(path+"/map.txt");
            br = new BufferedReader(fr);
            Map<String,String> index = new HashMap<>();
            while(line!=null){
                String token[] = line.split("=");
                if(token.length==1){
                    index.put("", "CarSearch.jsp");
                }
                else if(token.length==2){
                    index.put(token[0], token[1]);
                }
                line = br.readLine();
            }
            context.setAttribute("MAP", index);
        } catch (Exception e) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
