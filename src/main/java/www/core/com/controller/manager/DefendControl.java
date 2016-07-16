package www.core.com.controller.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import www.core.com.Enum.Enum;
import www.core.com.annotation.MethodInfo;
import www.core.com.annotation.TableInfo;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.BaseCode;
import www.core.com.utils.ClassTool;

@Controller
@RequestMapping("defend/")
public class DefendControl extends BaseController{

	@ResponseBody
	@RequestMapping("init")
	public String init(HttpServletRequest request) throws ClassNotFoundException, IOException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String path = request.getSession().getServletContext().getRealPath("/" + "WEB-INF/");
		System.out.println(path);
		String packageName = "www.core.com.pojo";
		Set<Class<?>> classNames = ClassTool.getClasses(packageName);
		for (Class<?> class1 : classNames) {
			Class<?> cls = Class.forName(class1.getName());
			Field[] fields = cls.getDeclaredFields();

			TableInfo tableInfo = class1.getAnnotation(TableInfo.class);
			if (tableInfo != null && "defend".equals(tableInfo.defend())) {
				System.out.println(tableInfo.packageName());
				Class t = tableInfo.packageName();
				System.out.println(tableInfo.defend());
				String head = "";
				String jstl = "";
				String content = "";
				String fileUrl = "";
				String search = "<table class=\"table table-bordered\" > <tr> ";
				jstl = jstl + "<td><a href='/manager/"+t.getSimpleName().toLowerCase()+"/modify?id=${entity.id}'>${entity.id}</a></td>";
				head = head + "<td> 主键</td>";
				for (Field field : fields) {
					if (field.isAnnotationPresent(MethodInfo.class) == true) {
						MethodInfo name = field.getAnnotation(MethodInfo.class);
						if (!"show".equals(name.isShow())) {
							continue;
						}
						fileUrl = tableInfo.packageName().getName();
						File file = new File(path + "/classes/config/mark/" + tableInfo.listview() + "/"
								+ tableInfo.listview() + ".ftl");
						content = super.readToString(file);
						if (!"".equals(name.getEnum())) {
							
							
							Class clazz = Class.forName(name.getEnum());
							Method method = clazz.getMethod("values");

							Object[] o = (Object[]) method.invoke(null, null);
							String reg = "";
							String createSelect = name.label()+":<select name='"+name.dataname()+"'><option value=''>未选择</option>";
							for (Object enumValue : o) {
								if(name.issearch() ==true){
									createSelect = createSelect+"<option value='"+enumValue.toString() +"'>"+((Enum) (enumValue)).show()+"</option>";
								}
								
								reg = "<#if entity." + field.getName() + "?? ><#if \"" + enumValue.toString() + "\"==entity." + field.getName() + " >"
										+ ((Enum) (enumValue)).show() + "</#if></#if>"+reg;
								System.out.println(reg);
							}
							createSelect = createSelect+"</select>";
							jstl = jstl + "<td>"+reg+"</td>";
							System.out.println(jstl);
							search =search+createSelect;
						} else {
							if(name.issearch() ==true){
								search ="<td   align=\"right\" nowrap=\"nowrap\" bgcolor=\"#f1f1f1\">"+search+ "<td>"+name.label()+"<td><input class=\"span1-1\" name='"+name.dataname()+"'/></td>";
							}
							if(name.detail() == BaseCode.class){
								jstl = jstl + "<td><#if entity." + field.getName() + "??>${entity." + name.dataname() + "}</#if></td>";
							}else{
								jstl = jstl + "<td><#if entity." + field.getName() + "??><#if entity." + name.dataname() + "??><a href='/manager/"+name.detail().getSimpleName().toLowerCase()+"/modify?id=${entity." + field.getName() + ".id}'>${entity." + name.dataname() + "}</a></#if></#if></td>";
							}
							
						}
						head = head + "<td>" + name.label() + "</td>";

					}

				}
				jstl = jstl + "<td><#if entity.createDate??>${entity.createDate}</#if></td>";
				head = head + "<td> 创建时间</td>";
				search=search+"</tr></table>"+"<table class=\"margin-bottom-20 table  no-border\">"+
        "<tbody><tr>"+
     	"<td class=\"text-center\"><input type=\"submit\" value=\"确定\" class=\"btn btn-info\" style=\"width:80px;\"/><input type=\"button\" onclick=\"window.location.href='${action}'\" value=\"清空\" class=\"btn btn-info\" style=\"width:80px;\"/></td> </tr></tbody></table>";
    
				head = head + "<td> 操作</td>";
				jstl = jstl + "<td><a href=\"javascript:deleteEntity('/manager/base/delete/',${entity.id},'"+tableInfo.packageName().getSimpleName()+"')\">删除</a>";
				if(tableInfo.doMethodName()!=null){
					for (int i = 0; i < tableInfo.doMethodName().length; i++) {
						if(StringUtils.equals("", tableInfo.doMethodName()[i])){
							continue;
						}
						jstl = jstl +"   <a href=\""+tableInfo.doMethodURL()[i]+"?id=${entity.id}\">"+tableInfo.doMethodName()[i]+"</a>";
					}
				}
				jstl = jstl + "</td>";
				content = StringUtils.replace(content, "{2}", search);
				content = StringUtils.replace(content, "{0}", head);
				content = StringUtils.replace(content, "{1}", jstl);
				System.out.println("---------");
				System.out.println(jstl);
				if (!content.equals("")) {

					File file = new File(path + "/manager/" + StringUtils.replace(fileUrl, ".", "") + "/list.ftl");
					File parent = file.getParentFile();
					if (parent != null && !parent.exists()) {
						parent.mkdirs();
					}

					// FileWriter fileWritter = new FileWriter(file.getName(),
					// false);
					File f = new File(path + "/manager/" + StringUtils.replace(fileUrl, ".", "") + "/list.ftl");// 新建一个文件对象
					FileWriter fw;
//					  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
					
					OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f),"gbk");
					out.write(content);
					out.flush();
					out.close();
					
//					File f1 = new File("/Users/yinnannan/git/fastcore/static/" + StringUtils.replace(fileUrl, ".", "") + "/list.ftl");// 新建一个文件对象
//					OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(f1),"gbk");
//					out1.write(content);
//					out1.flush();
//					out1.close();
					//					fw = new FileWriter(f);// 新建一个FileWriter
//					fw.write(content);// 将字符串写入到指定的路径下的文件中
//					fw.close();
//					File f1 = new File("/Users/yinnannan/git/fastcore/static/list.ftl");// 新建一个文件对象
//					fw = new FileWriter(f1);// 新建一个FileWriter
//					fw.write(content);// 将字符串写入到指定的路径下的文件中
//					fw.close();
				}
			}
		}
		return "";
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class clazz = Class.forName("www.core.com.Enum.SexEnum");
		Method method = clazz.getMethod("values");

		Object[] o = (Object[]) method.invoke(null, null);
		for (Object object : o) {
			System.out.println(((Enum) object).show());

			System.out.println(object);
		}

	}



}
