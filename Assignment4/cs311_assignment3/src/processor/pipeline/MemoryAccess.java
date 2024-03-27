package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	OF_EX_LatchType OF_EX_Latch;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType if_Enable_latch, OF_EX_LatchType oF_eX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch =if_Enable_latch;
		this.OF_EX_Latch=oF_eX_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
			//TODO
			System.out.println("MA Stage:");
			if(EX_MA_Latch.getisNOP()==true) {
				EX_MA_Latch.setisNOP(false);
				MA_RW_Latch.setisNOP(true);
				EX_MA_Latch.setInstruction(null);
				return;
			}
			Instruction inst=EX_MA_Latch.instruction;
			EX_MA_Latch.setInstruction(null);
			MA_RW_Latch.setInstruction(inst);
			if(inst==null) {
				return;
			}
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
			IF_EnableLatch.setIF_enable(true);
			if(op_type==all_operations[29]) {
				OF_EX_Latch.setEX_enable(false);
				IF_EnableLatch.setIF_enable(false);
			}
		}
	}

}
