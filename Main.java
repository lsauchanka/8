//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello, World!");
//    }
//}

//BazaSQLite4 - Program korzystający ze sterownika JDBC: sqlite-jdbc-3.5.9.jar

import java.io.*;
import java.sql.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            //Rozwiązanie problemu polskich znaków w konsoli systemu
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"Cp852"), true);

            //Załadowanie sterownika
            Class.forName("org.sqlite.JDBC");
            pw.println("Załadowano sterownik.");

            //Nawiązanie połączenia z bazą danych SQLite znajdującą się w pliku filmy.db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:filmy.db");
            pw.println("Utworzono połączenie z bazą.");

            //Utworzenie obiektu pozwalającego wysyłać polecenia do bazy danych
            Statement statement = connection.createStatement();
            pw.println("Utworzono obiekt do wysyłania poleceń do bazy.");

            //Utworzenie tabeli "Wykonawca"
            int resultInt1 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Wykonawca (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
            pw.println("Wysłano polecenie do bazy -> Utworzono tabelę Wykonawca.");

            //Wypełnienie danymi tabeli "Wykonawca" w bazie danych
            resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (1, 'Maryla Rodowicz', 1945)");
            resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (2, 'Zbigniew Wodecki', 1950)");
            resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (3, 'Michał Bajor', 1957)");
            resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (4, 'Edyta Górniak', 1972)");
            pw.println("Wysłano polecenie do bazy -> Wypełniono danymi tabelę Wykonawca.");

            //Utworzenie tabeli "Kompozytor"
            int resultInt2 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Kompozytor (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
            pw.println("Wysłano polecenie do bazy -> Utworzono tabelę Kompozytor.");

            //Wypełnienie danymi tabeli "Kompozytor" w bazie danych
            resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (1, 'Andrzej Korzyński', 1940)");
            resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (2, 'Włodzimierz Korcz', 1943)");
            resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (3, 'Stanisław Syrewicz', 1946)");
            resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (4, 'Wojciech Trzciński', 1949)");
            pw.println("Wysłano polecenie do bazy -> Wypełniono danymi tabelę Kompozytor.");

            //Utworzenie tabeli "Piosenka"
            int resultInt3 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Piosenka (Id INT NOT NULL PRIMARY KEY, Tytuł VARCHAR(50) NOT NULL UNIQUE, Rok_premiery INT DEFAULT '0', Wykonawca VARCHAR(50) NOT NULL UNIQUE, Kompozytor VARCHAR(50) NOT NULL UNIQUE)");
            pw.println("Wysłano polecenie do bazy -> Utworzono tabelę Piosenka.");

            //Wypełnienie danymi tabeli "Piosenka" w bazie danych
            resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (1, 'Pieśń ciszy', 1976, 'Zbigniew Wodecki', 'Wojciech Trzciński')");
            resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (2, 'Gimnastyka', 1983, 'Maryla Rodowicz', 'Andrzej Korzyński')");
            resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (3, 'To nie ja', 1994, 'Edyta Górniak', 'Stanisław Syrewicz')");
            resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (4, 'Ogrzej mnie', 1999, 'Michał Bajor', 'Włodzimierz Korcz')");
            pw.println("Wysłano polecenie do bazy -> Wypełniono danymi tabelę Piosenka.");

            //Odczytanie danych z tabeli "Wykonawca" w kolejności rosnącej
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Wykonawca ORDER BY Nazwisko ASC");
            pw.println();
            pw.println("Informacja o wykonawcach umieszczonych w bazie:");
            while(resultSet1.next())
            {
                String name = resultSet1.getString("Nazwisko");
                int year = resultSet1.getInt("Rok_urodzenia");
                pw.print(' ' + name + " urodził/-ła się w ");
                pw.print(year);
                pw.println(" roku.");
            }

            //Odczytanie danych z tabeli "Kompozytor" w kolejności rosnącej
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Kompozytor ORDER BY Nazwisko ASC");
            pw.println();
            pw.println("Informacja o kompozytorach umieszczonych w bazie:");
            while(resultSet2.next())
            {
                String name = resultSet2.getString("Nazwisko");
                int year = resultSet2.getInt("Rok_urodzenia");
                pw.print(' ' + name + " urodził się w ");
                pw.print(year);
                pw.println(" roku.");
            }

            //Odczytanie danych z tabeli "Piosenka" w kolejności rosnącej
            ResultSet resultSet3 = statement.executeQuery("SELECT * FROM Piosenka ORDER BY Tytuł ASC");
            pw.println();
            pw.println("Informacja o piosenkach umieszczonych w bazie:");
            while(resultSet3.next())
            {
                String name = resultSet3.getString("Tytuł");
                int year = resultSet3.getInt("Rok_premiery");
                pw.print(' ' + name + " została wydana w ");
                pw.print(year);
                pw.println(" roku.");
            }

            //Zamknięcie połączenia z bazą
            connection.close();
            pw.println("Zamknięto połączenie z bazą.");
        }
        catch(UnsupportedEncodingException uee)
        {
            System.err.print("UnsupportedEncoding Exception: ");
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