package org.Rooney.apps.spring.dao

import java.util.List;

import org.Rooney.apps.entities.Options;

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
	
	String findName(String id)

}
