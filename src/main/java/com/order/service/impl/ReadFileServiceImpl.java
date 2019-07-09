package com.order.service.impl;

import com.order.exception.ReadFileServiceException;
import com.order.service.ReadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReadFileServiceImpl implements ReadFileService {

    private Logger logger = LoggerFactory.getLogger(ReadFileServiceImpl.class);

    final String READ_FILE_INVALID_PARAM_MESSAGE_ERROR = "The filePath must not be null not empty!";

    public Set<String> readFile(final String filePath) {


        if (Objects.isNull(filePath) || filePath.trim().isEmpty()) {
            throw new ReadFileServiceException(READ_FILE_INVALID_PARAM_MESSAGE_ERROR);
        }

        return getFile(filePath).collect(Collectors.toSet());
    }

    private Stream<String> getFile(final String filePath) {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            String errorMessage = String.format("Error reading the file [%s]. Message %s", filePath, e.getLocalizedMessage());
            logger.error(errorMessage, e);
            throw new ReadFileServiceException(errorMessage);
        }
    }
}
