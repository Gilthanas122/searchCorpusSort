import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {

        File folder = new File("/home/pityu/Desktop/findstringrada/src/textfiles");
        File[] listOfFiles = folder.listFiles();
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a search term: ");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String searchTerm = reader.nextLine();
        String directoryName = searchTerm + timestamp;
        File destinationDir = new File("searchResults/" + directoryName);
        Files.createDirectory(destinationDir.toPath());

        copyFilesNewDirectory(listOfFiles, destinationDir, searchTerm, folder);

    }
    private static void copyFilesNewDirectory(File[] listOfFiles, File destinationDir, String searchTerm, File folder) throws IOException {
        for (File file : listOfFiles) {
            if (!file.isDirectory()) {
                List<String> readAllLines = Files.readAllLines(file.toPath());
                for (String lines : readAllLines) {
                    if (lines.contains(searchTerm)) {
                        System.out.println(file.getName());
                        File destFile = new File(destinationDir + "/" + file.getName());
                        copyFileToLocation(file, destFile);
                        break;
                    }
                }
            } else {
                folder = new File(folder.getPath() + "/" + file.getName());
                listOfFiles = folder.listFiles();
                copyFilesNewDirectory(listOfFiles, destinationDir, searchTerm, folder);
            }
        }
    }

    private static boolean checkDirectoryName(File theDir) {
        return theDir.exists();
    }

    private static void copyFileToLocation(File file, File destination) throws IOException {
        Path origin = Paths.get(file.getPath());
        Path destinationPath = Paths.get(destination.getPath());
        Files.copy(origin, destinationPath);
    }
}
