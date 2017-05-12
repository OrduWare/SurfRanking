package com.orduware.proyectos.crawler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	static final String URL = "http://www.worldsurfleague.com/athletes/tour/mct?year=2017";
	static final String INDEX_HTML = "src/files/worldsurfleague.html";

	public static void main(String[] args) throws IOException {

		System.out.println("Empezando a recolectar de " + URL);

		// preguntar si finceheros descargados ?
		Document doc;
		File fIndex = new File(INDEX_HTML);

		if (fIndex.exists()) {
			System.out.println("leyendo desde disco duro");
			doc = Jsoup.parse(fIndex, "UTF-8", URL);

		} else {
			System.out.println("descargando de internet " + INDEX_HTML);
			doc = Jsoup.connect(URL).get();
			saveFile(doc, null);
		}

		System.out.println("RANKING");
		System.out.println("_______________________________");

		Elements nombres = doc.select("a.athlete-name");
		Elements puntos = doc.select("span.tour-points");
		Elements pais = doc.select("span.athlete-nation-abbr");
		Elements imagenes = doc.select("a.headshot");

		Element eImg;

		for (int i = 1; i < nombres.size(); i++) {

			eImg = imagenes.get(i);

			String img = eImg.attr("data-img-src");

			System.out.println(i + " " + nombres.get(i).text() + "[" + pais.get(i).text() + "] " + puntos.get(i).text()
					+ " " + img);
		}

	}

	/**
	 * Guarda un ficchero en la raiz del proyecto <br>
	 * Si el nombre es null crea "index.hml", en caso contario crear fichero con
	 * nombre
	 * 
	 * @param doc
	 * @param nombre
	 */
	private static void saveFile(Document doc, String nombre) {
		try {
			File f = new File(INDEX_HTML);
			FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
			System.out.println("fichero salvado");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
