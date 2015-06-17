package org.Rooney.apps.entities

/**图表的展示数据类
 * @author ming
 *
 */
class HightCharts{
	private List<String> categories
	private List<Dates> series
	class Dates{
		private String name
		private List<Integer> data
		public List<Integer> getData() {
			return data;
		}
		public void setData(List<Integer> data) {
			this.data = data;
		}
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<Dates> getSeries() {
		return series;
	}
	public void setSeries(List<Dates> series) {
		this.series = series;
	}
}
