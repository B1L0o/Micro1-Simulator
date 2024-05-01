package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class Micro1Viewer extends JPanel implements ActionListener
{
    private JTextField[] registerFields;
    private JTextField loadInput;
    private JTextField loadAssemblyInput;
    private JTextField stepInput;
    
    private JButton stepButton;
    private JButton loadButton;
    private JButton loadAssemblyButton;
    private JButton memoryButton;
    private JButton quitButton;

    private Memory memory;
    private Processor cpu;

    public Micro1Viewer() {

        memory = new Memory(256);
        cpu = new Processor();
        cpu.setMemory(memory);
        
        
        registerFields = new JTextField[8];
        for (int i = 0; i < 8; i++) {
            registerFields[i] = new JTextField("0");
            registerFields[i].setEditable(false);
            registerFields[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }
        
        loadInput = new JTextField("Not Selected");
        loadInput.setEditable(false);
        loadAssemblyInput = new JTextField("Not Selected");
        loadAssemblyInput.setEditable(false);
        stepInput = new JTextField("1");
        stepInput.setEditable(true);
        stepInput.setHorizontalAlignment(SwingConstants.RIGHT);
        loadAssemblyInput.setHorizontalAlignment(SwingConstants.RIGHT);
        loadInput.setHorizontalAlignment(SwingConstants.RIGHT);

        loadAssemblyButton = new JButton("Load Assembly");
        stepButton = new JButton("Step");
        loadButton = new JButton("Load");
        memoryButton = new JButton("Memory");
        quitButton = new JButton("Quit");

        setLayout(new GridLayout(15, 0));
        
        add(new JLabel("   Register 1:"));
        add(registerFields[0]);
        add(new JLabel("   Register 2:"));
        add(registerFields[1]);
        add(new JLabel("   Register 3:"));
        add(registerFields[2]);
        add(new JLabel("   Register 4:"));
        add(registerFields[3]);
        add(new JLabel("   Register 5:"));
        add(registerFields[4]);
        add(new JLabel("   Register 6:"));
        add(registerFields[5]);
        add(new JLabel("   Register 7:"));
        add(registerFields[6]);
        add(new JLabel("   Register 8:"));
        add(registerFields[7]);
        
        
        add(loadButton);
        add(loadInput);
        add(loadAssemblyButton);
        add(loadAssemblyInput);
        add(stepButton);
        add(stepInput);
        add(memoryButton);
        add(quitButton);

        loadButton.addActionListener(this);
        loadAssemblyButton.addActionListener(this);
        stepButton.addActionListener(this);
        memoryButton.addActionListener(this);
        quitButton.addActionListener(this);

        
    }

    public void updateRegisters() {
        for (int i = 0; i < 8; i++) {
            registerFields[i].setText(String.valueOf(cpu.getRegister()[i]));
        }
    }
    
    public void loadAssembly(File f) {
        try {

            String[] instr =
                    { "halt", "load","loadc","store","add","mul","sub","div","and","or","not","lshift","rshift","bwc","bwd","if"};
            
            List<String> instructions = Arrays.asList(instr);
            
            Scanner scan  = new Scanner(f);
            String instruction;
            int address = 0;
            while(scan.hasNext())
            {
                String line = scan.nextLine();
                String[] splitedLine = line.split("\\s+");
                if (splitedLine.length == 1) {
                    splitedLine[0] = (splitedLine[0].equals("halt")) ? "0" : splitedLine[0];
                    instruction = Integer.toString(Integer.parseInt(splitedLine[0]),16);
                }
                else
                {
                    String a = splitedLine[1];
                    String b = (splitedLine.length == 2 ) ? "0" :splitedLine[2];
                    if (splitedLine[0].equals( "if")) 
                    {
                        instruction = "f" + a + b; 
                    }
                    else
                    {
                        instruction = Integer.toString(instructions.indexOf(splitedLine[0]), 16) + a + b;
                    }				
                }
                memory.write(address++, Integer.parseInt(instruction,16));
            }
            cpu.setPC(0);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void load(File f) {
        try {
            Scanner scan  = new Scanner(f);
            int address = 0;
            while(scan.hasNext()) {
                memory.write(address++, scan.nextInt(16));
            }
            cpu.setPC(0);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    
    public void actionPerformed(ActionEvent evt) 
    {
        updateRegisters();
        if (evt.getSource() == memoryButton) 
        {
            String res = memory.dump();

            JFrame tst = new JFrame("Memory Display");
            tst.setSize(200, 150); 
            tst.setLocationRelativeTo(null); 
            tst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea textArea = new JTextArea(res);
            textArea.setEditable(false);

            textArea.setMargin(new Insets(4, 4, 4, 4));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            tst.add(scrollPane);
            tst.setVisible(true);

        } 
        else if (evt.getSource() == loadButton) 
        {
            JFileChooser chooser = new JFileChooser();
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                load(selectedFile);
                loadInput.setText(selectedFile.toString());
                loadAssemblyInput.setText("Not Selected");
                cpu.resetRegister();
                updateRegisters();
            }
        }
        else if (evt.getSource() == loadAssemblyButton) 
        {
            JFileChooser chooser = new JFileChooser();
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                loadAssembly(selectedFile);
                loadAssemblyInput.setText(selectedFile.toString());
                loadInput.setText("Not Selected");
                cpu.resetRegister();
                updateRegisters();
            }
        }
        else if (evt.getSource() == stepButton) 
        {
            String textFieldValue = stepInput.getText();
            int num = Integer.parseInt(textFieldValue,10);
            boolean halt = false;
            for (int i = 0; i < num && !halt; i++) 
            {
                if (!halt)
                    halt = cpu.step();
                if (halt) {
                    System.out.println("program terminated");
                    break;
                }
                updateRegisters();
            }
        }
        else if (evt.getSource() == quitButton) 
        {
            System.out.println("bye");
            System.exit(0);
        }
        
    }
}
