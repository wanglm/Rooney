package org.Rooney.hadoop.storm;

import org.apache.thrift7.TException;

import backtype.storm.generated.DRPCExecutionException;
import backtype.storm.utils.DRPCClient;

public class StormClients {
	
	public void execute(){
		DRPCClient client=new DRPCClient("127.0.0.1", 8080);
		try {
			client.execute("", "");
		} catch (TException | DRPCExecutionException e) {
			e.printStackTrace();
		}
	}

}
