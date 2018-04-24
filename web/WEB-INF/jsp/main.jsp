<%@ include file="/WEB-INF/jsp/include/page_header.jspf" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <title>Event Management</title>
        <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        
    </head>

    <body>
        <s:form beanclass="com.pra.stripes.LoginActionBean" >
            <div class="row">
                <div class="col-md-12">

                    <div class="welcome-text-2">
                        <div class="content-section" >
                            <div class="container" >
                                <div class="row" align="center">
                                    <div class="col-md-12">
                                        <display:table class="tablecloth" name="${actionBean.listEventfile}" requestURI="/main" id="line">
                                            <display:column title="Event">
                                                <a href="${pageContext.request.contextPath}/dokumen/view/eventfile/${line.eventFileId}" rel="prettyPhoto[gallery1]" 
                                                   title="${line.title}">
                                                    <img src="${pageContext.request.contextPath}/dokumen/view/eventfile/${line.eventFileId}" width="300" height="300"/>
                                                </a>
                                            </display:column>
                                        </display:table> 
                                    </div>
                                </div>
                                <div class="row" >
                                    <div class="col-md-12">

                                        <div class="product-information">
                                            <p>
                                            <h4> <a href="${pageContext.request.contextPath}/main?newEvent" >Create New Event</a>&nbsp;</h4>
                                            </p>

                                        </div> <!-- /.product-information -->
                                    </div> <!-- /.col-md-8 -->

                                </div> <!-- /.row -->
                            </div> <!-- /.container -->
                        </div> <!-- /.content-section -->
                    </div>
                </div>
            </div>

            <!-- End Copyright Section -->
            <a href="#" class="back-to-top"><i class="fa fa-angle-up"></i></a>
            </s:form>
    </body>

</html>

