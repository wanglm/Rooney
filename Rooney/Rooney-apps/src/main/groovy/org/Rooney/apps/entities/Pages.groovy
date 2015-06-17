package org.Rooney.apps.entities

/**分页类
 * @author ming
 *
 */
class Pages {
	private String lastPage // 上一页
	private String nextPage // 下一页
	private String currentPage // 当前页
	private String allPage // 总页数
	private List<Page> list

	class Page{
		private String pageNum // 页码

	}
}
