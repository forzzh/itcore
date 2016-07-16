package www.core.com.utils;

import java.io.File;
import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class Uploader {
	// ���ú��˺ŵ�ACCESS_KEY��SECRET_KEY
	String ACCESS_KEY = "x_baqGrIB64f51S6Pi8wgFHjcfYBXRQfG9Dmr_5W";
	String SECRET_KEY = "ysh6WCoYe9wn3yLsP16YyRqP9OAj08jssAnCpXVa";
	// Ҫ�ϴ��Ŀռ�
	String bucketname = "core";
	// �ϴ�����ţ�󱣴���ļ���
	String key = "my-java.png";
	// �ϴ��ļ���·��

	// ��Կ����
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// �����ϴ�����
	UploadManager uploadManager = new UploadManager();

	// ���ϴ���ʹ��Ĭ�ϲ��ԣ�ֻ��Ҫ�����ϴ��Ŀռ����Ϳ�����
	public String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	public void upload(String FilePath, String key) throws IOException {
		
		if(!new File(FilePath).exists()){
			return ;
		}
		try {
			// ����put�����ϴ�
			Response res = uploadManager.put(FilePath, key, getUpToken());
			// ��ӡ���ص���Ϣ
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// ����ʧ��ʱ��ӡ���쳣����Ϣ
			System.out.println(r.toString());
			try {
				// ��Ӧ���ı���Ϣ
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	public void delete( String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// ʵ����һ��BucketManager����
		BucketManager bucketManager = new BucketManager(auth);
		// Ҫ���ԵĿռ��key���������key����ռ��д���
		try {
			// ����delete�����ƶ��ļ�
			bucketManager.delete(bucketname, key);
		} catch (QiniuException e) {
			// �����쳣��Ϣ
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
