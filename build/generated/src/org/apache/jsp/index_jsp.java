package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>cSearch: Clustering search engine</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"front.css\" type=\"text/css\">\n");
      out.write("        <style type=\"text/css\">\n");
      out.write("            body, body div, body p, body th, body td, body li, body dd  {\n");
      out.write("                font-size:  small}\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <body id=\"front\" >\n");
      out.write("        <div class=\"header\">\n");
      out.write("            <div class=\"upper1\">\n");
      out.write("                <a href=\"footer/AboutCSEARCH.jsp\">about <strong>cSearch</strong></a> &nbsp;\n");
      out.write("                <a href=\"footer/TermsOfUse.jsp\">Terms of Use</a>&nbsp;\n");
      out.write("                <a href=\"footer/AboutUs.jsp\">About us</a> &nbsp;\n");
      out.write("            </div>\n");
      out.write("        </div>\t\n");
      out.write("        <div id=\"main\">\n");
      out.write("            <div id=\"top\">\n");
      out.write("                <div class=\"search\">\n");
      out.write("                    <div class=\"logo\" > </div>\n");
      out.write("                    <div class=\"seperator1\"> </div>\n");
      out.write("                    <div id=\"tabs\">\n");
      out.write("                        <table>\n");
      out.write("                            <tbody>\n");
      out.write("                                <tr>\n");
      out.write("                                    <td class=\"tab\"><span>Clustering Search Engine</span></td>\n");
      out.write("                                </tr>\n");
      out.write("                            </tbody>\n");
      out.write("                        </table>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"search-form\">\n");
      out.write("                        <form method=\"post\" action=\"main.jsp\" class=\"simple\" name=\"search\" id=\"search\">\n");
      out.write("                            <fieldset>\n");
      out.write("                                <input name=\"input-form\" value=\"simple\" type=\"hidden\">\n");
      out.write("                                <table class=\"form\">\n");
      out.write("                                    <tbody>\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td class=\"input\">\n");
      out.write("                                                <input value=\"\" size=\"50\" name=\"query\" class=\"query\" tabindex=\"1\">\n");
      out.write("                                                <input value=\"Search\" tabindex=\"2\" type=\"submit\">\n");
      out.write("                                                <!-- For customized image button\n");
      out.write(" <input value=\"Search\" type=\"image\" src=\"images/box.png\" tabindex=\"2\" alt=\"submit\">\n");
      out.write("                               -->\n");
      out.write("                                            </td>\n");
      out.write("                                            <td class=\"seperator\">\n");
      out.write("                                            </td>\n");
      out.write("                                            <td class=\"form-links\">\n");
      out.write("                                                <ul class=\"form-links\">\n");
      out.write("                                                    <li><a target=\"_top\" href=\"\" style=\"text-decoration:none;\">other options</a></li>\n");
      out.write("                                                </ul>\n");
      out.write("                                            </td>\n");
      out.write("                                        </tr>\n");
      out.write("                                        \n");
      out.write("                                    </tbody>\n");
      out.write("                                </table>\n");
      out.write("                                Enter your search query in the box\n");
      out.write("                            </fieldset>\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div><!--main-->\n");
      out.write("         \n");
      out.write("        <div id=\"bottom\">\n");
      out.write("            <div class=\"new\">\n");
      out.write("                <div class=\"fresh\">\n");
      out.write("                    <div class=\"bi\">\n");
      out.write("                        <p><span style=\"color:#6F6F6F;\"><strong>Final Year Project Under Construction</strong></span></p>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"footers\">\n");
      out.write("        </div>\n");
      out.write("        <div class=\"copyright\"><img src=\"images/D2logo.jpeg\" alt=\"D2\">Powered by D2hawkeye - @ 2008 Clusturing search. All Rights Reserved </div>\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
