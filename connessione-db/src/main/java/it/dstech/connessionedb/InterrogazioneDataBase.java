package it.dstech.connessionedb;
/*
 *Creare un programma Java che si colleghi allo schema WORLD e
 * che permetta le seguenti operazioni:
1. Trovare tutte le città di un dato "countrycode"
2. Data una nazione, trovare la città più popolosa
3. Data una forma di governo tra quelle presenti
 nel database (suggerimento: select distinct....), indicare lo stato con estensione territoriale più grande.

 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InterrogazioneDataBase {
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner input=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver"); // in questo punto carichia nella JVM in esecuzione la nostra libreria 
		String password ="gigi"; // la vostra password
		String username = "root"; // la vostra username
		String url = "jdbc:mysql://localhost:3306/world?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false";
		Connection connessione = DriverManager.getConnection(url, username, password);
		Statement statement = connessione.createStatement();
		
		while (true) {
			System.out.println("Cosa vuoi fare");
			menu();
			int scelta = input.nextInt();
			input.nextLine();
			switch (scelta) {
			case 1: {
				System.out.println("Inserisci la nazione");
				String countrycode=input.nextLine();
				ResultSet risultatoQuery = statement.executeQuery("select * from city where CountryCode ='"+countrycode+"';");
				while(risultatoQuery.next()) {
					int id = risultatoQuery.getInt(1);
					String nome = risultatoQuery.getString("Name");
					int pop = risultatoQuery.getInt(5);
					String codiceStato=risultatoQuery.getString("CountryCode");
					System.out.println(id + " " + nome + " - " + pop +" - "+codiceStato);
				}

			}
				break;
			case 2: {
				System.out.println("Inserisci la sigla della nazione");
				String countrycode=input.nextLine();
				ResultSet risultatoQuery = statement.executeQuery("select * from city where CountryCode ='"+countrycode+"'order by Population desc limit 1;");
				while(risultatoQuery.next()) {
					String nome = risultatoQuery.getString("Name");
					int pop = risultatoQuery.getInt(5);
					String codiceStato=risultatoQuery.getString("CountryCode");
					System.out.println("Citta " + nome + "  Abitanti " + pop +"  Nazione "+codiceStato);
				}
							 
			}
				break;
			case 3:{
				System.out.println("Lista delle forme di governo");
				ResultSet risultatoQuery = statement.executeQuery("select distinct(GovernmentForm) from country order by GovernmentForm asc;");
				while(risultatoQuery.next()) {
					String formaGoverno = risultatoQuery.getString("GovernmentForm");					
					System.out.println("Forma di governo " + formaGoverno);
					
				}
				System.out.println(" ");
				System.out.println("Scegli una forma di governo e digitela");
				String fG=input.nextLine();
				ResultSet risultatoQuery2 = statement.executeQuery("select name, SurfaceArea, GovernmentForm from country where GovernmentForm ='"+fG+"' order by SurfaceArea desc limit 1;");
				while(risultatoQuery2.next()) {
					String stato = risultatoQuery2.getString("name");	
					Double superficie=risultatoQuery2.getDouble("SurfaceArea");
					String formaGoverno=risultatoQuery2.getString("GovernmentForm");
					System.out.println("Stato " + stato+ " Superficie "+superficie+" Forma_Governo "+formaGoverno);
					System.out.println(" ");
				}
				break;
			}
			case 4:{
			
				break;
			}
			default: {
				System.exit(0);
			}
			}
		}
		
		
		
		
	}

	private static void menu() {
		System.out.println("1. Trovare tutte le città di un dato countrycode");
		System.out.println("2. Data una nazione, trovare la città più popolosa");
		System.out.println("3. Data una forma di governo tra quelle presenti indicare lo stato con estensione territoriale più grande ");
		
	}
		

}
