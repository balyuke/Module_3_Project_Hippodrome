package com.javarush.baliuk.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    private final String name = "Horse";
    private final double distance = 10.0;
    private final double negativeDistance = -10.0;
    private final double speed = 5.0;
    private final double negativeSpeed = -5.0;
    private final String exceptionMessageWhenNameIsNull = "Name cannot be null.";
    private final String exceptionMessageWhenNameIsBlank = "Name cannot be blank.";
    private final String exceptionMessageWhenSpeedIsNegative = "Speed cannot be negative.";
    private final String exceptionMessageWhenDistanceIsNegative = "Distance cannot be negative.";


    @Test
    public void constructor_nameNullException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed, distance));
    }

    @Test
    public void constructor_nameNullExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed, distance));
        assertEquals(exceptionMessageWhenNameIsNull, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "  ", "\n\n", "\t\t"})
    void constructor_nameBlankException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "  ", "\n\n", "\t\t"})
    void constructor_nameBlankExceptionMessage(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(exceptionMessageWhenNameIsBlank, exception.getMessage());
    }

    @Test
    public void constructor_speedNegativeException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, negativeSpeed, distance));
    }

    @Test
    public void constructor_speedNegativeExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, negativeSpeed, distance));
        assertEquals(exceptionMessageWhenSpeedIsNegative, exception.getMessage());
    }

    @Test
    public void constructor_distanceNegativeException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, negativeDistance));
    }

    @Test
    public void constructor_distanceNegativeExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, negativeDistance));
        assertEquals(exceptionMessageWhenDistanceIsNegative, exception.getMessage());
    }

    @Test
    public void getName_MethodReturnCorrectName()  {
        Horse horse = new Horse(name, speed);

        assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeed_MethodReturnCorrectSpeed()  {
        Horse horse = new Horse(name, speed);
        //double actual = horse.getSpeed();

        //assertEquals(speed, actual);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistance_MethodReturnCorrectDistance() {
        Horse horse1 = new Horse(name, speed, distance);
        Horse horse2 = new Horse(name, speed);

        assertAll(
                () -> assertEquals(distance, horse1.getDistance()),
                () -> assertEquals(0, horse2.getDistance())
        );
    }

    @Test
    public void move_MethodCallsGetRandomDoubleParam() {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse(name, speed, distance);
            horse.move();

            mockedStaticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 25, 150})
    public void move_MethodSetDistance(double fakeValue) {
        double min = 0.2;
        double max = 0.9;
        double speed = 2.5;
        double distance = 25;
        double expectedDistance = distance + speed * fakeValue;

        Horse horse = new Horse(name, speed, distance);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeValue);
            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());

    }

    @Test
    void checkIfMethodMoveCallsGetRandomDoubleMethod() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse(name, speed);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.6, 0.7})
    void checkIfMethodMoveCalculateCorrectDistance(double fakeValue) {
        double min = 0.2;
        double max = 0.9;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeValue);
            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());
    }
}
