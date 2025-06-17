//BazaMSAccessUCA - Program korzystaj�cy ze sterownika ucanaccess-4.0.4.jar

import java.io.*;
import java.sql.*;

public class BazaMSAccessUCA
{
 public static void main(String[] args)
 {
  try
  {
   //Rozwi�zanie problemu polskich znak�w w konsoli systemu
   PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"Cp852"), true);

   //Za�adowanie sterownika
   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
   pw.println("Za�adowano sterownik.");

   //Nawi�zanie po��czenia z baz� danych "Filmy.mdb" znajduj�c� si� w bie�acym katalogu.
   //Plik pustej bazy danych w formacie MS Access 2000 zostanie stworzony w trakcie dzia�ania programu.
   Connection connection = DriverManager.getConnection("jdbc:ucanaccess://" + System.getProperty("user.dir") + "\\db.mdb;newdatabaseversion=V2000");
   pw.println("Utworzono po��czenie z baz�.");

   //Utworzenie obiektu pozwalaj�cego wysy�a� polecenia do bazy danych
   Statement statement = connection.createStatement();
   pw.println("Utworzono obiekt do wysy�ania polece� do bazy.");

   //Utworzenie tabeli "Filmy"
   int resultInt = statement.executeUpdate("CREATE TABLE Filmy (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Tytul VARCHAR(50) CONSTRAINT c_Tytul UNIQUE, [Rok produkcji] INT, Rezyser VARCHAR(50) CONSTRAINT c_Rezyser, Aktorka VARCHAR(50) CONSTRAINT c_Aktorka)");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Filmy.");

   //Wype�nienie danymi tabeli "Filmy" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (1, 'Zakl�ta w soko�a', 1985, 'Richard Donner', 'Michelle Pfeiffer')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (2, 'Zakl�te serca', 2005, 'Petter Naess', 'Radha-Louise Mitchell')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (3, 'Co kryje prawda', 2000, 'Robert Zemeckis', 'Michelle Pfeiffer')");
   resultInt = statement.executeUpdate("INSERT INTO Filmy VALUES (4, 'Wladca Pierscieni', 2003, 'Peter Jackson', 'Miranda Otto')");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Filmy.");


   //Utworzenie tabeli "Aktorka"
   resultInt = statement.executeUpdate("CREATE TABLE Aktorka (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Nazwisko VARCHAR(50) CONSTRAINT c_Nazwisko UNIQUE, [Rok urodzenia] INT)");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Aktorka.");

   //Wype�nienie danymi tabeli "Aktorka" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (1, 'Radha Mitchell', 1973)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (2, 'Lysette Anthony', 1963)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (3, 'Amy Adams', 1974)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (4, 'Michelle Pfeiffer', 1958)");
   resultInt = statement.executeUpdate("INSERT INTO Aktorka VALUES (5, 'Miranda Otto', 1967)");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Aktorka.");


   //Utworzenie tabeli "Rezyser"
   resultInt = statement.executeUpdate("CREATE TABLE Rezyser (Id COUNTER CONSTRAINT c_Id PRIMARY KEY, Nazwisko VARCHAR(50) CONSTRAINT c_Nazwisko UNIQUE, [Rok urodzenia] INT)");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Rezyser.");

   //Wype�nienie danymi tabeli "Rezyser" w bazie danych
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (1, 'Richard Donner', 1930)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (2, 'Petter Naess', 1960)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (3, 'Robert Zameckis', 1952)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (4, 'Peter Jackson', 1961)");
   resultInt = statement.executeUpdate("INSERT INTO Rezyser VALUES (5, 'Andrzej Wajda', 1926)");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Rezyser.");


   //Odczytanie danych z tabeli "Aktorka" w kolejno�ci rosn�cej
   ResultSet resultSet = statement.executeQuery("SELECT * FROM Aktorka ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o aktorkach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Nazwisko");
    int year = resultSet.getInt("Rok urodzenia");
    pw.print(' ' + name + " urodzi�a si� w ");
    pw.print(year);
    pw.println(" roku.");
   }

   //Odczytanie danych z tabeli "Rezyser" w kolejno�ci rosn�cej
   resultSet = statement.executeQuery("SELECT * FROM Rezyser ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o Re�yserach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Nazwisko");
    int year = resultSet.getInt("Rok urodzenia");
    pw.print(' ' + name + " urodzi� si� w ");
    pw.print(year);
    pw.println(" roku.");
   }

   //Odczytanie danych z tabeli "Filmy" w kolejno�ci rosn�cej
   resultSet = statement.executeQuery("SELECT * FROM Filmy ORDER BY Id ASC");
   pw.println();
   pw.println("Informacja o Filmach umieszczonych w bazie:");
   while(resultSet.next())
   {
    String name = resultSet.getString("Tytul");
    String director = resultSet.getString("Rezyser");
    pw.print(' ' + name + " wyre�yserowany przez ");
    pw.print(director);
    pw.println(".");
   }

   //Zamkni�cie po��czenia z baz�
   connection.close();
   pw.println("Zamkni�to po��czenie z baz�.");
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