package com.wuyou.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import entity.Result;
import util.FastDFSClient;

/** 
* @author  jie
* @date 创建时间：2019年4月18日 下午7:19:37 
* @version 1.0  
* @return  
* 文件上传Controller
*/
@RestController
public class UploadController {
	@Value("${FILE_SERVER_URL}")
	private String FILE_SERVER_URL;//文件服务器地址

	@RequestMapping("/upload")
	public Result upload( MultipartFile file){				
		//1取文件名
		String originalFilename = file.getOriginalFilename();
		//取扩展名
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		try {
			//2、创建一个 FastDFS 的客户端
			FastDFSClient fastDFSClient  = new FastDFSClient("classpath:config/fdfs_client.conf");
			//3、执行上传处理
			String path = fastDFSClient.uploadFile(file.getBytes(), extName);
			//4、拼接返回的 url 和 ip 地址，拼装成完整的 url
			String url = FILE_SERVER_URL + path;			
			return new Result(true,url);			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}		
	}	

}
