package generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

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
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
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
