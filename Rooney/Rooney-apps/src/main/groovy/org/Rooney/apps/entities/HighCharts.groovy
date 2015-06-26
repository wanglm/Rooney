package org.Rooney.apps.entities

/**图表的展示数据类
 * @author ming
 *
 */
class HighCharts{
	private List<String> categories
	private List<Datas> series
	/**每条线的数据对象
	 * @author ming
	 *
	 */
	class Datas{
		private String name
		private List<Point> data
		private long pointStart
		private long pointInterval=3600*1000
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Point> getData() {
			return data;
		}
		public void setData(List<Point> data) {
			this.data = data;
		}
		public long getPointStart() {
			return pointStart;
		}
		public void setPointStart(long pointStart) {
			this.pointStart = pointStart;
		}
		public long getPointInterval() {
			return pointInterval;
		}
		public void setPointInterval(long pointInterval) {
			this.pointInterval = pointInterval;
		}
	}

	class Point{
		private String name
		private long x
		private int y
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getX() {
			return x;
		}
		public void setX(long x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Datas> getSeries() {
		return series;
	}

	public void setSeries(List<Datas> series) {
		this.series = series;
	}
}
