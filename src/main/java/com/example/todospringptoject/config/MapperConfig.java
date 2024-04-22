package com.example.todospringptoject.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // Указываем пакет для сканирования мапперов
@ComponentScan(basePackages = "com.example.todospringptoject.mapper")
public class MapperConfig {
}
