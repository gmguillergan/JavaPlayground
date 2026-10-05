
package Lesson2_3;

/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      PolymorphismExample.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       October 5, 2026
 Description: Example Code
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class PolymorphismExample {
    public static void main(String[] args) {
        Animal myAnimal;
        
        myAnimal = new Dog();
        myAnimal.sound();
        
        myAnimal = new Cat();
        myAnimal.sound();
    }
}
