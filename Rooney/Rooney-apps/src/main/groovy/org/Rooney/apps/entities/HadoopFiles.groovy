package org.Rooney.apps.entities

/**hadoop处理的文件
 * @author ming
 *
 */
class HadoopFiles extends BaseEntity{
	private String fileName
	private String type
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
