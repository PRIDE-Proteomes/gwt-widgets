package uk.ac.ebi.pride.widgets.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractProxyServlet extends HttpServlet {

    // Proxy host params
    /**
     * The host to which we are proxying requests
     */
	private String stringProxyHost;
	/**
	 * The port on the proxy host to wihch we are proxying requests. Default value is 80.
	 */
	private int intProxyPort = 80;
	/**
	 * The (optional) path on the proxy host to wihch we are proxying requests. Default value is "".
	 */
	private String stringProxyPath = "";
	/**
	 * The maximum size for uploaded files in bytes. Default value is 5MB.
	 */
	private int intMaxFileUploadSize = 5 * 1024 * 1024;


    /**
	 * Initialize the <code>ProxyServlet</code>
	 * @param servletConfig The Servlet configuration passed in by the servlet conatiner
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

		// Get the proxy host
		String stringProxyHostNew = getServletContext().getInitParameter("proxyHost");//servletConfig.getInitParameter("proxyHost");
		if(stringProxyHostNew == null || stringProxyHostNew.length() == 0) {
			throw new IllegalArgumentException("Proxy host not set, please set init-param 'proxyHost' in web.xml");
		}
		this.setProxyHost(stringProxyHostNew);
		// Get the proxy port if specified
		String stringProxyPortNew = getServletContext().getInitParameter("proxyPort");//servletConfig.getInitParameter("proxyPort");
		if(stringProxyPortNew != null && stringProxyPortNew.length() > 0) {
			this.setProxyPort(Integer.parseInt(stringProxyPortNew));
		}
		// Get the proxy path if specified
		String stringProxyPathNew = getServletContext().getInitParameter("proxyPath");//servletConfig.getInitParameter("proxyPath");
		if(stringProxyPathNew != null && stringProxyPathNew.length() > 0) {
			this.setProxyPath(stringProxyPathNew);
		}
		// Get the maximum file upload size if specified
		String stringMaxFileUploadSize = getServletContext().getInitParameter("maxFileUploadSize");
		//servletConfig.getInitParameter("maxFileUploadSize");
		if(stringMaxFileUploadSize != null && stringMaxFileUploadSize.length() > 0) {
			this.setMaxFileUploadSize(Integer.parseInt(stringMaxFileUploadSize));
		}
	}

    protected String getProxyHostAndPort() {
    	if(this.getProxyPort() == 80) {
    		return this.getProxyHost();
    	} else {
    		return this.getProxyHost() + ":" + this.getProxyPort();
    	}
	}

    // Accessors
    protected String getProxyURL(HttpServletRequest httpServletRequest) {
		// Set the protocol to HTTP
		String stringProxyURL = "http://" + this.getProxyHostAndPort();
		// Check if we are proxying to a path other that the document root
		if(!this.getProxyPath().equalsIgnoreCase("")){
			stringProxyURL += this.getProxyPath();
		}
		// Handle the path given to the servlet
        if(httpServletRequest.getPathInfo() != null){
		    stringProxyURL += httpServletRequest.getPathInfo();
        }
		return stringProxyURL;
    }

    protected String getProxyHost() {
		return this.stringProxyHost;
	}
	protected void setProxyHost(String stringProxyHostNew) {
		this.stringProxyHost = stringProxyHostNew;
	}
	protected int getProxyPort() {
		return this.intProxyPort;
	}
	protected void setProxyPort(int intProxyPortNew) {
		this.intProxyPort = intProxyPortNew;
	}
	protected String getProxyPath() {
		return this.stringProxyPath;
	}
    protected void setProxyPath(String stringProxyPathNew) {
		this.stringProxyPath = stringProxyPathNew;
	}
	protected int getMaxFileUploadSize() {
		return this.intMaxFileUploadSize;
	}
	protected void setMaxFileUploadSize(int intMaxFileUploadSizeNew) {
		this.intMaxFileUploadSize = intMaxFileUploadSizeNew;
	}
}
