import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Facade {

    static ObjectMapper mapper = new ObjectMapper();

    // https://www.baeldung.com/java-9-http-client

    public static void allBooks(MyModel dataModel) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/books"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        dataModel.deleteRows();

        List<BookDto> books = mapper.readValue(response.body(), new TypeReference<>(){});
        books.forEach(
                b -> {
                    Object[] row = {
                            String.valueOf(b.getId()),
                            b.getTitle(),
                            b.getPages()
                    };
                    dataModel.insertRow(row);
                }
        );
    }

    public static void newBook() throws URISyntaxException, IOException, InterruptedException {
        BookDto newBook = new BookDto("New book from Swing", 250);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/books"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(newBook)) )
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
