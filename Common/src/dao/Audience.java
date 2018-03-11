package dao;

public class Audience extends BaseDAO {
	private String ageCategory;
	private String description;
	private String emblem; //image
	
	public String getAgeCategory() {
		return ageCategory;
	}
	
	public void setAgeCategory(String ageCategory) {
		this.ageCategory = ageCategory;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getEmblem() {
		return emblem;
	}
	
	public void setEmblem(String emblem) {
		this.emblem = emblem;
	}

	@Override
	public String toString() {
		return "Audience [ageCategory=" + ageCategory + ", description=" + description + ", emblem=" + emblem + "]";
	}
	
}
