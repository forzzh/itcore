package www.core.com.controller.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;

import www.core.com.pojo.Message;

@Controller
@RequestMapping("/base")
public class BaseIncludeController extends BaseController{
	

	
	@RequestMapping("/uploadKinditor")
	@ResponseBody
	public void uploadKinditor(HttpServletResponse response,HttpServletRequest request){
		//文件保存目录路径
		SimpleDateFormat current = new SimpleDateFormat("yyyymmdd");
		String savePath = super.getStaticUrl() + "/temp/" +  "/" + current.format(new Date()).toString()
				+ "/";

		//文件保存目录URL
		String saveUrl  ="/temp/" +  "/" + current.format(new Date()).toString()+"/";

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
//		extMap.put("flash", "swf,flv");
//		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
//		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		PrintWriter out = null;
	
		
		String parent = super.getStaticUrl() + "/temp/" +  "/" + current.format(new Date()).toString()
				+ "/";
		try{
			out = response.getWriter();
		}catch (Exception ex){
			out.println(getError("上传失败"+ex.getMessage()));
			return ;
		}
		//最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(getError("请选择文件。"));
			return ;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
				return ;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
//			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
				return ;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		savePath +=   "/";
		saveUrl +=  "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
	       
	       for (MultipartFile file : fileMap.values()) {
	    	   String fileName = file.getOriginalFilename();
	    	   long fileSize = file.getSize();
	    	   if(file.getSize() > maxSize){
					out.println(getError("上传文件大小超过限制。"));
						return ;
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				}
				String newFileName = current.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					file.transferTo(uploadedFile);
				}catch(Exception e){
					out.println(getError("上传文件失败。"));
						return ;
				}
				
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl +"/"+ newFileName);
				out.println(obj.toJSONString());
		}
		
	}
	// 所有的文件上传
		@RequestMapping(value = "upload", method = RequestMethod.POST)
		public @ResponseBody Message upload(@RequestParam(value = "file", required = false) MultipartFile file,
				HttpServletRequest request, HttpServletResponse response ) {
			// request = new MulpartRequestWrapper(request);
			Message message = new Message();
			
			String fileName = file.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			String newFileName = createCode() + "." + ext;
			SimpleDateFormat current = new SimpleDateFormat("yyyymmdd");
			String parent = super.getStaticUrl() + "/temp/" +  "/" + current.format(new Date()).toString()
					+ "/";
			System.out.println("===================================="+parent);

			if ("jpg".equals(ext) || "png".equals(ext) || "jpeg".equals(ext)) {
				
				File targetFile = new File(parent, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				// 保存
				try {
					file.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					message.setStatus(false);
					message.setMsg("上传失败");
					message.setData(e.getMessage());
					return message;
				}
				message.setStatus(true);
				message.setData("/temp/"   + current.format(new Date()).toString()
					+ "/"+newFileName);
				return message;
			}
			message.setStatus(false);
			message.setMsg("上传失败");

			return message;
		}

		@RequestMapping(value = "uploadPhoto", method = RequestMethod.POST)
		public void uploadPhoto(@RequestParam(value = "file", required = false) MultipartFile file,
				HttpServletRequest request, HttpServletResponse response ) throws IOException {
			// request = new MulpartRequestWrapper(request);
			
			Message message = new Message();
			String fileName = file.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			String newFileName = createCode() + "." + ext;
			SimpleDateFormat current = new SimpleDateFormat("yyyymmdd");
			String parent = super.getStaticUrl() + "/temp/" +  "/" + current.format(new Date()).toString()
					+ "/";
			System.out.println("===================================="+parent);

			if ("jpg".equals(ext) || "png".equals(ext) || "jpeg".equals(ext)) {
				
				
				
			
				File targetFile = new File(parent, newFileName);
				
				
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

			
				try {
					file.transferTo(targetFile);
					BufferedImage bi = ImageIO.read(targetFile);
					int srcWidth = bi.getWidth(); // 源图宽度
					int srcHeight = bi.getHeight(); // 源图高度// 保存
					if(srcWidth>700||srcHeight >600){
						message.setStatus(false);
						message.setMsg("图片的宽不能超过700并且高度不能高于600");
						response.setContentType("application/json; charset=UTF-8");
						response.getWriter().print(toJson(message));
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.setStatus(false);
					message.setMsg("上传失败");
					message.setData(e.getMessage());
//					return message;
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().print(toJson(message));
					return;
				}
				message.setStatus(true);
				message.setData("/temp/"   + current.format(new Date()).toString()
					+ "/"+newFileName);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().print(toJson(message));
				return;
//				return message;
			}
			message.setStatus(false);
			message.setMsg("上传失败");
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(toJson(message));
			return;
//			return message;
		}
		
		private String toJson(Object obj) {
			return JSON.toJSONString(obj);
		}
}
