package org.example.DB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.DB.DBConection.sendPerson;

public class DBConectionTest {
    @Test
    public void dataBaseTest() {
        String localAnswer = "ID: 749240804, Name: ya3, Gender: false, Age: 23, Town: moscow, Description: hip and hop";
        String infoDB = sendPerson(23);
        Assertions.assertEquals(infoDB, localAnswer);
    }
}
