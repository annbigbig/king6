package javax.servlet;

import java.io.IOException;

public interface Filter2 {

	/**
	 * web �{���ҰʮɩI�s����k, �Ω��l�Ƹ� Filter
	 * 
	 * @param config
	 *            �i�H�q�ӰѼƤ���o��l�ưѼƥH��ServletContext��T��
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException;

	/**
	 * �Ȥ�ШD�A�Ⱦ��ɷ|�g�L
	 * 
	 * @param request
	 *            �Ȥ�ШD
	 * @param response
	 *            �A�Ⱦ��T��
	 * @param chain
	 *            �o����, �z�L chain.doFilter(request, response) �N�ШD�ǵ��U�� Filter ��
	 *            Servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException;

	/**
	 * web �{�������ɩI�s����k, �Ω�P���@�Ǹ귽
	 */
	public void destroy();

}
