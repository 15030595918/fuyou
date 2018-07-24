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
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="volume/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="VOLUME_ID" id="VOLUME_ID" value="${pd.VOLUME_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<%--<tr>--%>
								<%--<td style="width:75px;text-align: right;padding-top: 13px;">备注1:</td>--%>
								<%--<td><input type="number" name="VOLUME_ID" id="VOLUME_ID" value="${pd.VOLUME_ID}" maxlength="32" placeholder="这里输入备注1" title="备注1" style="width:98%;"/></td>--%>
							<%--</tr>--%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">全宗号:</td>
								<td><input type="text" name="GENERAL_ARCHIVE" id="GENERAL_ARCHIVE" value="${pd.GENERAL_ARCHIVE}" maxlength="3" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">目录号:</td>
								<td><input type="text" name="CATALOG_NUMBER" id="CATALOG_NUMBER" value="${pd.CATALOG_NUMBER}" maxlength="3" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">档号:</td>
								<td><input type="text" name="VOLUME_NUM" id="VOLUME_NUM" value="${pd.VOLUME_NUM}" maxlength="12" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">题名:</td>
								<td><input type="text" name="VOLUME_NAME" id="VOLUME_NAME" value="${pd.VOLUME_NAME}" maxlength="128" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td><input class="span10 date-picker" name="VOLUME_START_TIME" id="VOLUME_START_TIME" value="${pd.VOLUME_START_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td><input class="span10 date-picker" name="VOLUME_END_TIME" id="VOLUME_END_TIME" value="${pd.VOLUME_END_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">归档年度:</td>
								<td><input type="number" name="VOLUME_YEAR" id="VOLUME_YEAR" value="${pd.VOLUME_YEAR}" maxlength="4" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">页数:</td>
								<td><input type="number" name="VOLUME_PAGES" id="VOLUME_PAGES" value="${pd.VOLUME_PAGES}" maxlength="32" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">保管期限:</td>
								<td><input type="text" name="STORAGE_TIME" id="STORAGE_TIME" value="${pd.STORAGE_TIME}" maxlength="5" placeholder="这里输入备注10" title="备注10" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">密级:</td>
								<td><input type="text" name="SECRET_LEVEL" id="SECRET_LEVEL" value="${pd.SECRET_LEVEL}" maxlength="10" placeholder="这里输入备注11" title="备注11" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">保管单位名称:</td>
								<c:if test="${empty pd.VOLUME_ID}">
								<td><input readonly type="text" name="COMPANY_NAME" id="COMPANY_NAME" value="北京市海淀区档案馆" maxlength="32" placeholder="这里输入备注12" title="备注12" style="width:98%;"/></td>
								</c:if>
								<c:if test="${not empty pd.VOLUME_ID}">
								<td><input  type="text" name="COMPANY_NAME" id="COMPANY_NAME" value="${pd.COMPANY_NAME}" maxlength="32" placeholder="这里输入备注12" title="备注12" style="width:98%;"/></td>
								</c:if>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="DESCRIPTION" id="DESCRIPTION" value="${pd.DESCRIPTION}" maxlength="128" placeholder="这里输入备注13" title="备注13" style="width:98%;"/></td>
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
//			if($("#VOLUME_ID").val()==""){
//				$("#VOLUME_ID").tips({
//					side:3,
//		            msg:'请输入备注1',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#VOLUME_ID").focus();
//			return false;
//			}
			if($("#GENERAL_ARCHIVE").val()==""){
				$("#GENERAL_ARCHIVE").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GENERAL_ARCHIVE").focus();
			return false;
			}
			if($("#CATALOG_NUMBER").val()==""){
				$("#CATALOG_NUMBER").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CATALOG_NUMBER").focus();
			return false;
			}
			if($("#VOLUME_NUM").val()==""){
				$("#VOLUME_NUM").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_NUM").focus();
			return false;
			}
			if($("#VOLUME_NAME").val()==""){
				$("#VOLUME_NAME").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_NAME").focus();
			return false;
			}
			if($("#VOLUME_START_TIME").val()==""){
				$("#VOLUME_START_TIME").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_START_TIME").focus();
			return false;
			}
			if($("#VOLUME_END_TIME").val()==""){
				$("#VOLUME_END_TIME").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_END_TIME").focus();
			return false;
			}

			if($("#VOLUME_END_TIME").val() < $("#VOLUME_START_TIME").val()){
				$("#VOLUME_END_TIME").tips({
					side: 3,
					msg: '时间输入有误',
					bg: '#AE81FF',
					time: 2
				});
				$("#VOLUME_END_TIME").focus();
				return false;
			}


			if($("#VOLUME_YEAR").val()==""){
				$("#VOLUME_YEAR").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_YEAR").focus();
			return false;
			}
			if($("#VOLUME_PAGES").val()==""){
				$("#VOLUME_PAGES").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VOLUME_PAGES").focus();
			return false;
			}
			if($("#STORAGE_TIME").val()==""){
				$("#STORAGE_TIME").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STORAGE_TIME").focus();
			return false;
			}
			if($("#SECRET_LEVEL").val()==""){
				$("#SECRET_LEVEL").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SECRET_LEVEL").focus();
			return false;
			}
//			if($("#COMPANY_NAME").val()==""){
//				$("#COMPANY_NAME").tips({
//					side:3,
//		            msg:'请输入备注12',
//		            bg:'#AE81FF',
//		            time:2
//		        });
//				$("#COMPANY_NAME").focus();
//			return false;
//			}
			if($("#DESCRIPTION").val()==""){
				$("#DESCRIPTION").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DESCRIPTION").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>