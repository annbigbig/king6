package javax.servlet;

import java.io.IOException;

public interface Filter2 {

	/**
	 * web 程式啟動時呼叫此方法, 用於初始化該 Filter
	 * 
	 * @param config
	 *            可以從該參數中獲得初始化參數以及ServletContext資訊等
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException;

	/**
	 * 客戶請求服務器時會經過
	 * 
	 * @param request
	 *            客戶請求
	 * @param response
	 *            服務器響應
	 * @param chain
	 *            濾鏡鏈, 透過 chain.doFilter(request, response) 將請求傳給下個 Filter 或
	 *            Servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException;

	/**
	 * web 程式關閉時呼叫此方法, 用於銷毀一些資源
	 */
	public void destroy();

}
