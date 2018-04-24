
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="/WEB-INF/jsp/include/page_header.jspf" %>
<!DOCTYPE html>
<%

    boolean main = (request.getRequestURI().indexOf("/main.jsp") > 0); //Hack! main page!!            
    pageContext.setAttribute("main", main, pageContext.PAGE_SCOPE);

    boolean register = (request.getRequestURI().indexOf("/register.jsp") > 0); //Hack! main page!!            
    pageContext.setAttribute("register", register, pageContext.PAGE_SCOPE);


%>

<html>
    <head>
        <title>Event Management</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

        <!-- CSS -->
        <!-- Bootstrap CSS  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" media="screen" />

        <!-- Font Awesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" type="text/css">

        <!-- Owl Carousel CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.transitions.css" type="text/css">

        <!-- Light Box CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lightbox.css" type="text/css">

        <!-- CSS Styles  -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

        <!-- Default Color -->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/colors/black.css">

        <!-- Colors CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/colors/blue.css" title="blue">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/colors/light-red.css" title="light-red">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/colors/black.css" title="black">

        <!-- Responsive CSS Style -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive.css">

        <!-- Css3 Transitions Styles  -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/animate.css">

        <!-- JS File -->

        <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/modernizr.custom.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/lightbox.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.appear.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.fitvids.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/superfish.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/supersubs.js"></script>
        <script src="${pageContext.request.contextPath}/js/styleswitcher.js"></script>
        <script src="${pageContext.request.contextPath}/js/script.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker3.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datetimepicker.css" />
        <script src="${pageContext.request.contextPath}/js/bootstrap-datepicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/clock.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/validate.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dataTables.css" />
        <script src="${pageContext.request.contextPath}/js/dataTables.bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/formatCurrency.js"></script>
        <script src="${pageContext.request.contextPath}/js/clear-text.js"></script>
        <script src="${pageContext.request.contextPath}/js/exporting.js"></script>
        <script src="${pageContext.request.contextPath}/js/highcharts.js"></script>

        <script type="text/javascript">
            $(function () {
                $("#clock").clock();

            });



            function validateNumber(elmnt, content) {
                //if it is character, then remove it..
                if (isNaN(content)) {
                    elmnt.value = removeNonNumeric(content);
                    return;
                }
            }

            function convert(val) {
                var amaun = CurrencyFormatted(val);
                amaun = CommaFormatted(amaun);
                return amaun;
            }

            function removeNonNumeric(strString)
            {
                var strValidCharacters = "1234567890+-";
                var strReturn = "";
                var strBuffer = "";
                var intIndex = 0;
                // Loop through the string
                for (intIndex = 0; intIndex < strString.length; intIndex++)
                {
                    strBuffer = strString.substr(intIndex, 1);
                    // Is this a number
                    if (strValidCharacters.indexOf(strBuffer) > -1)
                    {
                        strReturn += strBuffer;
                    }
                }
                return strReturn;
            }

            $(document).ready(function () {
                $("ul#nav li").attr("onclick", "return true");
                $('form').submit(function () {
                });

                $('.uppercase').each(function () {
                    $(this).blur(function () {
                        $(this).val($(this).val().toUpperCase());
                    });
                });

            });



        </script>
        <decorator:head /> 
    </head>

    <body>      



        <div class="container">  
            <!-- Start Header Section -->
            <div class="header-section">
                <div class="hidden-xs">
                    <a href="#"><img src="${pageContext.request.contextPath}/images/banner.jpg" class="img-responsive" alt=""></a>
                </div>
                <div class="hidden-sm hidden-md hidden-lg hidden-xl">
                    <a href="#"><img src="${pageContext.request.contextPath}/images/login/banner_xs.jpg" class="img-responsive" alt=""></a>
                </div>
            </div>
            <c:if test="${main || register}">

                <div class="panel panel-default">
                    <!-- Toggle Heading -->
                    <div class="panel-heading">
                        <h4 class="panel-title" style="text-align: right">
                            <a data-toggle="collapse" data-parent="#accordion" >
                                <div class="row">
                                    <div class="col-md-12">
                                        <marquee  behavior="scroll"  scrolldelay="100" scrollamount="5" style="text-align: center" >
                                            Welcome to Event Management. 
                                        </marquee>           
                                    </div>                                    
                                </div>

                            </a>
                        </h4>
                    </div>
                </div>
            </c:if>

            <!-- End Navigation Section -->             
            <decorator:body />

            <div class="row">
                <div class="col-md-12">
                    <div class="copyright-section">
                        <div class="row">
                            <div class="col-md-7 col-sm-6">
                                <div class="copy"><strong>Event Management<br></strong></div>

                            </div>                           
                        </div>
                    </div>
                </div>
            </div>


        </div>

        <!-- End Copyright Section -->
        <a href="#" class="back-to-top"><i class="fa fa-angle-up"></i></a>
    </body>

</html>

