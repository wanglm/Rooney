package org.Rooney.apps.entities

import org.Rooney.api.time.QuartzJob;

/**定时任务类
 * @author ming
 *
 */
class Jobs extends QuartzJob{
	private String jobUseClass // 执行任务类
	private String jobCronText //执行时间
	private String jobGroupCn //任务组中文名
	private String jobNameCn //任务中文名
	private int jobType  //用户类型
	private String jobDsc //备注
	private int num //序号
	private long id
	private long createId
	private long createTime
	private long updateId
	private long updateTime
	public String getJobUseClass() {
		return jobUseClass;
	}
	public void setJobUseClass(String jobUseClass) {
		this.jobUseClass = jobUseClass;
	}
	public String getJobCronText() {
		return jobCronText;
	}
	public void setJobCronText(String jobCronText) {
		this.jobCronText = jobCronText;
	}
	public String getJobGroupCn() {
		return jobGroupCn;
	}
	public void setJobGroupCn(String jobGroupCn) {
		this.jobGroupCn = jobGroupCn;
	}
	public String getJobNameCn() {
		return jobNameCn;
	}
	public void setJobNameCn(String jobNameCn) {
		this.jobNameCn = jobNameCn;
	}
	public long getJobType() {
		return jobType;
	}
	public void setJobType(long jobType) {
		this.jobType = jobType;
	}
	public String getJobDsc() {
		return jobDsc;
	}
	public void setJobDsc(String jobDsc) {
		this.jobDsc = jobDsc;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCreateId() {
		return createId;
	}
	public void setCreateId(long createId) {
		this.createId = createId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(long updateId) {
		this.updateId = updateId;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

}
