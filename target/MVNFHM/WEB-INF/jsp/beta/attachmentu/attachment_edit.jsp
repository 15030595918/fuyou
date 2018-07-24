<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
        <script type="text/javascript" src="static/ace/js/jquery.js"></script>
        <!-- 上传插件 -->
        <link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
        <script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
        <!-- 上传插件 -->
        <script type="text/javascript">
            var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
        </script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="attachmentu/${msg }.do" onsubmit="return valid()" name="Form" id="Form" method="post">
                        <input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="ATTACHMENT_ID" id="ATTACHMENT_ID" value="${pd.ATTACHMENT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">备注1:</td>--%>
								<%--<td><input type="number" name="ATTACHMENT_ID" id="ATTACHMENT_ID" value="${pd.ATTACHMENT_ID}" maxlength="32" placeholder="这里输入备注1" title="备注1" style="width:98%;"/></td>--%>
							<%--</tr>--%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">附件名称:</td>
								<td><input type="text" name="ATTACHMENT_NAME" id="ATTACHMENT_NAME" value="${pd.ATTACHMENT_NAME}" maxlength="255" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">上传者:</td>--%>
								<%--<td><input type="text" name="ATTACHMENT_USERNAME" id="ATTACHMENT_USERNAME" value="${pd.ATTACHMENT_USERNAME}" maxlength="255" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>--%>
							<%--</tr>--%>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;" id="ATTACHMENT_PATHn">文件:</td>
									<td>
										<input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/>
										<input type="hidden" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH" value=""/>
									</td>
								</tr>
								<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="ATTACHMENT_DESCRIPTION" id="ATTACHMENT_DESCRIPTION" value="${pd.ATTACHMENT_DESCRIPTION}" maxlength="255" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">上传时间:</td>--%>
								<%--<td><input class="span10 date-picker" name="ATTACHMENT_DATE" id="ATTACHMENT_DATE" value="${pd.ATTACHMENT_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="备注5" title="备注5" style="width:98%;"/></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">附件大小:</td>--%>
								<%--<td><input type="text" name="ATTACHMENT_SIZE" id="ATTACHMENT_SIZE" value="${pd.ATTACHMENT_SIZE}" maxlength="255" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">附件路径:</td>--%>
								<%--<td><input type="text" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH" value="${pd.ATTACHMENT_PATH}" maxlength="255" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>--%>
							<%--</tr>--%>
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">文件编号:</td>--%>
								<%--<td><input type="number" name="UNROLLING_ID" id="UNROLLING_ID" value="${pd.UNROLLING_ID}" maxlength="32" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>--%>
							<%--</tr>--%>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">文件题名:</td>
									<td id="file">
										<select <c:if test="${not empty pd.UNROLLING_ID}">disabled</c:if> class="chosen-select form-control" name="UNROLLING_ID" id="UNROLLING_ID" data-placeholder="请选择文件" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${unList}" var="file">
												<option value="${file.UNROLLING_ID }" <c:if test="${file.UNROLLING_ID == pd.UNROLLING_ID }">selected</c:if>>${file.FILE_NAME}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
//			if($("#ATTACHMENT_ID").val()==""){
//				$("#ATTACHMENT_ID").tips({
//					side:3,
//		            msg:'请输入备注1',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#ATTACHMENT_ID").focus();
//			return false;
//			}
			if($("#ATTACHMENT_NAME").val()==""){
				$("#ATTACHMENT_NAME").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ATTACHMENT_NAME").focus();
			return false;
			}
//			if($("#ATTACHMENT_USERNAME").val()==""){
//				$("#ATTACHMENT_USERNAME").tips({
//					side:3,
//		            msg:'请输入备注3',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#ATTACHMENT_USERNAME").focus();
//			return false;
//			}
            if($("#hasTp1").val()=="no"){
                $("ATTACHMENT_PATHn").tips({
                    side:2,
                    msg:'请选择文件',
                    bg:'#AE81FF',
                    time:2
                });
                return false;
            }
			if($("#ATTACHMENT_DESCRIPTION").val()==""){
				$("#ATTACHMENT_DESCRIPTION").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ATTACHMENT_DESCRIPTION").focus();
			return false;
			}
//			if($("#ATTACHMENT_DATE").val()==""){
//				$("#ATTACHMENT_DATE").tips({
//					side:3,
//		            msg:'请输入备注5',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#ATTACHMENT_DATE").focus();
//			return false;
//			}
//			if($("#ATTACHMENT_SIZE").val()==""){
//				$("#ATTACHMENT_SIZE").tips({
//					side:3,
//		            msg:'请输入备注6',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#ATTACHMENT_SIZE").focus();
//			return false;
//			}
//			if($("#ATTACHMENT_PATH").val()==""){
//				$("#ATTACHMENT_PATH").tips({
//					side:3,
//		            msg:'请输入备注7',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#ATTACHMENT_PATH").focus();
//			return false;
//			}
			if($("#UNROLLING_ID").val()==""){
				$("#file").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UNROLLING_ID").focus();
			return false;
			}
            $('#uploadify1').uploadifyUpload();
        }
		function valid() {
			$("#UNROLLING_ID").removeAttr("disabled");
			return true;

		}
        //====================上传=================
        $(document).ready(function(){
            var str='';
            $("#uploadify1").uploadify({
                'buttonImg'	: 	"<%=basePath%>static/images/fileup.png",
                'uploader'	:	"<%=basePath%>plugins/uploadify/uploadify.swf",
                'script'    :	"<%=basePath%>plugins/uploadify/uploadFile.jsp;jsessionid="+jsessionid,
                'cancelImg' :	"<%=basePath%>plugins/uploadify/cancel.png",
                'folder'	:	"<%=basePath%>uploadFiles/uploadFile",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
                'queueId'	:	"fileQueue",
                'queueSizeLimit'	:	1,//限制上传文件的数量
                //'fileExt'	:	"*.rar,*.zip",
                //'fileDesc'	:	"RAR *.rar",//限制文件类型
                'fileExt'     : '*.*;*.*;*.*',
                'fileDesc'    : 'Please choose(.*, .*, .*)',
                'auto'		:	false,
                'multi'		:	true,//是否允许多文件上传
                'simUploadLimit':	2,//同时运行上传的进程数量
                'buttonText':	"files",
                'scriptData':	{'uploadPath':'/uploadFiles/uploadFile/'},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
                'method'	:	"GET",
                'onComplete':function(event,queueId,fileObj,response,data){
                    str = response.trim();//单个上传完毕执行
                },
                'onAllComplete' : function(event,data) {
                    //alert(str);	//全部上传完毕执行
                    $("#ATTACHMENT_PATH").val(str);
                    $("#Form").submit();
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                },
                'onSelect' : function(event, queueId, fileObj){
                    $("#hasTp1").val("ok");
                }
            });

        });
        //====================上传=================

        //清除空格
        String.prototype.trim=function(){
            return this.replace(/(^\s*)|(\s*$)/g,'');
        };

		$(function() {
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true});
				$(window)
						.off('resize.chosen')
						.on('resize.chosen', function() {
							$('.chosen-select').each(function() {
								var $this = $(this);
								$this.next().css({'width': $this.parent().width()});
							});
						}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
		});
        </script>
		


</body>
</html>