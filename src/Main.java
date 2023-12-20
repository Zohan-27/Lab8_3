import java.io.*;
import java.util.Scanner;

interface Displayable {
    void displayInfo();
}

interface DetailsProvider<T> {
    T getDetails();
}

abstract class Visualization<T extends DetailsProvider<?>> implements Displayable {
    private int width;
    protected int height;
    private String backgroundColor;
    public boolean isInteractive;

    public Visualization(int width, int height, String backgroundColor, boolean isInteractive) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.isInteractive = isInteractive;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public abstract void displayInfo();

    public abstract T getDetails();

    public abstract String getDataForFile();
}

class VisualizationFrameDetails implements DetailsProvider<String> {
    private String frameType;
    private boolean isResizable;
    private int zIndex;

    public VisualizationFrameDetails(String frameType, boolean isResizable, int zIndex) {
        this.frameType = frameType;
        this.isResizable = isResizable;
        this.zIndex = zIndex;
    }

    @Override
    public String getDetails() {
        return "Frame Type: " + frameType + ", Resizable?: " + isResizable + ", Z-Index: " + zIndex;
    }
}

class VisualizationFrame extends Visualization<VisualizationFrameDetails> {
    private VisualizationFrameDetails details;

    public VisualizationFrame(int width, int height, String backgroundColor, boolean isInteractive,
                              VisualizationFrameDetails details) {
        super(width, height, backgroundColor, isInteractive);
        this.details = details;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationFrame Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + getBackgroundColor());
        System.out.println("Interactive?: " + isInteractive);
        System.out.println(getDetails().getDetails());
    }

    @Override
    public VisualizationFrameDetails getDetails() {
        return details;
    }

    @Override
    public String getDataForFile() {
        return "VisualizationFrame:" + " Width:" + getWidth() + ";" +" Height:" + height + ";" + " Background color:" + getBackgroundColor() + ";" + " IsInteractive:" +
                isInteractive + "; " + details.getDetails();
    }
}

class VisualizationLayerDetails implements DetailsProvider<Integer> {
    private String layerName;
    private int opacity;
    private boolean isVisible;

    public String getLayerName() {
        return layerName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public VisualizationLayerDetails(String layerName, int opacity, boolean isVisible) {
        this.layerName = layerName;
        this.opacity = opacity;
        this.isVisible = isVisible;
    }

    @Override
    public Integer getDetails() {
        return opacity;
    }
}

class VisualizationLayer extends Visualization<VisualizationLayerDetails> {
    private VisualizationLayerDetails details;

    public VisualizationLayer(int width, int height, String backgroundColor, boolean isInteractive,
                              VisualizationLayerDetails details) {
        super(width, height, backgroundColor, isInteractive);
        this.details = details;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationLayer Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + getBackgroundColor());
        System.out.println("Interactive?: " + isInteractive);
        System.out.println("Layer Name: " + details.getLayerName() + ", Opacity: " + details.getDetails() + ", Visible?: " + details.isVisible());
    }

    @Override
    public VisualizationLayerDetails getDetails() {
        return details;
    }

    @Override
    public String getDataForFile() {
        return "VisualizationLayer:" + " Width:" + getWidth() + ";" + "Height:" + height + ";" + " Background color:" + getBackgroundColor() + ";" + " IsInteractive:" +
                isInteractive + ";" + " LayerName: " + details.getLayerName() + ";" + " Opacity: " + details.getDetails() + ";" + " IsVisible: " + details.isVisible();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueInput = true;

        while (continueInput) {
            System.out.println("Enter 1 for VisualizationFrame, 2 for VisualizationLayer (or any other key to exit):");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                VisualizationFrame frame = createVisualizationFrame(scanner);
                if (frame != null) {
                    saveDataToFile(frame);
                }
            } else if (input.equals("2")) {
                VisualizationLayer layer = createVisualizationLayer(scanner);
                if (layer != null) {
                    saveDataToFile(layer);
                }
            } else {
                continueInput = false;
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String choice = scanner.next();
            continueInput = choice.equalsIgnoreCase("yes");
            scanner.nextLine(); // Read newline character after next()
        }

        readDataFromFile();

        scanner.close();
    }

    private static VisualizationFrame createVisualizationFrame(Scanner scanner) {
        System.out.println("Enter VisualizationFrame details:");
        System.out.print("Width: ");
        int width = scanner.nextInt();
        System.out.print("Height: ");
        int height = scanner.nextInt();
        scanner.nextLine(); // Read newline character after nextInt()
        System.out.print("Background Color: ");
        String backgroundColor = scanner.nextLine();
        System.out.print("Interactive visualization (true/false): ");
        boolean isInteractive = scanner.nextBoolean();
        scanner.nextLine(); // Read newline character after nextBoolean()
        System.out.print("Frame Type: ");
        String frameType = scanner.nextLine();
        System.out.print("Resizable (true/false): ");
        boolean isResizable = scanner.nextBoolean();
        System.out.print("Z-Index: ");
        int zIndex = scanner.nextInt();
        scanner.nextLine(); // Read newline character after nextInt()

        VisualizationFrameDetails details = new VisualizationFrameDetails(frameType, isResizable, zIndex);
        return new VisualizationFrame(width, height, backgroundColor, isInteractive, details);
    }

    private static VisualizationLayer createVisualizationLayer(Scanner scanner) {
        System.out.println("Enter VisualizationLayer details:");
        System.out.print("Width: ");
        int width = scanner.nextInt();
        System.out.print("Height: ");
        int height = scanner.nextInt();
        scanner.nextLine(); // Read newline character after nextInt()
        System.out.print("Background Color: ");
        String backgroundColor = scanner.nextLine();
        System.out.print("Interactive visualization (true/false): ");
        boolean isInteractive = scanner.nextBoolean();
        scanner.nextLine(); // Read newline character after nextBoolean()
        System.out.print("Layer Name: ");
        String layerName = scanner.nextLine();
        System.out.print("Opacity: ");
        int opacity = scanner.nextInt();
        scanner.nextLine(); // Read newline character after nextInt()
        System.out.print("Visible (true/false): ");
        boolean isVisible = scanner.nextBoolean();
        scanner.nextLine(); // Read newline character after nextBoolean()

        VisualizationLayerDetails details = new VisualizationLayerDetails(layerName, opacity, isVisible);
        return new VisualizationLayer(width, height, backgroundColor, isInteractive, details);
    }

    private static void saveDataToFile(Visualization<?> visualization) {
        try (FileWriter writer = new FileWriter("visualizations.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            String data = visualization.getDataForFile();
            bw.write(data);
            bw.newLine();
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    private static void readDataFromFile() {
        try (Scanner fileScanner = new Scanner(new File("visualizations.txt"))) {
            System.out.println("Reading data from file:");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
