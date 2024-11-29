package org.example.DB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.DB.DBConection.sendPerson;

public class DBConectionTest {
    @Test
    public void dataBaseTest() {
        String localAnswer = "кирилл\n99\nкрасивого\nплейбой, филантроп гей";
        System.out.println(sendPerson(767567988));
        String infoDB = sendPerson(767567988);
        Assertions.assertEquals(infoDB, localAnswer);
    }
}
