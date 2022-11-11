package com.otus.spring.dao.impl;

import java.util.List;

public interface CsvRowParser {

    List<String> parse(String csvRow);

}
