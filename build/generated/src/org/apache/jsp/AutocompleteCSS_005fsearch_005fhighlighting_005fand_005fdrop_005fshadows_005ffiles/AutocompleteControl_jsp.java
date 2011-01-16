package org.apache.jsp.AutocompleteCSS_005fsearch_005fhighlighting_005fand_005fdrop_005fshadows_005ffiles;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class AutocompleteControl_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<script id=\"AutocompleteInitScript\" language=\"JavaScript\" type=\"text/JavaScript\">\n");
      out.write("  \tvar cssPath  \t\t      = \"");
      out.print(request.getParameter("cssPath")  );
      out.write("\";\n");
      out.write(" \tvar labelText\t\t      = \"");
      out.print(request.getParameter("labelText"));
      out.write("\";\n");
      out.write("\tvar labelClass\t\t      = \"");
      out.print(request.getParameter("labelClass"));
      out.write("\";\n");
      out.write("\tvar textFieldClass \t      = \"");
      out.print(request.getParameter("textFieldClass"));
      out.write("\";\n");
      out.write("\tvar searchButtonText      = \"");
      out.print(request.getParameter("searchButtonText"));
      out.write("\";\n");
      out.write("\tvar searchButtonClass     = \"");
      out.print(request.getParameter("searchButtonClass"));
      out.write("\";\n");
      out.write("\tvar listSize \t\t      = \"");
      out.print(request.getParameter("listSize") );
      out.write("\";\n");
      out.write("\tvar scrollbarColorClass   = \"");
      out.print(request.getParameter("scrollbarColorClass") );
      out.write("\";\n");
      out.write("\tvar highlightClass        = \"");
      out.print(request.getParameter("highlightClass") );
      out.write("\";\n");
      out.write("\tvar dropShadow\t\t  \t  = \"");
      out.print(request.getParameter("addDropShadow") );
      out.write("\";\n");
      out.write("\t\n");
      out.write("\tvar jsPath  \t   = \"");
      out.print(request.getParameter("jsPath")  );
      out.write("\";\n");
      out.write("  \tvar jsonParserPath = \"");
      out.print(request.getParameter("jsonParserPath")  );
      out.write("\";\n");
      out.write("\t\n");
      out.write("\t//set defaults\n");
      out.write("\tif (!jsPath) \t\t jsPath \t\t= \"AutocompleteControl.js\";\n");
      out.write("\tif (!jsonParserPath) jsonParserPath\t= \"json_parse.js\";\n");
      out.write("\t\n");
      out.write("\taddJsScript(jsPath);\n");
      out.write("\taddJsScript(jsonParserPath);\n");
      out.write("\t\n");
      out.write("\tfunction addJsScript(scriptPath)\n");
      out.write("\t{\n");
      out.write("\t\tvar newJsFile  = document.createElement(\"script\"); \n");
      out.write("\t    with (newJsFile)\n");
      out.write("\t    {\n");
      out.write("\t\t    if ( setAttribute )\n");
      out.write("\t\t    {\n");
      out.write("\t\t    \tsetAttribute('type', \"text/javascript\"); \n");
      out.write("\t\t\t\tsetAttribute('src', scriptPath); \n");
      out.write("\t\t    }\n");
      out.write("\t\t    else\n");
      out.write("\t\t    {\n");
      out.write("\t\t    \ttype = \"text/javascript\"; \n");
      out.write("\t\t\t\tsrc  = scriptPath; \n");
      out.write("\t\t    }\n");
      out.write("\t\t}\n");
      out.write("\t\tvar thisScript = document.getElementById(\"AutocompleteInitScript\");\n");
      out.write("\t\tthisScript.parentNode.insertBefore(newJsFile, thisScript);\n");
      out.write("\t}\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" id=\"AutocompleteCSS\" href=\"AutocompleteControl.css\">\n");
      out.write("<form action=\"autocomplete\" method=\"get\">\n");
      out.write("    <input id=\"action\" type=\"hidden\" name=\"action\" value=\"lookupbyname\"/>\n");
      out.write("    <div id=\"AutocompleteControl\">\n");
      out.write("        <div id=\"AutocompleteTextFieldLabel\" class=\"Autocomplete\">Fund Name:</div>\n");
      out.write("        <div id=\"AutocompleteTextField\"      class=\"Autocomplete\">\n");
      out.write("            <input  type=\"text\"\n");
      out.write("                    autocomplete=\"off\"\n");
      out.write("                    id=\"complete-field\"\n");
      out.write("                    name=\"searchString\" />\n");
      out.write("        </div>\n");
      out.write("        <div id=\"AutocompleteSubmit\" class=\"Autocomplete\"><input id=\"submit_btn\" type=\"submit\" value=\"Lookup Funds\"> </div>         \n");
      out.write("    </div>\n");
      out.write("</form>");
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
