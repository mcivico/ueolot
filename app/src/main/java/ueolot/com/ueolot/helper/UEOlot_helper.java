package ueolot.com.ueolot.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UEOlot_helper {
	private static String _prefs_name = "UEO_PrefsFile";

	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public static String getLastNotificacion(Context context){
		SharedPreferences settings = context.getSharedPreferences(_prefs_name, 0);
		String lastNotificacion = settings.getString("lastNotificacionPk", "");
		return lastNotificacion;
	}

	public static void saveLastNotificacion(Context context, String pk){
		SharedPreferences settings = context.getSharedPreferences(_prefs_name, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("lastNotificacionPk", pk);
		editor.commit();
	}

	public static String formatDate(String dateStr){
		String formated = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			formated = new SimpleDateFormat("dd/MM/yyyy").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formated;
	}

	public static String formatDateTime(String dateStr){
		String formated = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			formated = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formated;
	}

	public static String getHtmlContent(String content){
		String head = "<head><style>@font-face {font-family: 'ueolot-font';src: url('file:///android_asset/fonts/FjallaOne-Regular.ttf');}body {font-family: 'ueolot-font'; !important;}* {color: #000000 ;}</style></head>";
		return "<html>" + head + "<body>" + content + "</body>";
	}

	public static void showInternetError(Activity activity){
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle("ERROR");
		alert.setMessage("S'ha produÏt un error al carregar les dades, verifica la teva connexió a Internet.");
		alert.setPositiveButton("OK", null);
		alert.show();
	}
}