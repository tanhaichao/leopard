package io.leopard.web.taglib.tags;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 文件上传
 * 
 * @author 阿海
 * 
 */
public class UploadTag extends AbstractStartTagSupport {

	private static final long serialVersionUID = 1L;

	protected static String createRandomInputId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	@Override
	protected String getContent() {
		String fileId = createRandomInputId();
		StringBuilder sb = new StringBuilder();
		sb.append("<script type='text/javascript'> ");
		sb.append("function upload").append(fileId).append("() {");

		sb.append("$.ajaxFileUpload({");
		sb.append("url :'/leopard/upload.do',");
		sb.append("secureuri :false,");
		sb.append("fileElementId :'" + fileId + "',");
		sb.append("dataType : 'text',");
		sb.append("success : function (data, status){");
		sb.append("var data2=data.replace(/\\<[^\\<\\>]*\\>/,'');");
		sb.append("var data3=data2.replace(/\\<[^\\<\\>]*\\>/,'');");
		sb.append("eval('var data4='+data3);");
		sb.append(this.callback + "(data3);");
		sb.append("},");
		sb.append("error : function(data, status, e) {");
		sb.append("alert(\"上传文件出错,请重新尝试!\");");
		sb.append("}");
		sb.append("});");
		sb.append("} ");
		sb.append("</script>");

		sb.append(" <input type='file' name='picFile'   onchange='upload" + fileId + "();' id='" + fileId + "' ");
		if (StringUtils.isNotEmpty(className)) {
			sb.append(" class='" + className + "' ");
		}
		sb.append(" />");
		// sb.append(" <input class='" + fileId +
		// "OtherInput' type='hidden' name='type'  value='" + type + "' />");
		// for (String weightAndHeight : formatsEntry) {
		// 增加缩略图
		if (StringUtils.isNotEmpty(compress)) {
			String[] picsSize = compress.split(",");
			for (String picSize : picsSize) {
				sb.append(" <input class='" + fileId + "OtherInput' type='hidden' name='weightAndHeight'  value='" + picSize + "' />");
			}
		}

		// }
		return sb.toString();
	}

	// private String getBody2() {
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("<script type='text/javascript'> ");
	// sb.append("function upload").append(fileId).append("() {");
	// sb.append(" var fileObj = document.getElementById(\"").append(fileId).append("\"); ");
	// // sb.append(" var FileController = '/leopard/upload.do';  ");
	// sb.append(" var FileController = '/resource/upload.do';  ");
	// sb.append("var form = new FormData(); ");
	// sb.append("form[\"enctype\"]=\"multipart/form-data\"; ");
	// sb.append(" form.append('picFile', fileObj);");
	// sb.append(" var xhr = new XMLHttpRequest();");
	// sb.append(" xhr.open('POST', FileController, true); ");
	// sb.append(" xhr.setRequestHeader('Content-type', 'multipart/form-data;boundary=----WebKitFormBoundaryBfyhAgdJzc42Ah9D' ); ");
	// if (StringUtils.isNotEmpty(callbackFunction)) {
	// sb.append(" xhr.onload = function(){" + callbackFunction +
	// "(xhr.responseText);};");
	// }
	// sb.append(" xhr.send(form); ");
	// sb.append("} ");
	// sb.append("</script>");
	// sb.append(" <input type='file' name='picFile' onclick='' onchange='upload"
	// + fileId + "();' id='" + fileId + "' class='" + className + "' />");
	// sb.append(" <input class='" + fileId +
	// "OtherInput' type='hidden' name='type'  value='" + type + "' />");
	// for (String weightAndHeight : formatsEntry) {
	// sb.append(" <input class='" + fileId +
	// "OtherInput' type='hidden' name='weightAndHeight'  value='" +
	// weightAndHeight + "' />");
	// }
	//
	// // <input type="file" name="picUrlFile" onchange="upload()" formId="id"
	// />
	// // <input type="hidden" name="picUrl" />
	// return sb.toString();
	// }

	// ======================

	// private String type;
	private String className;
	// private String fileId;
	// private String[] formatsEntry;
	private String callback = "callback";

	// 压缩图,支持多个,用,号分隔
	private String compress;

	public void setClassName(String className) {
		this.className = className;
	}

	// public void setFileId(String fileId) {
	// this.fileId = fileId;
	// }

	public void setType(String type) {
		// this.type = type;
	}

	// public void setFormats(String formats) {
	// if (StringUtils.isBlank(formats)) {
	// return;
	// }
	// String[] weightAndHeights = formats.split(",");
	// formatsEntry = new String[weightAndHeights.length];
	// for (int i = 0; i < weightAndHeights.length; i++) {
	// String weightAndHeightStr = weightAndHeights[i];
	// formatsEntry[i] = weightAndHeightStr;
	// }
	// }

	public void setCompress(String compress) {
		this.compress = compress;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
