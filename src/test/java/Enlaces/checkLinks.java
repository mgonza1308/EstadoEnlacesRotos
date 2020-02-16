package Enlaces;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class checkLinks {

	private WebDriver driver;
	
	public checkLinks(WebDriver driver) {
		this.driver = driver;
	};
	
	public boolean checkPageLinks() {
		//Buscar los elementos que tienen enlaces en la pagina y asignarlo a arreglo
		List<WebElement> links 	= driver.findElements(By.tagName("a"));
		String url 				="";
		List<String> linkRoto 	= new ArrayList<String>();
		List<String> linkOk 	= new ArrayList<String>();
		
		HttpURLConnection httpConnection = null;
		int responseCode = 200;
		Iterator<WebElement> it = links.iterator();
		
		
		while (it.hasNext()) {
			//se guarda la url
			url = it.next().getAttribute("href");
			if (url ==null || url.isEmpty()) {
				System.out.println(url + "URL Vacia");
				continue;
			}
			try {
				//Establecer la coneccion:
				httpConnection = (HttpURLConnection)(new java.net.URL(url).openConnection());
				//solo recibe cabecera de la URL
				httpConnection.setRequestMethod("HEAD");
				httpConnection.connect();
				//entrega status del Response
				responseCode = httpConnection.getResponseCode();
				
				//Se llena el arreglo de enlace Ok y roto.
				if (responseCode > 400) {
					System.out.println("Link con error_---->>"+url);
					linkRoto.add(url);
				} else {
					System.out.println("Link Ok---->>"+url);
					linkOk.add(url);
				}
			} catch (Exception e) {
				// TODO: handle exceptionew
				e.printStackTrace();
		}
		System.out.println("Link OK ---->>" + linkOk.size());
		System.out.println("Link OK ---->>" + linkRoto.size());
		
		if (linkRoto.size()>0) {
			System.out.println("******Link Rotos******");
			for (int i = 0; i < linkRoto.size(); i++) {
				System.out.println(linkRoto.get(i));
				return false;
			}
		} else {
			System.out.println("******NO HAY Link Rotos******");
			return true;
		}
	}
		return true;
}
}	
