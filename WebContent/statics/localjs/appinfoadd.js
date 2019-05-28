$(function(){  
	//动态加载所属平台列表
	$.ajax({
		type:"GET",//请求类型
		url:"datadictionarylist.json",//请求的url
		data:{tcode:"APP_FLATFORM"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			$("#flatformId").html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].valueId+"\">"+data[i].valueName+"</option>";
			}
			$("#flatformId").html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载平台列表失败！");
		}
	});  
	//动态加载一级分类列表
	$.ajax({
		type:"GET",//请求类型
		url:"categorylevellist.json",//请求的url
		data:{pid:null},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			$("#categoryLevel1").html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
			}
			$("#categoryLevel1").html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载一级分类列表失败！");
		}
	});  
	//动态加载二级分类列表
	$("#categoryLevel1").change(function(){
		var categoryLevel1 = $("#categoryLevel1").val();
		if(categoryLevel1 != '' && categoryLevel1 != null){
			$.ajax({
				type:"GET",//请求类型
				url:"categorylevellist.json",//请求的url
				data:{pid:categoryLevel1},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					$("#categoryLevel2").html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					$("#categoryLevel2").html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载二级分类失败！");
				}
			});
		}else{
			$("#categoryLevel2").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel2").html(options);
		}
		$("#categoryLevel3").html("");
		var options = "<option value=\"\">--请选择--</option>";
		$("#categoryLevel3").html(options);
	});
	//动态加载三级分类列表
	$("#categoryLevel2").change(function(){
		var categoryLevel2 = $("#categoryLevel2").val();
		if(categoryLevel2 != '' && categoryLevel2 != null){
			$.ajax({
				type:"GET",//请求类型
				url:"categorylevellist.json",//请求的url
				data:{pid:categoryLevel2},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					$("#categoryLevel3").html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					$("#categoryLevel3").html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载三级分类失败！");
				}
			});
		}else{
			$("#categoryLevel3").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel3").html(options);
		}
	});
	
	$("#back").on("click",function(){
		window.location.href = "list";
	});
	
	$("#APKName").bind("blur",function(){
		//ajax后台验证--APKName是否已存在
		$.ajax({
			type:"GET",//请求类型
			url:"apkexist.json",//请求的url
			data:{APKName:$("#APKName").val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.APKName == "empty"){//参数APKName为空，错误提示
					$(this).next().html("APKName为不能为空!");
					$(this).focus();
				}else if(data.APKName == "exist"){//账号不可用，错误提示
					$(this).next().html("该APKName已存在，不能使用！");
				}else if(data.APKName == "noexist"){//账号可用，正确提示
					$(this).next().html("该APKName可以使用！");
				}
			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				alert("请求错误！");
			}
		});
	});
	
	$("#softwareName").bind("blur",function(){
		//后台验证--softwareName是否非空
		var softwareName=$("#softwareName").val();
		if(softwareName==null || softwareName==""){
			$(this).next().html("软件名称不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
	
	$("#supportROM").bind("blur",function(){
		//后台验证--supportROM是否非空
		var supportROM=$("#supportROM").val();
		if(supportROM==null || supportROM==""){
			$(this).next().html("支持ROM不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
	
	$("#interfaceLanguage").bind("blur",function(){
		//后台验证--interfaceLanguage是否非空
		var interfaceLanguage=$("#interfaceLanguage").val();
		if(interfaceLanguage==null || interfaceLanguage==""){
			$(this).next().html("界面语言不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
	
	$("#softwareSize").bind("blur",function(){
		//后台验证--softwareSize是否非空,为非数字
		var softwareSize=$("#softwareSize").val();
		if(softwareSize==null || softwareSize==""){
			$(this).next().html("软件大小不能为空!");
			$(this).focus();
		}else if(softwareSize<=0){
			$(this).next().html("软件大小不能小于0!");
			$(this).focus().val(1);
		}else{
			$(this).next().html("");
		}
	});
	
	$("#downloads").bind("blur",function(){
		//后台验证--downloads是否非空
		var downloads=$("#downloads").val();
		if(downloads==null || downloads==""){
			$(this).next().html("下载次数不能为空!");
		}else if(downloads<0){
			$(this).next().html("下载次数不能小于0!");
			$(this).focus().val(0);
		}else{
			$(this).next().html("");
		}
	});
	
	$("#flatformId").bind("blur",function(){
		//后台验证--flatformId是否非空
		var flatformId=$("#flatformId").val();
		if(flatformId==null || flatformId==""){
			$(this).next().html("请选择一个所属平台项!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#categoryLevel1").bind("blur",function(){
		//后台验证--categoryLevel1是否非空
		var categoryLevel1=$("#categoryLevel1").val();
		if(categoryLevel1==null || categoryLevel1==""){
			$(this).next().html("请选择一个一级分类项!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#categoryLevel2").bind("blur",function(){
		//后台验证--categoryLevel2是否非空
		var categoryLevel2=$("#categoryLevel2").val();
		if(categoryLevel2==null || categoryLevel2==""){
			$(this).next().html("请选择一个二级分类项!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#categoryLevel3").bind("blur",function(){
		//后台验证--categoryLevel3是否非空
		var categoryLevel3=$("#categoryLevel3").val();
		if(categoryLevel3==null || categoryLevel3==""){
			$(this).next().html("请选择一个三级分类项!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#appInfo").bind("blur",function(){
		//后台验证--appInfo是否非空
		var appInfo=$("#appInfo").val();
		if(appInfo==null || appInfo==""){
			$(this).next().html("应用简介不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#a_logoPicPath").bind("blur",function(){
		//后台验证--a_logoPicPath是否非空
		var a_logoPicPath=$("#a_logoPicPath").val();
		if(a_logoPicPath==null || a_logoPicPath==""){
			$(this).next().html("LOGO图片不能为空,请选择一个上传图片!!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
	
	
});
       