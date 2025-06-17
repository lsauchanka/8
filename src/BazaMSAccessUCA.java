//BazaMSAccessUCA - Program korzystaj¹cy ze sterownika ucanaccess-4.0.4.jar

import java.io.*;
import java.sql.*;

public class BazaMSAccessUCA
{
 public static void main(String[] args)
 {
  try
  {
   //Rozwi¹zanie problemu polskich znaków w konsoli systemu
   PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"Cp852"), true);

   //Za³adowanie sterownika
   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
   pw.println("Za³adowano sterownik.");

   //Nawi¹zanie po³¹czenia z baz¹ danych "Filmy.mdb" znajduj¹c¹ siê w bie¿acym katalogu.
   //Plik pustej bazy danych w formacie MS Access 2000 zostanie stworzony w trakcie dzia³ania programu.
   Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + System.getProperty("user.dir") + "\\db.mdb;newdatabaseversion=V2000");
   pw.println("Utworzono po³¹czenie z baz¹.");

   //Utworzenie obiektu pozwalaj¹cego wysy³aæ polecenia do bazy danych
   Statement statement = connection.createStatement();
   pw.println("Utworzono obiekt do wysy³ania poleceñ do bazy.");

   //Utworzenie tabeli "Filmy"
   int resultInt = statement.executeUpdate("CREATE TABLE Filmy (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Tytul VARCHAR(50) CONSTRAINT c_Tytul UNIQUE, [Rok produkcji] INT, Rezyser VARCHAR(50) CONSTRAINT c_Rezyser, Aktorka VARCHAR(50) CONSTRAINT c_Aktorka)");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Filmy.");

   //Wype³nienie danymi tabeli "Filmy" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (1, 'Zaklêta w soko³a', 1985, 'Richard Donner', 'Michelle Pfeiffer')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (2, 'Zaklête serca', 2005, 'Petter Naess', 'Radha-Louise Mitchell')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (3, 'Co kryje prawda', 2000, 'Robert Zemeckis', 'Michelle Pfeiffer')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (4, 'Wladca Pierscieni', 2003, 'Peter Jackson', 'Miranda Otto')");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Filmy.");


   //Utworzenie tabeli "Aktorka"
   resultInt = statement.executeUpdate("CREATE TABLE Aktorka (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Nazwisko VARCHAR(50) CONSTRAINT c_Nazwisko UNIQUE, [Rok urodzenia] INT)");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Aktorka.");

   //Wype³nienie danymi tabeli "Aktorka" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (1, 'Radha Mitchell', 1973)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (2, 'Lysette Anthony', 1963)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (3, 'Amy Adams', 1974)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (4, 'Michelle Pfeiffer', 1958)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (5, 'Miranda Otto', 1967)");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Aktorka.");


   //Utworzenie tabeli "Rezyser"
   resultInt = statement.executeUpdate("CREATE TABLE Rezyser (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Nazwisko VARCHAR(50) CONSTRAINT c_Nazwisko UNIQUE, [Rok urodzenia] INT)");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Rezyser.");

   //Wype³nienie danymi tabeli "Rezyser" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (1, 'Richard Donner', 1930)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (2, 'Petter Naess', 1960)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (3, 'Robert Zameckis', 1952)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (4, 'Peter Jackson', 1961)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (5, 'Andrzej Wajda', 1926)");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Rezyser.");


   //Odczytanie danych z tabeli "Aktorka" w kolejnoœci rosn¹cej
   ResultSet resultSet = statement.executeQuery("SELECT * FROM Aktorka ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o aktorkach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Nazwisko");
    int year = resultSet.getInt("Rok urodzenia");
    pw.print(' ' + name + " urodzi³a siê w ");
    pw.print(year);
    pw.println(" roku.");
   }

   //Odczytanie danych z tabeli "Rezyser" w kolejnoœci rosn¹cej
   resultSet = statement.executeQuery("SELECT * FROM Rezyser ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o Re¿yserach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Nazwisko");
    int year = resultSet.getInt("Rok urodzenia");
    pw.print(' ' + name + " urodzi³ siê w ");
    pw.print(year);
    pw.println(" roku.");
   }

   //Odczytanie danych z tabeli "Filmy" w kolejnoœci rosn¹cej
   resultSet = statement.executeQuery("SELECT * FROM Filmy ORDER BY Id ASC");
   pw.println();
   pw.println("Informacja o Filmach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Tytul");
    String director = resultSet.getString("Rezyser");
    pw.print(' ' + name + " wyre¿yserowany przez ");
    pw.print(director);
    pw.println(".");
   }

   //Zamkniêcie po³¹czenia z baz¹
   connection.close();
   pw.println("Zamkniêto po³¹czenie z baz¹.");
  }
  catch(UnsupportedEncodingException uee)
  {
   System.err.print("UnsupportedEncodingException: ");
   System.err.println(uee.getMessage());
  }
  catch(ClassNotFoundException cnfe)
  {
   System.err.print("ClassNotFound Exception: ");
   System.err.println(cnfe.getMessage());
  }
  catch(SQLException sqle)
  {
   System.err.print("SQL Exception: ");
   System.err.println(sqle.getMessage());
  }
  catch(Exception e)
  {
   System.out.print("Exception: ");
   System.out.println(e.getMessage());
  }
 }
}