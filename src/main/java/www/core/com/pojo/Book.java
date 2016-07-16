
package www.core.com.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="sysBook")
public class Book extends BaseCode {

	@Column(columnDefinition = "char(20)")
	private String name;



	@Type(type="text")  
	private String  content;
	@Column(columnDefinition = "char(20)")
	private String author;
	@Column
	private float price =0;
	@Column
	private String description;
	
	
	@Column
	private String image;
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage() {
		return image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@OneToOne
	@JoinColumn(name = "labelId")
	private Label label;
	
	@Column(nullable=false,columnDefinition="INT default 0")	
	private Integer share;
	
	
	public Integer getShare() {
		return share;
	}
	public void setShare(Integer share) {
		this.share = share;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
}

