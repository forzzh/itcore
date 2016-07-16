package www.core.com.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
/**
 * Created by 艺 on 16/03/31.
 */
@WebServlet(name = "upload", urlPatterns = "/c/imgUpload", initParams = {
		@WebInitParam(name = "upload_path", value = "/ImageDir/") })
public class UploadServlet extends javax.servlet.http.HttpServlet {

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		resp.addHeader("Access-Control-Allow-Headers", "Cache-Control,X-Requested-With,Content-Type");
		super.doOptions(req, resp);
	}

	public static String GetValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(
					new FileInputStream(UploadServlet.class.getResource(filePath).getPath()));
			pps.load(in);
			String value = pps.getProperty(key);
			System.out.println(key + " = " + value);
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	  public static boolean GenerateImage(String imgStr,String imgFilePath,String FileName)  
	    {   //对字节数组字符串进行Base64解码并生成图片  
	        if (imgStr == null) //图像数据为空  
	            return false;  
	        BASE64Decoder decoder = new BASE64Decoder();  
	        try   
	        {  
	            //Base64解码  
	            byte[] b = decoder.decodeBuffer(imgStr);  
	            for(int i=0;i<b.length;++i)  
	            {  
	                if(b[i]<0)  
	                {//调整异常数据  
	                    b[i]+=256;  
	                }  
	            }  
	            //生成jpeg图片  
	            File file = new File(imgFilePath);
	            if(!file.exists()){
	            	file.mkdirs();
	            }
	            OutputStream out = new FileOutputStream(imgFilePath+FileName);      
	            out.write(b);  
	            out.flush();  
	            out.close();  
	            return true;  
	        }   
	        catch (Exception e)   
	        {  
	            return false;  
	        }  
	    }  
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        String imageDir = getServletConfig().getInitParameter("upload_path");
        String directory = req.getSession().getServletContext().getRealPath(imageDir);
        StringBuilder path = new StringBuilder(GetValueByKey("/config/properties/sys.properties", "staticUrl")+"/temp/upload/");
        path.append(File.separator);

        StringBuffer url = req.getRequestURL();
//        String urlBase =  "/temp/remote/";
        String urlBase = url.substring(0, url.indexOf(req.getRequestURI())) +"/temp/upload/" ;
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            List<String> imgFileNames = new ArrayList<>();

            try {
                for (FileItem item : servletFileUpload.parseRequest(req)) {
                    if (!item.isFormField()) { // file upload
                        //文件名称
                        String name = item.getName();
                        String fileName = new Date().getTime() + name.substring(name.lastIndexOf('\\') + 1, name.length());
                        File dir = new File(path.toString());
                        if(!dir.exists()){
                        	dir.mkdir();
                        }
                        //上传文件
                        File file = new File(path + fileName);
                        dir = file.getParentFile();
                        if (!dir.exists() && !dir.isDirectory()) {
                            dir.mkdir();
                        }
                        item.write(file);

                        imgFileNames.add(fileName);
                    } else if (item.getFieldName().equals("base64Date")) { // base64 upload
                        String fileName = new Date().getTime() + ".png";
                        StringBuilder base64Data = new StringBuilder();
                        Reader reader = new InputStreamReader(item.getInputStream(), "UTF-8");
                        char[] buffer = new char[1024];
                        int read;
                        while ((read = reader.read(buffer)) != -1) {
                            base64Data.append(buffer, 0, read);
                        }
                        reader.close();
                        String base64 = base64Data.toString();
                        if (!"".equals(base64.trim())) {
                            base64 = base64.substring(base64.lastIndexOf(',') + 1);
                           
                            GenerateImage(base64 ,path.toString(),fileName);
                            imgFileNames.add(fileName);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.setContentType("text/json; charset=UTF-8");
            OutputStream outputStream = resp.getOutputStream();
            System.out.println(imgFileNames.stream()
                    .map(fileName -> (urlBase + fileName))
                    .collect(Collectors.joining("\n"))
                    .getBytes("UTF-8"));
            outputStream.write(imgFileNames.stream()
                    .map(fileName -> (urlBase + fileName))
                    .collect(Collectors.joining("\n"))
                    .getBytes("UTF-8"));

            outputStream.flush();
            outputStream.close();
            return ;
        }
    }
}