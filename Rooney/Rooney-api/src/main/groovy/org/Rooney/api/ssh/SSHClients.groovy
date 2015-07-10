package org.Rooney.api.ssh

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

class SSHClients {
	private Session session
	public SSHClients() {
	}
	public SSHClients(Session session) {
		this.session = session;
	}

	void setUp(String username, String host){
		def jsch=new JSch()
		def _session=jsch.getSession(username, host, 22)
		_session.setConfig("StrictHostKeyChecking", "no")
		_session.timeout=5_000
		_session.setPassword('$RFV5tgb')
		this.session=_session
	}

	List<String> runCommondBySSH(String commond){
		def list=new ArrayList<String>()
		try{
			session.connect()
			def channel=(ChannelExec)session.openChannel("exec")
			channel.with { e->
				e.setInputStream(null)
				e.setErrStream(System.err)
				e.command=commond
				e.inputStream.withReader{ br->
					e.connect()
					br.eachLine{ line->
						list.add(line)
					}
				}
				e.disconnect()
			}
			session.disconnect()
		}catch(Exception e){
			e.printStackTrace()
		}
		return list
	}
}
