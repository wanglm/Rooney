package org.Rooney.apps.spring.dao

import java.util.List;

import org.Rooney.apps.entities.Options;
import org.Rooney.apps.entities.PyScript;

interface LogDao {
	/**将用户id存入数据库
	 * @param userIds
	 * @return
	 */
	int saveUser(List<String> userIds)
	/**从数据库里读出所有用户id
	 * @return
	 */
	List<Options> findUser()
	/**将物品id存入数据库
	 * @param userIds
	 * @return
	 */
	int saveItem(List<String> itemIds)
	/**从数据库里读出所有物品id
	 * @return
	 */
	List<Options> findItem()
	
	/**根据id查询文件名
	 * @param id
	 * @return
	 */
	String findName(String id)
	
	long countForQuery(String fileName,String type,String sd, String ed)
	
	List<PyScript> find(String fileName,String type,String sd, String ed,
		String start,String length,String orderColumn,String orderDir)

}
