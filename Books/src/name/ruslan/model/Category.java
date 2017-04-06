package name.ruslan.model;

public class Category {
	
	private Long id;
	private String categoryDescription;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	@Override
	public String toString() {
		return "Category - id=" + id + ", categoryDescription=" + categoryDescription;
	}

}
