package generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;
import processor.pipeline.MA_RW_LatchType;

// import generic.Statistics;

public class Simulator {
      
   static Processor processor;
   static boolean simulationComplete;
   // static Statistics statistics;
   public static void setupSimulation(String assemblyProgramFile, Processor p)
   {
      Simulator.processor = p;
      try {
         loadProgram(assemblyProgramFile);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      simulationComplete = false;
   }
   
   
   static void loadProgram(String assemblyProgramFile) throws IOException
   {
      /*
       * TODO
       * 1. load the program into memory according to the program layout described
       *    in the ISA specification
       * 2. set PC to the address of the first instruction in the main
       * 3. set the following registers:
       *     x0 = 0
       *     x1 = 65535
       *     x2 = 65535
       */
      InputStream inp_st = null;
      try {

         inp_st = new FileInputStream(assemblyProgramFile);
      }
      catch (FileNotFoundException err) {

         err.printStackTrace();
      }
      
      
      DataInputStream dis  = new DataInputStream(inp_st);
      
      

      int addr = -1;
      while(dis.available() > 0) {
         int next_inst = dis.readInt();
         if(addr==-1) {
            processor.getRegisterFile().setProgramCounter(next_inst);
         }
         else {
            processor.getMainMemory().setWord(addr, next_inst);
         }

         addr += 1;
      }
      
      processor.getRegisterFile().setValue(0, 0);
        processor.getRegisterFile().setValue(1, 65535);
        processor.getRegisterFile().setValue(2, 65535);
        
        System.out.println(processor.getMainMemory().getContentsAsString(0, 25));
   }
        
   
   
   public static void simulate()
   {
      // statistics.setNumberOfCycles(0);
      // statistics.setNumberOfInstructions(0);
      int i=0;
      while(simulationComplete == false)
      
      {
         System.out.println("--------------cycle number"+(Statistics.getNumberOfCycles()+1));
         System.out.println("--------------rw ENABLE: "+processor.getMA_RW().isRW_enable()+" Instruction: "+processor.getMA_RW().getInstruction()+" ALu result: "+processor.getMA_RW().getAluResult()+" Ld result: "+processor.getMA_RW().getldResult()+" isNOP: "+processor.getMA_RW().getisNOP());
         processor.getRWUnit().performRW();
         Clock.incrementClock();
         System.out.println("--------------ma Enable: "+processor.getEX_MA().isMA_enable()+" Instruction: "+processor.getEX_MA().getInstruction()+" Alu result: "+processor.getEX_MA().getAluResult()+" Op2: "+processor.getEX_MA().getOp2()+" isNOP: "+processor.getEX_MA().getisNOP());
         processor.getMAUnit().performMA();
         Clock.incrementClock();
         System.out.println("--------------ex enable: "+processor.getOF_EX().isEX_enable()+" Instruction: "+processor.getOF_EX().getInstruction()+" Op1: "+processor.getOF_EX().getOp1()+" Op2: "+processor.getOF_EX().getOp2()+" Imm: "+processor.getOF_EX().getImm()+" Branch Target: "+processor.getOF_EX().getbranchTarget()+" isNOP: "+processor.getOF_EX().getisNOP());
         processor.getEXUnit().performEX();
         Clock.incrementClock();
         System.out.println("--------------of enable: "+processor.getIF_OF().isOF_enable()+" Instruction: "+processor.getIF_OF().getInstruction());
         processor.getOFUnit().performOF();
         Clock.incrementClock();
         System.out.println("--------------ex: if Enable: "+processor.getEX_IF().isIF_enable()+" Branch Target: "+processor.getEX_IF().getbranchTarget()+" Is branch Taken: "+processor.getEX_IF().getIsBranchTaken()+" IF Enable: "+processor.getIF_enable().isIF_enable());
         processor.getIFUnit().performIF();
         Clock.incrementClock();
         i++;
         
         Statistics.setNumberOfInstructions(Statistics.getNumberofInstructions()+1);
         Statistics.setNumberOfCycles(Statistics.getNumberOfCycles()+1);
      }
      
      // TODO
      // set statistics
      
   }
   
   public static void setSimulationComplete(boolean value)
   {
      simulationComplete = value;
   }
}