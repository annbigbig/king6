package com.helloweenvsfei.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
如果不明白這個過濾器為你作了什麼事，可以在啟動這個web app之後，到Tomcat7的工作目錄下
快取的頁面也許是
/home/anntony/workspaceEE/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/work/Catalina/localhost/king6/mm.jsp
/home/anntony/workspaceEE/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/work/Catalina/localhost/king6/index.jsp
把這些副檔名是.jsp的檔案用記事本打開來看看
會發現這些居然是被cache的靜態的HTML頁面
*/
public class CacheFilter implements Filter {

	private ServletContext servletContext;

	// 快取檔案夾，使用Tomcat工作目錄
	private File temporalDir;

	// 快取記憶體時間，設定在Filter初使化參數中
	private long cacheTime = Long.MAX_VALUE;

	public void init(FilterConfig config) throws ServletException {
		temporalDir = (File) config.getServletContext().getAttribute(
				"javax.servlet.context.tempdir");
		servletContext = config.getServletContext();
		cacheTime = new Long(config.getInitParameter("cacheTime"));
		System.out.println("javax.servlet.context.tempdir = " + temporalDir);
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// 如果為POST，則不經過快取記憶體
		if ("POST".equals(request.getMethod())) {
			chain.doFilter(request, response);
			return;
		}

		// 請求的 URI
		String uri = request.getRequestURI();
		if (uri == null)
			uri = "";
		// 替換特殊字元
		uri = uri.replace(request.getContextPath() + "/", "");
		uri = uri.trim().length() == 0 ? "index.jsp" : uri;
		uri = request.getQueryString() == null ? uri : (uri + "?" + request
				.getQueryString());

		//對應的快取檔案
		File cacheFile = new File(temporalDir, URLEncoder.encode(uri, "UTF-8"));
		System.out.println("get the cache file : " + cacheFile);
		System.out.println("javax.servlet.context.tempdir = " + temporalDir);
		

		// 如果快取檔案不存在，或者快取已經逾時，則請求Servlet
		if (!cacheFile.exists()
				|| cacheFile.length() == 0
				|| cacheFile.lastModified() < System.currentTimeMillis()
						- cacheTime) {

			// 自訂response，用於快取記憶體輸出內容
			CacheResponseWrapper cacheResponse = new CacheResponseWrapper(
					response);

			chain.doFilter(request, cacheResponse);

			//將快取記憶體的內容寫入快取檔案
			char[] content = cacheResponse.getCacheWriter().toCharArray();

			temporalDir.mkdirs();		//建立資料夾
			cacheFile.createNewFile();	//建立新快取檔案

			//輸出到檔案中
			Writer writer = new OutputStreamWriter(new FileOutputStream(
					cacheFile), "UTF-8");
			writer.write(content);
			writer.close();
		}

		// 設定請求的ContentType
		String mimeType = servletContext.getMimeType(request.getRequestURI());
		response.setContentType(mimeType);

		// 讀取快取檔案的內容，寫入用戶端瀏覽器
		Reader ins = new InputStreamReader(new FileInputStream(cacheFile),
				"UTF-8");
		StringBuffer buffer = new StringBuffer();
		char[] cbuf = new char[1024];
		int len;
		while ((len = ins.read(cbuf)) > -1) {
			buffer.append(cbuf, 0, len);
		}
		ins.close();
		// 輸出到用戶端
		response.getWriter().write(buffer.toString());
	}

	public void destroy() {
	}
}
