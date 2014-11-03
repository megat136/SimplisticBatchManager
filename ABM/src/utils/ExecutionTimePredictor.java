package utils;

import it.sauronsoftware.cron4j.Predictor;

public class ExecutionTimePredictor {

	public static String predictNext(String s){
		Predictor predictor=new Predictor(s);
		return predictor.nextMatchingDate().toString();
		
	}
}
