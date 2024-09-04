package com.jp.project.MovieManagement;

import com.jp.project.MovieManagement.persistence.repository.MovieCrudRepository;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class MovieManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieManagementApplication.class, args);
	}

}
