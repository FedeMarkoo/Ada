package com.fedemarkoo.AdaLovelaceIA.service;

import com.fedemarkoo.AdaLovelaceIA.dao.DiccionarioDao;
import com.fedemarkoo.AdaLovelaceIA.exceptions.DiccionarioNotFoundException;
import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class DictionaryService {
	@Value("${app_id}")
	private String app_id;
	@Value("${app_key}")
	private String app_key;

	@Autowired
	private DiccionarioDao dicDao;

	@SneakyThrows
	private DiccionarioCache getDiccionario(String word, boolean throwOnNotFound) {
		try {
			DiccionarioCache diccionarioCache = dicDao.findById(word.toLowerCase());
			if (diccionarioCache != null) return diccionarioCache;
			return getFromWebOrRetry(word, 5);
		} catch (Exception e) {
			DiccionarioCache dic = new DiccionarioCache(word);
			dicDao.save(dic);
			if (throwOnNotFound)
				throw new DiccionarioNotFoundException(word);
			else
				return dic;
		}

	}

	private String inflections(String word) {
		final String language = "es";
		final String word_id = word.toLowerCase();
		return "https://od-api.oxforddictionaries.com:443/api/v2/lemmas/" + language + "/" + word_id;
	}

	@SneakyThrows
	private DiccionarioCache getFromWebOrRetry(String word, int retry) {
		try {
			System.out.println("Buscando " + word + " en el diccionario web");
			DiccionarioCache fromWeb = getFromWeb(word);
			dicDao.save(fromWeb);
			return fromWeb;
		} catch (Exception e) {
			if (retry < 0)
				throw e;
			return getFromWebOrRetry(word, retry - 1);
		}
	}

	private DiccionarioCache getFromWeb(String word) throws Exception {
		HttpsURLConnection urlConnection = getHttpsURLConnection(word);

		// read the output from the server
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();

		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line + "\n");
		}

		return getDiccionarioCache(word, stringBuilder);
	}

	private DiccionarioCache getDiccionarioCache(String word, StringBuilder stringBuilder) throws JSONException {
		JSONObject jsonObj = new JSONObject(stringBuilder.toString());
		JSONObject jsonObject = jsonObj.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0);
		String text = jsonObject.getJSONArray("inflectionOf").getJSONObject(0).getString("text");
		String type = jsonObject.getJSONObject("lexicalCategory").getString("text").toLowerCase();
		return new DiccionarioCache(word, text, type);
	}

	private HttpsURLConnection getHttpsURLConnection(String word) throws IOException {
		URL url = new URL(inflections(word));
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setRequestProperty("app_id", app_id);
		urlConnection.setRequestProperty("app_key", app_key);
		return urlConnection;
	}

	public DiccionarioCache getDiccionarioNoThrows(String word) {
		return getDiccionario(word, false);
	}

	public DiccionarioCache getDiccionario(String word) {
		return getDiccionario(word, true);
	}
}