import java.util.Random;
import java.util.Scanner;

// Base Pet Class (Parent)
abstract class Pet {
    protected String name;
    protected int hunger;
    protected int happiness;
    protected int energy;

    public Pet(String name) {
        this.name = name;
        this.hunger = 5;
        this.happiness = 5;
        this.energy = 5;
    }

    public void feed() {
        hunger = Math.max(0, hunger - 2);
        energy = Math.min(10, energy + 1);
        System.out.println(name + " has been fed!");
    }

    public void play() {
        happiness = Math.min(10, happiness + 2);
        energy = Math.max(0, energy - 2);
        hunger = Math.min(10, hunger + 2);
        System.out.println(name + " played and feels happy!");
    }

    public void sleep() {
        energy = 10;
        hunger = Math.min(10, hunger + 3);
        System.out.println(name + " is sleeping.");
    }

    public void showStatus() {
        System.out.println("\n" + name + " Status:");
        System.out.println("Hunger: " + hunger + "/10");
        System.out.println("Happiness: " + happiness + "/10");
        System.out.println("Energy: " + energy + "/10");
    }

    public abstract void specialAbility();
}

// Dog Class (Child)
class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void specialAbility() {
        System.out.println(name + " is guarding the house!");
    }
}

// Cat Class (Child)
class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void specialAbility() {
        System.out.println(name + " is climbing a tree!");
    }
}

// Hunger System (Multithreading)
class HungerThread extends Thread {
    private Pet pet;

    public HungerThread(Pet pet) {
        this.pet = pet;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(5000); // প্রতি ৫ সেকেন্ড পর Hunger বাড়বে
                pet.hunger = Math.min(10, pet.hunger + 1);
                System.out.println("\n" + pet.name + " is getting hungry...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Random Event System
class RandomEvent {
    public static void triggerEvent(Pet pet) {
        Random random = new Random();
        int eventNumber = random.nextInt(3);

        switch (eventNumber) {
            case 0:
                System.out.println("\nSurprise! " + pet.name + " found a toy and is very happy!");
                pet.happiness = Math.min(10, pet.happiness + 3);
                break;
            case 1:
                System.out.println("\nUh-oh! " + pet.name + " got sick. You need to take care!");
                pet.happiness = Math.max(0, pet.happiness - 2);
                pet.energy = Math.max(0, pet.energy - 2);
                break;
            case 2:
                System.out.println("\n" + pet.name + " met a new friend and is having fun!");
                pet.happiness = Math.min(10, pet.happiness + 2);
                break;
        }
    }
}

// Main Game Class
public class VirtualPetGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a pet (1 - Dog, 2 - Cat): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Pet myPet;
        if (choice == 1) {
            myPet = new Dog("Buddy");
        } else {
            myPet = new Cat("Kitty");
        }

        System.out.println("\nYou adopted " + myPet.name + "! Take good care of it.");

        // Start Hunger System
        HungerThread hungerThread = new HungerThread(myPet);
        hungerThread.start();

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1 - Feed");
            System.out.println("2 - Play");
            System.out.println("3 - Sleep");
            System.out.println("4 - Special Ability");
            System.out.println("5 - Show Status");
            System.out.println("6 - Trigger Random Event");
            System.out.println("7 - Exit");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    myPet.feed();
                    break;
                case 2:
                    myPet.play();
                    break;
                case 3:
                    myPet.sleep();
                    break;
                case 4:
                    myPet.specialAbility();
                    break;
                case 5:
                    myPet.showStatus();
                    break;
                case 6:
                    RandomEvent.triggerEvent(myPet);
                    break;
                case 7:
                    System.out.println("Goodbye! Take care of your pet!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
