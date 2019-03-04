package com.onlinetutorialspoint.SpringBootExceptionHandling.exception;

import com.onlinetutorialspoint.controller.ItemController;
import com.onlinetutorialspoint.exception.ItemExceptionHandler;
import com.onlinetutorialspoint.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ItemExceptionHandlerTest {

    @InjectMocks
    ItemExceptionHandler itemExceptionHandler;
    @Mock
    ItemController itemController;

    MockMvc mockMvc;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(itemController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
    }

    @Test(expected = ItemNotFoundException.class)
    public void applicationExceptionTest() throws Exception {
        int ItemId = 0;

//        mockMvc.perform(get("/item/itemId").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
      /*  Mockito.when(itemController.getItem(0)).thenThrow(new ItemNotFoundException("error"));
        itemController.getItem(0);*/

    }
}
