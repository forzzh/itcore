package www.core.com.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import www.core.com.annotation.MethodInfo;
import www.core.com.annotation.TableInfo;

/**
 * ��ҳ�����ö��б�
 * @author yinnannan
 *
 */

@Entity
@Table(name="sysIndexBookConfig")
@TableInfo(defend = "defend", packageName = IndexBookConfig.class )
public class IndexBookConfig  extends BaseCode{

	@OneToOne
	@JoinColumn(name = "bookId")
	@MethodInfo(dataname = "book.name", label = "������", isShow = "show")
	private Book  book;
	
	@Column(columnDefinition="INT default 0")
	@MethodInfo(dataname = "sort", label = "˳λ", isShow = "show")
	private Integer sort =0;
	
	public void setBook(Book book) {
		this.book = book;
	}
	public Book getBook() {
		return book;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
		return sort;
	}

}
