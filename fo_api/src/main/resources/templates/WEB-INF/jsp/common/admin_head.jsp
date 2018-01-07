<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header" class="cfix">
	<div class="logo l">
    	<div class="bdr l"><img src="skin/images/logo.png" width="75" height="33" alt="" /></div>
    	开运大师管理中心
    </div>
    <div class="other r">
    	<div class="row1">欢迎： <a href="javascript:void(0);" class="c-red"><c:out value="${sessionScope.userName}"/></a> <a href="admin/logout.do" title="退出" class="ico-quit"></a></div>
    </div>
</div>
<!-- End:header -->