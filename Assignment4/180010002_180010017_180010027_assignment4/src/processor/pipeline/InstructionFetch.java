package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor,
							IF_EnableLatchType iF_EnableLatch,
							IF_OF_LatchType iF_OF_Latch,
							EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		//String output = containingProcessor.getMainMemory().getContentsAsString(0, 15);
        //System.out.println(output);
		if(IF_EnableLatch.isIF_enable())
		{
			if(EX_IF_Latch.getIS_enable())
			{
				int newPC = EX_IF_Latch.getPC();
//				System.out.println("Ex to if is executed.");
				containingProcessor.getRegisterFile().setProgramCounter(newPC);
				EX_IF_Latch.setIS_enable(false);
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			System.out.println("IF is enabled with instruction: " + Integer.toBinaryString(newInstruction) + "..");
			System.out.println("currentPC " + Integer.toString(currentPC));
			IF_OF_Latch.setInstruction(newInstruction);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
//			System.out.println(Integer.toBinaryString(newInstruction).substring(0, 5) + "::::::::::::::");
			
//			String end_program = Integer.toBinaryString(newInstruction).substring(0, 5);
			
//			String instruction = Integer.toBinaryString(newInstruction);
//			System.out.println("IF : " + instruction + ".");
//			while (instruction.length() != 32) {
//				instruction = "0" + instruction;
//			}
//			String opcode = instruction.substring(0, 5);
//			if (opcode == "11101") {
//				System.out.println("End program on end.");
//				Simulator.setSimulationComplete(true);
//			}

//			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
		}
	}

}
