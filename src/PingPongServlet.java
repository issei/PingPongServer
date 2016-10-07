

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/PingPongServlet" }, loadOnStartup=1)
public class PingPongServlet extends HttpServlet {
	private static String URLREST = "http://dashboard-inovationindra.rhcloud.com/rest/backlog/buscaBacklogPorGrupo/1";
	private static String PASS = "***";
	private static String USER = null;
	private static String PROXY_HOST = "proxylatam.indra.es";
	private static int PROXY_PORT;
	private static int RANDOMTIME = 600000;
	private static final long serialVersionUID = 1L;
	private ServletConfig config;   
    public PingPongServlet() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						String retorno = GetHTTPService.sendGET(URLREST,PROXY_HOST,PROXY_PORT,USER,PASS);
						SocketServer.notifica(retorno);
						int timePause = (int)(Math.random()*RANDOMTIME);
						Thread.sleep(timePause);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		th.start();
	}
	public ServletConfig getServletConfig() {
		return this.config;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String proxy = request.getParameter("PROXY");
		if(proxy!=null)
			PingPongServlet.PROXY_HOST = proxy;
		String port = request.getParameter("PORT");
		if(port!=null)
			PingPongServlet.PROXY_PORT = Integer.parseInt(port);
		if("UNSET".equals(proxy))PingPongServlet.PROXY_HOST = null;		
		String user = request.getParameter("USER");
		if(user!=null)
			PingPongServlet.USER = user;
		if("UNSET".equals(user))PingPongServlet.USER = null; 
		String pass = request.getParameter("PASS");
		if(pass!=null)
			PingPongServlet.PASS = pass;
		String rest = request.getParameter("REST");
		if(rest!=null)
			PingPongServlet.URLREST = rest;
		String time = request.getParameter("TIME");
		if(time!=null)
			PingPongServlet.RANDOMTIME = Integer.parseInt(time);
		
		response.getWriter().append(PingPongServlet.PROXY_HOST)
		.append(PingPongServlet.PROXY_PORT+" ")
		.append(PingPongServlet.USER)
		.append(PingPongServlet.PASS)
		.append(PingPongServlet.URLREST)
		.append(PingPongServlet.RANDOMTIME+"");
	}
}
