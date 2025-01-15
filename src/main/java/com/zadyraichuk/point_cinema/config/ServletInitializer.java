package com.zadyraichuk.point_cinema.config;

import com.zadyraichuk.point_cinema.PointCinemaApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PointCinemaApplication.class);
	}

}
