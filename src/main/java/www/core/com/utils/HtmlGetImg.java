package www.core.com.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.markdown4j.Markdown4jProcessor;

import www.core.com.pojo.TopicGetImage;

public class HtmlGetImg {

	public static TopicGetImage content(String content  ) throws IOException {
		
		Markdown4jProcessor markdown = new Markdown4jProcessor();
		String htmlContent = markdown.process(content);
		List<String> list = new ArrayList();
		TopicGetImage topic = new TopicGetImage();
		Document doc = Jsoup.parse(htmlContent);
		Elements elements = doc.getElementsByTag("img");
		String hrefImage = null;
		for (Element link : elements) {
			hrefImage = link.attr("src");
			if (hrefImage.indexOf("/temp/upload") >= 0) {
				hrefImage= hrefImage.substring(hrefImage.indexOf("/temp/upload"));
				list.add(hrefImage);
				link.attr("src", hrefImage.replace("/temp", "/topic"));
			}
		}
		htmlContent = doc.getElementsByTag("body").html().toString();
		topic.setImages(list);
		return topic;
	}

	public static String getAllText(String content) {
		Document doc = Jsoup.parse(content);
		return doc.getElementsByTag("body").text().toString();
	}

	public static String convertcontent(String content, String qiniu) {
		TopicGetImage topic = new TopicGetImage();
		Document doc = Jsoup.parse(content);
		Elements elements = doc.getElementsByTag("img");
		for (Element link : elements) {
			if (link.attr("src").indexOf("qiniuurl") >= 0) {
				continue;
			}
			if (link.attr("src").indexOf("http://") >= 0) {
				continue;
			}
			link.attr("src", qiniu + link.attr("src"));

		}
		content = doc.getElementsByTag("body").html();
		return content;
	}

	public static void main(String[] args) {

		// String content = "<img src=\"/temp/20161604/image/20161604_50.jpg\"
		// alt=\"\" />";
		// HtmlGetImg.content(content,
		// "http://7xtex6.com2.z0.glb.clouddn.com/");
		// System.out.println(content);
		System.out.println("http://localhost/temp/topic/20161604/image/20161604_50.jpg".substring("http://localhost/temp/topic/20161604/image/20161604_50.jpg".indexOf("/temp/topic")));
	}
}
