<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="huake" uri="/huake"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	// G2 对数据源格式的要求，仅仅是 JSON 数组，数组的每个元素是一个标准 JSON 对象。
	// Step 1: 创建 Chart 对象
	$.getJSON(${data}, function(data) {
		var Stat = G2.Stat;
		var chart = new G2.Chart({
			id : 'c1',
			forceFit : true,
			height : 450,
			plotCfg : {
				margin : [ 20, 60, 80, 120 ]
			}
		});
		var Frame = G2.Frame;
		var frame = new Frame(data);
		frame = Frame.sort(frame, 'release');
		chart.setMode('select'); // 开启框选模式
		chart.select('rangeX'); // 设置 X 轴范围的框选
		chart.source(frame, {
			'..count' : {
				alias : 'top2000 唱片总量'
			},
			release : {
				tickInterval : 5,
				alias : '唱片发行年份'
			}
		});
		chart.interval().position(Stat.summary.count('release')).color(
				'#e50000');
		chart.render();
		// 监听双击事件，这里用于复原图表
		chart.on('plotdblclick', function(ev) {
			chart.get('options').filters = {}; // 清空 filters
			chart.repaint();
		});
	});
</script>
