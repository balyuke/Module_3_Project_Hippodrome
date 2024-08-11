package com.javarush.baliuk.hippodrome;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    private final String name = "Horse";
    private final double speed = 10.0;

    @Test
    public void constructor_horsesNullException(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void constructor_horsesNullExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void constructor_horsesEmptyException(){
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    public void constructor_horsesEmptyExceptionMessage(){
        List<Horse> horses = new ArrayList<>();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses_MethodReturnCorrectList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(name + i, speed, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

//        assertNotNull(hippodrome.getHorses());
//        assertEquals(30, hippodrome.getHorses().size());
//        for (int i = 0; i < 30; i++) {
//            assertEquals(name + i, hippodrome.getHorses().get(i).getName());
//        }

        assertAll(
                () -> assertNotNull(hippodrome.getHorses()),
                () -> assertEquals(30, hippodrome.getHorses().size()),
                () -> {
                    for (int i = 0; i < 30; i++) {
                        assertEquals(name + i, hippodrome.getHorses().get(i).getName());
                    }
                }
        );

    }

    @Test
    public void move_MethodMoveCallsForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse: horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinner_MethodReturnHorseWithLargestParamDistance() {
//        List<Horse> horses = new ArrayList<>();
//        horses.add(new Horse("Horse1", 10., 1));
//        horses.add(new Horse("Horse2", 10., 100));
//        horses.add(new Horse("Horse3", 10., 3));
//        horses.add(new Horse("Horse4", 10., 4));
//        horses.add(new Horse("Horse5", 10., 5));
//        Hippodrome hippodrome = new Hippodrome(horses);

        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse(name+"1", speed, 1),
                new Horse(name+"2", speed, 100),
                new Horse(name+"3", speed, 3)
        ));

        assertEquals(name+"2", hippodrome.getWinner().getName());
    }
}
