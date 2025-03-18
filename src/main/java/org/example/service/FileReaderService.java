package org.example.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public interface FileReaderService {

    BufferedReader getBufferedReader(String fileName) throws FileNotFoundException;
}
