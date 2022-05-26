package org.knime.moodle.internals.connection;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.NotificationPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.knime.core.node.NodeLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.ubumonitor.controllers.Controller;
import es.ubu.lsi.ubumonitor.util.I18n;
import es.ubu.lsi.ubumonitor.util.UtilMethods;
import es.ubu.lsi.ubumonitor.webservice.api.tool.mobile.ToolMobileGetPublicConfig;
import es.ubu.lsi.ubumonitor.webservice.webservices.WebService;
import es.ubu.lsi.ubumonitor.controllers.load.Connection;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MoodleLogin {

	private static final String HTTP = "http://";

	private static final String HTTPS = "https://";

	private static final String HOST_LOGIN_DEFAULT_PATH = "/login/index.php";
	private static final Pattern PATTERN_TOKEN = Pattern.compile("^\\w+:::(\\w+)(:::(\\w+))?");
	private static final int DEFAULT_TYPE_OF_LOGIN = 1;

	private WebService webService;
	private int typeoflogin;
	private String username;
	private String password;
	private String host;
	
	private String moodleSession;
	private String token;

	public MoodleLogin(String host, String username, String password) {

		webService = new WebService();
		this.host = host;
		this.username = username;
		this.password = password;
	}


	public void loginWeb() throws IOException {
		webService = new WebService(host, username, password);
		String hostLogin = host + HOST_LOGIN_DEFAULT_PATH;
		
		NodeLogger logger=NodeLogger.getLogger("Moodle Integration");
		
		try (Response response = Connection.getResponse(hostLogin)) {
			String redirectedUrl = response.request()
					.url()
					.toString();
			Document loginDoc = Jsoup.parse(response.body()
					.byteStream(), null, hostLogin);
			Element e = loginDoc.selectFirst("input[name=logintoken]");
			String logintoken = (e == null) ? "" : e.attr("value");

			logger.warn("logintoken: " + logintoken);
						
			RequestBody formBody = new FormBody.Builder().add("username", username)
					.add("password", password)
					.add("logintoken", logintoken)
					.build();

			Response response2 = Connection.getResponse(new Request.Builder().url(redirectedUrl)
							.post(formBody)
							.build());
						
			// obtiene MoodleSession
			// TODO: generalizar captura de cookie con un for
			
			String cookies_str_name = Connection.getCookieManager().getCookieStore().getCookies().get(0).getName();
			String cookies_str_value = Connection.getCookieManager().getCookieStore().getCookies().get(0).getValue();
			
			moodleSession = cookies_str_value; 			
						
			logger.warn("MoodleSession: " + moodleSession );
			
			String html = response2.body().string();

			String sesskey = findSesskey(html);
			
			logger.warn("sesskey: " + sesskey);
			
			webService.setSesskey(sesskey);
		}
	}

	
	public String findSesskey(String html) {
		Pattern pattern = Pattern.compile("sesskey=(\\w+)");
		Matcher m = pattern.matcher(html);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public String checkUrlServer(String host) throws MalformedURLException {
		String url = convertToHttps(host);
		URL httpsUrl = new URL(url);
		if (checkWebsService(httpsUrl)) {
			return httpsUrl.toString();
		}

		url = url.replaceFirst(HTTPS, HTTP);
		URL httpUrl = new URL(url);
		if (checkWebsService(httpUrl)) {
			return httpUrl.toString();
		}
		throw new IllegalArgumentException(I18n.get("error.host"));

	}

	private boolean checkWebsService(URL url) {

		try (Response response = Connection.getResponse(url + "/login/token.php")) {
			JSONObject jsonObject = new JSONObject(response.body()
					.string());
			return jsonObject.has("error");

		} catch (IOException e) {
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(I18n.get("error.malformedurl"), e);
		}

		return false;
	}

	private String convertToHttps(String host) {
		String url;

		if (!host.matches("^(?i)https?://.*$")) {

			url = HTTPS + host;
		} else if (host.matches("^(?i)http://.*$")) {
			url = host.replaceFirst("(?i)http://", HTTPS);
		} else {
			url = host;
		}

		return url;
	}

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

		
	public void setHost(String host) {
		this.host = host;
	}

	public String getMoodleSession() {
		return moodleSession;
	}

	
	
}
