<%@ include file="/WEB-INF/jsp/include/page_header.jspf" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="${pageContext.request.contextPath}/js/util.js" type="text/javascript"></script> 
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <script type="text/javascript">


            function send(val) {
                $('#eventId').val(val);
                $("#edit").attr("onclick", "return true");
                $('#edit').click();
            }




            function validate() {

                $('#sendModal').modal('toggle');
                validate = true;


                return validate;
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

            function validateSpecialChtr(elmnt, content) {
                //if it is character, then remove it..
                if (isNaN(content)) {
                    elmnt.value = removeSpecialChtr(content);
                    return;
                }
            }

            function validateNumber(elmnt, content) {
                if (isNaN(content)) {
                    elmnt.value = removeNonNumeric(content);
                    return;
                }
            }


            function removeSpecialChtr(strString)
            {
                return strString.replace(/[^a-z\d\s]+/gi, "");
            }

        </script>
        <style>
            .validateTips { border: 1px solid transparent; padding: 0.3em; color: red; font-weight: bold}
        </style>
    </head>

    <body>  
        <s:messages/>
        <s:errors/>
        <div class="row">
            <div class="col-md-12">
                <s:form beanclass="com.pra.stripes.LoginActionBean" >
                    <s:hidden name="event.eventId" id="eventId"/> 
                    <div class="welcome-text-2">
                        <br>
                        <div class="panel panel-default" id="maklumatCarian">
                            <!-- Toggle Heading -->
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse-1">
                                        <i class="fa fa-angle-up control-icon"></i>
                                        New Event Registration
                                    </a>
                                </h4>
                            </div>

                            <!-- Toggle Content -->
                            <div id="collapse-1" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="form-horizontal">
                                        <br>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" ><em>*</em>&nbsp;Event Name : </label>
                                            <div class="col-sm-6">
                                                <s:text name="event.eventName" class="form-control"  id="eventName" />
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label" for="textinput"><em>*</em>&nbsp;Event Date : </label> 
                                            <div class="col-sm-3">
                                                <s:text name="event.eventDate" class="form-control" id="eventDate" formatPattern="dd/MM/yyyy"/> 
                                                <script type="text/javascript">
                                                    $(function () {
                                                        $('#eventDate')
                                                                .datepicker({
                                                                    format: "dd/mm/yyyy",
                                                                    changeMonth: true,
                                                                    changeYear: true
                                                                });
                                                    });
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" ><em>*</em>&nbsp;Event Price : </label>
                                            <div class="col-sm-6">
                                                <s:text name="event.eventPrice" class="form-control uppercase" id="eventPrice" />
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="textinput"><em>*</em>&nbsp;Event Image : </label>  
                                            <div class="col-sm-6">
                                                <s:file name="document" onchange="readURL(this);" id="document"  data-error="Sila Pilih Fail!" />
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="textinput"><em>*</em>&nbsp;Event Type : </label>  
                                            <div class="col-sm-6">
                                                <s:text name="event.eventType" class="form-control uppercase" id="eventType" />
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <s:button name="send" onclick="return validate();"  class="btn btn-primary toggle-loading" id="send">Register</s:button>&nbsp; 
                                                <a href="${pageContext.request.contextPath}/main?newEvent" class="btn btn-primary">Clear</a>&nbsp;
                                                <a href="${pageContext.request.contextPath}/main" class="btn btn-primary">Exit</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <br>
                            <br>
                            <div class="form-group">
                                <div class="table-responsive">
                                    <display:table class="table table-hover table table-striped table-bordered" name="${actionBean.listOfEvent}" size="10" id="line">
                                        <display:setProperty name="basic.msg.empty_list" value="No Records " />
                                        <display:column title="No." >${line_rowNum}</display:column>
                                        <display:column title="Event Name" property="eventName"/>
                                        <display:column title="Event Price" property="eventPrice"/>
                                        <display:column title="Event Type" property="eventType"/>
                                        <display:column title="Event Date">
                                            <fmt:formatDate value="${line.eventDate}" pattern="dd/MM/yyyy"/>
                                        </display:column>
                                        <display:column title="Edit">
                                            <a href="javascript:void(0);" onclick="send('${line.eventId}');
                                                    return false"><img src="${pageContext.request.contextPath}/images/icon/edit.png" alt="delete" width="20" height="20"/>
                                            </a>
                                        </display:column>
                                        <display:column title="Delete" style="text-align:center">
                                            <a href="main?deleteEvent&eventId=${line.eventId}" onclick="return confirm('Are you sure?');">
                                                <img src="${pageContext.request.contextPath}/images/icon/gnome_edit_delete.png" alt="delete" width="20" height="20"/>
                                            </a>
                                        </display:column>
                                    </display:table>
                                </div>
                            </div>
                            <div class="modal modal-cfrm " id="sendModal">
                                <form data-toggle="validator" role="form">
                                    <div class="modal-dialog" style="border: 1px">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title">
                                                    <div>  <fmt:message key="ppra.confirm.reg3"/></div>
                                                </h4>
                                            </div>
                                            <div class="modal-body">
                                                <fmt:message key="ppra.confirm.reg3"/>
                                            </div>
                                            <div class="modal-footer">
                                                <s:submit name="submitEvent" class="btn btn-primary toggle-loading"><fmt:message key="ppra.submit"/></s:submit>&nbsp;  
                                                    <input type="button" class="btn btn-warning" data-dismiss="modal" value="No" id="cancelBtn" style="width:100px;"/>
                                                </div>
                                            </div><!-- /.modal-content -->
                                        </div>
                                    </form><!-- /.modal-dialog -->
                                </div>
                                <div class="panel panel-default" style="display:none;">                            
                                    <div style="text-align: right">
                                    <s:submit name="editMohon" class="btn btn-primary" value="Edit" style="width:100px" id="edit"/>&nbsp;
                                </div>
                            </div>
                        </s:form>
                    </div>
                </div>
            </div>
    </body>
</html>

