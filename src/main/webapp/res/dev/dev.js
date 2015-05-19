var trContent = $('.firstTr').html();
		$('.firstTr select').attr('readonly', 'readonly');
		$('.firstTr input').attr('readonly', 'readonly');
	
		$('.top-right input').attr('readonly', 'readonly');
		
		$('input[name=fromDB]').change(function() {
			var fromDB = $(this).val() == 1;
			console.log("fromDB = " + fromDB);
			if (fromDB) {
				$('.top-right input').removeAttr('readonly');				
			} else {
				$('.top-right input').attr('readonly', 'readonly');		
			}
		});
		
		function addLine(obj) {
			var tr = $(obj).parent().parent();
			var newTr = tr.html();
			tr.parent().append("<tr>" + trContent + "</tr>");
		}
		
		function deleteLine(obj) {
			var tr = $(obj).parent().parent();
			if ($(obj).parent().parent().hasClass('firstTr')) {
				return;
			}
			tr.remove();
			console.log('delete line...');
		}
		
		$('input[name=model]').keyup(function() {
			$('input[name=menuUrl]').val("admin/" + $(this).val() + "/list");
		});
		
		$('input[name=parentAuthority]').blur(function() {
			$('input[flag=a-list]').val($(this).val() + ".list");
		});

	function cleanTableInfo() {
		$('#table > tbody > tr').not('.firstTr').remove();
	}	
	function getTableInfo() {
		var table = $('input[name=table]').val();
		$.ajax({
            url: "getTableInfo?table=" + table,
            dataType: "json",
            data: {
            	dbName:$('input[name=dbName]').val(),
            	dbUsername: $('input[name=dbUsername]').val(),
            	dbPassword: $('input[name=dbPassword]').val()
            },
            success: function(data) {
            	console.log('tableInfo: ' + JSON.stringify(data));
            	var tbody = $('tr.firstTr').parent(); 
            	for (var i = 0; i < data.length; i++) {
            		if (data[i].name == 'id') {
            			continue;
            		}
            		
            		var row = data[i];
            		var newTr = $("<tr>" + trContent + "</tr>");
            		console.log(newTr.find('input[field=type]').html());
            		var typeSelect = newTr.find('input[field=type]');
            		var convertedType = convertType(row.type, row.name);
            		typeSelect.val(convertedType);
            		
            		// user
            		var userReg = /^.*\.User$/;
            		if (convertedType.match(userReg)) {
            			newTr.find('input[name=modelDisplayField]').val('username');
            		}
            		
            		// community
            		var communityReg = /^.*\.Community$/;
            		if (convertedType.match(communityReg)) {
            			newTr.find('input[name=modelDisplayField]').val('name');
            		}
            		newTr.find('input[field=dbName]').val(row.name);
            		var javaName = row.name + "";
            		if (javaName.indexOf("_id") >= 0) {
            			javaName = javaName.replace("_id", "");
            		}
            		newTr.find('input[field=name]').val(javaName);
            		newTr.find('input[field=comment]').val(row.comment);
            		newTr.find('input[field=shortComment]').val(row.comment);
            		
            		tbody.append(newTr);
            	}
            }
        });
	}
	
	function convertType(type, column) {
		if (column.toLowerCase().indexOf("money") != -1) {
			return "java.math.BigDecimal";
		}
		if (column.indexOf("_") == -1) {
			return type;
		}
		var realType = column;
		realType = realType.replace("_id", "");
		realType = realType.replace("parent_", "");
		realType = realType.substring(0, 1).toUpperCase() + realType.substring(1);
		if ("User" == realType) {
			realType = "com.daxia.core.model." + realType;
		} else {
			realType = $('#basePackage').val() + ".model." + realType;
		}
		console.log("realType = " + realType)
		
		return realType;
	}

	function generate() {
		var params = getParams();
		document.cookie = 'projectPath=' + params.projectPath + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'basePackage=' + params.basePackage + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'dbName=' + params.db + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'modelChineseName=' + params.modelChineseName + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'model=' + params.model + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'table=' + params.table + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'menuTable=' + params.menuTable + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'authorityTable=' + params.authorityTable + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'roleAuthorityTable=' + params.roleAuthorityTable + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		document.cookie = 'parentMenu=' + params.parentMenu + "; expires=Thu, 18 Dec 2020 12:00:00 GMT";
		console.log('params = ' + params);
		$.ajax({
            url: "generate?t=" + Math.random(),
            dataType: "json",
            type:"post",
            data: params,
            success: function(data) {
            	alert('成功');
            },
            error: function() {
            	alert('失败了');
            }
        });
	}
	
	function getParams() {
		var params = {};
		params.templateType = $('#templateType').val();
		// String model,
		params.model = $('input[name=model]').val();
    	// String modelChineseName,
		params.modelChineseName = $('input[name=modelChineseName]').val();
    	// String parentMenu,
		params.parentMenu = $('input[name=parentMenu]').val();
    	// String menu,
		params.menu = $('input[name=menu]').val();
    	// String menuUrl,
		params.menuUrl = $('input[name=menuUrl]').val();
    	// String parentAuthorityName,
		params.parentAuthorityName = $('input[name=parentAuthorityName]').val(); 
    	// String parentAuthorityCode,
		params.parentAuthorityCode = $('input[name=parentAuthorityCode]').val();
    	// String authorityCode,
		params.authorityCode = $('input[name=authorityCode]').val();
		
		params.projectPath = $('input[name=projectPath]').val();
		params.basePackage = $('input[name=basePackage]').val();
		params.db = $('#dbName').val();
		params.dbUsername = $('#dbUsername').val();
		params.dbPassword = $('#dbPassword').val();
		
		params.table = $('#table').val();
		params.menuTable = $('#menuTable').val();
		params.authorityTable = $('#authorityTable').val();
		params.roleAuthorityTable = $('#roleAuthorityTable').val();
		
    	// String[] types,
		params.types = new Array();
		$('input[field=type]').each(function() {
			params.types.push($(this).val());
		});
    	// String[] names,
		params.names = new Array();
		$('input[field=name]').each(function() {
			params.names.push($(this).val());
		});
		params.dbNames = new Array();
		$('input[field=dbName]').each(function() {
			params.dbNames.push($(this).val());
		});
		
    	// String[] comments,
		params.comments = new Array();
		$('input[field=comment]').each(function() {
			params.comments.push($(this).val());
		});
		params.shortComments = new Array();
		$('input[field=shortComment]').each(function() {
			params.shortComments.push($(this).val());
		});
		
    	// boolean[] asQueries,
		params.asQueries = new Array();
		$('input[name=asQuery]').each(function() {
			params.asQueries.push($(this).attr('checked') == 'checked');
		});
    	// boolean[] asLikeQueries
		params.asLikeQueries = new Array();
		$('input[name=asLikeQuery]').each(function() {
			params.asLikeQueries.push($(this).attr('checked') == 'checked');
		});
		
		// 日期格式
		params.dateformats = new Array();
		$('select[name=dateFormat]').each(function() {
			params.dateformats.push($(this).val());
		});
		
		params.modelDisplayFields = new Array();
		$('input[name=modelDisplayField]').each(function() {
			params.modelDisplayFields.push($(this).val());
		});
		
		// 页面查询类型
		params.queryTypes = new Array();
		$('select[name=queryType]').each(function() {
			params.queryTypes.push($(this).val());
		});
	
		var generateContents = "";
		$('input[name=generateContents]:checked').each(function(i) {
			generateContents += $(this).val() + ",";
		});
		params.generateContents = generateContents;
		
		return params;
	}

function switchDiv(a) {
	$div = $(a).next('div');
	isShow = $div.css('display') == 'block';
	if (isShow) {
		$div.hide();
	} else {
		$div.show();
	}
}
