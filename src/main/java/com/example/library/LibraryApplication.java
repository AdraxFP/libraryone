package com.example.library;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.GutendexService;
import com.example.library.service.StatisticsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

	private final GutendexService gutendexService;
	private final BookService bookService;
	private final AuthorService authorService;
	private final StatisticsService statisticsService;

	public LibraryApplication(GutendexService gutendexService, BookService bookService, AuthorService authorService, StatisticsService statisticsService) {
		this.gutendexService = gutendexService;
		this.bookService = bookService;
		this.authorService = authorService;
		this.statisticsService = statisticsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		gutendexService.fetchAndSaveBooks();
		displayMenu();
	}

	private void displayMenu() {
		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {
			System.out.println("----- Menú de Biblioteca -----");
			System.out.println("1. Mostrar todos los libros");
			System.out.println("2. Agregar un nuevo libro");
			System.out.println("3. Listar todos los autores");
			System.out.println("4. Listar autores vivos en un año determinado");
			System.out.println("5. Mostrar estadísticas de libros por idioma");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");

			try {
				choice = scanner.nextInt();
				scanner.nextLine();  // Consume la nueva línea

				switch (choice) {
					case 1:
						showAllBooks();
						break;
					case 2:
						addNewBook(scanner);
						break;
					case 3:
						showAllAuthors();
						break;
					case 4:
						showLivingAuthorsInYear(scanner);
						break;
					case 5:
						showBookStatisticsByLanguage(scanner);
						break;
					case 0:
						System.out.println("Saliendo del programa...");
						break;
					default:
						System.out.println("Opción no válida. Por favor, intente de nuevo.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				scanner.next();  // Limpiar la entrada no válida
			}
		}

		scanner.close();
	}

	private void showAllBooks() {
		List<Book> books = bookService.getAllBooks();
		if (books.isEmpty()) {
			System.out.println("No hay libros disponibles.");
		} else {
			for (Book book : books) {
				System.out.println(book);
			}
		}
	}

	private void addNewBook(Scanner scanner) {
		try {
			System.out.print("Ingrese el título del libro: ");
			String title = scanner.nextLine();

			System.out.print("Ingrese el nombre del autor: ");
			String authorName = scanner.nextLine();

			System.out.print("Ingrese el año de nacimiento del autor: ");
			int birthYear = Integer.parseInt(scanner.nextLine());

			System.out.print("Ingrese el año de fallecimiento del autor (o presione Enter si aún vive): ");
			String deathYearStr = scanner.nextLine();
			Integer deathYear = deathYearStr.isEmpty() ? null : Integer.parseInt(deathYearStr);

			System.out.print("Ingrese el idioma del libro: ");
			String language = scanner.nextLine();

			System.out.print("Ingrese el enlace de descarga del libro: ");
			String downloadLink = scanner.nextLine();

			Author author = new Author();
			author.setName(authorName);
			author.setBirthYear(birthYear);
			author.setDeathYear(deathYear);

			Book book = new Book();
			book.setTitle(title);
			book.setAuthor(author);
			book.setLanguage(language);
			book.setDownloadLink(downloadLink);

			bookService.saveBook(book);
			System.out.println("Libro agregado exitosamente.");
		} catch (NumberFormatException e) {
			System.out.println("Error en el formato de los datos numéricos. Por favor, intente de nuevo.");
		} catch (Exception e) {
			System.out.println("Error al agregar el libro: " + e.getMessage());
		}
	}

	private void showAllAuthors() {
		List<Author> authors = authorService.getAllAuthors();
		if (authors.isEmpty()) {
			System.out.println("No hay autores disponibles.");
		} else {
			for (Author author : authors) {
				System.out.println(author);
			}
		}
	}

	private void showLivingAuthorsInYear(Scanner scanner) {
		try {
			System.out.print("Ingrese el año para buscar autores vivos: ");
			int year = Integer.parseInt(scanner.nextLine());

			List<Author> authors = authorService.getLivingAuthorsInYear(year);
			if (authors.isEmpty()) {
				System.out.println("No hay autores vivos en el año " + year + ".");
			} else {
				for (Author author : authors) {
					System.out.println(author);
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Error en el formato del año. Por favor, ingrese un año válido.");
		}
	}

	private void showBookStatisticsByLanguage(Scanner scanner) {
		System.out.print("Ingrese el idioma para obtener estadísticas (por ejemplo, 'en' para inglés, 'es' para español): ");
		String language = scanner.nextLine();

		long count = statisticsService.getBookCountByLanguage(language);
		System.out.println("Cantidad de libros en " + language + ": " + count);
	}
}
