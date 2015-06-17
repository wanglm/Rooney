package org.Rooney.apps.entities

/**表格ajax查询数据
 * @author ming
 *
 * @param <T>
 */
class TableDatas<T> {
	private int draw //绘制次数
	private int recordsTotal //数据总数
	private int recordsFiltered 	//查询过滤后的数据
	private List<T> data //展示的数据
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

}
