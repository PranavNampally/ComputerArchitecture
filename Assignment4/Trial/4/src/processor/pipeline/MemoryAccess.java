package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performMA()
	{
		System.out.println("MA Stage:");
		if(EX_MA_Latch.isMA_enable()){
			System.out.println("MA Enabled");
			boolean isNOP=EX_MA_Latch.getisNOP();
			if(isNOP==true){		//if it is a bubble then clear all the values in MA_RW_Latch
				System.out.println("NOP instruction");
				MA_RW_Latch.setisNOP(false);
				MA_RW_Latch.setInstruction(null);
				MA_RW_Latch.setAluResult(-1);
				MA_RW_Latch.setldResult(-1);
				EX_MA_Latch.setisNOP(false);
				return;
			}

			//TODO
			
			Instruction inst=EX_MA_Latch.instruction;
			MA_RW_Latch.setInstruction(inst);
			if(inst==null){
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

			IF_EnableLatch.setIF_enable(true);
			if(op_type==all_operations[29]){
				//set instruction as true only if the current inst is not end
				IF_EnableLatch.setIF_enable(false);
			}
			MA_RW_Latch.setRW_enable(true);
			//clearing EX_MA_latch
			EX_MA_Latch.setInstruction(null);
		}else{
			System.out.println("MA Disabled");
		}
		
	}

}
