package com.fedemarkoo.AdaLovelaceIA.service;

import com.fedemarkoo.AdaLovelaceIA.dao.DiccionarioDao;
import com.fedemarkoo.AdaLovelaceIA.utils.DiccionarioCache;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

@Service
public class DictionaryService {
	@Value("${app_id}")
	private String app_id;
	@Value("${app_key}")
	private String app_key;

	@Autowired
	private DiccionarioDao dicDao;

	public DiccionarioCache getDiccionario(String word) {
		DiccionarioCache diccionarioCache = dicDao.findById(word);
		if (diccionarioCache != null) return diccionarioCache;

		return getFromWebOrRetry(word, 5);
	}

	@SneakyThrows
	private DiccionarioCache getFromWebOrRetry(String word, int retry) {
		try {
			return getFromWeb(word);
		} catch (Exception e) {
			if (retry < 0)
				throw e;
			return getFromWebOrRetry(word, retry - 1);
		}
	}

	private String inflections(String word) {
		final String language = "es";
		final String word_id = word.toLowerCase();
		return "https://od-api.oxforddictionaries.com:443/api/v2/lemmas/" + language + "/" + word_id;
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

		DiccionarioCache diccionarioCache = getDiccionarioCache(word, stringBuilder);

		dicDao.save(diccionarioCache);
		return diccionarioCache;
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

}