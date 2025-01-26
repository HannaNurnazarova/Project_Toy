import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyRaffle {
    private PriorityQueue<Toy> toyQueue;
    private Random random;

    public ToyRaffle() {
        toyQueue = new PriorityQueue<>((t1, t2) -> t2.weight - t1.weight);
        random = new Random();
    }

    public void addToy(String id, String name, int weight) {
        Toy toy = new Toy(id, name, weight);
        toyQueue.add(toy);
    }

    public String getToy() {
        int totalWeight = toyQueue.stream().mapToInt(t -> t.weight).sum();
        int randomNumber = random.nextInt(totalWeight) + 1;

        int cumulativeWeight = 0;
        for (Toy toy : toyQueue) {
            cumulativeWeight += toy.weight;
            if (randomNumber <= cumulativeWeight) {
                return toy.id;
            }
        }
        return null;
    }

    public void raffleToys(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < 10; i++) {
                String toyId = getToy();
                if (toyId != null) {
                    writer.write(toyId + "\\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Toy {
        String id;
        int weight;

        public Toy(String id, String name, int weight) {
            this.id = id;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        ToyRaffle raffle = new ToyRaffle();
        raffle.addToy("1", "конструктор", 2);
        raffle.addToy("2", "робот", 2);
        raffle.addToy("3", "кукла", 6);

        raffle.raffleToys("raffle_results.txt");
    }
}
