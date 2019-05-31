package caferest;

import java.util.ArrayList;
import java.util.HashMap;

import utils.CafeeDBUtils;

public class QueryLearner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql = "select * from cafeuser where email like '%shamim%'";
		
		ArrayList<HashMap<String, String>> results = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), sql);
		for (HashMap<String, String> hashMap : results) {
			System.out.println(hashMap.get("email"));
		}
		
	}

}
