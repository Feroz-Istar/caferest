package apppojo;

import java.util.Date;

public class Product {
	private Integer id;
	private String image_url;
	private Date created_at;
	private Integer category_id;
	private String name;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Product(String image_url, Date created_at, Integer category_id, String name) {
		super();
		this.image_url = image_url;
		this.created_at = created_at;
		this.category_id = category_id;
		this.name = name;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
