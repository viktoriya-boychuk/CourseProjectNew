package dao;

public class Program extends BaseDAO {
	private String category;
	private String genre;
	private Integer duration;
	private String country;
	private String authorOrProducer;
	private String description;
	//ownIdea
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAuthorOrProducer() {
		return authorOrProducer;
	}
	
	public void setAuthorOrProducer(String authorOrProducer) {
		this.authorOrProducer = authorOrProducer;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Program [category=" + category + ", genre=" + genre + ", duration=" + duration + ", country=" + country
				+ ", authorOrProducer=" + authorOrProducer + ", description=" + description + "]";
	}
	
}
