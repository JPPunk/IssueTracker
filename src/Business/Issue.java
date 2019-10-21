package Business;

public class Issue {

	private String description;
	private String title;
	private Integer creator;
	private Integer resolver;
	private Integer verifier;
	private int id = 0;
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Integer getResolver() {
		return resolver;
	}
	public void setResolver(Integer resolver) {
		this.resolver = resolver;
	}
	public Integer getVerifier() {
		return verifier;
	}
	public void setVerifier(Integer verifier) {
		this.verifier = verifier;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString()
	{
		return getTitle();
	}
}
