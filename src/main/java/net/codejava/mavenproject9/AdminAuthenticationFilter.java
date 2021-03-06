/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codejava.mavenproject9;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import  javax.servlet.http.*;

/**
 *
 * @author LENOVO
 */
@WebFilter(filterName = "AdminAuthenticationFilter", urlPatterns = {"/admin/*"})
public class AdminAuthenticationFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AdminAuthenticationFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AdminAuthenticationFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AdminAuthenticationFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
         HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);//g??n tham s??? true th?? tr??? v??? session m???i(new session) n???u current session request v??? kh??ng t???n t???i
        //session.setAttribute("adminUser", new String[]{"minh","ngoc"});                                                 //M?? ??? ????y ta ????? false v?? c???n l???y v??? current session n???u kh??ng c?? s??? tr??? v??? null
        boolean isLoggedIn = (session != null && session.getAttribute("adminUser") != null);
        String loginURI = httpRequest.getContextPath() + "/admin/login";//Xem x??t link resquet hi???n t???i tr??n tr??nh duy???t c?? ph???i l?? c???a servlet x??? l?? login hay kh??ng; c??? th??? ??? ????y l?? /mavenproject9/admin/login
        //String loginURI1 = httpRequest.getContextPath();
        String loginURI1 = httpRequest.getContextPath();
        String loginUR2 = httpRequest.getAuthType();
        String isLoginRequest1 = httpRequest.getRequestURI();
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);//Request L???y link URI hi???n t???i tr??n tr??nh duy???t
 
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");//So s??nh ??u??i c?? chu???i "login.jsp" hay kh??ng
 
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the admin is already logged in and he's trying to login again
            // then forwards to the admin's homepage
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
            dispatcher.forward(request, response);
            // dispatcher d??ng ????? chuy???n to??n b??? x??? l?? t??? servlet n??y sang servlet kh??c hay resource kh??c(html,.jsp)
            //n???u foward l?? chuy???n dispascher ????ng vai tr?? nh??ng chuy???nto??n b??? cho servlet ???????c chuy???n ?????n x??? l??
            //n???u l?? include th?? gi??? l???i nh???ng x??? l?? ??? servlet hi???n t???i g???p v???i servlet ???????c chuy???n h?????ng
            //N???u tham s??? chuy???n t???i html hay .jsp th?? m???c ????ch ch??? l?? chuy???n h?????ng ?????n trang ????
        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);//b??? qua Filter n??y(AdminAuthenticationFilter) v?? x??? l?? ?????n filter ti???p theo n???u t???n t???i fiter ti???p theo
                                             //V?? filter ho???t ?????ng theo chu???i n??n ???????c g???i l?? filter chain
// 
        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
            //Chuy???n h?????ng to??n x??? l??  request v?? reponse v??? trang login.jsp
            //N???u d??ng .include(request, response) thi gi??? l???i x??? l?? ??? fdoFlter c??ng v???i nh???ng x??? l?? ??? trang chuy???n t???i(c?? th??? l?? x??? l?? trong servlet    )
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("AdminAuthenticationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AdminAuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AdminAuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
