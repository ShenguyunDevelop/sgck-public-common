package com.sgck.oauth2.client.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgck.oauth2.client.OAuthPath;

@WebServlet("/" + OAuthPath.IMPORT_FILE)
public class ImportFile extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      
        response.setContentType("application/x-download charset=UTF-8");
        //java.io.FileInputStream fis = null;
        InputStream fis = null;
        
        @SuppressWarnings("deprecation")
		String filepath = request.getRealPath("");
        
        javax.servlet.ServletOutputStream sos = null;
        
        try {
            if(null == request.getParameter("filename")  ||  request.getParameter("filename").equals("")){
                return;
            }
            String filename = request.getParameter("filename");
            System.out.println("DownloadFile filename:" + filepath + "\\" +  filename);
            
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            
            fis = this.getClass().getResourceAsStream( "/" + filename ); 
   
            sos = response.getOutputStream();
            
             if (fis != null) {
                byte[] buff = new byte[1024];
                int bytesRead;
                while(-1 != (bytesRead = fis.read(buff, 0, buff.length))) {
                    sos.write(buff, 0, bytesRead);
                    sos.flush();
                 } 
            }
            sos.flush();
          
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
            } finally {
                try {
                    if(sos!=null){
                        sos.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }
 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         return;
    }
  
}
