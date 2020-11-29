package testJsoup;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static final String URL_WIKIE = "https://www.imdb.com/title/tt0111161/";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		getTitle(URL_WIKIE);
		//getHRef(URL_WIKIE);
		//getImgs(URL_WIKIE);
		//getH4(URL_WIKIE);
		getSummary(URL_WIKIE);
		getSummaryItem(URL_WIKIE);
	}
	
	/**
	 * Este método recupera el título de cualquier página web
	 * @param url
	 * @throws IOException
	 */
	public static void getTitle(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println(url + ":\t" + doc.title());
		System.out.println();
	}
	
	/**
	 * Este método recupera todos los href de una página web y los muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getHRef(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("a href from:\t" + doc.title());
		Elements lst = doc.select("a[href]");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.text());
		}
		System.out.println();
	}
	
	/**
	 * Este método recupera todas las imagenes de una página web y las muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getImgs(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("Imgs from:\t" + doc.title());
		Elements lst = doc.select("img[src~=[\\w\\d\\W]logo[\\w\\d\\W]]");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.attr("src"));
		}
		System.out.println();
	}
	
	/**
	 * Este método recupera todas las etiquetas h4 de una página web y las muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getH4(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("H4 from:\t" + doc.title());
		//obtenemos todas las etiquetas h4
		Elements lst = doc.select("h4");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.childNode(0).toString());
		}
		System.out.println();
	}
	
	/**
	 * Este método recupera todas las etiquetas div summary_tex de una página web y las muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getSummary(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("Summary from:\t" + doc.title());
		//obtenemos el div summary_text
		Elements lst = doc.select("div.summary_text");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.text());
		}
		System.out.println();
	}
	
	
	/**
	 * Este método recupera todas las etiquetas div credit_summary_item de una página web y las muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getSummaryItem(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("Items from:\t" + doc.title());
		//obtenemos el div credit_summary_item
		Elements lst = doc.select("div.credit_summary_item");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.text());
		}
		System.out.println();
	}
}
