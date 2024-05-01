package src;

import java.util.Arrays;
import java.util.List;

public class Memory 
{
    private final int[] mem;

    public int[] getMem() 
    {
        return mem;
    }
    
    public Memory(int cap) {
        mem = new int[cap];
    }

    public int read(int address, Processor processor)
    {
        processor.setIR(mem[address]);
        
        if (processor.getIR() == 0)
            return 0;
        String currentVal = Integer.toString(Integer.parseInt(Integer.toString(processor.getIR()), 10), 16);
        int len = currentVal.length();
        if (len != 3)
            return 0;
        String[] words = currentVal.split("");
        
        String[] index = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        List<String> indx = Arrays.asList(index);
        
        int a = indx.indexOf(words[1]);
        int b = indx.indexOf(words[2]);
        int p = indx.indexOf(words[0]);
        switch (p) {
            case 1:
                processor.load(a, b);
                break;
            case 2:
                processor.loadc(a);
                break;
            case 3:
                processor.store(a, b);
                break;
            case 4:
                processor.add(a, b);
                break;
            case 5:
                processor.mul(a, b);
                break;
            case 6:
                processor.sub(a, b);
                break;
            case 7:
                processor.div(a, b);
                break;
            case 8:
                processor.and(a, b);
                break;
            case 9:
                processor.or(a, b);
                break;
            case 10:
                processor.not(a, b);
                break;
            case 11:
                processor.lshift(a, b);
                break;
            case 12:
                processor.rshift(a, b);
                break;
            case 13:
                processor.bwc(a, b);
                break;
            case 14:
                processor.bwd(a, b);
                break;
            case 15:
                processor.IF(a, b);
                break;
        }
        return 1;
    }

    public void write(int address, int value) {
        mem[address] = value;
    }

    public String dump() {
        for (int i = 0; i < mem.length; i++) {
            System.out.printf("%02X: %02X\n", i, mem[i]);
        }
        String res ="";
        for (int i = 0; i < mem.length; i++) 
        {

            res += String.format("%02X:              %08X%n", i, mem[i]) + "\n";
        }
        return res;
    }
    
}
