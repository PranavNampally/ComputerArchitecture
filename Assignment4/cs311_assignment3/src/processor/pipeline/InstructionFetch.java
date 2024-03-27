package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		System.out.println("IF Stage:");
		
		if(IF_EnableLatch.isIF_enable())
		{	
			int newInstruction =-1;
			if(EX_IF_Latch.getIsBranchTaken()){
				containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getbranchTarget());
				int currentPC=containingProcessor.getRegisterFile().getProgramCounter();
				newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
				EX_IF_Latch.setIsBranchTarget(false);
				currentPC+=1;
				containingProcessor.getRegisterFile().setProgramCounter(currentPC);
				System.out.println("Branch: "+currentPC+" "+newInstruction);
			}
			else{
				int currentPC=containingProcessor.getRegisterFile().getProgramCounter();
				newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
				currentPC+=1;
				containingProcessor.getRegisterFile().setProgramCounter(currentPC);
				System.out.println("Branch: "+currentPC+" "+newInstruction);
			}
			
			
			// System.out.println(currentPC+" "+newInstruction);
//			System.out.println(containingProcessor.getMainMemory().getContentsAsString(0, 100));
			IF_OF_Latch.setInstruction(newInstruction);
			
			IF_OF_Latch.setOF_enable(true);
		}
	}

}
