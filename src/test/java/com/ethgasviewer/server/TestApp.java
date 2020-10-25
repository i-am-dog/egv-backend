package com.ethgasviewer.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ethgasviewer.server.Application.GRAB_PROFILE;
import static com.ethgasviewer.server.Application.WEB3_PROFILE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles({
    WEB3_PROFILE
    ,
    GRAB_PROFILE
})
public class TestApp {

    @Test
    public void start() {
        while (true) {}
    }
}
