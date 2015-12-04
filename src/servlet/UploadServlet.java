package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet implements javax.servlet.Servlet{
	private static final long serialVersionUID = 1L;
	private static String path ;
    File saveDir = null;//初始化上传文件后的保存目录
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		path = request.getRequestURI();
		try{  
	        if(ServletFileUpload.isMultipartContent(request)){  
	          DiskFileItemFactory dff = new DiskFileItemFactory();//创建该对象  
	          dff.setSizeThreshold(1024000);//指定在内存中缓存数据大小,单位为byte  
	          ServletFileUpload sfu = new ServletFileUpload(dff);//创建该对象  
	          sfu.setSizeMax(5000000);//指定单个上传文件的最大尺寸  
	          sfu.setSizeMax(10000000);//指定一次上传多个文件的总尺寸  
	          FileItemIterator fii = sfu.getItemIterator(request);//解析request 请求,并返回FileItemIterator集合  
	          while(fii.hasNext()){  
	            FileItemStream fis = fii.next();//从集合中获得一个文件流  
	            if(!fis.isFormField() && fis.getName().length()>0){//过滤掉表单中非文件域  
	                String fileName = fis.getName().substring(fis.getName().lastIndexOf("\\"));//获得上传文件的文件名 
	                BufferedInputStream in = new BufferedInputStream(fis.openStream());//获得文件输入流  
	                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveDir+"\\"+fileName));//获得文件输出流  
	                Streams.copy(in, out, true);//开始把文件写到你指定的上传文件夹  
	            }  
	          } 
	        response.setContentType("text/html;charset=utf-8");
	  		response.addHeader("Access-Control-Allow-Origin","*");
	  		response.getWriter().println("File upload successfully!!!");
	        }  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    }  
	}
	
	 public void init() throws ServletException {  
		    /* 对上传文件夹和临时文件夹进行初始化 
		    * 
		    */  
		    super.init();   
		    //這裡為服務器的地址
		    String savePath ="C://Users//Administrator//yw//workspace//UploadDemo//WebContent/image";
		    saveDir = new File(savePath);  
		    if(!saveDir.isDirectory())  
		        saveDir.mkdir();     
		  }     

}
