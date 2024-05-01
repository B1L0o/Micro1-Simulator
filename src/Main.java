package src;

import javax.swing.*;
import java.io.IOException;


public class Main 
{
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Micro-1 Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        int width = 1280; 
        int height = 720; 
        frame.setSize(width, height);

        int xPosition = 100; 
        int yPosition = 100;
        frame.setLocation(xPosition, yPosition);

        frame.getContentPane().add(new Micro1Viewer());
        frame.setVisible(true);
    }
}