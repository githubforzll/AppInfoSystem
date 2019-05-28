$(function(){  
	$("#back").on("click",function(){
		window.location.href = "list";
	});

	$("#versionNo").bind("blur",function(){
		//后台验证--versionNo是否非空
		var versionNo=$("#versionNo").val();
		if(versionNo==null || versionNo==""){
			$(this).next().html("版本号不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
	
	$("#versionSize").bind("blur",function(){
		//后台验证--versionSize是否非空
		var versionSize=$("#versionSize").val();
		if(versionSize==null || versionSize==""){
			$(this).next().html("版本大小不能为空!");
		}else if(versionSize<0){
			$(this).next().html("版本大小不能小于0!");
			$(this).focus().val(1);
		}else{
			$(this).next().html("");
		}
	});
	
	$("#versionInfo").bind("blur",function(){
		//后台验证--versionInfo是否非空
		var versionInfo=$("#versionInfo").val();
		if(versionInfo==null || versionInfo==""){
			$(this).next().html("版本简介不能为空!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});

	$("#a_downloadLink").bind("blur",function(){
		//后台验证--a_downloadLink是否非空
		var a_downloadLink=$("#a_downloadLink").val();
		if(a_downloadLink==null || a_downloadLink==""){
			$(this).next().html("apk文件不能为空,请选择一个上传图片!!");
			$(this).focus();
		}else{
			$(this).next().html("");
		}
	});
});
      
      
      