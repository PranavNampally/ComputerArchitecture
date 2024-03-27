package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
			//TODO
			System.out.println("MA Stage:");
			Instruction inst=EX_MA_Latch.instruction;
			MA_RW_Latch.setInstruction(inst);
			OperationType op_type=inst.getOperationType();
			OperationType[] all_operations= OperationType.values();

			int alu_addr=EX_MA_Latch.getAluResult();
			int op2_val=EX_MA_Latch.getOp2();
			int lr=0;
			if(op_type==all_operations[22]){//ld
				lr=containingProcessor.getMainMemory().getWord(alu_addr);
				MA_RW_Latch.setldResult(lr);
			}
			else if(op_type==all_operations[23]){//st
				containingProcessor.getMainMemory().setWord(alu_addr, op2_val);
				System.out.print("Updated Memory:"+alu_addr+":"+containingProcessor.getMainMemory().getWord(alu_addr)+" ");
			}
			MA_RW_Latch.setAluResult(alu_addr);
			System.out.println(MA_RW_Latch.getAluResult()+" "+MA_RW_Latch.getldResult()+" "+MA_RW_Latch.getInstruction());
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
		}
	}

}
