package com.order.service;

import com.order.exception.ReadFileServiceException;
import com.order.service.impl.ReadFileServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReadFileServiceImplTest {

    ReadFileServiceImpl service = null;

    @Before
    public void setUp() {
        service = new ReadFileServiceImpl();
    }

    @Test(expected = ReadFileServiceException.class)
    public void readFile_NullPathGiven_ShouldThrowException() {

        String filePath = null;

        service.readFile(filePath);
    }

    @Test(expected = ReadFileServiceException.class)
    public void readFile_EmptyPathGiven_ShouldThrowException() {

        String filePath = "";

        service.readFile(filePath);
    }

    @Test(expected = ReadFileServiceException.class)
    public void readFile_InvalidPathGiven_ShouldThrowException() {

        String filePath = "Invalid Path for test. Dont worry.";

        service.readFile(filePath);
    }
}
