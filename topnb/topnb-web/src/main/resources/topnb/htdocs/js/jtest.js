var Jtest = {
	"startServer" : function(project) {
		var status = this.getState(project);
		var action;
		if (status == 'started') {
			action = "start";
		} else if (status == 'stopped') {
			action = "stop";
		}
		var url = "/manager.do?project=" + project + "&action=" + action;
		url += "&time=" + new Date().getTime();
		document.getElementById("btn_" + project).disabled = true;
		$.get(url, function(data) {
			Jtest.changeState(project, data);
			document.getElementById("btn_" + project).disabled = false;
		}, "text");
	},
	
	"getState" : function(project) {
		var btnId = "btn_" + project;
		var button = document.getElementById(btnId);
		if (button.className == 'btn btn-danger') {
			return 'stopped';
		} else if (button.className == 'btn') {
			return 'started';
		} else {
			alert("未知按钮className[" + button.className + "].");
		}
	},
	"changeState" : function(project, data) {
		if ("started" == data || "starting" == data) {
			Jtest.updateState(project, 'start');
		} else if ("stopped" == data || "stopping" == data) {
			Jtest.updateState(project, 'stop');
		} else {
			alert("未知状态[" + data + "].");
		}
	},
	"updateState" : function(project, status) {
		var btnId = "btn_" + project;
		var button = document.getElementById(btnId);
		if (status == "start") {
			button.className = "btn btn-danger";
			button.value = "停止";
		} else if (status == "stop") {
			button.className = "btn";
			button.value = "启动";
		} else {
			alert("未知状态[" + status + "].");
		}
	},
	"checkState" : function(project) {
		var url = "/manager.do?project=" + project + "&action=status";
		url += "&time=" + new Date().getTime();

		$.get(url, function(data) {
			Jtest.changeState(project, data);
		}, "text");
	},
	"end" : null
}