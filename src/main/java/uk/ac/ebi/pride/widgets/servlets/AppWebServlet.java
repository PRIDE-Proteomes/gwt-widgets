package uk.ac.ebi.pride.widgets.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class AppWebServlet extends HttpServlet {

    /**
     * Initialize the <code>AppWebServlet</code>
     * @param servletConfig The Servlet configuration passed in by the servlet container
     */
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    /**
     * Performs an HTTP GET request
     * @param request The {@link javax.servlet.http.HttpServletRequest}
     * @param response The {@link javax.servlet.http.HttpServletResponse}
     */
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        URL url = new URL("http://wwwdev.ebi.ac.uk/frontier/template-service/templates/services/web.html");
        //URL url = new URL("http://localhost:9595/frontier-web-service/templates/services/web.html");
        // make post mode connection
        URLConnection urlc = url.openConnection();
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);
        OutputStreamWriter wr = new OutputStreamWriter(urlc.getOutputStream());
        wr.write(getWebConfigurationJSON());
        wr.flush();

        // retrieve result
        BufferedReader br1 = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br1.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br1.close();
        String html = sb.toString();
        //html = html.replace("##contentHTML##", "<h1>Hello static contenct</h1>");
        html = html.replace("##optionalBottomStuff##", getOptionalBottomStuff());
        response.getWriter().write(html);
    }

    private String getOptionalBottomStuff(){
        return "<!-- OPTIONAL: include this if you want history support -->\n" +
                "    <iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1'\n" +
                "            style=\"position:absolute;width:0;height:0;border:0\"></iframe>\n" +
                "\n" +
                "\n" +
                "    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->\n" +
                "    <noscript>\n" +
                "        <div style=\"width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif\">\n" +
                "            Your web browser must have JavaScript enabled\n" +
                "            in order for this application to display correctly.\n" +
                "        </div>\n" +
                "    </noscript>";
    }

    private String getWebConfigurationJSON() throws IOException {
        StringBuilder sb = new StringBuilder();
        ServletContext servletContext = this.getServletContext();
        String pathContext = servletContext.getRealPath("WEB-INF/webconfig/config.json");
        FileInputStream fstream = new FileInputStream(pathContext);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            sb.append(strLine);
        }
        return sb.toString();
    }
}
