package caferest;

import com.github.javafaker.Faker;

import utils.CafeeDBUtils;

public class CreateProduct {
public static void main(String args[]) {
	Faker faker = new Faker();
	for(int i=0;i<10;i++) {
	String sql = "INSERT INTO public.product(name, image_url, created_at, category_id) VALUES('"+faker.superhero().power()+"', '"+faker.internet().avatar()+"', now(),null)";
	CafeeDBUtils.getInstance().insertIntoDB(sql);
	}
}
}
