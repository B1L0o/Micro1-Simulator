package src;


public class Processor 
{

    private int PC;
    private int IR;
    private int[] reg;
    private Memory mem;

    public Processor() 
    {
        reg = new int[8];
        PC = 0;
        IR = 0;
    }
    
    public int[] getRegister()
    {
        return reg;
    }
    
    public void resetRegister()
    {
        for (int i = 0; i< 8; i++)
            reg[i] = 0;
    }

    public void setIR(int ir) 
    {
        IR = ir;
    }
    
    public int getIR()
    {
        return IR;
    }

    public void setMemory(Memory mem)
    {
        this.mem = mem;
    }

    public void setPC(int mem)
    {
        PC = mem;
    }

    public boolean step() 
    {
        if (mem.read(PC, this) == 0)
            return true;
        PC++;
        return false;
    }
    
    
    
    public void load(int a, int b) 
    { 
        reg[a] = mem.getMem()[reg[b]];
    }

    public void loadc(int a) 
    {
        PC ++;
        reg[a] = mem.getMem()[PC];
    }

    public void store(int a, int b) 
    {
        mem.getMem()[reg[a]] = reg[b];
    }
    public void add(int a, int b) 
    {
        reg[a] = reg[a] + reg[b];
    }

    public void mul(int a, int b) 
    {
        reg[a] = reg[a] * reg[b];
    }

    public void sub(int a, int b) 
    {
        reg[a] = reg[a] - reg[b];
    }

    public void div(int a, int b) 
    { 
        reg[a] = reg[a] / reg[b];
    }

    public void and(int a, int b) 
    { 
        if (reg[a]!= 0&&reg[b]!=0) 
            reg[a]=1;
        else reg[a]=0;
    }

    public void or(int a, int b) 
    { 
        if (reg[a]!=0||reg[b]!=0) 
            reg[a]=1;
        else 
            reg[a]=0;
    }

    public void not(int a, int b) 
    { 
        if (reg[b]!=0) 
            reg[a]=0;
        else 
            reg[a]=1;
    }

    public void lshift(int a, int b) 
    {
        reg[a] = reg[b] << 1;
    }

    public void rshift(int a, int b) 
    {
        reg[a] = reg[b] >> 1;
    }

    public void bwc(int a, int b) 
    { 
        reg[a] = reg[a] & reg[b];
    }

    public void bwd(int a, int b) 
    { 
        reg[a] = reg[a] | reg[b];
    }
    
    public void IF(int a, int b)
    {
        if (reg[a] != 0) 
            setPC(reg[b]);
    }

}
