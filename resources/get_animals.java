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

public static void main(String[] args) throws IOException {


    }
public static void getAnimals() throws IOException{
		url = "https://www.madridteacher.com/Activities/Files/animals-vocabulary-list-print.htm"
		List<String> AnimalsList = new ArrayList<>();
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