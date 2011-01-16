<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.d2.cse.Controller" language="java" %>
<%@ page import="org.d2.cse.dto.Cluster"%>
<%@ page import="org.d2.cse.dto.Document"%>
<%@ page import="org.d2.cse.dto.CObject"%>
<%@ page import="java.util.Vector"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>cSearch :Conceptual Engine Final Project</title>
        <link rel="Shortcut Icon" type="image/x-icon" href="">
        <link rel="stylesheet" media="screen" href="results.css" type="text/css">
        <link rel="stylesheet" media="print" href="print.css" type="text/css">
        <style type="text/css">
            body, body div, body p, body th, body td, body li, body dd  {
                font-size:  small}
        </style>
        <style type="text/css">.b0{font-weight: bold}</style>
    </head>
    
    <body class="tb">
        <%
            /*query string whose results are to be displayed*/
            String query = "";
            /* clusters to be displayed for the searched query*/
            CObject displayCluster = new CObject();
            /* cluster number whose results are to be displayed at the moment
             * if no clusters are choosed by the user results of first cluster will be displayed
             */
            int clusterNumber = 1;
            Cluster dispCluster=new Cluster();

            try {
                if (request.getParameter("cNumber") != null) {
                    clusterNumber = Integer.valueOf(request.getParameter("cNumber"));
                } else {
                    clusterNumber = 1;
                }
            } catch (NullPointerException ex) {
                System.err.println("Processing cannot go forward because there is null pointer exception:cSearch");
            }

            try {
                if (request.getParameter("query") != null) {
                    query = request.getParameter("query");
                } else {
                    /* !!!!if user clicks the button without entering anything query is null so fix it later*/
                    query = "php";
                }
            } catch (NullPointerException ex) {
                System.err.println("Sorry cannot go forward because there is null pointer exception:cSearch");
            }

            /* trims(removes spaces before and after the query which are not necessary
             * and change it to uppper case because case of the query should not affect the output
             */
            query = query.toUpperCase();
            query = query.trim();

            /************* main program part here*************************/
            /* Sending the query through the constructor of controller */
            Controller control = new Controller(query);

            /* set Mysql or Oracle as the working database (for now only mysql)*/
            control.setDatabaseUsed("mysql");

            /* Final cluster to be displayed to the user*/
            try {
                displayCluster = control.getFinalClusterForDisplay();
            } catch (NullPointerException npex) {
                System.err.println("sorry display vector not initialized" + npex);
            } catch (Exception ex) {
                System.err.println("sorry some exception caught" + ex);
            }
            /**************main program part ends here********************/
        %>
        
        <div id="page">
            <div id="header" class="clearfix">
                <div class="search">
                    <div id="logo">  </div>
                    <div id="seperator">  </div>
                    <div id="tabs">
                        <table>
                            <tr>
                                <td class="tab"><span><a href="index.jsp">Clustering Search Engine</a></span></td>
                            </tr>
                        </table>
                    </div>    <!--end of id:tabs -->
                                 
                    <div class="search-form">
                        <form method="post" action="main.jsp" class="simple" name="search" id="search">	
                            <fieldset>
                                <input name="input-form" value="clusty-simple" type="hidden">
                                <table class="form">
                                    <td class="input">
                                        <input value="" size="50" name="query" class="query" tabindex="1" accesskey="k">								 
                                        <input value="Search" tabindex="2" type="submit">								
                                    </td>
                                    <td class="seperator">
                                    </td>
                                    <td class="form-links">
                                        <ul class="form-links">
                                            <li><a target="_top" href="" style="text-decoration:none;">other options</a></li>
                                        </ul>
                                    </td>
                                </table>
                            </fieldset>
                        </form>
                    </div><!--end of id:search-form -->
                </div><!--end of id:search -->
            </div>
            <!--end of id:clearfix -->
                 

            <div id="main">
                <div id="content" class="clearfix">
                    <div id="cluster-nav">
                        <div id="clusterby-actions">
                            <table id="clusterby-actions">
                                <div><a>CLUSTER LABELS </a></div>
                            </table>
                        </div>
                        <div id="clusters">
                            <div class="bi">
                                <ul id="nodes">
                                    <li class="active">
                                        <%            for (int i = 0; i < displayCluster.getCluster().size(); i++) {
                                                            Cluster cus= (Cluster) displayCluster.getCluster().get(i);
                                                            String ClusterName=cus.getClustername();
                                                            long ClusterId=cus.getClusterid();
                                                        /*    Document doc= (Document) displayCluster.get(i);
                                                            query=doc.getQuery();
                                                            int ClusterId=doc.getClusterId();
                                                            String ClusterName=doc.getClustername();
                                                            */
                                            /*Cluster cus = (Cluster) displayCluster.get(i);
                long ClusterId = cus.getClusterId();
                String ClusterName = cus.getClusterName();
                String QueryName = cus.getQuery().getQueryString();
                long clusterqueryID = cus.getQuery().getQueryId();*/
                                        %> 
                                        <li>
                                            <div id="DN656" class="cluster clearfix cloud6">
                                                <div class="label clearfix">
                                                    <a href="main.jsp?cNumber=<%=ClusterId%>&query=<%=query%>"><%=ClusterName%></a>
                                                </div>
                                            </div>
                                        </li>
                                        <%}//end of for loop%>      
                                    </li>
                                </ul>
                            </div>
                        </div>  <!-- end of id:clusters-->
                    </div><!-- end of id:cluster-nav-->
                    <div id="results">
                        <div id="content-results">
                            <div id="results-list">
                                <div id="list-intro" class="clearfix">
                                    <div class="listintroright">
                                    </div>
                                    <div class="listintroleft">
                                        Top 202 results of at least 132,090,000 retrieved for the query "<%=query%>"
                                    </div>
                                </div>
                                <div class="clear">
                                </div>
                                <div class="list-header">
                                    cSearch Results
                                </div>
                                <ol class="organic">
                                             <% for (int i = 0; i < displayCluster.getCluster().size(); i++) {
                dispCluster = (Cluster) displayCluster.getCluster().get(i);
                if (dispCluster.getClusterid() == clusterNumber) {
                    break;
                }
            }%>
                                   <% for (int document = 0; document < dispCluster.getDocuments().size(); document++) {
                Document docDisplay = (Document) dispCluster.getDocuments().get(document);
                                    String title=docDisplay.getDocumenttitle();
                                    String link=docDisplay.getDocumentlink();
                                    String snippet=docDisplay.getDocumentsnippet();
                                    %>
                                    <li class="document">
                                        <div class="document-header">
                                            <a target="_top" href="">
                                                <span class="title">
                                                    <span class="b0"><a href="<%=link %>"><%=title %></a></span>
                                                </span>
                                            </a>
                                        </div>
                                        <span class="snippet">
                                            <span class="b0"></span>
                                            <%=snippet %>
                                        </span>
                                        <div class="document-footer">
                                          <a href="<%=link %>">  <span class="url" style="text-decoration:none;"><%=link %></span> </a>
                                        </div>
                                    </li>
                                    <% }//end of for loop%>
                                </ol>
                            </div>
                        </div>
                    </div><!-- end of ed:resutlts-->
                </div>
            </div><!-- end of id:main-->
           
           
           
            
            <div id="bottom">
                <div class="new">
                    <div class="fresh">
                    </div>
                </div>
                <div class="footers">
                    <a href="footer/AboutCSEARCH.jsp">about cSearch</a> &nbsp;
                    <a href="footer/TermsOfUse.jsp">Terms of Use</a>&nbsp;
                    <a href="footer/AboutUs.jsp">About us</a> &nbsp;
                    <a href="footer/PrivacyPolicy.jsp">Privacy Policy</a>&nbsp;
                </div>
                
                <div class="copyright"><img src="images/D2logo.jpeg" alt="D2">Powered by D2hawkeye - @ 2008 Clusturing search. All Rights Reserved </div>
            </div>
        </div><!-- end of id:page-->
    </body>
</html>