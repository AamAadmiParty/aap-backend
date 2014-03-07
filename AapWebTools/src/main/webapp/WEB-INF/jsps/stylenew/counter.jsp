<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" type="text/javascript"></script>
<script src="https://remote.aamaadmiparty.org/js/jquery.flipCounter.1.2.pack.js" type="text/javascript"></script>
<div id="counter">
	<input type="hidden" name="counter-value" value="100" />
</div>
<script type="text/javascript">
	$(document).ready(function($) {
		$("#counter").flipCounter('startAnimation', {
			number: 0,
			end_number: '${totalDonation}',
			duration:1000,
			imagePath:'https://remote.aamaadmiparty.org/img/flipCounter-medium.png'
			});
	});
</script>