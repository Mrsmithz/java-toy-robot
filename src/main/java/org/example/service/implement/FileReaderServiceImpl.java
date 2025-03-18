package org.example.service.implement;

import org.example.service.FileReaderService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }
}
