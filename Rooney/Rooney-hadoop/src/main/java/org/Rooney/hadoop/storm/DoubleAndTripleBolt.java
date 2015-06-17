package org.Rooney.hadoop.storm;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DoubleAndTripleBolt extends BaseRichBolt {
	private static final long serialVersionUID = 4978907685545630055L;
	private OutputCollector _collector;

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		arg2 = _collector;
	}

	@Override
	public void execute(Tuple arg0) {
		int val = arg0.getInteger(0);
		_collector.emit(arg0, new Values(val * 2, val * 3));
		_collector.ack(arg0);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("double", "triple"));

	}

	public static void main(String... args){
		TopologyBuilder builder = new TopologyBuilder();        
		builder.setSpout("words", new TestWordSpout(), 10);        
		builder.setBolt("exclaim1", new ExclamationBolt(), 3)
		        .shuffleGrouping("words");
		builder.setBolt("exclaim2", new ExclamationBolt(), 2)
		        .shuffleGrouping("exclaim1");
		Config conf = new Config();
		try {
			StormSubmitter.submitTopologyWithProgressBar("", conf, builder.createTopology());
		} catch (AlreadyAliveException | InvalidTopologyException e) {
			e.printStackTrace();
		}
	}
}
