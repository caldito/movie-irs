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
	private static String log_file ="../logs.txt";

	public static void main(String[] args) throws IOException {

		readCSV();
		films.parallelStream().forEach((film) -> {
			try {
				extractDate(film);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				getSummary(film.getLink(), film);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				getActors(film.getLink(),film);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			listToJson("../films.json");
		});
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
				score = -1.0;
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
			try {
				PrintWriter out = new PrintWriter(new FileWriter(Scraper.log_file, true));
				film.setYear(-1);
				out.println("Error not Indexed Date: (added as -1)"+film.getLink());
				out.close();
			} catch (Exception err) {
				System.out.println(err);
			}
		}
	}

	public static void getActors(String url, Film film) throws IOException{
		url = url + "/fullcredits?ref_=tt_cl_sm#cast";
		List<String> actorsList = new ArrayList<>();
		Document doc = Jsoup.connect(url).get();
		if (doc.select("table.cast_list").size() == 0){
			return;
		}
		Element table = doc.select("table.cast_list").get(0);
		Elements rows = table.select("tr");

		for (Element row:rows) {
			Elements cols = row.select("td");
			try{
				if (cols.size() == 4){
					actorsList.add(cols.get(1).select("a").text());
				}
			} catch (Exception e){
				//Skip this line and print to logs file
				try {
					PrintWriter out = new PrintWriter(new FileWriter(Scraper.log_file, true));
					out.println("Error not Indexed Actors: "+film.getLink());
					out.close();
				} catch (Exception err) {
					System.out.println(err);
				}
			}
		}
		String[] actors = actorsList.toArray(new String[0]);
		film.setActors(actors);
	}
	
	/**
	 * Este método recupera todas las etiquetas div summary_tex de una página web y las muestra por pantalla
	 * @param url
	 * @throws IOException
	 */
	public static void getSummary(String url, Film film) throws IOException{
		try{
			Document doc = Jsoup.connect(url).get();
			//obtenemos el div summary_text

			Elements lst = doc.select("div.summary_text");
			String summary = lst.get(0).text();
			String linkFull = lst.select("a").attr("abs:href");

			//if contains the word plotsummary it means is a link to the whole summary
			if(linkFull.equals("") || !linkFull.contains("plotsummary")) {
				film.setSummary(summary);
			}else{
				Document docSum = Jsoup.connect(linkFull).get();
        	    Elements fullSummary = docSum.select("li.ipl-zebra-list__item");
				film.setSummary(fullSummary.select("p").get(0).text());
			}
		}catch(Exception e){
			try {
				PrintWriter out = new PrintWriter(new FileWriter(Scraper.log_file, true));
				out.println("Error not Indexed Summary: "+film.getLink());
				out.close();
			} catch (Exception err) {
				System.out.println(err);
			}
		}
	}

	public static void listToJson(String output){
		Gson gson = new Gson();
		String json = gson.toJson(films);
		try {
			//PrintWriter out = new PrintWriter(new FileOutputStream(output, false));
			PrintWriter out = new PrintWriter(output);
			out.println(json);
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
