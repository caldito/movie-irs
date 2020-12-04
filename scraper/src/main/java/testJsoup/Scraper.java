package testJsoup;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.*;

import com.opencsv.CSVReader;

public class Scraper {
	private static List<Film>films = new ArrayList<Film>();

	public static void main(String[] args) throws IOException {
		readCSV();
		
		String url;
		for(Film film : films) {
			url =film.getLink();
			extractDate(film);
			//getSummary(url, film);
			//getSummaryItem(url, film);
		}
		listToJson("../films.json");

	}

	private static void readCSV() throws IOException{
		CSVReader reader = new CSVReader(new FileReader("../data.csv"));
        List<String[]> lines = reader.readAll();
        lines.remove(0); 
        
        for(String[] d : lines) {
        	int id = Integer.parseInt(d[0]);
        	String link = d[1];
        	String title = d[2];
			double score;
			if (d[3] == null || d[3].equals("")){
				score = 0;
			}else{
				score = Double.parseDouble(d[3]);
			}
        	String[]genres = d[4].split("\\|");
        	String poster = d[5];
        	
        	films.add(new Film(id,link,title,score, genres, poster));
        }
        reader.close();
	}
	
	private static void extractDate(Film film) {
		try{
			String str = film.getTitle();
			int year = Integer.parseInt(str.substring(str.indexOf("(")+1,str.indexOf(")")));
			String newTitle = str.substring(0,str.indexOf("(")-1);
			
			film.setTitle(newTitle);
			film.setYear(year);
		}catch( Exception exception){
			//if fails doesn't go to list
		}
	}
	
	/**
	 * Este método recupera todos los href de una página web y los muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getHRef(String url, Film film) throws IOException{
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
	public static void getImgs(String url, Film film) throws IOException{
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
	public static void getH4(String url, Film film) throws IOException{
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
	public static void getSummary(String url, Film film) throws IOException{
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
	public static void getSummaryItem(String url, Film film) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("Items from:\t" + doc.title());
		//obtenemos el div credit_summary_item
		Elements lst = doc.select("div.credit_summary_item");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.text());
		}
		System.out.println();
	}

	public static void listToJson(String output){
		Gson gson = new Gson();
		String json = gson.toJson(films);
		try {
			PrintWriter out = new PrintWriter(output);
			out.println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
