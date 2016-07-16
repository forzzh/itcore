package www.core.com.utils;

import java.io.File;
import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class Uploader {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	String ACCESS_KEY = "x_baqGrIB64f51S6Pi8wgFHjcfYBXRQfG9Dmr_5W";
	String SECRET_KEY = "ysh6WCoYe9wn3yLsP16YyRqP9OAj08jssAnCpXVa";
	// 要上传的空间
	String bucketname = "core";
	// 上传到七牛后保存的文件名
	String key = "my-java.png";
	// 上传文件的路径

	// 密钥配置
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 创建上传对象
	UploadManager uploadManager = new UploadManager();

	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	public void upload(String FilePath, String key) throws IOException {
		
		if(!new File(FilePath).exists()){
			return ;
		}
		try {
			// 调用put方法上传
			Response res = uploadManager.put(FilePath, key, getUpToken());
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	public void delete( String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth);
		// 要测试的空间和key，并且这个key在你空间中存在
		try {
			// 调用delete方法移动文件
			bucketManager.delete(bucketname, key);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}

	
	public static void main(String args[]) throws IOException {
//		String file = "d:/1.JPG";
//		System.out.println(file.hashCode());
//		new Uploader().upload(file, file);
		new Uploader().delete("d:/1.JPG");
		
	}

}
