package com.saalamsaifi.auto.roster.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;

@Configuration
public class MongoConfig {
	private static final String MONGO_DB_URL = "localhost";
	private static final String MONGO_DB_NAME = "embeded_db";
	private static final int MONGO_DB_PORT = 27017;

	@SuppressWarnings("deprecation")
	@Bean
	public MongoTemplate mongoTemplate() throws IOException {
		MongoClient mongoClient = new EmbeddedMongoBuilder().bindIp(MONGO_DB_URL).port(MONGO_DB_PORT).build();

		MongoDbFactory dbFactory = new MongoTemplate(mongoClient, MONGO_DB_NAME).getMongoDbFactory();

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(dbFactory),
				new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(dbFactory, converter);

		return mongoTemplate;
	}
}
